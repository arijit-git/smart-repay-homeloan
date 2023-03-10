-- LOAN PLAN
-- clean up
--ALTER TABLE IF EXISTS public.loan_plan DROP CONSTRAINT IF EXISTS loan_plan_id_pk;
--DROP TABLE IF EXISTS loan_plan;
--DROP SEQUENCE IF EXISTS loan_plan_id_seq;
-- create sequence
CREATE SEQUENCE IF NOT EXISTS loan_plan_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 100000000000;
-- create table
CREATE TABLE IF NOT EXISTS loan_plan
(
    amount integer NOT NULL,
    tenure integer NOT NULL,
    interest_rate integer NOT NULL,
    no_yearly_pre_payment integer,
    yearly_emi_increase_percentage integer,
    id integer NOT NULL DEFAULT nextval('loan_plan_id_seq'::regclass),
    CONSTRAINT loan_plan_id_pk PRIMARY KEY (id)
);


-- EMI BREAKUP

-- clean up
--ALTER TABLE IF EXISTS public.emi_breakup DROP CONSTRAINT IF EXISTS emi_breakup_id_pk;
--DROP TABLE IF EXISTS emi_breakup;
--DROP SEQUENCE IF EXISTS emi_breakup_id_seq;

-- create sequence
CREATE SEQUENCE IF NOT EXISTS emi_breakup_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 100000000000;
-- create table
CREATE TABLE IF NOT EXISTS emi_breakup
(
    month_num integer NOT NULL,
    amount_towards_principal integer NOT NULL,
    amount_towards_interest integer NOT NULL,
    total_amount integer,
    pre_payment_amount integer,
    id integer NOT NULL DEFAULT nextval('emi_breakup_id_seq'::regclass),
    CONSTRAINT emi_breakup_id_pk PRIMARY KEY (id)
);
