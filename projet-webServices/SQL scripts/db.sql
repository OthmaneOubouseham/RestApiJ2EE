-- -----------------------------------------------------
-- CREATING TABLES
-- -----------------------------------------------------

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema web_services
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `web_services` ;

-- -----------------------------------------------------
-- Schema web_services
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `web_services` DEFAULT CHARACTER SET utf8 ;
USE `web_services` ;

-- -----------------------------------------------------
-- Table `web_services`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `web_services`.`user` ;

CREATE TABLE IF NOT EXISTS `web_services`.`user` (
  `ID_user` INT NOT NULL AUTO_INCREMENT,
  `pseudo` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `date_creation` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_user`)
  )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_services`.`multimedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `web_services`.`multimedia` ;

CREATE TABLE IF NOT EXISTS `web_services`.`multimedia` (
  `ID_multimedia` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255)  NULL,
  `description` VARCHAR(510) NULL,
  `language` VARCHAR(2)  NULL,
  `genre` VARCHAR(255) NULL,
  `category` TINYINT(3)  NULL,
  `status` TINYINT(3)  NULL,
  `date_status` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP,
  `date_upload` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP,
  `date_release` DATE NULL,
  `ID_uploader` INT  NULL,
  PRIMARY KEY (`ID_multimedia`),
    FOREIGN KEY (`ID_uploader`)
    REFERENCES `web_services`.`user` (`ID_user`)
	ON DELETE CASCADE
    )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_services`.`book`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `web_services`.`book` ;

CREATE TABLE IF NOT EXISTS `web_services`.`book` (
  `ID_book` INT NOT NULL AUTO_INCREMENT,
  `author` VARCHAR(100) NOT NULL,
  `publisher` VARCHAR(100) NULL,
  `ID_multimedia` INT NOT NULL,
  PRIMARY KEY (`ID_book`),
    FOREIGN KEY (`ID_multimedia`)
    REFERENCES `web_services`.`multimedia` (`ID_multimedia`)
	ON DELETE CASCADE
    )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_services`.`videoGame`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `web_services`.`videoGame` ;

CREATE TABLE IF NOT EXISTS `web_services`.`videoGame` (
  `ID_videoGame` INT NOT NULL AUTO_INCREMENT,
  `developer` VARCHAR(100) NOT NULL,
  `publisher` VARCHAR(100) NULL,
  `ID_multimedia` INT NOT NULL,
  PRIMARY KEY (`ID_videoGame`),
    FOREIGN KEY (`ID_multimedia`)
    REFERENCES `web_services`.`multimedia` (`ID_multimedia`)
	ON DELETE CASCADE
    )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_services`.`film`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `web_services`.`film` ;

CREATE TABLE IF NOT EXISTS `web_services`.`film` (
  `ID_film` INT NOT NULL AUTO_INCREMENT,
  `director` VARCHAR(100) NOT NULL,
  `productor` VARCHAR(100) NULL,
  `mainCast` VARCHAR(255) NULL,
  `duration` TIME NULL,
  `ID_multimedia` INT NOT NULL,
  PRIMARY KEY (`ID_film`),
    FOREIGN KEY (`ID_multimedia`)
    REFERENCES `web_services`.`multimedia` (`ID_multimedia`)
	ON DELETE CASCADE
    )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_services`.`rate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `web_services`.`rate` ;

CREATE TABLE IF NOT EXISTS `web_services`.`rate` (
  `ID_rate` INT NOT NULL AUTO_INCREMENT,
  `value` TINYINT(3) NOT NULL,
  `date_creation` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ID_user` INT NOT NULL,
  `ID_multimedia` INT NOT NULL,
  PRIMARY KEY (`ID_rate`),
    FOREIGN KEY (`ID_user`)
    REFERENCES `web_services`.`user` (`ID_user`)
	ON DELETE CASCADE,
    FOREIGN KEY (`ID_multimedia`)
    REFERENCES `web_services`.`multimedia` (`ID_multimedia`)
	ON DELETE CASCADE
    )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_services`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `web_services`.`comment` ;

CREATE TABLE IF NOT EXISTS `web_services`.`comment` (
  `ID_comment` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(255) NOT NULL,
  `date_creation` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ID_user` INT NOT NULL,
  `ID_multimedia` INT NOT NULL,
  PRIMARY KEY (`ID_comment`),
    FOREIGN KEY (`ID_user`)
    REFERENCES `web_services`.`user` (`ID_user`)
	ON DELETE CASCADE,
    FOREIGN KEY (`ID_multimedia`)
    REFERENCES `web_services`.`multimedia` (`ID_multimedia`)
	ON DELETE CASCADE
    )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- View `web_services`.`book_v`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `web_services`.`book_v` ;

CREATE VIEW book_v 
	AS SELECT m.ID_multimedia, m.title, m.description, m.language, m.genre, m.category, m.status, m.date_status, m.date_upload, m.date_release, m.ID_uploader, b.ID_book, b.author, b.publisher
	FROM multimedia m
		INNER JOIN book b 
		ON m.ID_multimedia = b.ID_multimedia;


-- -----------------------------------------------------
-- View `web_services`.`film_v`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `web_services`.`film_v` ;

CREATE VIEW film_v
	AS SELECT m.ID_multimedia, m.title, m.description, m.language, m.genre, m.category, m.status, m.date_status, m.date_upload, m.date_release, m.ID_uploader, f.ID_film, f.director, f.productor, f.mainCast, f.duration
	FROM multimedia m
		INNER JOIN film f
		ON m.ID_multimedia = f.ID_multimedia;


-- -----------------------------------------------------
-- View `web_services`.`videoGame_v`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `web_services`.`videoGame_v` ;

CREATE VIEW videoGame_v
	AS SELECT m.ID_multimedia, m.title, m.description, m.language, m.genre, m.category, m.status, m.date_status, m.date_upload, m.date_release, m.ID_uploader, v.ID_videoGame, v.developer, v.publisher
	FROM multimedia m
		INNER JOIN VideoGame v
		ON m.ID_multimedia = v.ID_multimedia;


		
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;