DROP TABLE lab2.calculation;

CREATE TABLE IF NOT EXISTS lab2.calculation (
    calculation_id bigint PRIMARY KEY NOT NULL DEFAULT nextval('lab2.calculation_sequence'),
    calculation_first_number text,
    calculation_first_number_radix text,
    calculation_second_number text,
    calculation_second_number_radix text,
    calculation_processed_at timestamp,
    calculation_operation_type text
);