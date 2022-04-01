package com.beta.ms.user.dao;

import com.beta.ms.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityDao  extends JpaRepository<UserEntity, Long> {
    public UserEntity findByUsername(String username);
}
