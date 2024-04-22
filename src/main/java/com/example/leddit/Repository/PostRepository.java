package com.example.leddit.Repository;

import com.example.leddit.Model.Post;
import com.example.leddit.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findBySubredditId(Long subredditId);
    List<Post> findByAuthorId(Long authorId);
    Optional<Post> findByIdAndAuthorId(Long id, Long authorId);
    Optional<Post> findByIdAndSubredditId(Long id, Long subredditId);
    void deleteByIdAndSubredditId(Long id, Long subredditId);
    List<Post> findByAuthor(User author);

}
