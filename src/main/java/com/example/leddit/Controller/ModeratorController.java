package com.example.leddit.Controller;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.example.leddit.Model.User;
import com.example.leddit.Repository.UserRepository;
import com.example.leddit.Service.ModeratorService;
import com.example.leddit.Service.UserService;

@Controller
public class ModeratorController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private ModeratorService moderatorService;

    // public ModeratorController(UserService userService, ModeratorService moderatorService) {
    //     this.userService = userService;
    //     this.moderatorService = moderatorService;
    // }

    @DeleteMapping("/delete/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable (value = "postId") Long postId,
                                             @RequestParam (value = "subredditId") Long subredditId,
                                             @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Long userId = userService.getUserIdFromToken(token);
        if (userId == 69L)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid User or Anonymous");
        User user = userService.getUserById(userId);
        if (user.getAdmin() != 1) 
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Moderator");
        moderatorService.deletePost(postId, subredditId);
        return ResponseEntity.ok().body("Post deleted successfully");
    }

    @DeleteMapping("/delete/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "userId") Long userId,
                                            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Long loggedInUserId = userService.getUserIdFromToken(token);
        
        // Check if the logged-in user is authorized and has admin privileges
        User loggedInUser = userService.getUserById(loggedInUserId);
        if (loggedInUserId == 69L || loggedInUser.getAdmin() != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        
        // Check if the user to be deleted exists
        User userToDelete = userService.getUserById(userId);
        if (userToDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        moderatorService.deleteUser(userToDelete);
        
        return ResponseEntity.ok().body("User deleted successfully");
    }

    @PutMapping("/update/user/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable(value = "userId") Long userId,
                                            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Long loggedInUserId = userService.getUserIdFromToken(token);
        
        // Check if the logged-in user is authorized and has admin privileges
        User loggedInUser = userService.getUserById(loggedInUserId);
        if (loggedInUserId == 69L || loggedInUser.getAdmin() != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        
        // Check if the user to be deleted exists
        User userToDelete = userService.getUserById(userId);
        if (userToDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        moderatorService.updateUser(userToDelete);
        
        return ResponseEntity.ok().body("User updated successfully");
    }
    
}
