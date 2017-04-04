CREATE TABLE `User` (
  `ID`    BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `SEX`   VARCHAR(10)
          COLLATE utf8mb4_unicode_ci   DEFAULT NULL,
  `NAME`  VARCHAR(30)
          COLLATE utf8mb4_unicode_ci   DEFAULT NULL,
  `AGE`   INT(11)                      DEFAULT NULL,
  `BIRTH` DATETIME                     DEFAULT NULL,
  PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci