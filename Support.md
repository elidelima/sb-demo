
### MySQL setup

#### Create new Database
```
CREATE DATABASE demo
```

#### Create products table 
```
CREATE TABLE product (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255),
description VARCHAR(255),
price DOUBLE,
quantity INT
);
```

#### Create address and customer tables 1-1
```
CREATE TABLE address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255)
);

CREATE TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    address_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES address(id)
);

INSERT INTO address (street, city, state) VALUES ('123 Main St', 'Cityville', 'CA');

INSERT INTO customer (first_name, last_name, address_id) VALUES ('John', 'Doe', 1);
```

#### Update relationship from 1-to-1 to 1-to-many
```
ALTER TABLE address 
ADD COLUMN customer_id BIGINT,
ADD CONSTRAINT fk_customer
FOREIGN KEY (customer_id)
REFERENCES customer (id);
```

#### Adding new customer and new addresses
```
INSERT INTO customer (first_name, last_name) VALUES ('Jane', 'Smith');

INSERT INTO address (street, city, state, customer_id) VALUES
('789 Pine St', 'CityC', 'SC', 1),
('101 elm St', 'CityD', 'SC', 2);

```

### Drop address_id field in table customer
```
ALTER TABLE customer
DROP FOREIGN KEY customer_ibfk_1;

ALTER TABLE customer DROP column address_id;
```

#### Update relationship 1-to-many to many-to-many
```
CREATE TABLE customer_address (
    customer_id BIGINT,
    address_id BIGINT,
    PRIMARY KEY (customer_id, address_id),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);

INSERT INTO customer_address (customer_id, address_id)
SELECT customer_id, id as address_id
FROM address;

ALTER TABLE address
DROP FOREIGN KEY fk_customer;

ALTER TABLE address
DROP COLUMN customer_id;
```

#### Create CatFact table
```
CREATE TABLE cat_facts (id INT AUTO_INCREMENT PRIMARY KEY, cat_fact_JSON JSON NOT NULL)
```

#### Create table order
```
CREATE TABLE orders( id BINARY(16) PRIMARY KEY, total DOUBLE NOT NULL);
```
#### Create table users to save CustomUser
```
CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
)
```
