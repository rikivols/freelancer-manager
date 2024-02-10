-- Remove conflicting tables
DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS freelancer CASCADE;
DROP TABLE IF EXISTS skill CASCADE;
DROP TABLE IF EXISTS task CASCADE;
-- End of removing

CREATE TABLE address (
                         address_id BIGSERIAL NOT NULL,
                         country VARCHAR(256) NOT NULL,
                         city VARCHAR(256) NOT NULL,
                         street VARCHAR(256) NOT NULL,
                         street_number VARCHAR(256) NOT NULL,
                         zip VARCHAR(256)
);
ALTER TABLE address ADD CONSTRAINT pk_address PRIMARY KEY (address_id);

CREATE TABLE freelancer (
                            freelancer_id BIGSERIAL NOT NULL,
                            first_name VARCHAR(256) NOT NULL,
                            middle_name VARCHAR(256),
                            last_name VARCHAR(256) NOT NULL,
                            email VARCHAR(256) NOT NULL,
                            phone_number VARCHAR(256) NOT NULL,
                            birthday DATE NOT NULL,
                            additional_information VARCHAR(2999),
);
ALTER TABLE freelancer ADD CONSTRAINT pk_freelancer PRIMARY KEY (freelancer_id);

CREATE TABLE skill (
                        skill_id BIGSERIAL NOT NULL,
                        name VARCHAR(256) NOT NULL,
                        number_of_years INTEGER NOT NULL,
                        note VARCHAR(2999)
);
ALTER TABLE skill ADD CONSTRAINT pk_skill PRIMARY KEY (skill_id);

CREATE TABLE task (
                      task_id BIGSERIAL NOT NULL,
                      name VARCHAR(256) NOT NULL,
                      description VARCHAR(256),
                      status VARCHAR(256) NOT NULL,
                      reward FLOAT NOT NULL,
                      paid BIT NOT NULL
);
ALTER TABLE task ADD CONSTRAINT pk_task PRIMARY KEY (task_id);
