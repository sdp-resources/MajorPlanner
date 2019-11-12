Feature: Schedule
  Scenario: User creates a blank schedule and views it
    Given u is a logged in User
    When u creates a schedule with name "name" description "desc" owned by u with resulting id i
    And u views the schedule s with id i
    Then the schedule s has name "name"
    And the schedule s has description "desc"
    And the schedule s has no courses

  Scenario: Admin creates a schedule for a user
    Given MrAdmin is a logged in Admin
    And Haris is a logged in User
    When MrAdmin creates a schedule with name "Schedule for Haris" description "Default template schedule" owned by Haris with resulting id i
    And MrAdmin views the schedule s with id i
    Then the schedule s has name "Schedule for Haris"
    And the schedule s has description "Default template schedule"
    And the schedule s has no courses

  Scenario: User creates a schedule for someone else
    Given Mackenzie is a logged in User
    And Brian is a logged in User
    When Mackenzie creates a schedule called "Schedule for someone else" with description "Very evil description!" owned by Brian
    Then the latest response has an error

  Scenario: Create a schedule for someone who doesn't exist
    Given Ben is a logged in Admin
    When Ben creates a schedule called "Schedule for a ghost" with description "Boo!" owned by JaneDoe
    Then the latest response has an error