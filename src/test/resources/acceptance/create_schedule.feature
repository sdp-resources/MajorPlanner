Feature: Create Blank Schedule
  Scenario: User creates schedule
    Given Ben is a user and logged in
    When Ben creates a schedule called "My Schedule" with description "It's my first schedule!"
    Then the latest response has no error
    And there exists a schedule called "My Schedule" with description "It's my first schedule!" owned by Ben

  Scenario: Admin creates a schedule
    Given MrAdmin is a admin and logged in
    When MrAdmin creates a schedule called "Schedule for Haris" with description "Default template schedule" in the name of Haris
    Then the latest response has no error
    And there exists a schedule called "Schedule for Haris" with description "Default template schedule" owned by Haris

  Scenario: User creates a schedule for someone else
    Given Mackenzie is a user and logged in
    When Mackenzie creates a schedule called "Schedule for someone else" with description "Very evil description!" in the name of Brian
    Then the latest response has an error