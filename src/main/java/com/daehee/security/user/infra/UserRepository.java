package com.daehee.security.user.infra;

import com.daehee.security.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<String, User> {

    User findByIdAndPassword(User user);
}
