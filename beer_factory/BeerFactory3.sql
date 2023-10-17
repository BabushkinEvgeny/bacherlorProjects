-- MySQL Script generated by MySQL Workbench
-- Sat Feb 18 00:15:07 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema BeerFactory
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema BeerFactory
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `BeerFactory` DEFAULT CHARACTER SET utf8 ;
USE `BeerFactory` ;

-- -----------------------------------------------------
-- Table `BeerFactory`.`Customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`Customers` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `bank` INT NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`roles` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`BeerTypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`BeerTypes` (
  `id_type` INT NOT NULL AUTO_INCREMENT,
  `type_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_type`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`Catalog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`Catalog` (
  `beer_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  `id_type` INT NOT NULL,
  `value` DECIMAL NOT NULL,
  PRIMARY KEY (`beer_id`),
  INDEX `id_type_idx` (`id_type` ASC) VISIBLE,
  CONSTRAINT `id_type`
    FOREIGN KEY (`id_type`)
    REFERENCES `BeerFactory`.`BeerTypes` (`id_type`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`Ingredients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`Ingredients` (
  `ingredient_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL NOT NULL,
  PRIMARY KEY (`ingredient_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`BeerCompound`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`BeerCompound` (
  `id_ingredient` INT NOT NULL,
  `id_beer` INT NOT NULL,
  INDEX `ingredient_id_idx` (`id_ingredient` ASC) VISIBLE,
  INDEX `beer_id_idx` (`id_beer` ASC) VISIBLE,
  CONSTRAINT `ingredient_id`
    FOREIGN KEY (`id_ingredient`)
    REFERENCES `BeerFactory`.`Ingredients` (`ingredient_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `beer_id`
    FOREIGN KEY (`id_beer`)
    REFERENCES `BeerFactory`.`Catalog` (`beer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`Statutes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`Statutes` (
  `status_id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`status_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`Orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`Orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `status_id` INT NOT NULL,
  `total_cost` DECIMAL NOT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `status_id_idx` (`status_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `status_id`
    FOREIGN KEY (`status_id`)
    REFERENCES `BeerFactory`.`Statutes` (`status_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `customer_id`
    FOREIGN KEY (`customer_id`)
    REFERENCES `BeerFactory`.`Customers` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`BeerStorage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`BeerStorage` (
  `id_beer` INT NOT NULL,
  `amount` DECIMAL NOT NULL,
  INDEX `id_beer_idx` (`id_beer` ASC) VISIBLE,
  CONSTRAINT `c_id_beer`
    FOREIGN KEY (`id_beer`)
    REFERENCES `BeerFactory`.`Catalog` (`beer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`IngredientsStorage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`IngredientsStorage` (
  `ingredient_id` INT NOT NULL,
  `amount` DECIMAL NOT NULL,
  INDEX `ingredient_id_idx` (`ingredient_id` ASC) VISIBLE,
  CONSTRAINT `s_ingredient_id`
    FOREIGN KEY (`ingredient_id`)
    REFERENCES `BeerFactory`.`Ingredients` (`ingredient_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`Employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`Employees` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `salary` DECIMAL NOT NULL,
  `role` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `role_id_idx` (`role` ASC) VISIBLE,
  CONSTRAINT `role_id`
    FOREIGN KEY (`role`)
    REFERENCES `BeerFactory`.`roles` (`role_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`Volume`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`Volume` (
  `id_volume` INT NOT NULL AUTO_INCREMENT,
  `volume` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_volume`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`Bottles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`Bottles` (
  `bottle_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL NOT NULL,
  `id_volume` INT NOT NULL,
  PRIMARY KEY (`bottle_id`),
  INDEX `id_measure_idx` (`id_volume` ASC) VISIBLE,
  CONSTRAINT `id_measure`
    FOREIGN KEY (`id_volume`)
    REFERENCES `BeerFactory`.`Volume` (`id_volume`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`BottlesStorage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`BottlesStorage` (
  `bottle_id` INT NOT NULL,
  `amount` DECIMAL NOT NULL,
  INDEX `bottle_id_idx` (`bottle_id` ASC) VISIBLE,
  CONSTRAINT `b_bottle_id`
    FOREIGN KEY (`bottle_id`)
    REFERENCES `BeerFactory`.`Bottles` (`bottle_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`BeerBottle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`BeerBottle` (
  `beer_bottle_id` INT NOT NULL AUTO_INCREMENT,
  `id_bottle` INT NOT NULL,
  `id_beer` INT NOT NULL,
  `total_price` DECIMAL NOT NULL,
  INDEX `id_beer_idx` (`id_beer` ASC) VISIBLE,
  INDEX `id_bottle_idx` (`id_bottle` ASC) VISIBLE,
  PRIMARY KEY (`beer_bottle_id`),
  CONSTRAINT `id_beer`
    FOREIGN KEY (`id_beer`)
    REFERENCES `BeerFactory`.`Catalog` (`beer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_bottle`
    FOREIGN KEY (`id_bottle`)
    REFERENCES `BeerFactory`.`Bottles` (`bottle_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`OrderCompound`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`OrderCompound` (
  `id_beer_bottle` INT NOT NULL,
  `id_order` INT NOT NULL,
  `amount` INT NOT NULL,
  INDEX `id_beer_idx` (`id_beer_bottle` ASC) VISIBLE,
  INDEX `id_order_idx` (`id_order` ASC) VISIBLE,
  CONSTRAINT `id_beer`
    FOREIGN KEY (`id_beer_bottle`)
    REFERENCES `BeerFactory`.`BeerBottle` (`beer_bottle_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_order`
    FOREIGN KEY (`id_order`)
    REFERENCES `BeerFactory`.`Orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`LineStatutes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`LineStatutes` (
  `id_status` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_status`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BeerFactory`.`Line`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BeerFactory`.`Line` (
  `id_order` INT NOT NULL,
  `id_employee` INT NOT NULL,
  `start` DATETIME NOT NULL,
  `finish` DATETIME NOT NULL,
  `id_status` INT NOT NULL,
  INDEX `id_employee_idx` (`id_employee` ASC) VISIBLE,
  INDEX `id_order_idx` (`id_order` ASC) VISIBLE,
  INDEX `id_status_idx` (`id_status` ASC) VISIBLE,
  CONSTRAINT `id_employee`
    FOREIGN KEY (`id_employee`)
    REFERENCES `BeerFactory`.`Employees` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `id_order`
    FOREIGN KEY (`id_order`)
    REFERENCES `BeerFactory`.`Orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_status`
    FOREIGN KEY (`id_status`)
    REFERENCES `BeerFactory`.`LineStatutes` (`id_status`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
