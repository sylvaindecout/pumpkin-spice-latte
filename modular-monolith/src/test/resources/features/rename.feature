Feature: As a client I want to order my favorite drink
  and get an invoice


  Scenario: A client want to order a drink not in the menu

    Given a client
    When he order, an hot coca-cola, drink not present on the menu
    Then client is notify that the drink hot coca-cola doesn't exist

  Scenario: An ingredient is missing to prepare a drink

    Given a client
    Given latte ingredients are missing
    When he order a latte drink
    Then client is notify that the drink  is currently unavailable
