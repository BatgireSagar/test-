package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycProfileRepository extends JpaRepository<Object, Long> {
    // TODO: Replace Object with KycProfile entity
}