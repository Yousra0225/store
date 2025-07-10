# Règles de Gestion

## Utilisateur
- **RG1 :** Un utilisateur doit avoir :
  - Un nom
  - Un prénom
  - Une adresse mail unique
  - Une adresse postale
  - Un moyen de paiement
  - Un mot de passe
- **RG2 :** Un utilisateur peut se connecter via son adresse mail et mot de passe
- **RG3 :** Un utilisateur peut ajouter des [articles](#article) dans son panier
- **RG4 :** Un utilisateur peut passer une commande à partir de son panier
- **RG5 :** Un utilisateur peut consulter l'historique de ses commandes
- **RG6 :** Un utilisateur peut gérer son profil :
  - Supprimer son compte
  - Modifier ses données personnelles

## Article
- **RG7 :** Un article doit avoir un nom
- **RG8 :** Un article doit avoir un prix
- **RG9 :** Un article doit avoir une quantité en stock
- **RG10 :** Un article doit avoir un compteur de ventes
- **RG11 :** Un article ne peut plus être ajouté au panier si la quantité vaut 0
- **RG12 :** Un article doit avoir plusieurs images
- **RG13 :** Un article doit avoir une description
- **RG14 :** Un article doit appartenir à une catégorie
- **RG15 :** Un article doit avoir une référence unique (à compléter si nécessaire)

## LigneArticle
- **RG16 :** Une ligne d'article est composée de :
  - Un article
  - Une quantité strictement positive
  - Un prix unitaire
- **RG17 :** Une ligne d'article ne doit apparaître qu'une seule fois dans le panier pour un article donné

## Commande
- **RG18 :** Une commande doit être associée à un utilisateur
- **RG19 :** Une commande doit avoir un numéro de suivi
- **RG20 :** Une commande doit contenir la liste des articles achetés
- **RG21 :** Une commande doit avoir un montant total
- **RG22 :** Une commande doit être liée à une méthode de paiement
- **RG23 :** Une commande doit avoir un montant total calculé à partir des lignes articles
- **RG24 :** Une commande doit avoir un statut parmi : en cours, payée/confirmée, expédiée, annulée, livrée

## Image
- **RG25 :** Une image est représentée par une URL
- **RG26 :** Une image est liée à un article

## Panier
- **RG27 :** Un panier peut contenir plusieurs [lignes d'article](#lignearticle)
- **RG28 :** Un panier doit afficher le prix total
- **RG29 :** Un panier peut être vide

## MoyenPaiement
- **RG30 :** Un moyen de paiement doit avoir un type (CB, PayPal, etc.)
- **RG31 :** Un moyen de paiement est associé à un utilisateur
- **RG32 :** Un moyen de paiement a une date d'enregistrement  
