DROP DATABASE IF EXISTS ECommerceDB;
CREATE DATABASE ECommerceDB;

USE ECommerceDB;

-- -----------------------------------------------------
-- Table `ExchangeRate`
-- -----------------------------------------------------
CREATE TABLE exchangeRate (
  exchangeId INT PRIMARY KEY AUTO_INCREMENT,
  cad DECIMAL(6,4) NOT NULL,
  eur DECIMAL(6,4) NOT NULL,
  gbp DECIMAL(6,4) NOT NULL,
  jpy DECIMAL(8,4) NOT NULL,
  cny DECIMAL(6,4) NOT NULL
);

-- -----------------------------------------------------
-- Table `Address`
-- -----------------------------------------------------
CREATE TABLE address (
  addressId INT PRIMARY KEY AUTO_INCREMENT,
  street VARCHAR(30) NOT NULL,
  city VARCHAR(30) NOT NULL,
  state CHAR(2) NOT NULL,
  postal VARCHAR(10) NOT NULL,
  country VARCHAR(30) NOT NULL
);

-- -----------------------------------------------------
-- Table `Customer`
-- -----------------------------------------------------
CREATE TABLE customer (
  customerId INT PRIMARY KEY AUTO_INCREMENT,
  customerName VARCHAR(50) NOT NULL,
  customerEmail VARCHAR(50) NOT NULL,
  customerPhone CHAR(10) NOT NULL,
  addressId INT NOT NULL,
	FOREIGN KEY (addressId) REFERENCES address (addressId)
);

-- -----------------------------------------------------
-- Table `Purchase`
-- -----------------------------------------------------
CREATE TABLE purchase (
  purchaseId INT PRIMARY KEY AUTO_INCREMENT,
  purchaseDate DATE DEFAULT(CURRENT_DATE()),
  currency CHAR(3) DEFAULT 'USD',
  exchangeId INT NOT NULL,
  customerId INT NOT NULL,
	FOREIGN KEY (exchangeId) REFERENCES ExchangeRate (exchangeId),
    FOREIGN KEY (customerId) REFERENCES Customer (customerId)
);

-- -----------------------------------------------------
-- Table `Item`
-- -----------------------------------------------------
CREATE TABLE item (
  itemId INT PRIMARY KEY,
  itemName VARCHAR(50) NOT NULL,
  category VARCHAR(30) NOT NULL,
  price DECIMAL(6,2) NOT NULL,
  quantity INT DEFAULT 1
);

-- -----------------------------------------------------
-- Table `ItemPurchase`
-- -----------------------------------------------------
CREATE TABLE item_purchase (
  itemId INT NOT NULL,
  purchaseId INT NOT NULL,
  PRIMARY KEY (itemId, purchaseId),
    FOREIGN KEY (itemId) REFERENCES item (itemId),
    FOREIGN KEY (purchaseId) REFERENCES purchase (purchaseId)
);