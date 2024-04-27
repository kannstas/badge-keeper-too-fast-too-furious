CREATE TABLE employees
(
    id         UUID PRIMARY KEY            NOT NULL,
    position   VARCHAR                     NOT NULL,
    department VARCHAR                     NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE employees_personal_data
(
    id           UUID PRIMARY KEY NOT NULL,
    employee_id  UUID UNIQUE      NOT NULL REFERENCES employees (id) ON DELETE CASCADE,
    first_name   VARCHAR(100)     NOT NULL,
    last_name    VARCHAR(100)     NOT NULL,
    middle_name  VARCHAR(100),
    age          INTEGER          NOT NULL CHECK (age > 18),
    address      VARCHAR          NOT NULL,
    phone_number VARCHAR(16)      NOT NULL,
    email        VARCHAR          NOT NULL
);

CREATE TABLE badges
(
    id                    UUID PRIMARY KEY            NOT NULL,
    recipient_employee_id UUID                        NOT NULL REFERENCES employees (id),
    issuer_employee_id    UUID                        NOT NULL REFERENCES employees (id),
    badge_serial_number   VARCHAR(100)                NOT NULL,
    issuance_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expiration_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    active                BOOLEAN                     NOT NULL
);