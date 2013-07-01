Feature: Saving favourite pubs
  As someone who quite enjoyed a pub that was suggested
  I want to save that pub as a favourite
  So that I am reminded that I liked it when it gets randomly suggested again

  Scenario: Pressing favourite button saves the pub
    Given I have a suggested pub on the screen
    When I press the button for adding to favourites
    Then I should get a prompt saying that pub is saved to my favourites

  Scenario: Random suggestion of pub previously favourited
    Given I have previously favourited a pub
    When I am randomly suggested that pub
    Then I should see a star next to the name
    And I should also see a button to remove from favourites

   Scenario: Removing pub from favourites
    Given I have previously favourited a pub
    And I have been suggested that pub
    When I press the button to remove from favourites
    Then I should get a prompt saying that pub was removed from my favourites
