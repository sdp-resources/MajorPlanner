Feature: Remove Course from Schedule
  Scenario: User removes a course from a schedule that doesn't exist
    Given Haris is a logged in User
    And "Data Structures" is a course with id "CS223"
    And i is the scheduleId 456
    When Haris removes course with id "CS223" from schedule with id i
    Then the latest response has an error

  Scenario: User removes course from a schedule that isn't theirs
    Given Alice is a logged in User
    And Eve is a logged in User
    And "Data Structures" is a course with id "CS223"
    When Alice creates a schedule with name "name" description "desc" owned by Alice with resulting id i
    When Eve removes course with id "CS223" from schedule with id i
    Then the latest response has an error

  Scenario: User removes course from their own schedule
    Given George is a logged in User
    And "Calculus 3" is a course with id "MAT221"
    When George creates a schedule with name "name" description "desc" owned by George with resulting id i
    When George adds "Calculus 3" to schedule with id i for Fall of Senior year
    When George removes course with id "MAT221" from schedule with id i
    When George views the schedule s with id i
    Then s doesn't have a course with id "MAT221" term Fall and year Senior

  Scenario: Admin removes a course from a schedule
    Given Ben is a logged in Admin
    And Celeste is a logged in User
    And "Data Structures" is a course with id "CS223"
    When Ben creates a schedule with name "name" description "desc" owned by Celeste with resulting id i
    When Ben adds "Data Structures" to schedule with id i for Winter of Freshman year
    When Ben removes course with id "CS223" from schedule with id i
    When Celeste views the schedule s with id i
    Then s doesn't have a course with id "CS223" term Winter and year Freshman

  Scenario: User removes course from a schedule that doesn't have it
    Given Celeste is a logged in User
    When Celeste creates a schedule with name "name" description "desc" owned by Celeste with resulting id i
    When Celeste removes course with id "CS223" from schedule with id i
    Then the latest response has an error