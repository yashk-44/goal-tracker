package com.yash.goal_tracker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yash.goal_tracker.entities.Goal;
import com.yash.goal_tracker.repositories.GoalRepository;

import lombok.RequiredArgsConstructor;

// THE SERVICE LAYER FOR THE CONTROLLER

@Service 
@RequiredArgsConstructor 
public class GoalService {
    private final GoalRepository goalRepository;

    public Goal createGoal(Goal goal) {
        goal.setCount(0);
        return goalRepository.save(goal);
    }

    public Goal incrementGoalProgress(int id) {
        Goal existingGoal = goalRepository.findById(id).orElse(null);
        if (existingGoal != null){
            existingGoal.setCount(existingGoal.getCount() + 1);
            return goalRepository.save(existingGoal);
        }
        return null;
    }
    public List<Goal> getGoals(){
        return goalRepository.findAll();
    }
    public Goal getGoalByID(int id){
        return goalRepository.findById(id).orElse(null);
    }

    public void deleteGoal(int id){
        goalRepository.deleteById(id);
    }
}
