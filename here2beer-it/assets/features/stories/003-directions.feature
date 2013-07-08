Feature: Show directions to the suggested pub
  As someone who wishes to visit a suggested pub, but doesn't know the area well
  I want to be shown directions to that pub
  So that I can find it without relying on knowing street names

  Scenario: Press button to open directions in Google Maps
    Given I have been suggested a pub
    When I press the button saying "Get Directions"
    Then I should be sent to Google Maps to show walking directions to the suggested pub
