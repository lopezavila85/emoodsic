SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

SHOW WARNINGS;
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`musicPrefDimension`
-- -----------------------------------------------------
SELECT 'emoodsic.musicPrefDimension' AS ' ';

TRUNCATE TABLE `emoodsic`.`musicPrefDimension`;
INSERT INTO `emoodsic`.`musicPrefDimension` (`idMusicPrefDimension`, `name`, `persTraitO`, `persTraitC`, `persTraitE`,
    `persTraitA`, `persTraitN`)
VALUES     
	(1, 'Reflective-Complex', 0.425, -0.04, -0.005, 0.02, -0.06),
	(2, 'Intense-Rebellious', 0.165, -0.035, 0.04, -0.015, 0.01),
	(3, 'Upbeat-Conventional', -0.11, 0.165, 0.195, 0.235, 0.055),
	(4, 'Energetic-Rhythmic', 0.035, -0.015, 0.205, 0.085, 0);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
