package com.example.leddit.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leddit.Model.User;
import com.example.leddit.Repository.PostRepository;
import com.example.leddit.Repository.UserRepository;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
public class ModeratorService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // public ModeratorService(PostRepository postRepository) {
    //     this.postRepository = postRepository;
    // }
    
    @Transactional
    public void deletePost(Long postId, Long subredditId) {
        postRepository.deleteByIdAndSubredditId(postId, subredditId);
    }

    @Transactional 
    public void deleteUser(User userToDelete) {
        // Update the user's active status to 0 (inactive)
        Optional<User> optionalUser = userRepository.findById(userToDelete.getId());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setActive(existingUser.getActive() == 1? 0: 1);

            // Save the updated user
            userRepository.save(existingUser);
        }
    }

    @Transactional
    public void updateUser(User userToUpdate) {
        Optional<User> optionalUser = userRepository.findById(userToUpdate.getId());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setAdmin(existingUser.getAdmin() == 1? 0: 1);

            // Save the updated user
            userRepository.save(existingUser);
        }
    }
}
