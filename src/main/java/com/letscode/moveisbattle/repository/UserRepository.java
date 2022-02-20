package com.letscode.moveisbattle.repository;

import com.letscode.moveisbattle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM USER ORDER BY SCORE DESC LIMIT :size")
    List<User> getRankingBySize(@Param("size") int size);
}
