package com.yash.goal_tracker.entities;

import java.util.Set; // junit jupiter test import

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class GoalTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    @Test
    void testValidGoalCreation(){
        Goal goal = Goal.builder().goal("Become fluent in German").unit("pages").period("day").quantity(3).numTimes(1).build();
        Set<ConstraintViolation<Goal>> violations = validator.validate(goal);
        assert violations.isEmpty();
    }

    @Test
    void testNegativeAttributeGoal(){
        Goal goal = Goal.builder().goal("Become fluent in German").unit("pages").period("day").quantity(-3).numTimes(1).build();
        Set<ConstraintViolation<Goal>> violations = validator.validate(goal);
        assert !violations.isEmpty();
    }
}
