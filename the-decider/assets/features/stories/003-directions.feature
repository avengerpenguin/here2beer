Feature: Show directions to chosen pub
  As someone who wishes to visit a suggested pub, but doesn't know the area well
  I want to be shown directions to that pub
  So that I can find it without relying on knowing street names

  Scenario Outline: Press button to open directions in Google Maps
    Given I have been suggested <pub>
    But I am currently at <location>
    When I press the button saying "Show Directions"
    Then I should be sent to Google Maps to show walking directions to the suggested pub

    Examples: 
      | pub                               | location            |
      | City Arms, Manchester             | 53.477626,-2.243872 |
      | Champion Of The Thames, Cambridge | 52.205277,0.121945  |
      | The George, Oundle                | 52.480902,-0.467577 |
