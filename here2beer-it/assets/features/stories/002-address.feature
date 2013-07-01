Feature: Address of the pub is given
  As someone who was just suggested a pub to visit
  I would like to know the address of the pub
  So that I can know where the pub is or look it up on a map

  Scenario: Address of pub is given alongside its name
    When I am suggested a pub
    Then I should see its address
