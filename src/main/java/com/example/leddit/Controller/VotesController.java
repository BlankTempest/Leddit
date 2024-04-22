package com.example.leddit.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.leddit.Model.User;
import com.example.leddit.Service.UserService;
import com.example.leddit.Service.VotesService;

@RestController
@RequestMapping("/votes")
public class VotesController {

    @Autowired
    private VotesService votesService;
    
    @Autowired
    private UserService userService;

    @PostMapping("/upvote")
    public ResponseEntity<String> upvote(@RequestParam(name = "userId") String userId,
                                         @RequestParam(value = "postId") Long postId,
                                         @RequestParam(value = "subredditId") Long subredditId,
                                         @RequestParam(value = "commentId", required = false) Long commentId) {
        try {
            User user = userService.getUserFromToken(userId);
            votesService.upvote(user, postId, subredditId, commentId);
            return ResponseEntity.ok("Upvoted successfully");
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upvote");
        }
    }

    @PostMapping("/downvote")
    public ResponseEntity<String> downvote(@RequestParam(name = "userId") String userId,
                                           @RequestParam(value = "postId") Long postId,
                                           @RequestParam(value = "subredditId") Long subredditId,
                                           @RequestParam(value = "commentId", required = false) Long commentId) {
        try {
            User user = userService.getUserFromToken(userId);
            votesService.downvote(user, postId, subredditId, commentId);
            return ResponseEntity.ok("Downvoted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to downvote");
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> countVotes(
            @RequestParam Long postId,
            @RequestParam Long userId,
            @RequestParam(required = false) Long commentId) {
        int voteCount = votesService.countVotes(postId, userId, commentId);
        return ResponseEntity.ok().body(voteCount);
    }
}
