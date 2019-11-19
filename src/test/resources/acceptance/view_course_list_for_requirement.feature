Feature: View course list for requirement
  Scenario: User is trying to fill a single course requirement
    Given u is a logged in User
    And "" is a course with id "PHY162"
    And a single course requirement "PHY162" with id i
    When u views the course list l for the requirement with id i
    Then l contains only "PHY162"

  Scenario: User is trying to fill an any requirement
    Given u is a logged in User
    And "" is a course with id "ENG243"
    And "" is a course with id "ENG244"
    And a single course requirement "ENG243" with id i
    And a single course requirement "ENG244" with id j
    And a any requirement which contains i and j with id k
    When u views the course list l for the requirement k
    Then l contains "ENG243" and "ENG244"

  Scenario: User is trying to fill a tag requirement
    Given u is a logged in User
    And "" is a course with id "ENG100"
    And "" is a course with id "ENG300"
    And "" is a course with id "ENG243"
    And "" is a course with id "ENG244"
    And "" is a course with id "PHY243"
    And a tag requirement "ENG" and '2XX" with id i
    When u views the course list l for the requirement i
    Then l contains