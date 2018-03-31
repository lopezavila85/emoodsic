SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

SHOW WARNINGS;
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`favArtist`
-- -----------------------------------------------------
SELECT 'emoodsic.favArtist' AS ' ';

TRUNCATE TABLE `emoodsic`.`favArtist`;
INSERT INTO `emoodsic`.`favArtist` (`idFavArtist`, `idUser`, `gid`, `name`)
VALUES
	(1, 1, '302bd7b9-d012-4360-897a-93b00c855680', 'David Guetta'),
	(2, 1, 'addb00fd-733c-4223-8fdf-f78be2488acd', 'Alesso'),
	(3, 1, 'a03affe5-47af-48c4-9b8e-67654fa318ec', 'Umek'),
	(4, 2, '43706fe2-bf24-4b1b-8d0b-28f8439b3d55', 'Violadores del Verso'),
	(5, 2, 'b7ffd2af-418f-4be2-bdd1-22f8b48613da', 'Nine Inch Nails'),
	(6, 2, '0efe858c-89e5-4e47-906a-356fa953fd6e', 'Editors'),
	(7, 3, 'f59c5520-5f46-4d2c-b2c4-822eabf53419', 'Linkin Park'),
	(8, 3, 'dfc83cae-a14c-4410-a82c-6789cff2438d', 'Fyahbwoy'),
	(9, 3, '1b98a682-db60-42de-a6c0-5519cf8c3108', 'Rayden'),
	(10, 4, '477b8c0c-c5fc-4ad2-b5b2-191f0bf2a9df', 'Armin van Buuren'),
	(11, 4, '3be4099e-f180-4038-af84-ae18a09eab02', 'Julio Iglesias'),
	(12, 4, '2de794c8-8826-48d0-90e0-6900183ba9e0', 'Ska-P'),
	(13, 5, 'c44e9c22-ef82-4a77-9bcd-af6c958446d6', 'Mumford & Sons'),
	(14, 5, 'f82f3a3e-29c2-42ca-b589-bc5dc210fa9e', 'The Kooks'),
	(15, 5, '9cdb07de-bd54-4985-9494-849ff7ad95ed', 'Lori Meyers'),
	(16, 6, 'a9bafe1a-f7ef-48b5-bb6d-9de5ce8a716d', 'Loquillo'),
	(17, 6, '83e59f23-3b0b-4304-834d-5bcafd5df6d2', 'Scooter'),
	(18, 6, '3be5dee4-5fa6-45a5-97c2-98914580bafa', 'Mecano'),
	(19, 7, '9cdb07de-bd54-4985-9494-849ff7ad95ed', 'Lori Meyers'),
	(20, 7, 'b23e8a63-8f47-4882-b55b-df2c92ef400e', 'Interpol'),
	(21, 7, 'cc197bad-dc9c-440d-a5b5-d52ba2e14234', 'Coldplay'),
	(22, 8, '6ffb8ea9-2370-44d8-b678-e9237bbd347b', 'Kings of Leon'),
	(23, 8, '847e8284-8582-4b0e-9c26-b042a4f49e57', 'Placebo'),
	(24, 8, 'e0e1a584-dd0a-4bd1-88d1-c4c62895039d', 'Foster the People'),
	(25, 9, '86d61837-890c-4d04-aeec-30d70ad77298', 'Melendi'),
	(26, 9, '705076ef-a0c5-472f-bebd-3e72174fcaf4', 'Luciano Pavarotti'),
	(27, 9, '48292311-c32c-41b6-af08-b7f61efd2f68', 'Christophe Maé'),
	(28, 10, 'b185451a-9edd-46df-8046-1db7e9594f5a', 'Vetusta Morla'),
	(29, 10, 'f59163ba-1a7a-4349-b0fd-fb9582ebe333', 'Extremoduro'),
	(30, 10, '9c9f1380-2516-4fc9-a3e6-f9f61941d090', 'Muse'),
	(31, 11, '302bd7b9-d012-4360-897a-93b00c855680', 'David Guetta'),
	(32, 11, 'ed2ac1e9-d51d-4eff-a2c2-85e81abd6360', 'Bob Marley'),
	(33, 11, 'f27ec8db-af05-4f36-916e-3d57f91ecf5e', 'Michael Jackson'),
	(34, 12, '27e948aa-6b38-4da2-acf6-8459964a130a', 'Quique González'),
	(35, 12, '541a58c4-29b3-48b1-b060-3d37b037a226', 'Savant'),
	(36, 12, 'b185451a-9edd-46df-8046-1db7e9594f5a', 'Vetusta Morla'),
	(37, 13, 'e280268a-a5ab-4bb0-be4d-ec470ca59131', 'Astor Piazzolla'),
	(38, 13, '54799c0e-eb45-4eea-996d-c4d71a63c499', 'Ella Fitzgerald'),
	(39, 13, '2ce02909-598b-44ef-a456-151ba0a3bd70', 'Ray Charles'),
	(40, 14, '2f569e60-0a1b-4fb9-95a4-3dc1525d1aad', 'Backstreet Boys'),
	(41, 14, '3be5dee4-5fa6-45a5-97c2-98914580bafa', 'Mecano'),
	(42, 14, '20244d07-534f-4eff-b4d4-930878889970', 'Taylor Swift'),
	(43, 15, '2f9ecbed-27be-40e6-abca-6de49d50299e', 'Aretha Franklin'),
	(44, 15, 'ad79836d-9849-44df-8789-180bbc823f3c', 'Antonio Vivaldi'),
	(45, 15, '3a1e1f72-dd4d-4ed0-8dc4-400107340c1b', 'Nuevo Mester de Juglaría'),
	(46, 16, '9cdb07de-bd54-4985-9494-849ff7ad95ed', 'Lori Meyers'),
	(47, 16, '7f8759ee-4ddc-4a6b-9d17-0771e1022db7', 'Sergio Dalma'),
	(48, 16, '16903d74-9a6a-4cd3-9081-1d752703e8ed', 'Fito & Fitipaldis');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
