CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS "User" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "email" TEXT UNIQUE NOT NULL,
  "password" TEXT NOT NULL,
  "salt" TEXT NOT NULL,
  "name" TEXT NOT NULL,
  "cellphone" TEXT NOT NULL,
  "profile_picture" TEXT,
  "is_anonymous" BOOL
);

CREATE TABLE IF NOT EXISTS "Doctor" (
  "id" uuid UNIQUE PRIMARY KEY NOT NULL,
  "crm" TEXT UNIQUE NOT NULL,
  "uf" TEXT NOT NULL,
  FOREIGN KEY ("id") REFERENCES "User" ("id")
);

CREATE TABLE IF NOT EXISTS "HealthPlan" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "name" TEXT NOT NULL,
  "health_plan_image" TEXT
);

CREATE TABLE IF NOT EXISTS "DoctorHealthPlan" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "id_doctor" uuid,
  "id_health_plan" uuid,
   FOREIGN KEY ("id_doctor") REFERENCES "Doctor" ("id"),
   FOREIGN KEY ("id_health_plan") REFERENCES "HealthPlan" ("id")
);

CREATE TABLE IF NOT EXISTS "Patient" (
  "id" uuid UNIQUE PRIMARY KEY NOT NULL,
  "cpf" TEXT,
  FOREIGN KEY ("id") REFERENCES "User" ("id")
);

CREATE TABLE IF NOT EXISTS "Information" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "title" TEXT,
  "link" TEXT,
  "image" TEXT,
  "description" TEXT,
  "id_doctor" uuid NOT NULL,
  FOREIGN KEY ("id_doctor") REFERENCES "Doctor" ("id")
);

CREATE TABLE IF NOT EXISTS "DoctorPatient" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "id_doctor" uuid,
  "id_patient" uuid,
  FOREIGN KEY ("id_doctor") REFERENCES "Doctor" ("id"),
  FOREIGN KEY ("id_patient") REFERENCES "Patient" ("id")
);

CREATE TABLE IF NOT EXISTS "Appointment" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "id_doctor" uuid,
  "id_patient" uuid,
  "appointment_date" timestamp NOT NULL,
  FOREIGN KEY ("id_doctor") REFERENCES "Doctor" ("id"),
  FOREIGN KEY ("id_patient") REFERENCES "Patient" ("id")
);

CREATE TABLE IF NOT EXISTS "Medicine" (
  "id" INTEGER PRIMARY KEY NOT NULL,
  "name" TEXT NOT NULL,
  "leaflet" TEXT
);

CREATE TABLE IF NOT EXISTS "Dosage" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "id_patient" uuid,
  "id_medicine" INTEGER,
  "quantity" TEXT NOT NULL,
  "initial_hour" timestamp,
  "frequency" timestamp,
  "final_date" timestamp,
  FOREIGN KEY ("id_patient") REFERENCES "Patient" ("id"),
  FOREIGN KEY ("id_medicine") REFERENCES "Medicine" ("id")
);

CREATE TABLE IF NOT EXISTS "Incompatibility" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "id_medicine" INTEGER,
  "id_medicine_inc" INTEGER,
  "severity" TEXT,
  "description" TEXT,
  FOREIGN KEY ("id_medicine") REFERENCES "Medicine" ("id"),
  FOREIGN KEY ("id_medicine_inc") REFERENCES "Medicine" ("id")
);

CREATE TABLE IF NOT EXISTS "Exam" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "description" TEXT,
  "exam_date" timestamp NOT NULL,
  "is_done" BOOLEAN,
  "id_patient" uuid,
  FOREIGN KEY ("id_patient") REFERENCES "Patient" ("id")
);

CREATE TABLE IF NOT EXISTS "Post" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "content" TEXT,
  "creation_date" timestamp NOT NULL,
  "id_patient" uuid,
  "is_main" BOOLEAN NOT NULL,
  "id_post" uuid,
  FOREIGN KEY ("id_post") REFERENCES "Post" ("id"),
  FOREIGN KEY ("id_patient") REFERENCES "Patient" ("id")
);

CREATE TABLE IF NOT EXISTS "Vote" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "is_like" bool NOT NULL,
  "id_post" uuid NOT NULL,
  "id_patient" uuid,
  FOREIGN KEY ("id_patient") REFERENCES "Patient" ("id"),
  FOREIGN KEY ("id_post") REFERENCES "Post" ("id")
);