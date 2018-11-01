package com.loving.store.biz;

public class HttpFactory {
    public static <T extends Factory> T create(Class<T> c) {
        Factory factory = null;
        try {
            factory = (Factory) Class.forName(c.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) factory;
    }
}