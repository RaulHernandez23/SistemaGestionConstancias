USE sistema_constancias;

-- Más registros para la tabla CATEGORIA
INSERT INTO `CATEGORIA` (`nombre`) VALUES 
('Asistente de Investigación'), 
('Técnico Académico'), 
('Coordinador de Programas'), 
('Asesor Académico');

INSERT INTO `PROGRAMA_EDUCATIVO` (`nombre`) VALUES
('Ingeniería de Software'),
('Ingeniería en Ciencia de Datos'),
('Ingeniería en Sistemas y Tecnologías de la Información'),
('Ingeniería de Ciberseguridad e Infraestructura de Cómputo');

-- Más registros para la tabla TIPO_CONTRATACION
INSERT INTO `TIPO_CONTRATACION` (`nombre`) VALUES 
('Parcial'), 
('Cátedra'), 
('Investigación');

-- Más registros para la tabla TIPO_USUARIO
INSERT INTO `TIPO_USUARIO` (`nombre`) VALUES 
('Docente'), 
('Personal administrativo'), 
('Administrador');

-- Más registros para la tabla USUARIO
INSERT INTO `USUARIO` (`no_personal`, `nombre`, `apellido_paterno`, `apellido_materno`, `correo_electronico`, `password`, `firma_digital`, `categoria_id`, `tipo_contratacion_id`) VALUES 
('11111', 'Daniel', 'Sánchez', 'Muñoz', 'daniel.sanchez@universidad.es', 'pwd123', 'firma_digital_daniel', 1, 2),
('22222', 'Laura', 'Castillo', NULL, 'laura.castillo@universidad.es', 'pwd456', NULL, 2, 3),
('33333', 'Miguel', 'Torres', 'Ramírez', 'miguel.torres@universidad.es', 'pwd789', 'firma_digital_miguel', 4, 1),
('44444', 'Patricia', 'Navarro', 'López', 'patricia.navarro@universidad.es', 'pwd012', NULL, 3, 2),
('55555', 'Andrés', 'Reyes', 'Castro', 'andres.reyes@universidad.es', 'pwd345', 'firma_digital_andres', 2, 1);

-- Más registros para la tabla USUARIO_TIPO_USUARIO
INSERT INTO `USUARIO_TIPO_USUARIO` (`tipo_usuario_id`, `usuario_id`) VALUES 
(1, 1), 
(2, 2), 
(3, 3), 
(2, 4), 
(3, 5);

-- Más registros para la tabla PERIODO_ESCOLAR
INSERT INTO `PERIODO_ESCOLAR` (`fecha_inicio`, `fecha_fin`, `nombre`) VALUES 
('2024-01-15', '2024-06-15', 'Primavera 2024'), 
('2024-08-01', '2024-12-20', 'Otoño 2024'),
('2025-01-15', '2025-06-15', 'Primavera 2025'),
('2025-08-01', '2025-12-20', 'Otoño 2025');

-- Más registros para la tabla PARTICIPACION
INSERT INTO `PARTICIPACION` (`tipo_participacion`, `docente_id`, `periodo_escolar_id`) VALUES 
('Impartición EE', 1, 2),
('PLADEA', 1, 3),
('Proyecto Campo', 1, 4),
('Jurado', 1, 1),
('PLADEA', 1, 2);

-- Más registros para la tabla IMPARTICION_EE
INSERT INTO `IMPARTICION_EE` (`participacion_id`, `experiencia_educativa`, `bloque`, `creditos`, `horas`, `meses`, `programa_educativo`, `seccion`, `semanas`) VALUES 
(1, 'Física Avanzada', 'Bloque B', 5, 90, 6, 102, 2, 18);

-- Más registros para la tabla PLADEA
INSERT INTO `PLADEA` (`participacion_id`, `acciones`, `eje_estrategico`, `metas`, `objetivos_generales`, `programa_estrategico`) VALUES 
(2, 'Mejorar habilidades de investigación', 'Investigación Básica', 'Desarrollar competencias analíticas', 'Fomentar el espíritu crítico', 'Programa de Formación en Investigación'),
(5, 'Implementar metodologías activas', 'Aprendizaje Activo', 'Motivar el aprendizaje autónomo', 'Fomentar el trabajo en equipo', 'Programa de Innovación Académica');

-- Más registros para la tabla PROYECTO_CAMPO
INSERT INTO `PROYECTO_CAMPO` (`participacion_id`, `proyecto_realizado`, `impacto_obtenido`, `lugar`, `nombre_alumnos`) VALUES 
(3, 'Proyecto de Electrónica en el Campo', 'Medio impacto en áreas rurales', 'Pueblo Nuevo', 'David Pérez, Marta Gómez');

-- Más registros para la tabla JURADO
INSERT INTO `JURADO` (`participacion_id`, `titulo_trabajo`, `fecha_presentacion`, `modalidad`, `nombre_alumnos`, `resultado_obtenido`) VALUES 
(4, 'Evaluación de Algoritmos para IA', '2025-11-20', 'Virtual', 'Paula Ramírez, Marcos Díaz', 'Aprobado');