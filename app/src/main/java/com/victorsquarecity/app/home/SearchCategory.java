package com.victorsquarecity.app.home;

/**
 * Created by app on 6/8/2017.
 */

public class SearchCategory {
    private static final String TAG = "SearchCategory";
    int id;
    String  name;

    public SearchCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public SearchCategory(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
