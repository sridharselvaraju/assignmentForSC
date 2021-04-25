CREATE SCHEMA IF NOT EXISTS sinecycle AUTHORIZATION postgres;

CREATE TABLE dta_agent_info
(
    id               int8         NOT NULL,
    change_date      timestamp    NULL,
    create_date      timestamp    NOT NULL,
    closed_count     int8         NULL,
    inprogress_count int8         NULL,
    agent_name       varchar(255) NULL,
    open_count       int8         NULL,
    resolved_count   int8         NULL,
    tickets_count    int8         NULL,
    CONSTRAINT dta_agent_info_pkey PRIMARY KEY (id),
    CONSTRAINT dta_agent_info_agent_name_unique UNIQUE (agent_name)
);

CALL createSeqIfNotExists('seq_agent_info_id');

CREATE TABLE dta_customer_info
(
    id               int8         NOT NULL,
    change_date      timestamp    NULL,
    create_date      timestamp    NOT NULL,
    customer_mail_id varchar(255) NULL,
    customer_name    varchar(255) NULL,
    customer_role    varchar(255) NULL,
    CONSTRAINT dta_customer_info_pkey PRIMARY KEY (id),
    CONSTRAINT dta_customer_info_customer_name_unique UNIQUE (customer_name)
);

CALL createSeqIfNotExists('seq_customer_info_id');

CREATE TABLE dta_ticket
(
    id          int8         NOT NULL,
    change_date timestamp    NULL,
    create_date timestamp    NOT NULL,
    created_by  varchar(255) NULL,
    description varchar(255) NULL,
    priority    varchar(255) NULL,
    status      varchar(255) NULL,
    title       varchar(255) NULL,
    type        varchar(255) NULL,
    customer    varchar(255) NULL,
    assigned_to varchar(255) NULL,
    CONSTRAINT dta_ticket_pkey PRIMARY KEY (id),
    CONSTRAINT dta_agent_info_fkey FOREIGN KEY (assigned_to) REFERENCES dta_agent_info (agent_name),
    CONSTRAINT dta_customer_info_fkey FOREIGN KEY (customer) REFERENCES dta_customer_info (customer_name)
);

CALL createSeqIfNotExists('seq_ticket_id');

CREATE TABLE dta_ticket_response
(
    id              int8         NOT NULL,
    change_date     timestamp    NULL,
    create_date     timestamp    NOT NULL,
    ticket_id       int8         NULL,
    ticket_response varchar(255) NULL,
    CONSTRAINT dta_ticket_response_pkey PRIMARY KEY (id),
    CONSTRAINT dta_ticket_fkey FOREIGN KEY (ticket_id) REFERENCES dta_ticket (id)
);

CALL createSeqIfNotExists('seq_ticket_response_id');