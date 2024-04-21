package com.example.leddit.Model;

import org.hibernate.query.IllegalMutationQueryException;
import java.time.LocalDateTime;

public class ContentFactory {

    public enum ContentType {
        COMMENT, POST
    }

    public static Content createContent(ContentType type, String title, String content, Subreddit subreddit, User author, LocalDateTime createdAt) {
        return new Post(title, content, subreddit, author, createdAt);
    }
    
    public static Content createContent(ContentType type, String title, String content, Post post, User author, LocalDateTime createdAt) {
            return new Comment(content, post, author, createdAt);
    }
}

