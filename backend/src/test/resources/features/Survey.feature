#language: fr

Fonctionnalité: Gestion des sondages
  Scénario: Participer à un sondage
    Etant donné Un client et un sondage avec une question
    Quand le client répond au sondage
    Alors le sondage contient un participant et une réponse de plus

  Scénario: Un client participe deux fois au même sondage
    Etant donné Un client et un sondage avec une question
    Quand le client répond au sondage
    Alors si le client répond encore au sondage, une erreur apparait

    Scénario: Un administrateur crée un sondage
    Etant donné Un administrateur
    Quand l'administrateur crée un sondage
    Alors il existe un sondage sans question

  Scénario: Un administrateur récupère les résultats d'un sondage
    Etant donné Un administrateur et un sondage avec une question et 5 participants
    Quand l'administrateur récupère les résultats du sondage
    Alors il y a 5 réponses à la question et 5 participants