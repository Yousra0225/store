# Store

Backend d’une boutique en ligne (catalogue, panier, commandes). Application REST uniquement : pas d’interface web, base de données et API exposées pour une démo des bonnes pratiques Spring Boot, Docker et CI/CD.

## Présentation

Store est un projet d’exercice qui met en place :

- Une API REST pour la gestion des articles, catégories, utilisateurs, panier et commandes
- Une base de données relationnelle (H2 en mémoire par défaut) avec JPA/Hibernate
- Une sécurisation (Spring Security, HTTP Basic, mots de passe hashés en BCrypt)
- Un pipeline CI/CD (GitHub Actions) avec tests unitaires et tests d’intégration
- Un conteneur Docker multi-stage pour build et exécution

Aucun système de paiement réel n’est intégré : un service factice (stub) est utilisé pour la création de commandes.

## Technologies

- Java 17
- Spring Boot 3.5
- Spring Data JPA, Spring Security, Spring Validation
- H2 (base en mémoire)
- Maven, Docker

## Prérequis

- JDK 17
- Maven 3.8+ (ou Maven Wrapper fourni)

## Lancer l’application

```bash
./mvnw spring-boot:run
```

L’API est disponible sur `http://localhost:8080`.

## Build et tests

```bash
# Compilation
./mvnw compile

# Tests unitaires
./mvnw test

# Tests d’intégration (après package)
./mvnw verify -DskipTests

# Compilation + tous les tests
./mvnw verify
```

## Docker

```bash
# Construction de l’image
docker build -t store:latest .

# Exécution du conteneur (port 8080)
docker run -p 8080:8080 store:latest
```

## Fonctionnalités actuelles

### Authentification et utilisateurs

- **Inscription** : `POST /api/auth/register` (corps JSON : firstName, lastName, email, postalAddress, password, gender). Crée un utilisateur et un panier associé.
- **Profil** : `GET /api/users/me` (authentification HTTP Basic requise : email + mot de passe).

### Catalogue

- **Articles** : liste et détail en lecture publique ; création et suppression réservées aux utilisateurs authentifiés.
  - `GET /api/articles` : liste des articles
  - `GET /api/articles/{id}` : détail d’un article
  - `POST /api/articles` : création (body JSON, authentification requise)
  - `DELETE /api/articles/{id}` : suppression (authentification requise)
- **Catégories** : lecture et création.
  - `GET /api/categories` : liste des catégories
  - `GET /api/categories/{id}` : détail d’une catégorie
  - `POST /api/categories` : création

### Panier

Tous les endpoints du panier nécessitent une authentification HTTP Basic.

- `GET /api/panier` : contenu du panier de l’utilisateur connecté (lignes + total)
- `POST /api/panier/lignes` : ajout d’une ligne (body : articleId, quantity)
- `DELETE /api/panier/lignes/{ligneId}` : suppression d’une ligne

### Commandes

- `POST /api/commandes` : création d’une commande à partir du panier (body : paymentMethodType, ex. "CARTE"). Vide le panier et appelle le service de paiement factice. Authentification requise.
- `GET /api/commandes` : liste des commandes de l’utilisateur connecté
- `GET /api/commandes/{id}` : détail d’une commande (si elle appartient à l’utilisateur)

### Sécurité

- Lecture des articles et catégories : accès public
- Inscription : accès public
- Panier, commandes, profil, création/suppression d’articles : authentification HTTP Basic (email = identifiant, mot de passe)

## Structure du projet

- `src/main/java/com/yousra/store` : code applicatif (controllers, services, repositories, modèles, sécurité, DTO, gestion d’erreurs)
- `src/main/resources` : configuration (application.properties), page statique minimale
- `src/test/java` : tests unitaires (services, repository) et tests d’intégration (API)
- `docs/` : règles de gestion (`regles-de-gestion.md`), script SQL, schéma (MCD)
- `.github/workflows` : pipeline CI (compilation, tests unitaires, tests d’intégration, build Docker)

## Documentation métier

Les règles de gestion (utilisateur, article, panier, commande, etc.) sont décrites dans `docs/regles-de-gestion.md`.
