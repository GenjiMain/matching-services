Feature: the offer can be retrieved
  Scenario: client makes call to POST /offers
    When the client calls /offers
    Then the client receives status code of 200
    And the client receives server id of offer