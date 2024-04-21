package com.example.leddit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.leddit.Model.Comment;
import com.example.leddit.Model.Post;
import com.example.leddit.Model.User;
import com.example.leddit.Model.Vote;
import com.example.leddit.Model.VoteType;

import jakarta.transaction.Transactional;
import java.util.*;

@Repository
public interface VotesRepository extends JpaRepository<Vote, Long> {
    
    @Modifying
    @Transactional
    @Query("INSERT INTO Vote(voteType, user, post) VALUES ('UPVOTE', :user, :post)")
    void upvotePost(@Param("user") User user, @Param("post") Post post);

    // @Modifying
    // @Transactional
    // @Query("INSERT INTO Vote(voteType, user, comment) VALUES ('UPVOTE', :user, :comment)")
    // void upvoteComment(@Param("user") User user, @Param("comment") Comment comment);
    
    // Downvoting methods
    @Modifying
    @Transactional
    @Query("INSERT INTO Vote(voteType, user, post) VALUES ('DOWNVOTE', :user, :post)")
    void downvotePost(@Param("user") User user, @Param("post") Post post);

    // @Modifying
    // @Transactional
    // @Query("INSERT INTO Vote(voteType, user, comment) VALUES ('DOWNVOTE', :user, :comment)")
    // void downvoteComment(@Param("user") User user, @Param("comment") Comment comment);

    // // Count votes by postId and voteType
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.post.id = :postId AND v.voteType = 'UPVOTE'")
    int countUpvotesByPostId(Long postId);

    // Count downvotes for a specific post
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.post.id = :postId AND v.voteType = 'DOWNVOTE'")
    int countDownvotesByPostId(Long postId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.post.id = :postId")
    Optional<Vote> findByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);

    void delete(Vote vote);
    // // Upvoting methods
    // void upvotePost(User user, Post post);
    // void upvoteComment(User user, Comment comment);
    
    // // Downvoting meLthods
    // void downvotePost(User user, Post post);
    // void downvoteComment(User user, Comment comment);
    // int countByPostIdAndVoteType(Long postId, VoteType voteType);

}
