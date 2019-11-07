Feature: Add Course to Schedule
  Scenario: User adds course to a schedule that doesn't exist
    Given Haris is a logged in User
    And "Data Structures" is a course with id "CS223"
    When Haris adds "Data Structures" to schedule with id 567 for Fall of Sophomore year
    Then the latest response has an error

  Scenario: User adds a non-existent course to a schedule
    Given Celeste is a logged in User
    And a schedule, MySchedule, with owner Celeste
    When Celeste adds "Da course" to MySchedule for Fall of Sophomore year
    Then the latest response has an error

  Scenario: User adds course to schedule that isn't theirs
    Given Alice is a logged in User
    And Eve is a logged in User
    And a schedule, MySchedule, with owner Alice
    And "Data Structures" is a course with id "CS223"
    When Eve adds "Data Structures" to MySchedule for Winter of Junior year
    Then the latest response has an error

  Scenario: User adds course to their own schedule
    Given George is a logged in User
    And a schedule, MySchedule, with owner George
    And "Calculus 3" is a course with id "MAT221"
    When George adds "Calculus 3" to MySchedule for Fall of Senior year
    Then the latest response has no error
    And MySchedule has the course, "Calculus 3" during Fall of Senior year

  Scenario: Admin adds a course to a schedule
    Given Ben is a logged in Admin
    And Celeste is a logged in User
    And a schedule, MySchedule, with owner Celeste
    And "Data Structures" is a course with id "CS223"
    When Ben adds "Data Structures" to MySchedule for Winter of Freshman year
    Then the latest response has no error
    And MySchedule has the course, "Data Structures" during Winter of Freshman year

  Scenario: User adds course to schedule twice
    Given Celeste is a logged in User
    And a schedule, MySchedule, with owner Celeste
    When Celeste adds "Da course" to MySchedule for Fall of Sophomore year
    And Celeste adds "Da course" to MySchedule for Winter of Sophomore year
    Then the latest response has an error