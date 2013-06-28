Feature: Random pub in Manchester
  As someone looking for a pub near St. Peter's Square Metrolink stop in Manchester
  I want a random pub nearby suggested
  So that I don't have to decide for myself where to drink

  Scenario: Starting app gives a random pub
    When I wait for the app to load
    Then I should be given a random pub name

