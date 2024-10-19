package com.threads.webservices.entity;

import java.io.Serializable;
import java.util.Objects;

public class ThreadInteractionId implements Serializable {
    private String user;
    private String thread;

    // Default constructor
    public ThreadInteractionId() {}

    public ThreadInteractionId(String user, String thread) {
        this.user = user;
        this.thread = thread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThreadInteractionId that = (ThreadInteractionId) o;
        return Objects.equals(user, that.user) && Objects.equals(thread, that.thread);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, thread);
    }
}
