# JAVAZIK

## Description

JAVAZIK est une application Java en console permettant de gérer un catalogue musical.  
L’application propose trois modes d’utilisation :
- administrateur
- abonné
- invité

Un utilisateur peut se connecter, créer un compte abonné ou entrer en tant qu’invité.  
Selon son rôle, il peut consulter le catalogue, rechercher des morceaux, albums, artistes ou groupes, gérer des playlists, écouter des morceaux ou administrer le catalogue.

---

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
