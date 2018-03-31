SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

SHOW WARNINGS;
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`mood`
-- -----------------------------------------------------
SELECT 'emoodsic.mood' AS ' ';

TRUNCATE TABLE `emoodsic`.`mood`;
INSERT INTO `emoodsic`.`mood` (`idMood`, `idMoodCategory`, `name`)
VALUES
	(1, 1, 'aggressive'),
	(2, 5, 'ambient'),
	(3, 1, 'angry'),
	(4, 4, 'angst-ridden'),
	(5, 3, 'bouncy'),
	(6, 5, 'calming'),
	(7, 8, 'carefree'),
	(8, 3, 'cheerful'),
	(9, 7, 'cold'),
	(10, 4, 'complex'),
	(11, 5, 'cool'),
	(12, 9, 'dark'),
	(13, 2, 'disturbing'),
	(14, 2, 'dramatic'),
	(15, 5, 'dreamy'),
	(16, 4, 'eerie'),
	(17, 12, 'elegant'),
	(18, 2, 'energetic'),
	(19, 2, 'enthusiastic'),
	(20, 12, 'epic'),
	(21, 6, 'fun'),
	(22, 12, 'funky'),
	(23, 12, 'futuristic'),
	(24, 11, 'gentle'),
	(25, 3, 'gleeful'),
	(26, 9, 'gloomy'),
	(27, 3, 'groovy'),
	(28, 3, 'happy'),
	(29, 1, 'harsh'),
	(30, 4, 'haunting'),
	(31, 6, 'humorous'),
	(32, 10, 'hypnotic'),
	(33, 2, 'industrial'),
	(34, 1, 'intense'),
	(35, 2, 'intimate'),
	(36, 3, 'joyous'),
	(37, 8, 'laid-back'),
	(38, 3, 'light'),
	(39, 3, 'lively'),
	(40, 4, 'manic'),
	(41, 5, 'meditation'),
	(42, 9, 'melancholia'),
	(43, 5, 'mellow'),
	(44, 5, 'mystical'),
	(45, 9, 'ominous'),
	(46, 6, 'party music'),
	(47, 2, 'passionate'),
	(48, 5, 'pastoral'),
	(49, 11, 'peaceful'),
	(50, 3, 'playful'),
	(51, 9, 'poignant'),
	(52, 10, 'quiet'),
	(53, 1, 'rebellious'),
	(54, 5, 'reflective'),
	(55, 8, 'relax'),
	(56, 6, 'romantic'),
	(57, 1, 'rowdy'),
	(58, 9, 'sad'),
	(59, 6, 'sentimental'),
	(60, 2, 'sexy'),
	(61, 11, 'smooth'),
	(62, 5, 'soothing'),
	(63, 7, 'sophisticated'),
	(64, 12, 'spacey'),
	(65, 5, 'spiritual'),
	(66, 12, 'strange'),
	(67, 6, 'sweet'),
	(68, 12, 'theater'),
	(69, 12, 'trippy'),
	(70, 5, 'warm'),
	(71, 6, 'whimsical');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
