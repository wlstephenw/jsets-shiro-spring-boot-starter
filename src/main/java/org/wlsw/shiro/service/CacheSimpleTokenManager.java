package org.wlsw.shiro.service;

import org.apache.shiro.cache.Cache;
import org.wlsw.shiro.token.SimpleToken;

import java.util.UUID;

/**
 * Copyright (c) 2018 energy-blockchain.com. All Rights Reserved.
 *
 * @author: wanglei  Date: 2018/4/12 0012 Time: 22:02
 */

public class CacheSimpleTokenManager implements SimpleTokenManager {

    private Cache<String, SimpleToken> cache;

    public CacheSimpleTokenManager(Cache<String, SimpleToken> cache) {
        this.cache = cache;
    }

    @Override
    public SimpleToken createToken(String userId) {
        String tokenStr = UUID.randomUUID().toString();
        SimpleToken simpleToken = new SimpleToken(userId, tokenStr);
        cache.put(simpleToken.getUserId(), simpleToken);
        return simpleToken;
    }

    @Override
    public SimpleToken getToken(String userId) {
        return cache.get(userId);
    }

    @Override
    public boolean checkToken(SimpleToken model) {
        if (model == null) {
            return false;
        }
        SimpleToken simpleToken = cache.get(model.getUserId());
        return simpleToken != null && simpleToken.getToken().equals(model.getToken());
    }

    @Override
    public void deleteToken(String userId) {
        cache.remove(userId);
    }
}
