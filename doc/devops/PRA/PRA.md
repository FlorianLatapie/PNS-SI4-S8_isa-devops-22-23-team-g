# Plan de Reprise de l'Activité

- [Plan de Reprise de l'Activité](#plan-de-reprise-de-lactivité)
  - [Introduction](#introduction)
  - [Résolution de problèmes déjà rencontrés](#résolution-de-problèmes-déjà-rencontrés)
    - [VM](#vm)
    - [Jenkins](#jenkins)
    - [SonarQube](#sonarqube)
    - [Artifactory](#artifactory)
    - [Smee](#smee)
    - [GitHub](#github)
  - [Procédures de reconstruction](#procédures-de-reconstruction)
    - [Connexion à la VM](#connexion-à-la-vm)
      - [Unix](#unix)
      - [Windows](#windows)
    - [Create Smee payload URL](#create-smee-payload-url)
      - [Scripts](#scripts)
    - [Artifactory initialisation manuelle](#artifactory-initialisation-manuelle)
    - [SonarQube initialisation manuelle](#sonarqube-initialisation-manuelle)
    - [Redémarrage Jenkins](#redémarrage-jenkins)

## Introduction

Dans le but d'automatiser au maximum notre plan de reprise de l'activité, nous l'avons testé à plusieurs reprises sur des machines virtuelles locales sur nos ordinateurs personnels.

L'exécution du script de reconstruction dure 10 minutes. L'initialisation manuelle d'Artifactory et les étapes finales durent 15 minutes. Nous estimons donc à environs 30 minutes la reconstruction de notre environnement par une personne connaissant la procédure avec une bonne connexion internet.
Celle-ci devrait prendre moins d'une heure pour une personne ne connaissant pas la procédure et suivant les instructions du PRA.

---

## Résolution de problèmes déjà rencontrés

### VM

- Problème : plus d'accès à la VM en SSH
- Solution : demander à redémarrer la VM sur Slack

- Problème : perte d'accès a la VM liée à l'utilisation élevée de RAM
- Solution : limitation des ressources allouées aux différents containers dans le fichier docker-compose

- Problème : plus de mémoire disponible
- Solution : suppression des anciennes images et volumes Docker ainsi que des anciennes branches des pipelines Jenkins.

### Jenkins

- Problème : les pipelines ne se lancent pas malgré que Smee fonctionne et que les Webhooks sont reçus voir [logs Jenkins](http://vmpx07.polytech.unice.fr:8000/manage/log/all)
- Solution : réindexer les branches des différentes pipelines

### SonarQube

- Problème : fail de la quality gate SonarQube malgré que les conditions soient respectées
- Solution : contrôler la mémoire disponible sur la VM (voir partie [VM](#vm))

### Artifactory

- Problème : Artifactory ne démarre pas
- Solution : relancer Artifactory

### Smee

- Problème : les pipelines ne sont plus déclenchées
- Solution :
  - vérifier que la session screen est toujours active `screen -ls` et que le smeeClient est toujours lancé `screen -r smeeClient`
  - si ce n'est pas le cas, relancer le Smee Client avec [le script start-smee.sh](./scripts/start-smee.sh)
  - vérifier que le site Smee fonctionne. Si ce n'est pas le cas :
    - Créer une instance [Smee](https://github.com/probot/smee.io)
    - Ou utiliser Jenkins en mode polling

### GitHub

- Problème : Jenkins ne me laisse pas merger ma branche malgré que mes tests passent en local
- Solution : Voir les raisons de l'échec de la pipeline sur Jenkins. Généralement 2 raisons :
  1. La quality gate SonarQube n'est pas passée
  2. La version du module existe déjà sur Artifactory

---

## Procédures de reconstruction

Pour reconfigurer entièrement une VM vierge, lancer le script [PRA/scripts/reinstall.sh](./scripts/reinstall.sh).

Celui-ci commence par les étapes nécessitant une action de l'utilisateur (entrée des secrets et identifiants désirés) pour ensuite être entièrement automatisé jusqu'au lancement du système.

Les dépendances de notre environnement sont installés par les scripts [base-config.sh](./scripts/base-config.sh), [install-docker.sh](./scripts/install-docker.sh) et [install-smee.sh](./scripts/install-smee.sh).

La reconstruction de notre serveur Jenkins est réalisée grâce aux plugins [Jenkins CasC](https://plugins.jenkins.io/configuration-as-code/) (Configuration as Code) et [JobDSL](https://plugins.jenkins.io/job-dsl/) ainsi qu'à notre [script d'initialisation des secrets](./scripts/initialize-secrets.sh). Nous pouvons retrouver notre configuration Jenkins [ici](../../../workflows/jenkins_conf/casc.yaml) et un exemple de script JobDSL [ici](../../../workflows/jenkins_conf/jobdsl/deployment.groovy).

La reconstruction de notre serveur SonarQube se fait grâce à des appels API par notre [script initialize-sonarqube.sh](./scripts/initialize-sonarQube.sh).

L'installation d'Artifactory se fait par notre script [install-artifactory.sh](./scripts/install-artifactory.sh) qui utilise le package expect pour répondre aux questions du script d'installation Artifactory.

La création des images docker de nos agents est réalisée par le script [build-images.sh](./scripts/build-images.sh).

Le démarrage du système est réalisé par les scripts [start-smee.sh](./scripts/start-smee.sh) et [start-system.sh](./scripts/start-system.sh).

L'initialisation d'Artifactory n'est pas possible automatiquement. En effet, les [routes API](https://jfrog.com/help/r/jfrog-rest-apis/update-repository-configuration#~cPyV_D_DZGiPmg34wxQDQ) permettant de préconfigurer les repository nécessitent la version Pro d'Artifactory. Il est donc nécessaire de suivre les étapes de [Artifactory initialisation manuelle](#artifactory-initialisation-manuelle).

Après l'initialisation d'Artifactory redémarrer le système pour que le token soit automatiquement ajouté à Jenkins

Suivre les instructions [Redémarrage Jenkins](#redémarrage-jenkins)

---

### Connexion à la VM

#### Unix

Exécutez le fichier [PRA/scripts/rsa-key.sh](./user-scripts/rsa-key.sh)

#### Windows

- Lancez `git bash` (mais pas WSL)
- Exécutez le fichier [PRA/scripts/rsa-key.sh](./user-scripts/rsa-key.sh)

### Create Smee payload URL

[Smee.io](https://smee.io) -> Start new Channel -> `Copy Webhook Proxy URL`

[github.com/pns-isa-devops/isa-devops-22-23-team-g-23/settings/hooks](https://github.com/pns-isa-devops/isa-devops-22-23-team-g-23/settings/hooks/) -> Settings -> Webhooks -> Add Webhook  

**Configuration:**

- Payload URL: URL délivrée par Smee
- Content Type: application/json
- Which events would you like to trigger this webhook: Send me everything

#### Scripts

[Installer Smee](./scripts/install-smee.sh)  
[Lancer Smee](./scripts/start-smee.sh)

### Artifactory initialisation manuelle

- Connection :
  - Username : admin
  - Password : password
- Reset admin password : choisir un mot de passe administrateur
- Set base URL : <http://vmpx07.polytech.unice.fr:8003>
- Configure Default Proxy: skip
- Create repositories: Maven
- Next
- Finish

- Create repositories
- Add repositories -> Local Repository -> Generic
- Repository Key : generic-releases-local
- Create local repository
- Left menu : User Management -> Access Token
- Generate Token
- Token scope: Admin
- User name: jenkinstoken
- Generate
- Copy token to workflows/env/.secrets.env -> `ARTIFACTORY_TOKEN=the_generated_token`

### SonarQube initialisation manuelle

*L'initialisation de SonarQube est totalement automatisée par le script [initialize-sonarqube.sh](./scripts/initialize-sonarQube.sh). Voici tout de même les étapes qui devraient être réalisées manuellement*

Pour chaque projet, à partir de la page d'accueil

- Create project
  - Project display name: nom du projet
  - Project key: nom-du-projet
  - Main branch name: main

Création du token d'authentification:

- Administrator (en haut à droit) -> My Account -> Security -> Generate Tokens
  - name: sonarqube-global-token
  - type: Global Analysis Token
  - Expires In: au choix
- Copier ce token d'authentification dans :
  - isa-devops-22-23-team-g-23/workflows/env/.secrets.env
  - SONARQUBE_TOKEN=token_genere
Redémarrer Jenkins, cette variable d'environnement sera utilisée par Jenkins CASC pour initialiser le token SonarQube

### Redémarrage Jenkins

Pour chacune des pipelines cliquer sur :

- le nom de la pipeline
- configure dans le menu à gauche
- save

Cette procédure permet de s'assurer de la bonne indexation des branches par Jenkins (étape nécessaire de par l'utilisation du plugin JobDSL).
