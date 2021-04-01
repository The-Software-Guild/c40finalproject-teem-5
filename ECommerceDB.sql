-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- DROP SCHEMA IF EXISTS 'ECommerceDB';
CREATE SCHEMA IF NOT EXISTS `ECommerceDB` DEFAULT CHARACTER SET utf8 ;
USE `ECommerceDB` ;

-- -----------------------------------------------------
-- Table `mydb`.`ExchangeRate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ECommerceDB`.`ExchangeRate` (
  `exchangeId` INT NOT NULL,
  `USD` DECIMAL NOT NULL,
  `CAD` DECIMAL NOT NULL,
  `EUR` DECIMAL NOT NULL,
  `GBP` DECIMAL NOT NULL,
  `JPY` DECIMAL NOT NULL,
  PRIMARY KEY (`exchangeId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ECommerceDB`.`Customer` (
  `customerId` INT NOT NULL,
  `customerName` VARCHAR(45) NOT NULL,
  `customerEmail` VARCHAR(45) NOT NULL,
  `customerAddress` VARCHAR(45) NOT NULL,
  `customerPhone` CHAR(10) NOT NULL,
  PRIMARY KEY (`customerId`),
  UNIQUE INDEX `customerEmail_UNIQUE` (`customerEmail` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ECommerceDB`.`Purchase` (
  `purchaseId` INT NOT NULL,
  `purchaseDate` DATE NOT NULL,
  `quantity` INT NOT NULL,
  `exchangeId` INT NOT NULL,
  `customerId` INT NOT NULL,
  PRIMARY KEY (`purchaseId`),
  INDEX `fk_Purchases_ExchangeRate_idx` (`exchangeId` ASC) VISIBLE,
  INDEX `fk_Purchase_Customer1_idx` (`customerId` ASC) VISIBLE,
  UNIQUE INDEX `customerId_UNIQUE` (`customerId` ASC) VISIBLE,
  UNIQUE INDEX `exchangeId_UNIQUE` (`exchangeId` ASC) VISIBLE,
  CONSTRAINT `fk_Purchases_ExchangeRate`
    FOREIGN KEY (`exchangeId`)
    REFERENCES `ECommerceDB`.`ExchangeRate` (`exchangeId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Purchase_Customer1`
    FOREIGN KEY (`customerId`)
    REFERENCES `ECommerceDB`.`Customer` (`customerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ECommerceDB`.`Item` (
  `itemId` INT NOT NULL,
  `itemName` VARCHAR(45) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`itemId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ItemPurchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ECommerceDB`.`ItemPurchase` (
  `itemId` INT NOT NULL,
  `purchaseId` INT NOT NULL,
  PRIMARY KEY (`itemId`, `purchaseId`),
  INDEX `fk_Items_has_Purchases_Purchases1_idx` (`purchaseId` ASC) VISIBLE,
  INDEX `fk_Items_has_Purchases_Items1_idx` (`itemId` ASC) VISIBLE,
  CONSTRAINT `fk_Items_has_Purchases_Items1`
    FOREIGN KEY (`itemId`)
    REFERENCES `ECommerceDB`.`Item` (`itemId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Items_has_Purchases_Purchases1`
    FOREIGN KEY (`purchaseId`)
    REFERENCES `ECommerceDB`.`Purchase` (`purchaseId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
