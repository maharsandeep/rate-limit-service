package org.ratelimit.ratelimitservice.model;

import java.util.concurrent.locks.ReentrantLock;

public class ClientTokenBucket {

    private long availableTokens;
    private long maxTokens;

    private final ReentrantLock lock = new ReentrantLock();

    public ClientTokenBucket(long maxTokens) {
        this.maxTokens = maxTokens;
        this.availableTokens = maxTokens;
    }

    public boolean isAllowed(){
        lock.lock();
        try {
            if (availableTokens > 0) {
                availableTokens--;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public void refreshLimit(long tokenLimit){
        lock.lock();
        try {
            maxTokens = tokenLimit;
            refillTokens();
        } finally {
            lock.unlock();
        }
    }

    public void refillTokens(){
        lock.lock();
        try {
            // Refresh the limit to the max tokens
            availableTokens = maxTokens;
        } finally {
            lock.unlock();
        }
    }
}
