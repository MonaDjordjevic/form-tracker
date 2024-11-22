package com.example.form_tracker.security;

import com.example.form_tracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrentUserUtil {

    private final UserRepository userRepository;

    public Integer getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalStateException("User not found for username: " + username));
            return user.getId();
        }
        throw new IllegalStateException("No authenticated user found.");
    }
}

