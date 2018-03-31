SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

SHOW WARNINGS;
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`recommendationType`
-- -----------------------------------------------------
SELECT 'emoodsic.recommendationType' AS ' ';

TRUNCATE TABLE `emoodsic`.`recommendationType`;
INSERT INTO `emoodsic`.`recommendationType` (`idRecommendationType`, `name`)
VALUES
	(1, 'artist'),
	(2, 'style'),
	(3, 'personality'),
	(4, 'cfPersonality'),
	(5, 'cfNeighbor');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
