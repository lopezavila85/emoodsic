SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

SHOW WARNINGS;
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`bigFiveInventory`
-- -----------------------------------------------------
SELECT 'emoodsic.bigFiveInventory' AS ' ';

TRUNCATE TABLE `emoodsic`.`bigFiveInventory`;
INSERT INTO `emoodsic`.`bigFiveInventory` (`idUser`,
    `likertScore1`, `likertScore2`, `likertScore3`, `likertScore4`, `likertScore5`,
	`likertScore6`, `likertScore7`, `likertScore8`, `likertScore9`, `likertScore10`,
	`likertScore11`, `likertScore12`, `likertScore13`, `likertScore14`, `likertScore15`,
	`likertScore16`, `likertScore17`, `likertScore18`, `likertScore19`, `likertScore20`,
	`likertScore21`, `likertScore22`, `likertScore23`, `likertScore24`, `likertScore25`,
	`likertScore26`, `likertScore27`, `likertScore28`, `likertScore29`, `likertScore30`,
	`likertScore31`, `likertScore32`, `likertScore33`, `likertScore34`, `likertScore35`,
	`likertScore36`, `likertScore37`, `likertScore38`, `likertScore39`, `likertScore40`,
	`likertScore41`, `likertScore42`, `likertScore43`, `likertScore44`)
VALUES
	(1,
	2,3,4,4,3,4,3,4,2,2,3,1,5,3,4,2,2,1,4,3,
	5,1,1,3,2,2,3,5,4,3,4,3,2,4,1,2,3,3,3,4,
	2,4,3,3),
	(2,
	3,1,4,3,4,3,4,5,4,4,3,2,4,2,3,3,4,5,2,4,
	3,3,1,4,4,4,3,3,4,4,4,3,4,4,2,3,2,3,2,4,
	1,3,4,4),
	(3,
	3,3,2,3,3,1,3,5,4,3,3,3,4,3,3,3,3,4,3,2,
	3,3,2,3,3,3,3,3,3,3,3,4,4,3,4,3,3,3,3,4,
	3,3,3,3),
	(4,
	3,1,3,1,4,3,5,5,5,4,4,1,5,5,4,4,1,3,5,4,
	3,5,1,5,3,4,1,4,1,4,3,5,4,5,2,5,1,1,1,5,
	3,5,5,3),
	(5,
	2,1,4,2,5,4,4,3,5,3,3,1,5,2,3,3,3,1,2,4,
	4,3,2,3,3,2,3,4,4,5,4,4,4,4,1,3,1,3,2,4,
	3,4,3,4),
	(6,
	5,4,5,5,4,4,4,4,3,4,4,3,5,5,4,3,3,3,5,5,
	3,5,4,4,5,4,3,5,5,5,4,4,5,5,3,5,5,4,5,4,
	3,5,4,3),
	(7,
	4,2,5,2,3,3,4,2,4,2,4,1,5,1,3,4,5,1,4,3,
	4,4,1,2,2,4,1,4,2,3,4,5,5,5,2,4,1,4,3,4,
	2,5,2,3),
	(8,
	4,2,3,1,3,3,4,4,2,5,2,2,4,3,2,3,4,3,4,4,
	1,3,5,4,3,2,2,3,3,4,3,4,2,4,2,3,2,2,3,3,
	2,4,5,3),
	(9,
	1,4,5,1,3,4,4,2,4,4,4,3,5,3,4,3,3,1,4,3,
	3,3,1,4,4,3,3,4,3,4,3,3,4,4,1,3,1,3,1,4,
	2,4,2,1),
	(10,
	3,4,4,3,4,4,4,3,4,4,4,2,4,2,4,3,3,2,3,4,
	3,2,1,4,3,4,4,4,3,3,3,4,4,4,1,3,2,4,2,4,
	4,3,2,2),
	(11,
	4,1,5,1,3,1,4,3,4,5,5,1,5,3,5,5,1,1,3,5,
	1,5,1,5,3,3,1,4,3,5,1,5,4,5,4,5,1,3,1,5,
	1,5,4,4),
	(12,
	5,4,5,4,5,3,5,2,3,4,3,1,5,3,5,3,4,4,4,5,
	1,3,3,1,5,3,3,5,5,5,3,5,4,4,1,4,1,2,2,4,
	1,4,3,5),
	(13,
	4,3,5,1,4,4,4,2,4,4,3,1,4,1,4,3,2,1,3,4,
	3,3,2,4,4,3,4,4,2,5,4,3,4,3,1,3,1,4,2,4,
	1,4,2,4),
	(14,
	5,4,4,4,4,4,3,3,2,5,2,3,4,3,3,3,1,4,5,5,
	3,2,4,1,4,3,4,3,4,3,4,5,4,3,5,3,1,4,4,5,
	1,4,4,4),
	(15,
	5,3,5,1,5,3,5,5,3,1,4,1,5,3,4,3,5,4,3,5,
	1,5,1,1,3,5,3,5,1,2,1,5,5,5,1,5,1,3,1,3,
	1,5,1,5),
	(16,
	4,2,4,3,4,4,4,4,2,4,5,2,5,2,4,5,3,1,4,4,
	2,3,2,3,4,2,4,5,2,2,4,4,4,3,1,4,1,4,3,4,
	3,4,2,3);
	
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
