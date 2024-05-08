# XML-Tux-Game
#### Auteurs: Ciré Keita & Higo Medeiros (https://github.com/MedeirosHigo)
#### Université Grenoble Alpes - Projet XML et Java (L3 MIAGE)
# I. INTRODUCTION

## A. Présentation du Projet
Le projet TUX est un jeu utilisant un environnement 3D, avec les technologies xml et java, où un personnage (en l'occurrence, un pingouin) collecte des lettres pour former un mot.
Dans notre concept, les lettres doivent être collectées dans un ordre spécifique et disparaissent progressivement une fois récupérées. Chaque partie est limitée à 20 secondes, et elle se termine soit lorsque le mot est complété, soit lorsque le temps imparti est écoulé. Dans ce dernier cas, les joueurs ont la possibilité de terminer la partie en indiquant leur progression.

<img width="448" alt="Capture d'écran 2024-03-16 085024" src="https://github.com/MedeirosHigo/XML-Tux-Game/assets/163612912/74fe5a27-393d-47ef-9021-1879505c890c">


## B. Objectifs et Enjeux
Les deux applications visent à atteindre plusieurs objectifs clés :
- Offrir un premier menu permettant de :
  - créer un nouveau joueur
  - charger le profil d'un joueur existant
  - afficher la page des meilleurs scores
  - quitter le jeu.
- Une fois un joueur actif, proposer un second menu pour :
  - éditer son profil
  - lancer une nouvelle partie
  - charger une partie existante
  - quitter le jeu.
- Pour une nouvelle partie, permettre la sélection d'un niveau de difficulté et charger un mot associé depuis le dictionnaire.
- Stocker les mots, qui respectent certaines contraintes (au moins 3 lettres, associés à une difficulté...), dans un dictionnaire.
- Terminer la partie lorsque les lettres sont collectées dans le bon ordre ou lorsque le temps imparti est écoulé, avec la possibilité de reprendre là où on s'est arrêté.

# II. IMPLEMENTATION

Certaines consignes pour l'implémentation du jeu ont été fournies tout au long des Travaux Pratiques. Nous avions cependant une liberté de choix concernant les règles du jeu. Voici quelques détails sur certains fichiers écrits durant le semestre :

- `dico.xml` : fichier du dictionnaire stockant tous les mots utilisés dans le jeu. Il doit contenir au moins 5 mots, chacun associé à une difficulté.
- `dico.xsd` : fichier XML Schema appliquant les contraintes sur les mots stockés dans `dico.xml`.
- `profil.xml` : fichier XML contenant les informations du joueur actif, allant de son nom aux détails de la partie (temps écoulé, niveau de difficulté, mot).
- `LanceurDeJeu.java` : classe JAVA gérant les jeux et les joueurs, maintenant l'application active et permettant de choisir un jeu, de le démarrer, de choisir un joueur et de quitter le jeu.
- `Jeu.java` : classe JAVA permettant de créer un nouvel environnement de jeu avec sa représentation graphique. Elle contient des méthodes pour afficher les menus du jeu, la liste des parties d'un joueur, les mots pendant 5 secondes avant le début de la partie, contrôler les mouvements du personnage et gérer les collisions entre les lettres et l'environnement, etc.
