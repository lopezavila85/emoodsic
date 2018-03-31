SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

SHOW WARNINGS;
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`sortOrder`
-- L = Left; R = Right; D = Down; U = Up
-- -----------------------------------------------------
SELECT 'emoodsic.sortOrder' AS ' ';

TRUNCATE TABLE `emoodsic`.`sortOrder`;
INSERT INTO `emoodsic`.`sortOrder` (`idSortOrder`, `name`)
VALUES
	(1, 'LRDU'),
	(2, 'RLDU'),
	(3, 'RLUD'),
	(4, 'LRUD');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
