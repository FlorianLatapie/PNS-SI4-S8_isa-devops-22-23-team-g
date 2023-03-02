#language: fr

Fonctionnalité: utilisation d'avantages

    Scénario: Acheter avec assez de points de fidélité
      Etant donné un invité ayant 10 points de fidélité
      Quand le client achète un produit coûtant 10 points de fidélité
      Alors le client a cet avantage
      Et le client a 0 points de fidélité


    Scénario: Acheter sans avoir assez de points de fidélité
      Etant donné un invité ayant 2 points de fidélité
      Quand le client achète un produit trop cher pour lui coûtant 10 points de fidélité
      Alors le client n'a pas cet avantage
      Et le client a 2 points de fidélité


    Scénario: dépenser des avantages
      Etant donné un client possèdant un avantage
        Quand le client dépense son avantage
        Alors le client n'a plus cet avantage

      Etant donné un client possèdant plusieurs avantage
        Quand le client dépense tout ses avantages
        Alors le client n'a plus ces avantages

      Etant donné un client ne possèdant pas un avantage
        Quand le client essaye de dépenser son avantage
        Alors le systeme revoie une AdvantageNotInBalanceException


