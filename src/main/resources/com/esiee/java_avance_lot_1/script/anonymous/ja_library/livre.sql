create table livre
(
    idLivre      int auto_increment
        primary key,
    titre        varchar(45) null,
    auteur       varchar(45) null,
    presentation varchar(45) null,
    parution     int         null,
    colonne      varchar(45) null,
    rangee       varchar(45) null,
    image        varchar(45) null
);

