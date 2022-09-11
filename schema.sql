-- -----------------------------------------------------
-- Table `Music`.`Artists`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `User`;

CREATE TABLE IF NOT EXISTS `User`
(
    `UserId`   INT          NOT NULL,
    `tgUsername` VARCHAR(255),
    `name` VARCHAR(255),
    `phoneNumber` INT,
    `email` VARCHAR(255),
    `social` VARCHAR(255),
    `age` INT,
    `status` VARCHAR(255),
    `sex` VARCHAR(255),
    `city` VARCHAR(255),
    `country` VARCHAR(255),
    `amountOfPeople` INT,
    `date` VARCHAR(255),
    `additional` VARCHAR(255),
    PRIMARY KEY (`UserId`)
);

