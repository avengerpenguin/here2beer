Feature: Random pub nearby
  As someone looking for a pub nearby
  I want a random pub nearby suggested
  So that I don't have to decide for myself where to drink

  Scenario Outline: Starting app gives a random pub around <place>
    Given I am at <location>
    When I press the button for a random pub suggestion
    Then I should be suggested a pub out of "<pub_names>"

    Examples: 
      | place                       | location            | pub_names                                                                                                                                                                                                                                                                                                             |
      | Central Manchester          | 53.477626,-2.243872 | The Wyvern,Table Table,Premier Inn Manchester Central,The Paramount,The Alibi,True,110 Restaurant and Bar,The Waterhouse,One Central Club,Henry s Cafe Bar,Tiger Lounge,City Arms,Opus Restaurant,The Vine Inn,Stalls Cafe Bar,Bannatyne's Health Club,Fab Cafe,Albert Square,The Slug And Lettuce,BrewDog Manchester |
      | Christ's College, Cambridge | 52.205277,0.121945  | Lola Lo,Baroosh,The Cow,Ta Bouche,D'Arrys Cookhouse & Wine Shop,Quinns Irish Pub,Cambridge City Hotel,Rat & Parrot,The Cambridge Brew House,The Bath House,All Bar One,Eraina Taverna,The Vaults Restaurant,Champion Of The Thames,The Regal,The Eagle,The King Street Run,Hidden Rooms,Clarendon Arms,The Fountain    |
      | Oundle, Northamptonshire    | 52.480902,-0.467577 | Rose and Crown,The Angel,The George                                                                                                                                                                                                                                                                                   |
