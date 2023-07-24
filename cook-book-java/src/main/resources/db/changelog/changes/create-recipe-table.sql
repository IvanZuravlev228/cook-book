--liquibase formatted sql
--changeset IvanZhuravlev:create-recipe-table
CREATE TABLE recipe (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    info VARCHAR(255) NOT NULL,
    parent_id bigint,
    previous_id bigint,
    date_of_creation DATE NOT NULL,
    changed TINYINT(1) DEFAULT 0,
    FOREIGN KEY (parent_id) REFERENCES recipe(id),
    FOREIGN KEY (previous_id) REFERENCES recipe(id)
);

--rollback DROP TABLE recipe;
