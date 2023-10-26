ALTER TABLE "Dosage" DROP CONSTRAINT IF EXISTS "Dosage_id_medicine_fkey";
ALTER TABLE "Incompatibility" DROP CONSTRAINT IF EXISTS "Incompatibility_id_medicine_fkey";
ALTER TABLE "Incompatibility" DROP CONSTRAINT IF EXISTS "Incompatibility_id_medicine_inc_fkey";

ALTER TABLE "Medicine" ADD COLUMN new_id SERIAL;
ALTER TABLE "Incompatibility" ADD COLUMN new_id SERIAL;
ALTER TABLE "Incompatibility" ADD COLUMN new_id_inc SERIAL;
ALTER TABLE "Dosage" ADD COLUMN new_id SERIAL;

UPDATE "Medicine" SET new_id = CAST(SUBSTRING(id::text FROM 1 FOR 8) AS INT);
UPDATE "Incompatibility" SET new_id = CAST(SUBSTRING(id_medicine::text FROM 1 FOR 8) AS INT);
UPDATE "Incompatibility" SET new_id_inc = CAST(SUBSTRING(id_medicine_inc::text FROM 1 FOR 8) AS INT);
UPDATE "Dosage" SET new_id = CAST(SUBSTRING(id_medicine::text FROM 1 FOR 8) AS INT);

ALTER TABLE "Medicine" DROP COLUMN id;
ALTER TABLE "Incompatibility" DROP COLUMN id_medicine;
ALTER TABLE "Incompatibility" DROP COLUMN id_medicine_inc;
ALTER TABLE "Dosage" DROP COLUMN id_medicine;

ALTER TABLE "Medicine" RENAME COLUMN new_id TO id;
ALTER TABLE "Incompatibility" RENAME COLUMN new_id TO id_medicine;
ALTER TABLE "Incompatibility" RENAME COLUMN new_id_inc TO id_medicine_inc;
ALTER TABLE "Dosage" RENAME COLUMN new_id TO id_medicine;

ALTER TABLE "Medicine" ADD PRIMARY KEY ("id");

ALTER TABLE "Dosage" ADD CONSTRAINT "Dosage_id_medicine_fkey" FOREIGN KEY ("id_medicine") REFERENCES "Medicine" ("id");
ALTER TABLE "Incompatibility" ADD CONSTRAINT "Incompatibility_id_medicine_fkey" FOREIGN KEY ("id_medicine") REFERENCES "Medicine" ("id");
ALTER TABLE "Incompatibility" ADD CONSTRAINT "Incompatibility_id_medicine_inc_fkey" FOREIGN KEY ("id_medicine_inc") REFERENCES "Medicine" ("id");
