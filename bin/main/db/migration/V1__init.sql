CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "Usuario" (
  "id" uuid PRIMARY KEY NOT NULL default generate_uuid_v4(),
  "email" TEXT UNIQUE NOT NULL,
  "senha" TEXT NOT NULL,
  "nome" TEXT NOT NULL,
  "telefone" INTEGER NOT NULL,
  "foto_perfil" TEXT,
  "anonimo" BOOL
);

CREATE TABLE "Medico" (
  "id" uuid UNIQUE PRIMARY KEY NOT NULL,
  "crm" INTEGER UNIQUE NOT NULL,
  "uf" TEXT NOT NULL
);

CREATE TABLE "Paciente" (
  "id" uuid UNIQUE PRIMARY KEY NOT NULL,
  "cpf" TEXT
);

CREATE TABLE "Informacao" (
  "id" uuid PRIMARY KEY NOT NULL default generate_uuid_v4(),
  "titulo" TEXT,
  "link" TEXT,
  "imagem" TEXT,
  "descricao" TEXT,
  "id_medico" uuid NOT NULL
);

CREATE TABLE "MedicoPaciente" (
  "id" uuid PRIMARY KEY NOT NULL default generate_uuid_v4(),
  "id_medico" INTEGER,
  "id_paciente" INTEGER
);

CREATE TABLE "Consulta" (
  "id" uuid PRIMARY KEY NOT NULL default generate_uuid_v4(),
  "id_medico" INTEGER,
  "id_paciente" INTEGER,
  "data_consulta" timestamp NOT NULL
);

CREATE TABLE "Dosagem" (
  "id" uuid PRIMARY KEY NOT NULL default generate_uuid_v4(),
  "id_paciente" INTEGER,
  "id_medicamento" INTEGER,
  "quantidade" TEXT NOT NULL,
  "horario_inicial" timestamp,
  "frequencia" timestamp,
  "data_final" timestamp
);

CREATE TABLE "Medicamento" (
  "id" uuid PRIMARY KEY NOT NULL default generate_uuid_v4(),
  "nome" TEXT NOT NULL,
  "bula" TEXT
);

CREATE TABLE "Incompatibilidade" (
  "id" uuid PRIMARY KEY NOT NULL default generate_uuid_v4(),
  "id_medicamento" INTEGER,
  "id_medicamento_inc" INTEGER,
  "severidade" TEXT,
  "descricao" TEXT
);

CREATE TABLE "Exame" (
  "id" uuid PRIMARY KEY NOT NULL default generate_uuid_v4(),
  "descricao" TEXT,
  "data_exame" timestamp NOT NULL,
  "realizado" BOOLEAN,
  "id_paciente" INTEGER
);

CREATE TABLE "Postagem" (
  "id" uuid PRIMARY KEY NOT NULL default generate_uuid_v4(),
  "conteudo" TEXT,
  "data_criacao" timestamp NOT NULL,
  "id_paciente" INTEGER,
  "main" BOOLEAN NOT NULL,
  "id_postagem_mae" INTEGER
);

CREATE TABLE "Votacao" (
  "id" uuid PRIMARY KEY NOT NULL default generate_uuid_v4(),
  "tipo" BOOLEAN NOT NULL,
  "id_postagem" INTEGER NOT NULL,
  "id_paciente" INTEGER
);

ALTER TABLE "Medico" ADD FOREIGN KEY ("id") REFERENCES "Usuario" ("id");

ALTER TABLE "Paciente" ADD FOREIGN KEY ("id") REFERENCES "Usuario" ("id");

ALTER TABLE "MedicoPaciente" ADD FOREIGN KEY ("id_medico") REFERENCES "Medico" ("id");

ALTER TABLE "MedicoPaciente" ADD FOREIGN KEY ("id_paciente") REFERENCES "Paciente" ("id");

ALTER TABLE "Consulta" ADD FOREIGN KEY ("id_medico") REFERENCES "Medico" ("id");

ALTER TABLE "Informacao" ADD FOREIGN KEY ("id_medico") REFERENCES "Medico" ("id");

ALTER TABLE "Consulta" ADD FOREIGN KEY ("id_paciente") REFERENCES "Paciente" ("id");

ALTER TABLE "Dosagem" ADD FOREIGN KEY ("id_paciente") REFERENCES "Paciente" ("id");

ALTER TABLE "Dosagem" ADD FOREIGN KEY ("id_medicamento") REFERENCES "Medicamento" ("id");

ALTER TABLE "Incompatibilidade" ADD FOREIGN KEY ("id_medicamento") REFERENCES "Medicamento" ("id");

ALTER TABLE "Incompatibilidade" ADD FOREIGN KEY ("id_medicamento_inc") REFERENCES "Medicamento" ("id");

ALTER TABLE "Exame" ADD FOREIGN KEY ("id_paciente") REFERENCES "Paciente" ("id");

ALTER TABLE "Postagem" ADD FOREIGN KEY ("id_paciente") REFERENCES "Paciente" ("id");

ALTER TABLE "Votacao" ADD FOREIGN KEY ("id_postagem") REFERENCES "Postagem" ("id");

ALTER TABLE "Votacao" ADD FOREIGN KEY ("id_paciente") REFERENCES "Paciente" ("id");