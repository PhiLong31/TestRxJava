package com.example.testrxjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable disposables = new CompositeDisposable();
    private Adapter adapter;
    private RecyclerView recyclerView;
    private String BASE_URL = "https://jsonplaceholder.typicode.com";
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = Client.getRetrofitInstance(BASE_URL).create(APIService.class);
        // init recycler view
        adapter = new Adapter();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        getPostObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Post, ObservableSource<Post>>() {
                    @Override
                    public ObservableSource<Post> apply(Post post) throws Throwable {
                        return getCommentObservable(post);
                    }
                })
                .subscribe(new Observer<Post>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Post post) {
                        // Update the post in the list
                        adapter.updatePosts(post);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private Observable<Post> getCommentObservable(final Post post){
        return apiService.getCommnets(post.getId())
                .map(new Function<ArrayList<Comment>, Post>() {
                    @Override
                    public Post apply(ArrayList<Comment> comments) throws Throwable {
                        post.setCommentArrayList(comments);
                        return post;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private Observable<Post> getPostObservable(){
        return apiService.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ArrayList<Post>, ObservableSource<Post>>() { // flatMap is get new Observable
                    @Override
                    public ObservableSource<Post> apply(ArrayList<Post> posts) throws Throwable {
                        adapter.setPosts(posts);
                        return Observable.fromIterable(posts)
                                .subscribeOn(Schedulers.io());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
