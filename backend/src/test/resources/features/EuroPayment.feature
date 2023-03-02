#language: fr

Fonctionnalité: Acheter un produit en euro

  Contexte:
    Etant donné un client

  Plan du Scénario: Scénario: Payer avec la carte de crédit
    Quand le client paye un montant <nbEuros> en euro
    Alors il recois <nbPoints> en point

    Exemples:
    # Need also to test later for float values
      | nbEuros | nbPoints |
      | 0            | 0   |
      | 1            | 2   |
      | 5            | 10     |
      | 10           | 20     |

  Plan du scénario: Scénario: Payer un montant négatif avec une carte de crédit
    Quand le client paye un montant négatif <nbEuros> en euro
    Alors le système renvoie une NegativePaymentException

    Exemples:
      | nbEuros |
      | -1      |
      | -5      |
      | -10     |



  Plan du Scénario: Scénario: Payer avec la carte de fidélité
    Etant donné la balance du client <nbEurosCard> euros
    Quand le client paye un montant <nbEuros> en euro avec sa carte de fidélité
    Alors il recois <nbPoints> en point
    Et la carte reste <nbEurosRestants>

    Exemples:
    # Need also to test later for float values
      | nbEurosCard | nbEuros | nbPoints | nbEurosRestants |
      | 50          | 50      | 100      | 0               |
      | 40          | 30      | 60       | 10              |
      | 0           | 0       | 0        | 0               |
