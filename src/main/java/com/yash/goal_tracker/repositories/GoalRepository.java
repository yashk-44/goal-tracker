package com.yash.goal_tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.goal_tracker.entities.Goal;

// THE REPOSITORY (DATABASE INTERACTION)

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    
}
