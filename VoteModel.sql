-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema voting_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema voting_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `voting_db` DEFAULT CHARACTER SET utf8 ;
USE `voting_db` ;

-- -----------------------------------------------------
-- Table `voting_db`.`constituency`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `voting_db`.`constituency` (
  `constituency_id` INT NOT NULL AUTO_INCREMENT,
  `daerah` VARCHAR(45) NOT NULL,
  `negeri` VARCHAR(45) NULL,
  PRIMARY KEY (`constituency_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `voting_db`.`voter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `voting_db`.`voter` (
  `ic` CHAR(12) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `gender` CHAR(1) NOT NULL,
  `address` VARCHAR(125) NOT NULL,
  `fingerprint_hash` BLOB NULL,
  `constituency_id` INT NOT NULL,
  PRIMARY KEY (`ic`, `constituency_id`),
  INDEX `fk_Voter_Constituency1_idx` (`constituency_id` ASC) VISIBLE,
  CONSTRAINT `fk_Voter_Constituency1`
    FOREIGN KEY (`constituency_id`)
    REFERENCES `voting_db`.`constituency` (`constituency_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `voting_db`.`party`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `voting_db`.`party` (
  `party_id` INT NOT NULL AUTO_INCREMENT,
  `party_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`party_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `voting_db`.`election`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `voting_db`.`election` (
  `election_id` INT NOT NULL AUTO_INCREMENT,
  `election_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`election_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `voting_db`.`candidate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `voting_db`.`candidate` (
  `candidate_id` INT NOT NULL AUTO_INCREMENT,
  `ic` CHAR(12) NOT NULL,
  `constituency_id` INT NOT NULL,
  `election_id` INT NOT NULL,
  `party_id` INT NOT NULL,
  `vote_number` INT NOT NULL,
  PRIMARY KEY (`candidate_id`, `ic`, `constituency_id`, `election_id`),
  INDEX `fk_Candidate_Voter_idx` (`ic` ASC) VISIBLE,
  INDEX `fk_Candidate_Consituency1_idx` (`constituency_id` ASC) VISIBLE,
  INDEX `fk_Candidate_Party1_idx` (`party_id` ASC) VISIBLE,
  INDEX `fk_Candidate_Election1_idx` (`election_id` ASC) VISIBLE,
  CONSTRAINT `fk_Candidate_Voter`
    FOREIGN KEY (`ic`)
    REFERENCES `voting_db`.`voter` (`ic`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Candidate_Consituency1`
    FOREIGN KEY (`constituency_id`)
    REFERENCES `voting_db`.`constituency` (`constituency_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Candidate_Party1`
    FOREIGN KEY (`party_id`)
    REFERENCES `voting_db`.`party` (`party_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Candidate_Election1`
    FOREIGN KEY (`election_id`)
    REFERENCES `voting_db`.`election` (`election_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `voting_db`.`vote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `voting_db`.`vote` (
  `ic` CHAR(12) NOT NULL,
  `election_id` INT NOT NULL,
  PRIMARY KEY (`ic`, `election_id`),
  INDEX `fk_Vote_Election1_idx` (`election_id` ASC) VISIBLE,
  CONSTRAINT `fk_Vote_Voter1`
    FOREIGN KEY (`ic`)
    REFERENCES `voting_db`.`voter` (`ic`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Vote_Election1`
    FOREIGN KEY (`election_id`)
    REFERENCES `voting_db`.`election` (`election_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `voting_db`.`include`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `voting_db`.`include` (
  `constituency_id` INT NOT NULL,
  `election_id` INT NOT NULL,
  PRIMARY KEY (`constituency_id`, `election_id`),
  INDEX `fk_Include_Election1_idx` (`election_id` ASC) VISIBLE,
  CONSTRAINT `fk_Include_Consituency1`
    FOREIGN KEY (`constituency_id`)
    REFERENCES `voting_db`.`constituency` (`constituency_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Include_Election1`
    FOREIGN KEY (`election_id`)
    REFERENCES `voting_db`.`election` (`election_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
