package com.example.testrxjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Post> posts = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(this.posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(ArrayList<Post> posts){
        this.posts = posts;
        notifyDataSetChanged();
    }

    public void updatePosts(Post post){
        this.posts.set(this.posts.indexOf(post), post);
        notifyItemChanged(this.posts.indexOf(post));
    }

    public ArrayList<Post> getPosts(){
        return posts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, numComments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            numComments = itemView.findViewById(R.id.numComments);
        }

        public void bind(Post post) {
            title.setText(post.getTitle());

            if(post.getCommentArrayList() == null){
                numComments.setText("");
            } else {
                numComments.setText(String.valueOf(post.getCommentArrayList().size()));
            }
        }
    }
}
