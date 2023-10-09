INSERT INTO public."User" (id, email, "password", salt, "name", cellphone, profile_picture, is_anonymous) VALUES('1aff2be1-e3d9-4c9c-8a6a-585ee1d2bb08'::uuid, 'teste@teste-paciente.com.br', 'uIY/x/xi0lMShpeWsskuTjLklruc6xn4fjkwYvu47q8=', 'SkRtIuQPbRvcLIEb', 'paciente', '51999999999', NULL, false);
--Senha: paciente
INSERT INTO public."User" (id, email, "password", salt, "name", cellphone, profile_picture, is_anonymous) VALUES('681714eb-5482-4aec-a864-b680db307436'::uuid, 'teste@teste-doctor.com.br', 'sFC5tloJ++heFsxiWtxXVw2/DnK18Z2c1aYPHo0Ztwk=', 'WhydDAKwZjBnuxTc', 'doutor', '51999999999', NULL, false);
--Senha: doutor

INSERT INTO public."Patient" (id, cpf) VALUES('1aff2be1-e3d9-4c9c-8a6a-585ee1d2bb08'::uuid, '12345678920');
INSERT INTO public."Doctor" (id, crm, uf) VALUES('681714eb-5482-4aec-a864-b680db307436'::uuid, '123456', 'RS');