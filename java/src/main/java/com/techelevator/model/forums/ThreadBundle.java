package com.techelevator.model.forums;

import java.util.List;

public class ThreadBundle {
    Thread thread;
    List<ThreadBundle> childThreads;

    public ThreadBundle(Thread thread, List<ThreadBundle> childThreads) {
        this.thread = thread;
        this.childThreads = childThreads;
    }

    public ThreadBundle() {
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public List<ThreadBundle> getChildThreads() {
        return childThreads;
    }

    public void setChildThreads(List<ThreadBundle> childThreads) {
        this.childThreads = childThreads;
    }
}
