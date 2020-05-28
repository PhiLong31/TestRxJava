package com.example.testrxjava;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface APIService {
    @GET("/posts")
    Observable<ArrayList<Post>> getPosts();

    @GET("/posts/{id}/comments")
    Observable<ArrayList<Comment>> getCommnets(
            @Field("id") int id
    );
}
