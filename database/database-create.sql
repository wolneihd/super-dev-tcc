-- USE the database
CREATE DATABASE IF NOT EXISTS aplicacao02;
USE aplicacao02;

-- -----------------------------------------------------
-- Dropping existing tables if they exist
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mensagens`;
DROP TABLE IF EXISTS `tiposMensagem`;
DROP TABLE IF EXISTS `usuarios`;

-- -----------------------------------------------------
-- Table `aplicacao02`.`tiposMensagem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiposMensagem` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `aplicacao02`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `first_name` VARCHAR(100) NOT NULL,
  `last_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `aplicacao02`.`mensagens`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mensagens` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_tipo_mensagem` INT NOT NULL,
  `id_usuario` INT NOT NULL,
  `timestamp_cod` BIGINT NOT NULL,
  `text_msg` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id_tipo_mensagem`) REFERENCES `tiposMensagem` (`id`),
  FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE = InnoDB;
