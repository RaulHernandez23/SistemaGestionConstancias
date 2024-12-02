USE sistema_constancias;

-- Más registros para la tabla categoria
INSERT INTO `categoria` (`nombre`) VALUES
('Asistente de Investigación'),
('Técnico Académico'),
('Coordinador de Programas'),
('Asesor Académico');

-- Más registros para la tabla programa_educativo
INSERT INTO `programa_educativo` (`nombre`) VALUES
('Ingeniería de Software'),
('Ingeniería en Ciencia de Datos'),
('Ingeniería en Sistemas y Tecnologías de la Información'),
('Ingeniería de Ciberseguridad e Infraestructura de Cómputo');

-- Más registros para la tabla tipo_contratacion
INSERT INTO `tipo_contratacion` (`nombre`) VALUES
('Parcial'),
('Cátedra'),
('Investigación');

-- Más registros para la tabla tipo_usuario
INSERT INTO `tipo_usuario` (`nombre`) VALUES
('Docente'),
('Personal administrativo'),
('Administrador');

-- Más registros para la tabla usuario
INSERT INTO `usuario` (`no_personal`, `nombre`, `apellido_paterno`, `apellido_materno`, `correo_electronico`, `password`, `firma_digital`, `categoria_id`, `tipo_contratacion_id`) VALUES
('D-010101', 'Daniel', 'Sánchez', 'Muñoz', 'daniel.sanchez@universidad.es', 'pwd123', 'firma_digital_daniel', 1, 2),
('A-020201', 'Laura', 'Castillo', NULL, 'laura.castillo@universidad.es', 'pwd456', NULL, 2, 3),
('A-030301', 'Miguel', 'Torres', 'Ramírez', 'miguel.torres@universidad.es', 'pwd789', 'firma_digital_miguel', 4, 1),
('A-020202', 'Patricia', 'Navarro', 'López', 'patricia.navarro@universidad.es', 'pwd012', NULL, 3, 2),
('A-030302', 'Andrés', 'Reyes', 'Castro', 'andres.reyes@universidad.es', 'pwd345', 'firma_digital_andres', 2, 1);

-- Más registros para la tabla usuario_tipo_usuario
INSERT INTO `usuario_tipo_usuario` (`tipo_usuario_id`, `usuario_id`) VALUES
(1, 1),
(2, 2),
(3, 3),
(1, 4),
(2, 5);

-- Más registros para la tabla periodo_escolar
INSERT INTO `periodo_escolar` (`fecha_inicio`, `fecha_fin`, `nombre`) VALUES
('2024-08-19', '2025-01-17', 'Agosto - Enero 24/25'),
('2024-02-06', '2024-07-04', 'Febrero - Julio 24'),
('2023-08-21', '2024-01-10', 'Agosto - Enero 23/24'),
('2023-02-07', '2023-06-23', 'Febrero - Julio 23');

-- Más registros para la tabla periodo_escolar
INSERT INTO `participacion` (`tipo_participacion`, `docente_id`, `periodo_escolar_id`) VALUES
('Impartición EE', 1, 2),
('PLADEA', 1, 3),
('Proyecto Campo', 1, 4),
('Jurado', 1, 1),
('PLADEA', 1, 2);

-- Más registros para la tabla imparticion_ee
INSERT INTO `imparticion_ee` (`participacion_id`, `experiencia_educativa`, `bloque`, `creditos`, `horas`, `meses`, `programa_educativo_id`, `seccion`, `semanas`) VALUES
(1, 'Bases de datos no relacionales', 'Bloque B', 5, 90, 6, 1, 2, 18);

-- Más registros para la tabla pladea
INSERT INTO `pladea` (`participacion_id`, `acciones`, `eje_estrategico`, `metas`, `objetivos_generales`, `programa_estrategico`) VALUES
(2, 'Mejorar habilidades de investigación', 'Investigación Básica', 'Desarrollar competencias analíticas', 'Fomentar el espíritu crítico', 'Programa de Formación en Investigación'),
(5, 'Implementar metodologías activas', 'Aprendizaje Activo', 'Motivar el aprendizaje autónomo', 'Fomentar el trabajo en equipo', 'Programa de Innovación Académica');

-- Más registros para la tabla proyecto_campo
INSERT INTO `proyecto_campo` (`participacion_id`, `proyecto_realizado`, `impacto_obtenido`, `lugar`, `nombre_alumnos`) VALUES
(3, 'Proyecto de Electrónica en el Campo', 'Medio impacto en áreas rurales', 'Pueblo Nuevo', 'David Pérez, Marta Gómez');

-- Más registros para la tabla jurado
INSERT INTO `jurado` (`participacion_id`, `titulo_trabajo`, `fecha_presentacion`, `modalidad`, `nombre_alumnos`, `resultado_obtenido`) VALUES
(4, 'Evaluación de Algoritmos para IA', '2025-11-20', 'Virtual', 'Paula Ramírez, Marcos Díaz', 'Aprobado');