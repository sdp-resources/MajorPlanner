Feature: View course list for requirement
  Scenario: User is trying to fill a single course requirement
    Given u is a logged in User
    And "" is a course with id "PHY162"
    And a requirement with id i
      """
      {
        "type": "course",
        "course": "PHY162"
      }
      """
    When u views the course list l for the requirement with id i
    Then l contains "PHY162"

  Scenario: User is trying to fill an either requirement
    Given u is a logged in User
    And "" is a course with id "ENG243"
    And "" is a course with id "ENG244"
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
    Then l contains "ENG243" and "ENG244"

  Scenario: User is trying to fill a tag requirement
    Given u is a logged in User
    And "" is a course with id "ENG100"
    And "" is a course with id "ENG300"
    And "" is a course with id "ENG243"
    And "" is a course with id "ENG244"
    And "" is a course with id "PHY243"
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
    Then l contains