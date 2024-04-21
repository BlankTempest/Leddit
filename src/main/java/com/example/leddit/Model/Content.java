package com.example.leddit.Model;

import java.time.LocalDateTime;

public interface Content {
    Long getId();
    String getContent();
    void setContent(String content);
    User getAuthor();
    void setAuthor(User author);
    LocalDateTime getCreatedDate();
    void setCreatedDate(LocalDateTime createdDate);
}
