package com.smx.test.lru;

public interface LRUInterface {
    public void add(String key, Object value);
    public String get(String key);
    public void setLimit(int limit);
    public void print();
}
