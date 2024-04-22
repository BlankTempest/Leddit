package com.example.leddit.Service;

import org.hibernate.query.IllegalMutationQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leddit.Model.Comment;
import com.example.leddit.Model.Post;
import com.example.leddit.Model.User;
import com.example.leddit.Model.Vote;
import com.example.leddit.Model.VoteType;
import com.example.leddit.Model.VoteBuilder;
import com.example.leddit.Repository.CommentRepository;
import com.example.leddit.Repository.PostRepository;
import com.example.leddit.Repository.UserRepository;
import com.example.leddit.Repository.VotesRepository;
import java.util.*;

@Service
public class VotesService {

    @Autowired
    private VotesRepository votesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public void upvote(User user, Long postId, Long subredditId, Long commentId) {
        // Check if the user exists
        Optional<Post> post = postRepository.findByIdAndSubredditId(postId, subredditId);
        Optional<Vote> vote2 = votesRepository.findByUserIdAndPostId(user.getId(), postId);
        // Check if the post or comment exists, depending on the vote type
        System.out.println(vote2.isPresent() + " " + post.isPresent());
        System.out.println(user.getId() + " " + postId);

        if (!(vote2.isPresent() && vote2.get().getVoteType() == VoteType.UPVOTE) && post.isPresent() && postId != null) { 
            if (vote2.isPresent())
                votesRepository.delete(vote2.get());
            // Vote vote = new Vote();
            // vote.setUser(user);
            // vote.setPost(post.get());
            // vote.setVoteType(VoteType.UPVOTE);
            // votesRepository.save(vote);
            Vote vote = new VoteBuilder()
                .user(user)
                .post(post.get())
                .voteType(VoteType.UPVOTE)
                .build();
            votesRepository.save(vote);
            // Perform upvoting logic for the post
            // For example: votesRepository.upvotePost(user, post);
        } else if (commentId != null) {
            // Perform upvoting logic for the comment
            // For example: votesRepository.upvoteComment(user, comment);
        } else {
            throw new IllegalMutationQueryException("Either postId or commentId must be provided");
        }
    }

    public void downvote(User user, Long postId, Long subredditId,  Long commentId) {
         // Check if the user exists
         Optional<Post> post = postRepository.findByIdAndSubredditId(postId, subredditId);
         Optional<Vote> vote2 = votesRepository.findByUserIdAndPostId(user.getId(), postId);
         // Check if the post or comment exists, depending on the vote type
         System.out.println(vote2.isPresent() + " " + post.isPresent());
         if (!(vote2.isPresent() && vote2.get().getVoteType() == VoteType.DOWNVOTE) &&  post.isPresent() && postId != null) {
            if (vote2.isPresent())
                votesRepository.delete(vote2.get());
            Vote vote = new VoteBuilder()
                .user(user)
                .post(post.get())
                .voteType(VoteType.DOWNVOTE)
                .build();
            votesRepository.save(vote);
            
        // Perform downvoting logic for the post
        // For example: votesRepository.downvotePost(user, post);
        } else if (commentId != null) {
        // Perform downvoting logic for the comment
        // For example: votesRepository.downvoteComment(user, comment);
        } else {
            throw new IllegalArgumentException("Either postId or commentId must be provided");
        }
    }

    public int countVotes(Long postId, Long userId, Long commentId) {
        int upvotes = votesRepository.countUpvotesByPostId(postId);
        int downvotes = votesRepository.countDownvotesByPostId(postId);
        return upvotes - downvotes;
    }
}
