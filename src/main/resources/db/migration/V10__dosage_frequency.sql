ALTER TABLE "Dosage"
ALTER COLUMN "frequency" TYPE INTEGER USING EXTRACT(EPOCH FROM "frequency");