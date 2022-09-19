-- -----------------------------------------------------
-- Table `Music`.`Artists`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `User`;

CREATE TABLE IF NOT EXISTS `user`
(
    `UserId`   INT NOT NULL AUTO_INCREMENT,
    `tgUsername` VARCHAR(255),
    `name` VARCHAR(255),
    `phoneNumber` INT,
    `email` VARCHAR(255),
    `social` VARCHAR(255),
    `age` VARCHAR(255),
    `status` VARCHAR(255),
    `sex` VARCHAR(255),
    `city` VARCHAR(255),
    `country` VARCHAR(255),
    `amountOfPeople` VARCHAR(255),
    `date` VARCHAR(255),
    `additional` VARCHAR(255),
    PRIMARY KEY (`UserId`)
    );

