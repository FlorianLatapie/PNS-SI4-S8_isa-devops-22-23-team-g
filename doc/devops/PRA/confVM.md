# Configuration et utilisation de la VM

- [Configuration et utilisation de la VM](#configuration-et-utilisation-de-la-vm)
  - [Configuration de la VM](#configuration-de-la-vm)
    - [Paquets installés](#paquets-installés)
    - [Installation JFrog Artifactory](#installation-jfrog-artifactory)
    - [Smee](#smee)

## Configuration de la VM

Pour réinstaller tout le nécessaire sur la VM, il suffit de lancer le script `re-install.sh`.

### Paquets installés

- neofetch
- docker [source](https://docs.docker.com/engine/install/ubuntu/)
- github cli (gh) [source](https://github.com/cli/cli/blob/trunk/docs/install_linux.md)

### Installation JFrog Artifactory

[Source exécutable](https://jfrog.com/community/download-artifactory-oss/)
[Tuto donné par le prof](https://www.jfrog.com/confluence/display/JFROG/Installing+Artifactory#InstallingArtifactory-DockerComposeInstallation)
[Second tuto donné par le prof](https://jfrog.com/knowledge-base/artifactory-installation-quick-start-guide-docker-compose/)

- Mot de passe PostgreSQL : voir Discord#credentials

- Démarrer :
  
  ```sh
  docker-compose -p rt up -d
  ```

- Stopper :
  
  ```sh
  docker-compose -p rt down
  ```

Vérifier qu'artifactory démarre en vérifiant les logs pour le message suivant :

```sh
docker logs -f artifactory
```

### Smee

- Installez `Node.js`
- Installez le client smee : `npm install -g smee-client`
- Notre url smee `https://smee.io/VzqN7rJZTqaUKUD6`
