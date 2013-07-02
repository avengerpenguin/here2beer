Feature: Random pub nearby
  As someone looking for a pub nearby
  I want a random pub nearby suggested
  So that I don't have to decide for myself where to drink

  Scenario Outline: Starting app gives a random pub around <place>
    Given I am at <location>
    When I press the button for a random pub suggestion
    Then I should be suggested a pub out of "<pub_names>"

    Examples: 
      | place                       | location            | pub_names                                                                                                                                                                                                                                                                                                                                      |
      | Central Manchester          | 53.477626,-2.243872 | The Bridgewater Hall,Cornerhouse,Castle Hotel,The Circle Club,The Moon Under Water,The Old Wellington,Night & Day Cafe,Cloud 23,Albert Square,The Font,Salutation,Joshua Brooks,The English Lounge,Hilton Manchester Deansgate Hotel,The Paramount,The Shakespeare,Taps,Malmaison,Crowne Plaza Manchester City Centre,Thomas Restaurant & Bar  |
      | Christ's College, Cambridge | 52.205277,0.121945  | The Eagle,The Fort Saint George,St Radegund Pub,Champion Of The Thames,The Baron of Beef,The Boathouse,The Maypole,Live & Let Live,Ta Bouche,The Red Bull,Clarendon Arms,De Luca Cucina & Bar,Doubletree by Hilton Cambridge,The Oak Bistro,Browns,Man On The Moon,Quinns Irish Pub,DArrys Cookhouse & Wine Shop,Panton Arms,The Burleigh Arms |
      | Oundle, Northamptonshire    | 52.480902,-0.467577 | Rose and Crown,The Angel,The George                                                                                                                                                                                                                                                                                                            |
