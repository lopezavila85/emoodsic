SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';
SHOW WARNINGS;

-- Insertar datos ejecutando este comando
-- mysql --default-character-set=utf8 -u root -p emoodsic < emoodsic_insert.sql

-- -----------------------------------------------------
-- emoodsic Schema
-- -----------------------------------------------------
SELECT '*** emoodsic *** ' AS ' ';
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`bigFiveInventory`
-- -----------------------------------------------------
source ./bigFiveInventory.sql;

-- -----------------------------------------------------
-- Table `emoodsic`.`education`
-- -----------------------------------------------------
source ./education.sql;

-- -----------------------------------------------------
-- Table `emoodsic`.`favArtist`
-- -----------------------------------------------------
source ./favArtist.sql;

-- -----------------------------------------------------
-- Table `emoodsic`.`favStyle`
-- -----------------------------------------------------
source ./favStyle.sql;

-- -----------------------------------------------------
-- Table `emoodsic`.`mood`
-- -----------------------------------------------------
source ./mood.sql;

-- -----------------------------------------------------
-- Table `emoodsic`.`moodCategory`
-- -----------------------------------------------------
source ./moodCategory.sql;

-- -----------------------------------------------------
-- Table `emoodsic`.`musicKnowledge`
-- -----------------------------------------------------
source ./musicKnowledge.sql;

-- -----------------------------------------------------
-- Table `emoodsic`.`musicPrefDimension`
-- -----------------------------------------------------
source ./musicPrefDimension.sql;

-- -----------------------------------------------------
-- Table `emoodsic`.`musicPrefDimStyle`
-- -----------------------------------------------------
source ./musicPrefDimStyle.sql;

-- -----------------------------------------------------
-- Table `emoodsic`.`recommendationType`
-- -----------------------------------------------------
source ./recommendationType.sql;

-- -----------------------------------------------------
-- Table `emoodsic`.`sortOrder`
-- -----------------------------------------------------
source ./sortOrder.sql;

-- -----------------------------------------------------
-- Table `emoodsic`.`style`
-- -----------------------------------------------------
source ./style.sql;

-- -----------------------------------------------------
-- Table `emoodsic`.`user`
-- -----------------------------------------------------
source ./user.sql;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
