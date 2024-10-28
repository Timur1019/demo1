package com.example.demo.repository;

import com.example.demo.db.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioItem, Long> {
}
