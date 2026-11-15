# Yash's "goal tracker" Personal Project 

## Description
This is a simple goal-tracking app I have created for a single user (me) to demonstrate my learnings. 
It is a terminal-based application with no frontend UI. It supports goal creation, retrieving a single goal, 
all the goals, updating the progress on a goal, and deleting a goal.

## Tech stack
- Java: for the backend
- Spring: using Springboot on Spring MVC I set this application to be a web service with the necessary REST api endpoints.
- Hibernate: the ORM for the Goal object.
- Postgres: the database (Likely using Supabase)

## Getting started
Since this application is not deployed on the cloud, users must currently **self-host** the application. Users must create a Supabase table 
and provide their Supabase Postgres database URL, username, and password as the environment variables (see .env.example). To actually run the application, 
they must pull the image from the DockerHub artifact repo using the following command:
```docker pull yashk2005/goal-tracker:eb4940b```

**notes**: 
- The repo is currently private so this command ideally should not work.
- Once I change the tagging conventions, the latest image should be tagged as "latest" and I will update the above command accordingly.

## How to use the application
1. **To start the application** users must first run ```docker run -p host:8080 --env-file .env yashk-44/goal-tracker:eb4940b```. Each path is prefixed with localhost:8080 but this is not an endpoint itself.
2. **The endpoints and all the actions** (```/goals``` is the endpoint):
   1. **Create a goal** requires a POST request with a JSON input in the request body, containing all the fields specified in the Goal object (see Goal Model).
   2. **Increment progress on a goal** requires a PATCH request, with the goal id from the database as the path variable (can access by retrieving all the goals).
   3. **Retrieve all goals**: a simple GET request that returns all the goals from the database.
   4. **Retrieve a goal by ID**: a GET request with the goal id in the path itself.
   5. **Deleting a goal by ID**: a DELETE request with the goal id in the path itself.
  
## Goal Model
**Each goal has the following structure, anything not created by the database or in the backend is created by you**
- id (Integer): the goal's identifier (created in the database).
- goal (String): what the goal is.
- whatToDo (string): the 'activity' you will do that will help you achieve that goal.
- unit (String): the "units" that help you measure your progress in the activity.
- quantity (Integer): the size of this activity.
- numTimes (Integer): how many times this activity should be done.
- period (String): the time period over which the activity should be done numTimes.
- count (Integer): the number of times this activity, with the unit and quantity, has been successfully completed.

### Example of a Goal Model
- **Consider the following example in plain english terms**: a user wants to become a fluent book reader, so they plan to read one chapter of a book they like, 3 times a week.
- **What that means in relation to the Goal model**:
  - goal: Become a fluent book reader
  - whatToDo: read a book they like
  - unit: chapter
  - quantity: 1
  - numTimes: 3
  - period: week
