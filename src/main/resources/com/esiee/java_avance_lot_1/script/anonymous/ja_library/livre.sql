create table livre
(
    idLivre      int auto_increment
        primary key,
    titre        MEDIUMTEXT null,
    auteur       MEDIUMTEXT null,
    presentation MEDIUMTEXT null,
    parution     int         null,
    colonne      varchar(45) null,
    rangee       varchar(45) null,
    image        MEDIUMTEXT null,
    etat         varchar(10) default 'false'
);

