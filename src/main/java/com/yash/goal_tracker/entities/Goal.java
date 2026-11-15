package com.yash.goal_tracker.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// THIS WHOLE CLASS IS THE "MODEL" IN THE MVC

@Entity 
@Table(name="Goals")
@Getter // Getter functions for each attribute
@Setter // Setter functions for each attribute
@AllArgsConstructor // 
@NoArgsConstructor
@Builder
public class Goal {
    /**
         * @param goal a short title describing what the goal itself is.
         * @param whatToDo what the user wants to do to achieve this goal (the activity).
         * @param quantity the "size" of this activity (duration, volume, etc.).
         * @param unit the unit describing the quantity (litres, glasses, etc.).
         * @param numTimes how many times the user wants to do this activity over <period>.
         * @param period the period over which the numTimes is being measures (hour, day, week, etc.).
         * @param count the number of times the activity (whatToDo) has been achieved with the unit and quantity.
         */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generates the ID like in postgres tables
    Integer id;

    @Column
    @NotBlank(message="Goal must not be blank!")
    String goal;

    @Column
    String whatToDo;

    @Column
    @NotBlank(message="Must have some units to measure the activity with!")
    String unit;

    @Positive(message="quantity must be above zero (0)!")
    @Column
    Integer quantity;

    @Positive(message="numTimes must be above zero (0)!")
    @Column
    Integer numTimes; 

    @NotBlank(message="the period of time must not be empty!")
    String period;

    @PositiveOrZero(message="the count must not be negative!")
    @Column
    Integer count;
}
