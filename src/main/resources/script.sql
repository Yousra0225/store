-- Extension UUID pour PostgreSQL
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Article (
    Id_Article UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    article_name VARCHAR(255) NOT NULL,
    article_price DECIMAL(10,2) NOT NULL,
    article_stock INT NOT NULL,
    article_sales INT NOT NULL DEFAULT 0,
    article_description TEXT NOT NULL
);

CREATE TABLE Commande (
    Id_Commande UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    command_payment_method VARCHAR(100) NOT NULL,
    command_client_firstname VARCHAR(100) NOT NULL,
    command_client_lastname VARCHAR(100) NOT NULL,
    command_client_email VARCHAR(255) NOT NULL,
    command_client_address VARCHAR(255) NOT NULL,
    command_total_price DECIMAL(10,2) NOT NULL,
    command_tracking_number UUID NOT NULL DEFAULT uuid_generate_v4(),
    UNIQUE(command_tracking_number)
);

CREATE TABLE Image (
    Id_Image UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    image_url VARCHAR(255) NOT NULL,
    Id_Article UUID NOT NULL,
    FOREIGN KEY (Id_Article) REFERENCES Article(Id_Article) ON DELETE CASCADE
);

CREATE TABLE Commande_Article (
    Id_Commande UUID NOT NULL,
    Id_Article UUID NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (Id_Commande, Id_Article),
    FOREIGN KEY (Id_Commande) REFERENCES Commande(Id_Commande) ON DELETE CASCADE,
    FOREIGN KEY (Id_Article) REFERENCES Article(Id_Article) ON DELETE CASCADE
);
