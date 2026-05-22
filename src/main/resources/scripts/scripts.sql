CREATE TABLE item (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(18,4) NOT NULL,
    weight DECIMAL(10,2)
);

CREATE TABLE kart (
    id BIGINT PRIMARY KEY,
	items JSONB,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES item(id),
    FOREIGN KEY (user_id) REFERENCES tbl_user(id)
);

CREATE TABLE tbl_user (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    address VARCHAR(500)
);



CREATE SEQUENCE item_id_seq START 1;
ALTER TABLE item ALTER COLUMN id SET DEFAULT nextval('item_id_seq');

CREATE SEQUENCE kart_id_seq START 1;
ALTER TABLE kart ALTER COLUMN id SET DEFAULT nextval('kart_id_seq');

CREATE SEQUENCE tbl_user_id_seq START 1;
ALTER TABLE tbl_user ALTER COLUMN id SET DEFAULT nextval('tbl_user_id_seq');