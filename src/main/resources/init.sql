CREATE TABLE IF NOT EXISTS `Freelancer` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `Task` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `dsc` VARCHAR(255) NOT NULL,
    `freelancer_id` BIGINT,
    FOREIGN KEY (`freelancer_id`) REFERENCES `Freelancer`(`id`)
);