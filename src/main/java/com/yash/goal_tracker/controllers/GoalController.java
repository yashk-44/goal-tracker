package com.yash.goal_tracker.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.goal_tracker.entities.Goal;
import com.yash.goal_tracker.services.GoalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** GoalController: THE CONTROLLER IN THE MVC
 * controls goal creation and retrieval (as of now).
 */
@RestController // Indicates that this class will be used for all the REST api ops
@RequestMapping("/goals") // Main endpoint will be at the "/goals" url.
@RequiredArgsConstructor 
public class GoalController {

    private final GoalService goalService;

    @PostMapping // Post request endpoint
    public Goal createGoal(@Valid @RequestBody Goal goal) {
       return goalService.createGoal(goal);
    }

    @PatchMapping("/{id}") // Patch request endpoint with id as the path variable
    public Goal incrementGoalProgress(@PathVariable Integer id) {
        return goalService.incrementGoalProgress(id);
    }

    @GetMapping
    public List<Goal> getGoals(){
        return goalService.getGoals();
    }

    @GetMapping("/{id}")  // Get request endpoint
    public Goal getGoalByID(@PathVariable Integer id){
        return goalService.getGoalByID(id);
    }

    @DeleteMapping("/{id}") // Delete request endpoint
    public void deleteGoal(@PathVariable Integer id){
        goalService.deleteGoal(id);
    }
}