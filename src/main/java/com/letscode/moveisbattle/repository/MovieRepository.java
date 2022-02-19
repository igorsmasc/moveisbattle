package com.letscode.moveisbattle.repository;

import com.letscode.moveisbattle.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {}
