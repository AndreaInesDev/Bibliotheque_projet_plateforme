# 📚 Système de Gestion de Bibliothèque (Library Management System)

Une application Java robuste pour gérer les livres, les membres et le cycle complet des emprunts avec calcul automatique des amendes de retard.

## 🚀 Fonctionnalités

### 📖 Gestion des Livres
* Ajout, recherche, affichage et suppression de livres.
* Gestion dynamique des stocks (incrémentation/décrémentation automatique lors des emprunts et retours).

### 👥 Gestion des Membres
* Enregistrement de nouveaux membres.
* Liste complète des adhérents.

### 🔄 Cycle des Emprunts (Cœur du projet)
* **Emprunt de livres** : Vérification de la disponibilité et fixation d'une date de retour.
* **Retour de livres** :
    * Mise à jour automatique du stock.
    * Détection des doublons (impossible de rendre deux fois le même livre).
* **Gestion des Retards** :
    * Calcul en temps réel des jours de dépassement.
    * Calcul automatique des amendes (ex: 100 FCFA/jour) et enregistrement en base de données.
    * Liste filtrée des emprunts en retard.

## 🛠️ Technologies Utilisées
* **Langage** : Java 21
* **Base de données** : MySQL 9.1
* **Pilote JDBC** : MySQL Connector/J
* **Outils** : Maven (pour la gestion des dépendances), IntelliJ IDEA

## 📋 Prérequis
* Java JDK 21 ou supérieur.
* Un serveur MySQL local.
* Le driver MySQL JDBC (`mysql-connector-j`).

## ⚙️ Configuration de la Base de Données
Créez une base de données nommée `bibliotheque` et exécutez les scripts pour créer les tables :
- `Livres` (id, titre, auteur, categorie, nbre_exemplaire)
- `Membres` (id, nom, ...)
- `Emprunt` (id, dateEmprunt, dateRetour, dateRetourEffective, id_livre, id_membre, amande_paye)

## 🖥️ Utilisation
Lancez la classe `Main.java`. Un menu interactif s'affiche dans la console :
1. Choisissez une option (ex: 5 pour emprunter, 7 pour retourner).
2. Suivez les instructions affichées à l'écran.

## ✒️ Auteur
* **Otele Andréa inès** - *Développement Complet*