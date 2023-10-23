ALTER TABLE "Patient" ADD COLUMN birth_date TEXT NOT NULL default now()::text,
                        ADD COLUMN num_sus TEXT;