DROP TABLE IF EXISTS game ;
DROP TABLE IF EXISTS player ;
CREATE SCHEMA `dice_game_jwt` DEFAULT CHARSET=utf8mb4 ;
USE `dice_game_jwt`;

CREATE TABLE `dice_game_jwt`.`player` (
  `id_player` INT NOT NULL AUTO_INCREMENT,
  `name_player` VARCHAR(150) NOT NULL,
  `username` VARCHAR(150) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `date_register_player` DATETIME NULL DEFAULT NULL,
  `success_rate_player` DECIMAL(5,2) NULL DEFAULT NULL,
   PRIMARY KEY (`id_player`),
  UNIQUE INDEX `id_player_UNIQUE` (`id_player` ASC))
ENGINE = InnoDB
DEFAULT CHARSET=utf8mb4,
COLLATE = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `dice_game_jwt`.`game` (
  `id_game` INT NOT NULL AUTO_INCREMENT,
  `dice_one` INT NOT NULL,
  `dice_two` INT NOT NULL,
  `win_game` TINYINT NULL DEFAULT NULL,
  `player_id` INT NOT NULL,
  PRIMARY KEY (`id_game`),
  UNIQUE INDEX `id_games_UNIQUE` (`id_game` ASC),
  INDEX `fk_game_player_idx` (`player_id` ASC),
  CONSTRAINT `fk_game_player`
    FOREIGN KEY (`player_id`)
    REFERENCES `dice_game_jwt`.`player` (`id_player`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4,
COLLATE = utf8mb4_unicode_ci;