SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

SHOW WARNINGS;
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`musicPrefDimStyle`
-- 150823: 'spanish' styles also considered in each dimension
-- -----------------------------------------------------

SELECT 'emoodsic.musicPrefDimStyle' AS ' ';

TRUNCATE TABLE `emoodsic`.`musicPrefDimStyle`;
INSERT INTO `emoodsic`.`musicPrefDimStyle` (`idMusicPrefDimStyle`, `idMusicPrefDimension`, `idStyle`)
VALUES
    (1, 1, 8),
	(2, 1, 16),
	(3, 1, 29),
	(4, 1, 46),
	(5, 2, 2),
	(6, 2, 40),
	(7, 2, 69),
	(8, 3, 17),
	(9, 3, 59),
	(10, 3, 68),
	(11, 3, 76),
	(12, 4, 19),
	(13, 4, 25),
	(14, 4, 30),
	(15, 4, 42),
	(16, 4, 65),
	(17, 4, 75),
	(18, 1, 31),
	(19, 1, 78),
	(20, 2, 77),
	(21, 2, 87),
	(22, 3, 83),
	(23, 4, 86);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
