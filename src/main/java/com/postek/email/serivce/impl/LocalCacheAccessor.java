package com.postek.email.serivce.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.postek.email.serivce.protocol.ICacheAccessor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @program: email
 * @description:
 * @author: Alleindrach@gmail.com
 * @create: 2019-07-25 16:14
 **/
@Service
public class LocalCacheAccessor implements ICacheAccessor{
    LoadingCache<String, String> localcache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
//            .removalListener(MY_LISTENER)
            .build(
                    new CacheLoader<String, String>() {
                        public String load(String key) throws Exception {
                            return null;
                        }
                    });
    @Override
    public Boolean put(String key, String value, Long expire) {
        localcache.put(key,value);
        return true;
    }

    @Override
    public Boolean put(String key, String value, Long expire, TimeUnit tu) {
        localcache.put(key,value);
        return true;
    }

    @Override
    public Long inc(String key) {
        return inc(key,1L);
    }

    @Override
    public Long inc(String key, Long step) {
        try {
            String value=localcache.get(key);
            Long valueOfLong=Long.parseLong(value);
            valueOfLong+=step;
            localcache.put(key,valueOfLong.toString());
            return valueOfLong;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean expire(String key, Long expire) {

        return null;
    }

    @Override
    public Boolean expire(String key, Long expire, TimeUnit tu) {
        return null;
    }

    @Override
    public String get(String key) {
        try {
            return localcache.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long getLong(String key) {
        try {
            String value=localcache.get(key);
            Long valueOfLong=Long.parseLong(value);

            return valueOfLong;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean exists(String key) {
        try {
            if(localcache.get(key)==null)
                return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean del(String key) {
        localcache.invalidate(key);
        return true;
    }
}
