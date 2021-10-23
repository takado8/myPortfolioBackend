package com.takado.myportfoliobackend.repository;

import com.takado.myportfoliobackend.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    User findByEmail(String email);
}
