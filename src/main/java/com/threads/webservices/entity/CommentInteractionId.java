package com.threads.webservices.entity;

import java.io.Serializable;
import java.util.Objects;

public class CommentInteractionId implements Serializable {
    private String user;
    private String comment;

    public CommentInteractionId(){}

    public CommentInteractionId(String userId, String commentId) {
        this.user = userId;
        this.comment = commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentInteractionId that = (CommentInteractionId) o;
        return Objects.equals(user, that.user) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, comment);
    }
}
