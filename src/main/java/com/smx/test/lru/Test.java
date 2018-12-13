package com.smx.test.lru;

public class Test {
    public static void main(String[] args) {
        DoubleLinked dbl = new DoubleLinked(4);
        dbl.add("key1", "abc");
        dbl.print();
        dbl.add("key2", "abd");
        dbl.print();
        dbl.add("key1", "abc");
        dbl.print();
        dbl.add("key3", "abe");
        dbl.print();
        dbl.add("key4", "abf");
        dbl.print();
        dbl.add("key5", "abg");
        dbl.print();
        dbl.add("key3", "abe");
        dbl.print();
        dbl.add("key6", "abh");
        dbl.print();
        dbl.add("key1", "abc");
        dbl.print();
        dbl.add("key7", "abi");
        dbl.print();


        System.out.println("测试100长度，5000个key以内，100W次");
        long start = System.currentTimeMillis();
        dbl = new DoubleLinked(100);
        for (int i = 1; i < 1000000; i++) {
            double random = Math.random() * 5000;
            String key = "key" + (int) random;
            dbl.add(key, key);
        }
        System.out.println("花费时间：" + (System.currentTimeMillis() - start));
        dbl.print();
    }
}
