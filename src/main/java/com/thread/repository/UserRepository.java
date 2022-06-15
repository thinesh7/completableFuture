package com.thread.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.thread.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
