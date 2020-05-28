package com.example.testrxjava;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Post {
    @SerializedName("userId") @Expose
    private int userId;

    @SerializedName("id") @Expose
    private int id;

    @SerializedName("title") @Expose
    private String title;

    @SerializedName("body") @Expose
    private String body;

    private ArrayList<Comment> commentArrayList;

    public Post(int userId, int id, String title, String body, ArrayList<Comment> commentArrayList) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
        this.commentArrayList = commentArrayList;
    }

    public ArrayList<Comment> getCommentArrayList() {
        return commentArrayList;
    }

    public void setCommentArrayList(ArrayList<Comment> commentArrayList) {
        this.commentArrayList = commentArrayList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
