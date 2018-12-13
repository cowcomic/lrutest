package com.smx.test.lru;

import java.util.HashMap;
import java.util.Map;

public class DoubleLinked implements LRUInterface {

    public DoubleLinked(int limit) {
        setLimit(limit);
        head = new Node();
        tail = new Node();
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void print() {
        StringBuffer sb = new StringBuffer();
        sb.append(length).append("=").append(cache.size()).append(" ");
        sb.append("[");
        for (String key : cache.keySet()) {
            sb.append(key).append(",");
        }
        sb.append("]       ");
        Node one = head;
        while (true) {
            one = one.next;
            if (one == null || one == tail) {
                break;
            }
            sb.append(one.key).append("(").append(one.value).append(") ");
        }
        System.out.println(sb.toString());
    }

    class Node {
        String key;
        Object value;
        Node last;
        Node next;
    }

    // head和tail只考虑next，因为双向链表对于他们来说都是同一个，这样考虑比较简单
    // 更好的方案是把这两个用单独的类来实现，继承自Node，next和last都做成方法，重载的时候自己在对象内容做特殊性
    private Node head, tail;
    private Map<String, Node> cache = new HashMap<String, Node>();
    private int limit, length = 0;


    public void add(String key, Object value) {
        Node one = cache.get(key);
        if (one != null) {
            // 如果缓存中存在，把他从当时的位置交换出来，放到最前面
            one.last.next = one.next;
            if (one.next == tail) {
                one.next.next = one.last;
            } else {
                one.next.last = one.last;
            }
            one.last = head;
            one.next = head.next;
            one.next.last = one;
            head.next = one;
        } else {
            if (length < limit) {
                // 如果是新的，而且长度还没超过限制，直接在最前面增加
                one = new Node();
                one.key = key;
                one.value = value;
                if (head.next == null) {
                    // 第一个的时候
                    one.last = head;
                    one.next = tail;
                    head.next = one;
                    tail.next = one;
                } else {
                    one.last = head;
                    one.next = head.next;
                    one.next.last = one;
                    head.next = one;
                }
                cache.put(key, one);
                length++;
            } else {
                // 如果是新的，而且长度超过了限制，需要把最后一个删掉，把新的加在最前面

                // 最后一个的关系删除
                Node remove = tail.next;
                tail.next = remove.last;
                remove.last.next = tail;

                one = new Node();
                one.key = key;
                one.value = value;
                one.last = head;
                one.next = head.next;
                one.next.last = one;
                head.next = one;
                cache.put(key, one);
                // 最后一个从缓存中删除
                cache.remove(remove.key);
            }
        }
    }

    public String get(String key) {
        return cache.containsKey(key) ? cache.get(key).value.toString() : null;
    }
}
