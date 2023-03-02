#language: fr

Fonctionnalité: Recharger la carte de fidélité

  Plan du Scénario: Scénario: Recharger la carte de fidélité
    Etant donné la balance du client <nbEurosCard> euros (card)
    Quand le client paye un montant <nbEuros> en euro (card)
    Alors la balance du client contient <nbEurosCardNew> euros


    Exemples:
 # Need also to test later for float values
      | nbEurosCard | nbEuros | nbEurosCardNew |
      | 50 | 60     | 110                      |
      | 0 | 10  | 10  |


  Plan du Scénario: Scénario: Recharger la carte de fidélité erreur
    Etant donné la balance du client <nbEurosCard> euros (card)
    Quand le client paye un montant <nbEuros> en euro (card)
    Alors il y a une NegativePaymentException


    Exemples:
 # Need also to test later for float values
      | nbEurosCard | nbEuros |
      | 50 | -10     |
      | 0 | -15 |
