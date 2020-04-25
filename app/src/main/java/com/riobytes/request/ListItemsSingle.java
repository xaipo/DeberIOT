package com.riobytes.request;

import java.util.ArrayList;
import java.util.List;

public class ListItemsSingle {
    private static ListItemsSingle single_instance = null;
    public List<ProductItem> listAdd;
    public List<ProductItem> listRemove;

    // private constructor restricted to this class itself
    private ListItemsSingle()
    {
        listAdd = new ArrayList<ProductItem>();
        listRemove = new ArrayList<ProductItem>();
    }

    // static method to create instance of Singleton class
    public static ListItemsSingle getInstance()
    {
        if (single_instance == null)
            single_instance = new ListItemsSingle();

        return single_instance;
    }
}
