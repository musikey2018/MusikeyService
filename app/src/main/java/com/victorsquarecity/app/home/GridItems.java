package com.victorsquarecity.app.home;

/**
 * Created by app on 6/8/2017.
 */

public class GridItems {

    public int id;
    public String title;

    public GridItems() {
    }

    public GridItems(int id, String title) {
        this.id = id;
        this.title = title;
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

    @Override
    public String toString() {
        return "GridItems{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
