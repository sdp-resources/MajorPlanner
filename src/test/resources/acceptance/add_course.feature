Feature: Add Course to Schedule
  Scenario: User adds course to a schedule that doesn't exist
    Given Haris is a logged in User
    And "Data Structures" is a course with id "CS223"
    And i is the scheduleId 456
    When Haris adds "CS223" to schedule with id i for Fall of Sophomore year
    Then the latest response has an error

  Scenario: User adds a non-existent course to a schedule
    Given Celeste is a logged in User
    When Celeste creates a schedule with name "Schedule for Celeste" description "Default template schedule" owned by Celeste with resulting id i
    When Celeste adds "NonexistentCourse" to schedule with id i for Fall of Sophomore year
    Then the latest response has an error

  Scenario: User adds course to schedule that isn't theirs
    Given Alice is a logged in User
    And Eve is a logged in User
    And "Data Structures" is a course with id "CS223"
    When Alice creates a schedule with name "name" description "desc" owned by Alice with resulting id i
    When Eve adds "CS223" to schedule with id i for Winter of Junior year
    Then the latest response has an error

  Scenario: User adds course to their own schedule
    Given George is a logged in User
    And "Calculus 3" is a course with id "MAT221"
    When George creates a schedule with name "name" description "desc" owned by George with resulting id i
    When George adds "MAT221" to schedule with id i for Fall of Senior year
    When George views the schedule s with id i
    Then s has a course with id "MAT221" term Fall and year Senior

  Scenario: Admin adds a course to a schedule
    Given Ben is a logged in Admin
    And Celeste is a logged in User
    And "Data Structures" is a course with id "CS223"
    When Ben creates a schedule with name "name" description "desc" owned by Celeste with resulting id i
    When Ben adds "CS223" to schedule with id i for Winter of Freshman year
    When Celeste views the schedule s with id i
    Then s has a course with id "CS223" term Winter and year Freshman

  Scenario: User adds course to schedule twice
    Given Celeste is a logged in User
    And "Data Structures" is a course with id "CS223"
    When Celeste creates a schedule with name "name" description "desc" owned by Celeste with resulting id i
    When Celeste adds "CS223" to schedule with id i for Fall of Sophomore year
    And Celeste adds "CS223" to schedule with id i for Winter of Sophomore year
    Then the latest response has an error

  Scenario: User adds transfer course
    Given Tucker is a logged in User
    And "AP Physics" is a course with id "APPHYS"
    When Tucker creates a schedule with name "name" description "desc" owned by Tucker with resulting id i
    When Tucker adds transfer course "APPHYS" to schedule with id i
    When Tucker views the schedule s with id i
    Then s has a course with id "APPHYS"