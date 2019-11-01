Feature: Add Course to Schedule
  Scenario: User adds course to a schedule that doesn't exist
    Given Haris is a User
    And Haris is logged in
    And "Data Structures" is a course with id "CS223"
    When Haris adds "Data Structures" to schedule with id 567 for Fall of Sophomore year
    Then the latest response has an error

  Scenario: User adds a non-existent course to a schedule
    Given Celeste is a User
    And Celeste is logged in
    And a schedule, MySchedule, with owner Celeste
    When Celeste adds "Da course" to MySchedule for Fall of Sophomore year
    Then the latest response has an error

  Scenario: User adds course to schedule that isn't theirs
    Given Alice is a User
    And Eve is a User
    And Eve is logged in
    And a schedule, MySchedule, with owner Alice
    And "Data Structures" is a course with id "CS223"
    When Eve adds "Data Structures" to MySchedule for Winter of Junior year
    Then the latest response has an error

  Scenario: Admin adds a course to a schedule
    Given Ben is a Admin
    And Celeste is a User
    And Ben is logged in
    And a schedule, MySchedule, with owner Celeste
    And "Data Structures" is a course with id "CS223"
    When Ben adds "Data Structures" to MySchedule for Winter of Freshman year
    Then the latest response has no error

  Scenario: User adds course to schedule twice
    Given Celeste is a User
    And Celeste is logged in
    And a schedule, MySchedule, with owner Celeste
    When Celeste adds "Da course" to MySchedule for Fall of Sophomore year
    And Celeste adds "Da course" to MySchedule for Winter of Sophomore year
    Then the latest response has an error