CREATE TABLE users
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username VARCHAR(255),
    email    VARCHAR(255),
    password VARCHAR(100),
    role     VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE events
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    acronym     VARCHAR(255),
    url         VARCHAR(255),
    CONSTRAINT pk_events PRIMARY KEY (id)
);

CREATE TABLE editions
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    year_edition   INT                                     NOT NULL,
    edition_number INT                                     NOT NULL,
    start_date     DATE                                    NOT NULL,
    end_date       DATE                                    NOT NULL,
    event_id       BIGINT,
    organizer_id   BIGINT,
    CONSTRAINT pk_editions PRIMARY KEY (id)
);

CREATE TABLE activities
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    type        VARCHAR(255)                            NOT NULL,
    name        VARCHAR(255)                            NOT NULL,
    description VARCHAR(255)                            NOT NULL,
    date        date                                    NOT NULL,
    start_time  time                                    NOT NULL,
    end_time    time                                    NOT NULL,
    mail_sent   BOOLEAN                                 NOT NULL,
    space_id    BIGINT,
    edition_id  BIGINT,
    CONSTRAINT pk_activities PRIMARY KEY (id)
);

CREATE TABLE space_resources
(
    space_id  BIGINT NOT NULL,
    resources VARCHAR(255)
);

CREATE TABLE spaces
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name     VARCHAR(255),
    location VARCHAR(255),
    capacity INT                                     NOT NULL,
    CONSTRAINT pk_spaces PRIMARY KEY (id)
);

CREATE TABLE user_activities
(
    activity_id BIGINT NOT NULL,
    user_id     BIGINT NOT NULL
);

ALTER TABLE activities
    ADD CONSTRAINT uc_activities_space UNIQUE (space_id);

ALTER TABLE activities
    ADD CONSTRAINT FK_ACTIVITIES_ON_EDITION FOREIGN KEY (edition_id) REFERENCES editions (id);

ALTER TABLE activities
    ADD CONSTRAINT FK_ACTIVITIES_ON_SPACE FOREIGN KEY (space_id) REFERENCES spaces (id);

ALTER TABLE editions
    ADD CONSTRAINT FK_EDITIONS_ON_EVENT FOREIGN KEY (event_id) REFERENCES events (id);

ALTER TABLE editions
    ADD CONSTRAINT FK_EDITIONS_ON_ORGANIZER FOREIGN KEY (organizer_id) REFERENCES users (id);

ALTER TABLE space_resources
    ADD CONSTRAINT fk_space_resources_on_space FOREIGN KEY (space_id) REFERENCES spaces (id);

ALTER TABLE user_activities
    ADD CONSTRAINT fk_useact_on_activity FOREIGN KEY (activity_id) REFERENCES activities (id);

ALTER TABLE user_activities
    ADD CONSTRAINT fk_useact_on_user FOREIGN KEY (user_id) REFERENCES users (id);