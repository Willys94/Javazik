# JAVAZIK

Application Java de gestion d'un catalogue musical et de playlists.
Architecture MVC, deux interfaces interchangeables (console et Swing),
persistance par sérialisation.

Projet réalisé à l'ECE Paris, mars-avril 2026, en équipe de 4.

## Architecture

Architecture MVC, quatre packages :

- `classes` — modèle et logique métier
- `controller` — contrôleurs
- `view` — les deux vues, console et Swing
- `main` — point d'entrée

Les deux vues s'appuient sur le même modèle et sont interchangeables.
L'état de l'application (catalogue, comptes, playlists, historiques)
est persisté par sérialisation Java.

## Fonctionnalités principales

### Invité
- consulter les morceaux et les albums
- rechercher un morceau, un album, un artiste ou un groupe
- écouter un morceau avec une limite d’écoutes

### Abonné
- se connecter
- créer, renommer et supprimer des playlists
- ajouter ou retirer des morceaux d’une playlist
- écouter des morceaux
- consulter son historique d’écoute
- consulter le nombre d’écoutes personnelles d’un morceau

### Administrateur
- ajouter et supprimer des morceaux
- ajouter et supprimer des albums
- ajouter et supprimer des artistes
- ajouter et supprimer des groupes
- afficher les abonnés
- suspendre, réactiver ou supprimer un abonné

---

## Structure du projet

Le projet est organisé autour des classes principales suivantes :

- `Main` : point d’entrée de l’application
- `AuthentificationService` : gestion de la connexion et de la création de compte
- `Utilisateurs` : classe abstraite représentant un utilisateur
- `Abonne` : gestion des playlists, de l’historique et des écoutes
- `Administrateur` : gestion du catalogue et des abonnés
- `Catalogue` : stockage et recherche des morceaux, albums, artistes et groupes
- `Morceau`, `Album`, `Artiste`, `Groupe`, `Playlist`, `Notation`
- `Interprete` : interface commune à `Artiste` et `Groupe`

---

## Lancer l'application

```bash
java -jar Javazik.jar
```

## Documentation (JavaDoc HTML)

La documentation générée se trouve dans le dossier `Javazik-javadoc/`.

### Accéder à la JavaDoc (lecture)

1. Ouvre le dossier `Javazik-javadoc/` à la racine du projet.
2. Double-clique sur `index.html` (ça ouvre la page d’accueil dans ton navigateur).

Chemin typique sur Windows :

`...\Javazik\Javazik-javadoc\index.html`

### Ouvrir rapidement depuis PowerShell

À la racine du projet :

```powershell
start .\Javazik-javadoc\index.html
```

### Regénérer la JavaDoc (à la racine du projet)

```powershell
javadoc -encoding UTF-8 -charset UTF-8 -docencoding UTF-8 `
  -d Javazik-javadoc `
  -sourcepath src `
  -subpackages classes:controller:main:view
```
