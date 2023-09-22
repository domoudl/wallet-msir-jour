Feature: Gestion des comptes

  Scenario: Créer un compte
    Given creer un compte avec comme informations nom "Fall", prenom "Modou", type de compte "epargne", email "mdfall@gmail.com", mot de passe "fall12@md"
    When le client envoie une demande de création de compte
    Then le statut de la réponse devrait renvoyé le code 201

  Scenario: Consulter le solde d'un compte
    Given Un client nous fournit son numero de compte avec l'ID 1
    When le client consulte le solde de son compte
    Then le solde du compte devrait être retournée avec la valeur 2000.0

  Scenario: Effectuer un dépôt sur un compte
    Given un client avec un compte existant ayant l'ID 1
    And le montant à déposer est de 1000.0
    When le client effectue le dépôt
    Then le solde du compte devrait être de 10000.0

  Scenario: Effectuer un retrait sur un compte
    Given un client avec un compte existant ayant l'ID 1
    When le client effectue un retrait de 5000.0
    Then le solde du compte devrait être de 15000.0

    Given un client avec un compte existant ayant l'ID 1
    When le client effectue un retrait de -2000.0
    Then une erreur devrait être renvoyée avec le message "Solde retrait négatif ou nul"

    Given un client avec un compte existant ayant l'ID 1
    When le client effectue un retrait de 70000.0
    Then une erreur devrait être renvoyée avec le message "Solde Insuffisant"

  Scenario: Récupérer un compte par son identifaint
    Given un client a un compte existant avec l'ID 1
    When le client demande les informations du compte
    Then les informations du compte devrait être retournées avec les infos clients : id 1 nom "Fall", prenom "Modou", type de compte "epargne", email "mdfall@gmail.com", mot de passe "fall12@md", et compte :  type de compte "epargne" et comme solde 5000.0
