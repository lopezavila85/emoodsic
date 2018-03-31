SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

SHOW WARNINGS;
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`musicKnowledge`
-- -----------------------------------------------------
SELECT 'emoodsic.musicKnowledge' AS ' ';

TRUNCATE TABLE `emoodsic`.`musicKnowledge`;
INSERT INTO `emoodsic`.`musicKnowledge` (`idMusicKnowledge`, `name`)
VALUES
	(1, 'None'),
	(2, 'Novice'),
	(3, 'Medium'),
	(4, 'Expert');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
