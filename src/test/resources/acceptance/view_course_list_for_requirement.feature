Feature: View course list for requirement
  Scenario: User is trying to fill a single course requirement
    Given u is a logged in User
    And "PHY162" is a course with id "PHY162"
    And a requirement with id i
      """
      {
        "type": "course",
        "course": "PHY162"
      }
      """
    When u views the course list l for the requirement with id i
    Then the list of courses l contains "PHY162"

  Scenario: User is trying to fill an either requirement
    Given u is a logged in User
    And "ENG243" is a course with id "ENG243"
    And "ENG244" is a course with id "ENG244"
    And "ENG245" is a course with id "ENG245"
    And a requirement with id i
      """
      {
        "type": "either",
        "requirements": [
          {
            "type": "course",
            "course": "ENG243"
          },
          {
            "type": "course",
            "course": "ENG244"
          }
        ]
      }
      """
    When u views the course list l for the requirement with id i
    Then the list of courses l contains "ENG243"
    And the list of courses l contains "ENG244"

  Scenario: User is trying to fill a tag requirement
    Given u is a logged in User
    And "ENG100" is a course with id "ENG100" with tags:
      | ENG |
      | 1XX |
    And "ENG300" is a course with id "ENG300" with tags:
      | ENG |
      | 3XX |
    And "ENG243" is a course with id "ENG243" with tags:
      | ENG |
      | 2XX |
    And "ENG244" is a course with id "ENG244" with tags:
      | ENG |
      | 2XX |
    And "PHY243" is a course with id "PHY243" with tags:
      | PHY |
      | 2XX |
    And a requirement with id i
      """
      {
        "type": "tag",
        "tags": [
          "ENG",
          "2XX"
        ]
      }
      """
    When u views the course list l for the requirement with id i
    Then the list of courses l contains "ENG243"
    And the list of courses l contains "ENG244"

  Scenario: User is trying to fill an exclude requirement
    Given u is a logged in User
    And "ENG100" is a course with id "ENG100" with tags:
      | ENG |
      | 1XX |
    And "ENG300" is a course with id "ENG300" with tags:
      | ENG |
      | 3XX |
    And "ENG243" is a course with id "ENG243" with tags:
      | ENG |
      | 2XX |
    And "ENG244" is a course with id "ENG244" with tags:
      | ENG |
      | 2XX |
    And "PHY243" is a course with id "PHY243" with tags:
      | PHY |
      | 2XX |
    And a requirement with id i
      """
      {
        "type": "exclude",
        "requirement": {
          "type": "tag",
          "tags" : [
            "ENG"
          ]
        },
        "courses": [
          "ENG100",
          "PHY243",
          "ENG244"
        ]
      }
      """
    When u views the course list l for the requirement with id i
    Then the list of courses l contains "ENG243"
    And the list of courses l contains "ENG300"