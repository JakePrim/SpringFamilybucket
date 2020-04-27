package com.prim.mall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TokenCache {
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    private static LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
            .initialCapacity(1000) //初始化1000个缓存空间
            .maximumSize(10000) //最大缓存空间10000个
            .expireAfterAccess(12, TimeUnit.HOURS) //缓存12个小时
            .build(new CacheLoader<String, String>() {
                //默认的数据加载实现 如果key没有对应的值，就会调用这个方法
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setCache(String key, String value) {
        loadingCache.put(key, value);
    }

    public static String getCache(String key) {
        String value = null;
        try {
            value = loadingCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
