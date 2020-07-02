--liquibase formatted sql
--changeset version:001
CREATE SCHEMA IF NOT EXISTS trc;

SET search_path TO trc;

create table IF NOT EXISTS account (
    id             bigserial   not null
        constraint account_pkey
            primary key,
    document_number varchar(50) not null
);

create table IF NOT EXISTS operation_type (
    id             bigserial   not null
        constraint operation_type_pkey
            primary key,
    name varchar(50) not null,
    description varchar(50) not null
);

create table IF NOT EXISTS transaction (
    id             bigserial   not null
        constraint transaction_pkey
            primary key,
    amount          numeric not null,
    event_date      timestamp not null,
    account_id      integer not null
        constraint transaction_account_id_fkey
            references account,
    operation_type_id      integer not null
        constraint transaction_operation_type_id_fkey
            references operation_type
);

-- populating tables
insert into operation_type(name, description) values ('COMPRA_A_VISTA', 'Compra à vista');
insert into operation_type(name, description) values ('COMPRA_PARCELADA', 'Compra parcelada');
insert into operation_type(name, description) values ('SAQUE', 'Saque');
insert into operation_type(name, description) values ('PAGAMENTO', 'Pagamento');

insert into account(document_number) values ('12345678900');
insert into account(document_number) values ('98765432100');

insert into transaction(amount, event_date, account_id, operation_type_id)
    values (-50, '2020-01-01T23:03:20.000Z',
    (select id from account where document_number = '12345678900'),
    (select id from operation_type where name = 'COMPRA_A_VISTA'));
insert into transaction(amount, event_date, account_id, operation_type_id)
    values (-23.5, '2020-01-02T23:03:20.000Z',
    (select id from account where document_number = '12345678900'),
    (select id from operation_type where name = 'COMPRA_A_VISTA'));
insert into transaction(amount, event_date, account_id, operation_type_id)
    values (-18.7, '2020-01-03T23:03:20.000Z',
    (select id from account where document_number = '12345678900'),
    (select id from operation_type where name = 'COMPRA_A_VISTA'));
insert into transaction(amount, event_date, account_id, operation_type_id)
    values (60, '2020-01-04T23:03:20.000Z',
    (select id from account where document_number = '12345678900'),
    (select id from operation_type where name = 'PAGAMENTO'));

insert into transaction(amount, event_date, account_id, operation_type_id)
    values (-70.2, '2020-01-04T23:03:20.000Z',
    (select id from account where document_number = '98765432100'),
    (select id from operation_type where name = 'COMPRA_PARCELADA'));
insert into transaction(amount, event_date, account_id, operation_type_id)
    values (-40, '2020-01-05T23:03:20.000Z',
    (select id from account where document_number = '98765432100'),
    (select id from operation_type where name = 'SAQUE'));
insert into transaction(amount, event_date, account_id, operation_type_id)
    values (-70.2, '2020-01-06T23:03:20.000Z',
    (select id from account where document_number = '98765432100'),
    (select id from operation_type where name = 'COMPRA_PARCELADA'));
insert into transaction(amount, event_date, account_id, operation_type_id)
    values (100, '2020-01-07T23:03:20.000Z',
    (select id from account where document_number = '98765432100'),
    (select id from operation_type where name = 'PAGAMENTO'));

--rollback DROP TABLE IF EXISTS transaction
--rollback DROP TABLE IF EXISTS operation_type
--rollback DROP TABLE IF EXISTS account