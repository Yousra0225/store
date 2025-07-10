# Règles de Gestion

## Utilisateur 
- **RG1 :** Un utilisateur doit avoir : 
    - Un nom
    - Un prénom
    - Une addresse mail unique
    - Une addresse postal
    - Un moyen de paiement
    - Un mot de passe 
- **RG8 :** Un utilisateur peut se connecter via son addresse mail et mot de passe
- **RG8 :** Un utilisateur peut ajouter des [articles](#article) danc son panier
- **RG8 :** Un utilisateur peut passer une commande à partir de son panier
- **RG8 :** Un utilisateur peut consulter l'historique de ses commandes

## Article
- **RG1 :** Un article doit avoir un nom.
- **RG2 :** Un article doit avoir un prix.
- **RG3 :** Un article doit avoir une quantité en stock.
- **RG4 :** Un article doit avoir un compteur de ventes.
- **RG5 :** Un article ne peut plus être ajouté au panier si la quantité vaut 0.
- **RG6 :** Un article doit avoir plusieurs images.
- **RG7 :** Un article doit avoir une description.
- **RG7 :** Un article doit appartenir à une catégorie
-  **RG7 :** Un article doit avoir

## Commande
- **RG8 :** Une commande doit être associée à un utilisateur
- **RG9 :** Une commande doit avoir un numéro de suivi.
- **RG10 :** Une commande doit contenir la liste des articles achetés.
- **RG11 :** Une commande doit avoir un montant total.
- **RG12 :** Une commande doit être liée à une méthode de paiement. 
- **RG12 :** Une commande doit avoir un montant total calculé
- **RG12 :** Une commande doit avoir un statut  (en cours, payée/confirmée, expediée, annulée, livrée)
- **RG12 :** Une commande doit avoir  

## Image
- **RG13 :** Une image est représentée par une URL.
- **RG14 :** Une image est liée à un article.
