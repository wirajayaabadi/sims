package com.example.sims.repository;

import com.example.sims.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, BigInteger> {
  @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
  boolean existsByEmail(@Param("email") String email);

  Optional<User> findByEmail(String email);
}
