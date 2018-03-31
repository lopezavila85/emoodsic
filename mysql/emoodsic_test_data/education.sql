SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

SHOW WARNINGS;
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`education`
-- -----------------------------------------------------
SELECT 'emoodsic.education' AS ' ';

TRUNCATE TABLE `emoodsic`.`education`;
INSERT INTO `emoodsic`.`education` (`idEducation`, `name`)
VALUES
	(1, 'Primary Education'),
	(2, 'Secondary Education'),
	(3, 'High School'),
	(4, 'Vocational Education'),
	(5, 'Bachelor\'s Degree'),
	(6, 'Master\'s Degree'),
	(7, 'Doctoral Degree');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
