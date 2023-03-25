# Plan de Reprise de l'Activité

## Problèmes potentiels

### Smee

### Jenkins

### Accès VM

## Procédures de reconstruction

### Introduction

Pour reconfigurer entièrement une VM vierge, lancer le script [reinstall](./scripts/reinstall.sh).

### Connexion à la VM

#### Unix

Exécutez le fichier [rsa-key](./user-scripts/rsa-key.sh)

#### Windows

- Lancez `git bash`
- Exécutez le fichier [rsa-key](./user-scripts/rsa-key.sh)

### Installation des dépendances

#### Script

### Jenkins

#### Création des pipelines:
*Le même protocole et à adapter pour chaque pipelines à créer (backend, cli, bank)*

 - Dashboard -> New Item
 - Entrer le nom de la pipeline (ex: backend)
 - Selectionner Multibranch pipeline
 - Cliquer sur suivant
 - Display name: nom de la pipeline (ex: backend)
 - Add source -> Github
 - Credentials: FlorianLatapie
 - Repository URL: [https://github.com/pns-isa-devops/isa-devops-22-23-team-g-23](https://github.com/pns-isa-devops/isa-devops-22-23-team-g-23)
 - Behaviors -> Add -> Custom Github Notification Context
 - Apply suffix
 - Label continuous-integration/jenkins/nom_de_la_pipeline
 - Script path: chemin de la pipeline dans le repository (ex: workflows/backend.Jenkinsfile)
 - Save

#### Script

### Smee
[Smee](https://smee.io) -> Start new Channel -> `Copy Webhook Proxy URL`

[Github](https://github.com/pns-isa-devops/isa-devops-22-23-team-g-23/settings/hooks/) -> Settings -> Webhooks -> Add Webhook  

**Configuration:**

- Payload URL: URL délivrée par Smee
- Content Type: application/json
- Which events would you like to trigger this webhook: Send me everything

#### Scripts
[Installer Smee](./scripts/install-smee.sh)  
[Lancer Smee](./scripts/start-smee.sh)

### Artifactory

#### Script

### DockerHub

#### Script

### SonarQube
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

#### Script
