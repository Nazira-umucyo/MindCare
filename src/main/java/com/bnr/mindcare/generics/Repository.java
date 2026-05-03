package com.bnr.mindcare.generics;

import java.util.ArrayList;
import java.util.List;

public class Repository<T> {

    private List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public void remove(T item) {
        items.remove(item);
    }

    public List<T> getAll() {
        return items;
    }

    public int count() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}