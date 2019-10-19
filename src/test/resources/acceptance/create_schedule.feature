Feature: Create Blank Schedule
  Scenario: User creates schedule
    Given Ben is a user and logged in
    When Ben creates a schedule called "My Schedule"
    Then there exists a schedule called "My Schedule" owned by Ben