Feature: Address of the pub is given
  As someone who was just suggested a pub to visit
  I would like to know the address of the pub
  So that I can know where the pub is or look it up on a map

  Scenario Outline: Address of pub is given alongside its name
    When I am suggested "<pub>"
    Then I should see its address is "<address>"

    Examples: 
      | pub                    | address              |
      | City Arms              | 46-48 Kennedy Street |
      | Champion Of The Thames | 68 King Street       |
      | Rose and Crown         | 11 Market Place      |
