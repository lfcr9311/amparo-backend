CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE "User" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "email" TEXT UNIQUE NOT NULL,
  "password" TEXT NOT NULL,
  "salt" TEXT NOT NULL,
  "name" TEXT NOT NULL,
  "cellphone" TEXT NOT NULL,
  "profile_picture" TEXT,
  "is_anonymous" BOOL
);

CREATE TABLE "Doctor" (
  "id" uuid UNIQUE PRIMARY KEY NOT NULL,
  "crm" INTEGER UNIQUE NOT NULL,
  "uf" TEXT NOT NULL
);

CREATE TABLE "DoctorHealthPlan" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "id_doctor" uuid,
  "id_health_plan" uuid
);

CREATE TABLE "HealthPlan" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "name" TEXT NOT NULL,
  "health_plan_image" TEXT
);

CREATE TABLE "Patient" (
  "id" uuid UNIQUE PRIMARY KEY NOT NULL,
  "cpf" TEXT
);

CREATE TABLE "Information" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "title" TEXT,
  "link" TEXT,
  "image" TEXT,
  "description" TEXT,
  "id_doctor" uuid NOT NULL
);

CREATE TABLE "DoctorPatient" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "id_doctor" uuid,
  "id_patient" uuid
);

CREATE TABLE "Appointment" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "id_doctor" uuid,
  "id_patient" uuid,
  "appointment_date" timestamp NOT NULL
);

CREATE TABLE "Dosage" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "id_patient" uuid,
  "id_medicine" uuid,
  "quantity" TEXT NOT NULL,
  "initial_hour" timestamp,
  "frequency" timestamp,
  "final_date" timestamp
);

CREATE TABLE "Medicine" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "name" TEXT NOT NULL,
  "leaflet" TEXT
);

CREATE TABLE "Incompatibility" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "id_medicine" uuid,
  "id_medicine_inc" uuid,
  "severity" TEXT,
  "description" TEXT
);

CREATE TABLE "Exam" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "description" TEXT,
  "exam_date" timestamp NOT NULL,
  "is_done" BOOLEAN,
  "id_patient" uuid
);

CREATE TABLE "Post" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "content" TEXT,
  "creation_date" timestamp NOT NULL,
  "id_patient" uuid,
  "is_main" BOOLEAN NOT NULL,
  "id_post" uuid
);

CREATE TABLE "Vote" (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  "is_like" bool NOT NULL,
  "id_post" uuid NOT NULL,
  "id_patient" uuid
);

ALTER TABLE "Doctor" ADD FOREIGN KEY ("id") REFERENCES "User" ("id");

ALTER TABLE "Patient" ADD FOREIGN KEY ("id") REFERENCES "User" ("id");

ALTER TABLE "DoctorPatient" ADD FOREIGN KEY ("id_doctor") REFERENCES "Doctor" ("id");

ALTER TABLE "DoctorPatient" ADD FOREIGN KEY ("id_patient") REFERENCES "Patient" ("id");

ALTER TABLE "Appointment" ADD FOREIGN KEY ("id_doctor") REFERENCES "Doctor" ("id");

ALTER TABLE "Information" ADD FOREIGN KEY ("id_doctor") REFERENCES "Doctor" ("id");

ALTER TABLE "Appointment" ADD FOREIGN KEY ("id_patient") REFERENCES "Patient" ("id");

ALTER TABLE "Dosage" ADD FOREIGN KEY ("id_patient") REFERENCES "Patient" ("id");

ALTER TABLE "Dosage" ADD FOREIGN KEY ("id_medicine") REFERENCES "Medicine" ("id");

ALTER TABLE "Incompatibility" ADD FOREIGN KEY ("id_medicine") REFERENCES "Medicine" ("id");

ALTER TABLE "Incompatibility" ADD FOREIGN KEY ("id_medicine_inc") REFERENCES "Medicine" ("id");

ALTER TABLE "Exam" ADD FOREIGN KEY ("id_patient") REFERENCES "Patient" ("id");

ALTER TABLE "Post" ADD FOREIGN KEY ("id_patient") REFERENCES "Patient" ("id");

ALTER TABLE "Vote" ADD FOREIGN KEY ("id_post") REFERENCES "Post" ("id");

ALTER TABLE "Vote" ADD FOREIGN KEY ("id_patient") REFERENCES "Patient" ("id");

ALTER TABLE "DoctorHealthPlan" ADD FOREIGN KEY ("id_health_plan") REFERENCES "HealthPlan" ("id");

ALTER TABLE "DoctorHealthPlan" ADD FOREIGN KEY ("id_doctor") REFERENCES "Doctor" ("id");