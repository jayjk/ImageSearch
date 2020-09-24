package com.test.imagesearch.models;

import androidx.room.ColumnInfo;

public class Images {

    private String type;

    private String link;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}