package com.example.leddit.Service;

import com.example.leddit.Model.User;
import com.example.leddit.Model.UserToken;
import com.example.leddit.Repository.UserRepository;
import com.example.leddit.Repository.UserTokenRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Date;
import java.util.Calendar;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User registerUser(User user) {
        // Encrypt the password before saving
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public boolean validateLogin(String username, String password) {
        Optional<User> user = findByUsername(username);
        return user.isPresent() && BCrypt.checkpw(password, user.get().getPassword());
    }

    public boolean isTokenValid(String token) {
        Optional<UserToken> userToken = userTokenRepository.findByToken(token);
        if (userToken.isPresent()) {
            // Check if token is expired (optional)
            UserToken tokenRecord = userToken.get();
            if (tokenRecord.getExpiresAt().after(new Date())) {
                return true;
            }
        }
        return false;
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void storeToken(Long userId, String token) {
        UserToken userToken = new UserToken();
        userToken.setUserId(userId);
        userToken.setToken(token);

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR, 12);
        Date expirationDate = calendar.getTime();
        userToken.setExpiresAt(expirationDate);
        
        userTokenRepository.save(userToken);
    }
    
    

    public void revokeToken(String token) {
        userTokenRepository.deleteByToken(token);
    }    
    
}
