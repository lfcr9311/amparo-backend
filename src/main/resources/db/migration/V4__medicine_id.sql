ALTER TABLE "Medicine" ALTER COLUMN id TYPE numeric;

ALTER TABLE "Dosage" ALTER COLUMN id_medicine TYPE numeric;

ALTER TABLE "Incompatibility"
    ALTER COLUMN id_medicine TYPE numeric,
    ALTER COLUMN id_medicine_inc TYPE numeric;