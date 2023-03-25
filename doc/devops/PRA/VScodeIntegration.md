# VS Code integration SSH

- Sur VSCode, installez Remote Explorer par Microsoft [lien ici](https://marketplace.visualstudio.com/items?itemName=ms-vscode.remote-explorer)
- Ajoutez un new remote `ssh teamg@vmpx07.polytech.unice.fr`
- Connectez-vous au remote (Icone `Explorateur distant` dans le menu à gauche VSCode)
  - Ajoutez une nouvelle connexion SSH (Icone `+` en haut à droite)
  - Entrez `ssh teamg@vmpx07.polytech.unice.fr`
  - Choississez le premier choix pour le fichier de config SSH
- Terminal -> Nouveau Terminal
- Dans l'onglet `Ports` :
  - Ajoutez un forward du port 8000 (le port 8000 de votre machine sera transferé sur le port 8000 de la machine distante)
- Connectez-vous à Jenkins sur votre navigateur (localhost:8000)
- Identifiant et mot de passe : Discord#credentials
