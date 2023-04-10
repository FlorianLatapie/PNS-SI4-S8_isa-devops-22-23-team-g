# Livrable Devops G

- [Livrable Devops G](#livrable-devops-g)
  - [Plan de Reprise de l'Activité](#plan-de-reprise-de-lactivité)
  - [Notre workflow](#notre-workflow)
  - [Les accès](#les-accès)

## Plan de Reprise de l'activité

[Plan de reprise de l'activité](./devops/PRA/PRA.md)

## Notre workflow

Nous avons construit notre workflow dans le but de répondre à 4 objectifs principaux :

- Avoir une branche main livrable à tout moment
- Avoir des pipelines rapides et sécures
- Pouvoir intégrer nos changements rapidement
- Avoir un feedback rapide en cas de problème

Pour répondre à ces impératifs, avons choisis de mettre en place une stratégie de branchement de type [Git Flow](https://nvie.com/posts/a-successful-git-branching-model/).

  **1. Une branche main livrable à tout moment :**  
L'avantage d'une stratégie de branchement de type Git Flow est qu'elle nous permet de réaliser des tests de bouts en bout uniquement une fois que les changements sont mergés sur la branche Develop.
En effet, si nous avions choisis une stratégie de type [GitHub Flow](https://docs.github.com/en/get-started/quickstart/github-flow) avec uniquement des branches de feature et une branche main
il est difficile de choisir où effectuer les tests de bout en bout.
Si nous les réalisons uniquement une fois que les changements sont mergés sur la branche main, nous n'avons pas une branche main livrable à tout moment.
Il semble donc nécessaire de réaliser des tests de bouts en bouts à chaque Pull-Request.
Cependant, ces tests sont le facteur limitant de nos pipelines et prennent en moyenne 10 minutes à s'exécuter
ils ralentiraient donc l'intégration de nos changements particulièrement le jeudi où les changements sur les Pull-request sont fréquents.
De plus si plusieurs tests de bout en bout s'exécutent en même temps, il existe un risque non négligeable d'interférence entre les pipelines s'exécutant de manière concurrente.
En effet, la première étape de nos tests de bout en bout est l'arrêt des containers existant. Cela permet de réaliser les tests dans un environnement
neutre. Cependant, nous pouvons facilement imaginer que si une pipeline débute ses tests de bout en bout pendant
qu'une autre est en train de les exécuter, celle-ci interrompra les tests de la première.  
C'est également pour conserver la branche main livrable que nous avons choisi d'éviter le [Trunk Based Development](https://www.atlassian.com/continuous-delivery/continuous-integration/trunk-based-development).
Cette approche présentée comme nécessaire à une véritable intégration continue ne nous semble pas permettre de tester de manière adéquate les changements à chaque intégration de code.

  **2. Des pipelines rapides et sécures :**  
Comme nous l'avons vu la stratégie Git Flow nous permet de réaliser les tests
de bout en bout uniquement une fois les changements mergés sur la branche Develop,
ce qui nous permet réduire les tests à réaliser sur les Pull-Requests.
Pour continuer notre optimisation du temps des pipelines sur les branches de feature
nous réalisons uniquement les tests sur le module qui a été modifié.
Pour implémenter cette stratégie, les pipelines se lancent en fonction du nom de la branche.
Par exemple, une branche nommée `backend/add_advantadges_items` lancera uniquement les tests du module backend,
une branche commençant par `cli/` lancera uniquement les tests du module cli et une branche commençant par `feature/` lancera les tests de tous les modules.
Les pipelines sont donc rapides et sécures, car elles exécutent tous les tests nécessaires sans exécuter de tests superflus.

  **3. Intégration rapide des changements :**  
L'existence de la branche Develop a mis-chemin entre les branches de feature et la branche main nous permet d'avoir une zone tampon où nous pouvons
intégrer rapidement nos changements sans risque que ceux-ci soient déployés.
  
  **4. Feedback rapide en cas de problème :**  
L'intégration des tests de bout en bout dans les pipelines s'exécutant sur la branche Develop lié à l'intégration de notifications Jenkins dans notre canal de communication d'équipe nous permet de corriger rapidement les problèmes qui peuvent survenir. Nous pouvons ainsi avoir confiance dans le code qui a été intégré.  
![Notification](./assets/2%20-%20DevOps-Notification.jpg)

**Autres :**

- Sur GitHub, nous bloquons le merge des Pull-requests en l'absence de status check de Jenkins (option `Require status checks to pass before merging` + `Status checks that are required: continuous-integration/jenkins/pr-head`)
- Nous vérifions dans nos pipeline de branche feature que les versions des modules à merger ne sont pas déjà présent sur Artifactory.
- L'intégration de SonarQube dans nos pipelines de branche feature nous a permis de prévenir certains bugs potentiels, principalement des risques de null pointer exceptions.
- Une fois sur Main les modules sont prêt à être livrés. C'est à ce moment que ceux-ci sont publiés sur Artifactory. Ces versions peuvent alors être utilisées par les pipelines en amonts pour réaliser les tests de bout en bout.

**Discussions et Perspectives :**  
Nous réalisons actuellement les mêmes tests de bouts en bouts sur les branches Develop, Main et sur les Releases.
En effet, nous réalisons les tests sur Develop pour avoir un feedback rapide suite au merge, nous réalisons ceux sur Main car dans la stratégie Git Flow les Hotfix ne passent pas par la branche Develop il est donc nécessaire de réaliser les tests avant le merge sur Main. Nous réalisons également les tests lors du déploiement pour vérifier. Actuellement ces tests de bout en bout sont les mêmes, mais les perspectives seraient de réaliser des tests vitaux lors des merge sur Develop ainsi que lors du déploiement et des tests de non-régression lors des merge sur Main.

**En résumé :**

- Notre projet est composé de deux branches principales (Main et Develop) ainsi que de branches de features.
- Les pipelines de features qui réalisent les tests unitaires et d'intégration des modules concernés par les changements.
  Elles vérifient la qualité du code via SonarQube et vérifient que les versions des modules que nous avons mis à jours n'existent pas déjà sur Artifactory.
- La pipeline de la branche Develop télécharge les artefacts déjà existants, build et test les nouveaux artefacts et build les images docker.
  Elle réalise ensuite les tests de bout en bout.
- Une fois que nous sommes sur la branche Main nous pouvons publier les artefacts (jar et zip) sur [Artifactory](http://vmpx07.polytech.unice.fr:8003/ui/repos/tree/General/libs-snapshot-local/fr/univ-cotedazur/mfc-backend).
- Le tag d'une nouvelle release sur la branche Main déclenche la pipeline de déploiement qui publie les images sur [DockerHub](https://hub.docker.com/u/teamgisadevops2023) et déploie le système sur la machine virtuelle.
  
![Workflow](./assets/2%20-%20DevOps-Workflow.png)

version en anglais : [2 - DevOps-Workflow (en).png](./assets/2%20-%20DevOps-Workflow%20(en).png)

## Les accès

- [Jenkins](http://vmpx07.polytech.unice.fr:8000)
- [Artifactory](http://vmpx07.polytech.unice.fr:8002)
- [SonarQube](http://vmpx07.polytech.unice.fr:8001)
- [DockerHub](https://hub.docker.com/u/teamgisadevops2023)

### Les credentials

Les credentials se trouvent sur la machine virtuelle dans `/home/teamg/isa-devops-22-23-team-g-23/workflows/env/.secrets.env`
Nous possédons également une copie des identifiants externes (DockerHub) en cas de problème avec la machine virtuelle.
