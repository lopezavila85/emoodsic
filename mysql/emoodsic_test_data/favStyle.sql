SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

SHOW WARNINGS;
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`favStyle`
-- -----------------------------------------------------
SELECT 'emoodsic.favStyle' AS ' ';

TRUNCATE TABLE `emoodsic`.`favStyle`;
INSERT INTO `emoodsic`.`favStyle` (`idFavStyle`, `idUser`, `idStyle`)
VALUES
	(1, 1, 24),
	(2, 1, 61),
	(3, 1, 93),
	(4, 2, 52),
	(5, 2, 60),
	(6, 2, 65),
	(7, 3, 65),
	(8, 3, 66),
	(9, 3, 69),
	(10, 4, 20),
	(11, 4, 88),
	(12, 4, 95),
	(13, 5, 19),
	(14, 5, 44),
	(15, 5, 66),
	(16, 6, 19),
	(17, 6, 83),
	(18, 6, 87),
	(19, 7, 44),
	(20, 7, 60),
	(21, 7, 80),
	(22, 8, 2),
	(23, 8, 44),
	(24, 8, 69),
	(25, 9, 12),
	(26, 9, 27),
	(27, 9, 46),
	(28, 10, 44),
	(29, 10, 69),
	(30, 10, 87),
	(31, 11, 4),
	(32, 11, 25),
	(33, 11, 66),
	(34, 12, 21),
	(35, 12, 23),
	(36, 12, 80),
	(37, 13, 29),
	(38, 13, 46),
	(39, 13, 80),
	(40, 14, 17),
	(41, 14, 59),
	(42, 14, 83),
	(43, 15, 16),
	(44, 15, 29),
	(45, 15, 59),
	(46, 16, 60),
	(47, 16, 69),
	(48, 16, 80);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
