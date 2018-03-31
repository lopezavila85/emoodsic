SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

SHOW WARNINGS;
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`moodCategory`
-- -----------------------------------------------------
SELECT 'emoodsic.moodCategory' AS ' ';

TRUNCATE TABLE `emoodsic`.`moodCategory`;
INSERT INTO `emoodsic`.`moodCategory` (`idMoodCategory`, `name`, `minValence`, `maxValence`, `minArousal`, `maxArousal`)
VALUES
	(1, 'Angry', 0, 0.333, 0.75, 1.0),
	(2, 'Excited', 0.333, 0.666, 0.75, 1.0),
	(3, 'Happy', 0.666, 1.0, 0.75, 1.0),
	(4, 'Nervous', 0, 0.333, 0.5, 0.75),
	(5, 'Calm', 0.333, 0.666, 0.25, 0.75),
	(6, 'Pleased', 0.666, 1.0, 0.5, 0.75),
	(7, 'Bored', 0, 0.333, 0.25, 0.5),
	(8, 'Relaxed', 0.666, 1.0, 0.25, 0.5),
	(9, 'Sad', 0, 0.333, 0, 0.25),
	(10, 'Sleepy', 0.333, 0.666, 0, 0.25),
	(11, 'Peaceful', 0.666, 1.0, 0, 0.25),
	(12, 'Unknown', 0, 1.0, 0, 1.0);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
