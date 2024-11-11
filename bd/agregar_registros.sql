USE sistema_constancias;

INSERT INTO `TIPO_USUARIO` (`nombre`) VALUES
                                          ('Administrador'),
                                          ('Docente'),
                                          ('Personal administrativo');

INSERT INTO `TIPO_CONTRATACION` (`nombre`) VALUES
                                       ('Planta'),
                                       ('Interino por plaza'),
                                       ('Interino por tiempo determinado'),
                                       ('Suplente o sustituto'),
                                       ('Trabajos especificos');

INSERT INTO `CATEGORIA` (`nombre`) VALUES
                                       ('Rector'),
                                       ('Secretario'),
                                       ('Director academico'),
                                       ('Secretario ');

INSERT INTO `USUARIO` (`no_personal`,
                       `nombre`,
                       `apellido_paterno`,
                       `apellido_materno`,
                       `correo_electronico`,
                       `password`,
                       `firma_digital`,
                       `id_tipo_usuario`,
                       `id_categoria`,
                       `id_tipo_contratacion`) VALUES
                        ('HEOLRA2910', 'Raul', 'Hernandez', 'Olivares', 'raulh230600@gmail.com', 'password', '9b2e5c4b85b2b1e0eecf4e3b716c0f08e4ff93e07d13d02f77e52a3b9cbf3d31', 3, 2 ,null),
                        ('MOCRMI2910', 'Miguel', 'Morales', 'Cruz', 'migue02@gmail.com', 'password', null, 2, null, 1),
                        ('VICOAL2910', 'Albhieri', 'Villa', 'Contreras', 'alcrivico@gmail.com', 'password', null, 1, null, null);

INSERT INTO PARTICIPACION (`constatacion`, `fecha_inicio`, `fecha_fin`, `tipo_participacion`, id_docente) VALUES 
						('El que suscribe, director de la Facultad de Estadística e Informática, de la Universidad Veracruzana HACE CONSTAR', '2024-10-10', '2024-10-19', 'PlaDEA', 2), 
                        ('El que suscribe, director de la Facultad de Estadística e Informática, de la Universidad Veracruzana HACE CONSTAR', '2024-10-10', '2024-10-19', 'Imparticion EE', 2),
                        ('El que suscribe, director de la Facultad de Estadística e Informática, de la Universidad Veracruzana HACE CONSTAR', '2024-10-10', '2024-10-19', 'Proyecto de campo', 2),
						('El que suscribe, director de la Facultad de Estadística e Informática, de la Universidad Veracruzana HACE CONSTAR', '2024-10-10', '2024-10-19', 'Jurado', 2);

INSERT INTO `PERIODO_ESCOLAR` (`fecha_inicio`, `fecha_fin`, `nombre`) VALUES
    ('2024-01-01', '2024-06-30', 'Fe-Junio 2024'),
    ('2024-07-01', '2024-12-31', 'Agosto-Diciembre 2024');

INSERT INTO `PLADEA` (`id_participacion`, `acciones`, `eje_estrategico`, `metas`, `objetivos_generales`, `programa_estrategico`)
VALUES
    (1, 'Implementación de programas de capacitación', 'Educación y Capacitación', 'Aumentar la participación de docentes en cursos anuales', 'Mejorar las habilidades pedagógicas del personal docente', 'Capacitación Docente');

INSERT INTO `IMPARTICION_EE` (`id_participacion`, `experiencia_educativa`, `bloque`, `creditos`, `horas`, `meses`, `programa_educativo`, `seccion`, `semanas`)
VALUES
    (2, 'Programación Orientada a Objetos', '2024-A', 8, 40, 4, 101, 2, 16);

INSERT INTO `PROYECTO_CAMPO` (`id_participacion`, `proyecto_realizado`, `impacto_obtenido`, `lugar`, `nombre_alumnos`)
VALUES
    (3, 'Desarrollo de una Aplicación para Gestión de Inventarios', 'Mejora en eficiencia operativa en un 30%', 'Ciudad Universitaria', 'Juan Pérez, Ana López, Carlos Méndez');

INSERT INTO `JURADO` (`id_participacion`, `titulo_trabajo`, `fecha_presentacion`, `modalidad`, `nombre_alumnos`, `resultado_obtenido`)
VALUES
    (4, 'Implementación de Inteligencia Artificial en la Industria', '2024-05-20', 'Tesis', 'Luis Martínez, Sofía García', 'Aprobado con mención honorífica');


