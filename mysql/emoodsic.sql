-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema emoodsic
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `emoodsic` ;

-- -----------------------------------------------------
-- Schema emoodsic
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `emoodsic` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `emoodsic` ;

-- -----------------------------------------------------
-- Table `emoodsic`.`education`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`education` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`education` (
  `idEducation` INT NOT NULL COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`idEducation`)  COMMENT '')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`musicKnowledge`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`musicKnowledge` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`musicKnowledge` (
  `idMusicKnowledge` INT NOT NULL COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`idMusicKnowledge`)  COMMENT '')
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`musicPrefDimension`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`musicPrefDimension` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`musicPrefDimension` (
  `idMusicPrefDimension` INT NOT NULL COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  `persTraitO` DOUBLE NOT NULL COMMENT '',
  `persTraitC` DOUBLE NOT NULL COMMENT '',
  `persTraitE` DOUBLE NOT NULL COMMENT '',
  `persTraitA` DOUBLE NOT NULL COMMENT '',
  `persTraitN` DOUBLE NOT NULL COMMENT '',
  PRIMARY KEY (`idMusicPrefDimension`)  COMMENT '')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`user` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`user` (
  `idUser` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `email` VARCHAR(45) NOT NULL COMMENT '',
  `encryptedPassword` VARCHAR(128) NOT NULL COMMENT '',
  `firstName` VARCHAR(45) NOT NULL COMMENT '',
  `lastName` VARCHAR(45) NOT NULL COMMENT '',
  `gender` CHAR(1) NOT NULL COMMENT '',
  `age` INT UNSIGNED NOT NULL COMMENT '',
  `idEducation` INT NOT NULL COMMENT '',
  `idMusicKnowledge` INT NOT NULL COMMENT '',
  `persTraitO` DOUBLE NULL COMMENT '',
  `persTraitC` DOUBLE NULL COMMENT '',
  `persTraitE` DOUBLE NULL COMMENT '',
  `persTraitA` DOUBLE NULL COMMENT '',
  `persTraitN` DOUBLE NULL COMMENT '',
  `numberSongsPlaylist` INT NULL DEFAULT 10 COMMENT '',
  `musicPrefRC` DOUBLE NULL COMMENT '',
  `musicPrefIR` DOUBLE NULL COMMENT '',
  `musicPrefUC` DOUBLE NULL COMMENT '',
  `musicPrefER` DOUBLE NULL COMMENT '',
  `idMusicPrefDimension` INT NULL COMMENT '',
  `tsLastModified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '',
  `fake` TINYINT(1) NULL DEFAULT 0 COMMENT '',
  PRIMARY KEY (`idUser`)  COMMENT '',
  INDEX `fk_user_education_idx` (`idEducation` ASC)  COMMENT '',
  INDEX `fk_user_musicKnow_idx` (`idMusicKnowledge` ASC)  COMMENT '',
  INDEX `fk_user_musicPrefDimension_idx` (`idMusicPrefDimension` ASC)  COMMENT '',
  UNIQUE INDEX `email_UNIQUE` (`email` ASC)  COMMENT '',
  CONSTRAINT `fk_user_education`
    FOREIGN KEY (`idEducation`)
    REFERENCES `emoodsic`.`education` (`idEducation`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_musicKnow`
    FOREIGN KEY (`idMusicKnowledge`)
    REFERENCES `emoodsic`.`musicKnowledge` (`idMusicKnowledge`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_musicPrefDimension`
    FOREIGN KEY (`idMusicPrefDimension`)
    REFERENCES `emoodsic`.`musicPrefDimension` (`idMusicPrefDimension`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`bigFiveInventory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`bigFiveInventory` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`bigFiveInventory` (
  `idUser` INT NOT NULL COMMENT '',
  `likertScore1` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore2` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore3` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore4` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore5` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore6` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore7` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore8` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore9` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore10` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore11` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore12` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore13` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore14` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore15` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore16` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore17` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore18` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore19` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore20` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore21` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore22` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore23` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore24` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore25` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore26` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore27` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore28` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore29` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore30` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore31` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore32` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore33` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore34` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore35` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore36` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore37` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore38` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore39` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore40` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore41` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore42` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  `likertScore43` INT UNSIGNED NOT NULL DEFAULT 5 COMMENT '',
  `likertScore44` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '',
  PRIMARY KEY (`idUser`)  COMMENT '',
  CONSTRAINT `fkBigFiveTestIdUser`
    FOREIGN KEY (`idUser`)
    REFERENCES `emoodsic`.`user` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`style`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`style` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`style` (
  `idStyle` INT NOT NULL COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`idStyle`)  COMMENT '')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`authToken`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`authToken` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`authToken` (
  `idUser` INT NOT NULL COMMENT '',
  `authTokenValue` VARCHAR(32) NOT NULL COMMENT '',
  PRIMARY KEY (`idUser`)  COMMENT '',
  CONSTRAINT `fkAuthTokenIdUser`
    FOREIGN KEY (`idUser`)
    REFERENCES `emoodsic`.`user` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`moodCategory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`moodCategory` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`moodCategory` (
  `idMoodCategory` INT NOT NULL COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  `minValence` DOUBLE NOT NULL DEFAULT 0 COMMENT '',
  `maxValence` DOUBLE NOT NULL DEFAULT 1 COMMENT '',
  `minArousal` DOUBLE NOT NULL DEFAULT 0 COMMENT '',
  `maxArousal` DOUBLE NOT NULL DEFAULT 1 COMMENT '',
  PRIMARY KEY (`idMoodCategory`)  COMMENT '')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`mood`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`mood` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`mood` (
  `idMood` INT NOT NULL COMMENT '',
  `idMoodCategory` INT NOT NULL COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`idMood`)  COMMENT '',
  INDEX `fk_mood_moodCategory_idx` (`idMoodCategory` ASC)  COMMENT '',
  CONSTRAINT `fk_mood_moodCategory`
    FOREIGN KEY (`idMoodCategory`)
    REFERENCES `emoodsic`.`moodCategory` (`idMoodCategory`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`sortOrder`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`sortOrder` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`sortOrder` (
  `idSortOrder` INT NOT NULL COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`idSortOrder`)  COMMENT '')
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`qbmPlaylist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`qbmPlaylist` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`qbmPlaylist` (
  `idQbmPlaylist` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `idUser` INT NOT NULL COMMENT '',
  `tsCreation` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '',
  `idInitialMoodCat` INT NOT NULL COMMENT '',
  `idFinalMoodCat` INT NOT NULL COMMENT '',
  `idSeedMood` INT NOT NULL COMMENT '',
  `gidSeedFavArtist` CHAR(36) NULL COMMENT '',
  `idSeedFavStyle` INT NULL COMMENT '',
  `idSeedMusicPrefDimStyle` INT NOT NULL COMMENT '',
  `tasteSongsNum` INT NOT NULL COMMENT '',
  `personalitySongsNum` INT NOT NULL COMMENT '',
  `tasteWeight` DOUBLE NULL COMMENT '',
  `personalityWeight` DOUBLE NULL COMMENT '',
  `idSortOrder` INT NULL COMMENT '',
  PRIMARY KEY (`idQbmPlaylist`)  COMMENT '',
  INDEX `fk_qbmpl_idUser_idx` (`idUser` ASC)  COMMENT '',
  INDEX `fk_qbmimc_initialMoodCat_idx` (`idInitialMoodCat` ASC)  COMMENT '',
  INDEX `fk_qbmfmc_finalMoodCat_idx` (`idFinalMoodCat` ASC)  COMMENT '',
  INDEX `fk_qbmsm_seedMood_idx` (`idSeedMood` ASC)  COMMENT '',
  INDEX `fk_qbmsfs_seedFavStyle_idx` (`idSeedFavStyle` ASC)  COMMENT '',
  INDEX `fk_qbmsps_seedPersStyle_idx` (`idSeedMusicPrefDimStyle` ASC)  COMMENT '',
  INDEX `fk_qbmpl_idSortOrder_idx` (`idSortOrder` ASC)  COMMENT '',
  CONSTRAINT `fk_qbmpl_idUser`
    FOREIGN KEY (`idUser`)
    REFERENCES `emoodsic`.`user` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_qbmimc_initialMoodCat`
    FOREIGN KEY (`idInitialMoodCat`)
    REFERENCES `emoodsic`.`moodCategory` (`idMoodCategory`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_qbmfmc_finalMoodCat`
    FOREIGN KEY (`idFinalMoodCat`)
    REFERENCES `emoodsic`.`moodCategory` (`idMoodCategory`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_qbmsm_seedMood`
    FOREIGN KEY (`idSeedMood`)
    REFERENCES `emoodsic`.`mood` (`idMood`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_qbmsfs_seedFavStyle`
    FOREIGN KEY (`idSeedFavStyle`)
    REFERENCES `emoodsic`.`style` (`idStyle`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_qbmsps_seedPersStyle`
    FOREIGN KEY (`idSeedMusicPrefDimStyle`)
    REFERENCES `emoodsic`.`style` (`idStyle`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_qbmpl_idSortOrder`
    FOREIGN KEY (`idSortOrder`)
    REFERENCES `emoodsic`.`sortOrder` (`idSortOrder`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`recommendationType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`recommendationType` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`recommendationType` (
  `idRecommendationType` INT NOT NULL COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`idRecommendationType`)  COMMENT '')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`qbmPlaylistInfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`qbmPlaylistInfo` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`qbmPlaylistInfo` (
  `idQbmPlaylistInfo` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `idQbmPlaylist` INT NOT NULL COMMENT '',
  `idRecommendationType` INT NULL COMMENT '',
  `gidArtistMb` CHAR(36) NULL COMMENT '',
  `artist` VARCHAR(64) NULL COMMENT '',
  `gidRecordingMb` CHAR(36) NULL COMMENT '',
  `song` VARCHAR(96) NULL COMMENT '',
  `likertScore` INT NULL COMMENT '',
  `youtubeVideoId` VARCHAR(45) NULL COMMENT '',
  `latitude` DOUBLE NULL COMMENT '',
  `longitude` DOUBLE NULL COMMENT '',
  `country` VARCHAR(45) NULL COMMENT '',
  `valence` DOUBLE NULL COMMENT '',
  `arousal` DOUBLE NULL COMMENT '',
  `playlistDistance` DOUBLE NULL COMMENT '',
  PRIMARY KEY (`idQbmPlaylistInfo`)  COMMENT '',
  INDEX `fk_qbmInfo_qbmPlay_idx` (`idQbmPlaylist` ASC)  COMMENT '',
  INDEX `fk_qbmInfo_qbmRecType_idx` (`idRecommendationType` ASC)  COMMENT '',
  CONSTRAINT `fk_qbmInfo_qbmPlay`
    FOREIGN KEY (`idQbmPlaylist`)
    REFERENCES `emoodsic`.`qbmPlaylist` (`idQbmPlaylist`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_qbmInfo_qbmRecType`
    FOREIGN KEY (`idRecommendationType`)
    REFERENCES `emoodsic`.`recommendationType` (`idRecommendationType`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`favArtist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`favArtist` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`favArtist` (
  `idFavArtist` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `idUser` INT NOT NULL COMMENT '',
  `gid` CHAR(36) NOT NULL COMMENT '',
  `name` VARCHAR(128) NOT NULL COMMENT '',
  PRIMARY KEY (`idFavArtist`)  COMMENT '',
  INDEX `fk_favArtist_idUser_idx` (`idUser` ASC)  COMMENT '',
  CONSTRAINT `fk_favArtist_idUser`
    FOREIGN KEY (`idUser`)
    REFERENCES `emoodsic`.`user` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`favStyle`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`favStyle` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`favStyle` (
  `idFavStyle` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `idUser` INT NOT NULL COMMENT '',
  `idStyle` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idFavStyle`)  COMMENT '',
  INDEX `fk_favStyle_idUser_idx` (`idUser` ASC)  COMMENT '',
  INDEX `fk_favStyle_idSyle_idx` (`idStyle` ASC)  COMMENT '',
  CONSTRAINT `fk_favStyle_idUser`
    FOREIGN KEY (`idUser`)
    REFERENCES `emoodsic`.`user` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_favStyle_idSyle`
    FOREIGN KEY (`idStyle`)
    REFERENCES `emoodsic`.`style` (`idStyle`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`musicPrefDimStyle`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`musicPrefDimStyle` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`musicPrefDimStyle` (
  `idMusicPrefDimStyle` INT NOT NULL COMMENT '',
  `idMusicPrefDimension` INT NOT NULL COMMENT '',
  `idStyle` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idMusicPrefDimStyle`)  COMMENT '',
  INDEX `fk_mpds_idMusicPrefDimension_idx` (`idMusicPrefDimension` ASC)  COMMENT '',
  INDEX `fk_mpds_idStyle_idx` (`idStyle` ASC)  COMMENT '',
  CONSTRAINT `fk_mpds_idMusicPrefDimension`
    FOREIGN KEY (`idMusicPrefDimension`)
    REFERENCES `emoodsic`.`musicPrefDimension` (`idMusicPrefDimension`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_mpds_idStyle`
    FOREIGN KEY (`idStyle`)
    REFERENCES `emoodsic`.`style` (`idStyle`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`musicPrefDimProb`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`musicPrefDimProb` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`musicPrefDimProb` (
  `idUser` INT NOT NULL COMMENT '',
  `idMusicPrefDimension` INT NOT NULL COMMENT '',
  `probRC` DOUBLE NOT NULL COMMENT '',
  `probIR` DOUBLE NOT NULL COMMENT '',
  `probUC` DOUBLE NOT NULL COMMENT '',
  `probER` DOUBLE NOT NULL COMMENT '',
  PRIMARY KEY (`idUser`)  COMMENT '',
  INDEX `fk_mpdprob_idMusicPrefDim_idx` (`idMusicPrefDimension` ASC)  COMMENT '',
  CONSTRAINT `fk_mpdprob_idUser`
    FOREIGN KEY (`idUser`)
    REFERENCES `emoodsic`.`user` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_mpdprob_idMusicPrefDim`
    FOREIGN KEY (`idMusicPrefDimension`)
    REFERENCES `emoodsic`.`musicPrefDimension` (`idMusicPrefDimension`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`neighbor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`neighbor` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`neighbor` (
  `idUser` INT NOT NULL COMMENT '',
  `idNeighbors` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`idUser`)  COMMENT '',
  CONSTRAINT `fk_neighbor_idUser`
    FOREIGN KEY (`idUser`)
    REFERENCES `emoodsic`.`user` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emoodsic`.`qbmSurvey`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emoodsic`.`qbmSurvey` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emoodsic`.`qbmSurvey` (
  `idQbmSurvey` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `idUser` INT NOT NULL COMMENT '',
  `questionNum` INT NOT NULL COMMENT '',
  `likertScore` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idQbmSurvey`)  COMMENT '')
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
