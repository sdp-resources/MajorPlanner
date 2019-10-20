Feature: Create Blank Schedule
  Scenario: User creates schedule
    Given Ben is a user and logged in
    When Ben creates a schedule called "My Schedule"
    Then the response has no error
    And there exists a schedule called "My Schedule" owned by Ben

  Scenario: Admin creates a schedule
    Given MrAdmin is a admin and logged in
    When they create a schedule called "Schedule for Haris" in the name of Haris
    Then the response has no error
    And there exists a schedule called "Schedule for Haris" owned by Haris

  Scenario: User creates a schedule for someone else
    Given Mackenzie is a user and logged in
    When they create a schedule called "Schedule for someone else" in the name of Brian
    Then the response has an error