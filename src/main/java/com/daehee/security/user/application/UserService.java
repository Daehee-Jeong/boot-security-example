package com.daehee.security.user.application;

import com.daehee.security.user.domain.User;
import com.daehee.security.user.infra.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByIdAndPassword(User user) {
        return this.userRepository.findByIdAndPassword(user);
    }
}
