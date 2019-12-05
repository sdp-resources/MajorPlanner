Feature: Compare schedule
  Scenario: Filled single course
    Given u is a logged in User
    When u creates a schedule with name "name" description "desc" owned by u with resulting id s
    Given "ENG160" is a course with id "ENG160"
    And the schedule with id s has schedule:
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
          "description": "hello world"
        }
      ]
    }
    """
    When u compares the schedule with id s
