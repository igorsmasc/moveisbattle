package com.letscode.moveisbattle.repository;


import com.letscode.moveisbattle.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM USER ORDER BY SCORE DESC LIMIT :rankingSize")
    List<UserStatus> getRankingBySize(@Param("rankingSize") int rankingSize);
}
