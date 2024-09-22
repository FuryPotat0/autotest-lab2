CREATE SEQUENCE lab2.calculation_sequence START WITH 1;

CREATE TABLE IF NOT EXISTS lab2.calculation (
    calculation_id bigint PRIMARY KEY NOT NULL DEFAULT nextval('lab2.calculation_sequence'),
    calculation_first_number varchar(50),
    calculation_first_number_radix varchar(10),
    calculation_second_number varchar(50),
    calculation_second_number_radix varchar(10),
    calculation_processed_at timestamp,
    calculation_operation_type varchar(1)
);