Feature: Compare schedule
  Scenario: Filled single course
    Given u is a logged in User
    When u creates a schedule with name "name" description "desc" owned by u with resulting id s
    Given "ENG160" is a course with id "ENG160"
    And the schedule with id s has program:
    """
    {
      "name": "Empty program",
      "description": "empty",
      "storedReqs": [
        {
          "requirement": {
             "type":"course",
             "course": "ENG160"
          },
          "id": 20,
          "description": "Must take ENG160"
        }
      ]
    }
    """
    When u compares the schedule with id s with resulting matches m
    Then matches m contains a requirement with description "Must take ENG160" and course "ENG160"
