package com.example.book_quiz.service;

import com.example.book_quiz.entity.UserEntity;
import com.example.book_quiz.model.CustomUserDetails;
import com.example.book_quiz.model.UserDto;
import com.example.book_quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new CustomUserDetails(
                new UserDto(user.getId(), user.getUsername(), user.getRole()),
                user.getPassword()
        );
    }
}