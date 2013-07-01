Feature: Request another suggestion
    As someone who doesn't like the pub that was suggested
    I want a quick way to request another suggestion
    So that I can perhaps find a place I am willing to visit

  Scenario: Press button to request another suggestion
    Given I have been suggested a pub
    When I press the button for another suggestion
    Then I should be suggested a different pub

  Scenario: Shake device to request another suggestion
    Given I have been suggested a pub
    When I shake my device
    Then I should be suggested a different pub
