Feature: Ability to change radius of the pub search
  As someone who doesn't want to walk very far or is willing to walk quite a distance
  I want suggested pubs within a radius smaller or larger than the default
  So that I have a shorter or longer walk to find beer, depending on my preference

  Scenario Outline: Changing the radius changes number of pubs around <place>
    Given I am at <location>
    When I set the radius of the search to "<radius>"
    And I press the button for a random pub suggestion
    Then I should be suggested a pub out of "<pub_names>"

    Examples: 
      | place                       | location            | radius    | pub_names                                                                                                                                   |
      | Central Manchester          | 53.477626,-2.243872 | 0.1 miles | The Paramount,Premier Inn Manchester Central,Table Table,110 Restaurant and Bar,The Alibi,One Central Club,The Wyvern,Henry s Cafe Bar,True |
      | Christ's College, Cambridge | 52.205277,0.121945  | 0.1 miles | Ta Bouche,The Cow,Baroosh,Lola Lo                                                                                                           |
      | Oundle, Northamptonshire    | 52.480902,-0.467577 | 0.1 miles | Rose and Crown,The Angel,The George                                                                                                         |
      | Oundle, Northamptonshire    | 52.480902,-0.467577 | 2 miles   | The Kings Arms,The Angel,Rose and Crown,Shuckburgh Arms,The George,Montagu Arms                                                             |
