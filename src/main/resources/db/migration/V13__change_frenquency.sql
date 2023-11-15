ALTER TABLE "Dosage"
ALTER COLUMN "frequency" TYPE text USING "frequency"::text;
