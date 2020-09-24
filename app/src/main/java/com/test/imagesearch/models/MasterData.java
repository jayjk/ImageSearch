package com.test.imagesearch.models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "masterdata")
public class MasterData {


    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey()
    @NonNull
    private String uniqueID;

    private String title;

    private String cover_width;

    private String cover_height;

    @ColumnInfo(name = "call")
    private String cell;

    private ArrayList<Images> images;


    private String user_comment = "";


    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_width() {
        return cover_width;
    }

    public void setCover_width(String cover_width) {
        this.cover_width = cover_width;
    }

    public String getCover_height() {
        return cover_height;
    }

    public void setCover_height(String cover_height) {
        this.cover_height = cover_height;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public ArrayList<Images> getImages() {
        return images;
    }

    public void setImages(ArrayList<Images> images) {
        this.images = images;
    }
}