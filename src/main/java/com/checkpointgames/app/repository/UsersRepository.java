package com.checkpointgames.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.checkpointgames.app.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>, UsersRepositoryCustom {
    Optional<Users> findByEmail(String email);
}