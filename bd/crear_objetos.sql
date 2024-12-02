CREATE VIEW v_usuarios AS
SELECT
  u.no_personal,
  u.nombre,
  u.apellido_paterno,
  u.apellido_materno,
  u.correo_electronico,
  u.firma_digital,
  GROUP_CONCAT(DISTINCT t_u.nombre ORDER BY t_u.nombre SEPARATOR ', ') AS tipo_usuario,
  GROUP_CONCAT(DISTINCT t_c.nombre ORDER BY t_c.nombre SEPARATOR ', ') AS tipo_contratacion,
  c.nombre AS categoria 
FROM
  usuario u
  LEFT JOIN usuario_tipo_usuario u_t_u ON u.id = u_t_u.usuario_id
  LEFT JOIN tipo_usuario t_u ON t_u.id = u_t_u.tipo_usuario_id
  LEFT JOIN categoria c ON u.categoria_id = c.id
  LEFT JOIN tipo_contratacion t_c ON u.tipo_contratacion_id = t_c.id
GROUP BY
  u.id;

CREATE VIEW v_imparticion_ee AS
SELECT
    u.no_personal,
    CONCAT(u.nombre, ' ', u.apellido_paterno, ' ', u.apellido_materno) AS nombre_docente,
    pe.nombre AS periodo_escolar,
    iee.experiencia_educativa,
    iee.bloque,
    iee.creditos,
    iee.horas,
    iee.meses,
    iee.seccion,
    iee.semanas,
    pr.nombre AS programa_educativo
FROM
    imparticion_ee iee
    JOIN participacion p ON iee.participacion_id = p.id
    JOIN usuario u ON p.docente_id = u.id
    JOIN periodo_escolar pe ON p.periodo_escolar_id = pe.id
    JOIN programa_educativo pr ON iee.programa_educativo_id = pr.id;

CREATE VIEW v_jurado AS
SELECT
    u.no_personal,
    CONCAT(u.nombre, ' ', u.apellido_paterno, ' ', u.apellido_materno) AS nombre_docente,
    pe.nombre AS periodo_escolar,
    j.titulo_trabajo,
    j.fecha_presentacion,
    j.modalidad,
    j.nombre_alumnos,
    j.resultado_obtenido
FROM
    jurado j
    JOIN participacion p ON j.participacion_id = p.id
    JOIN usuario u ON p.docente_id = u.id
    JOIN periodo_escolar pe ON p.periodo_escolar_id = pe.id;

CREATE VIEW v_proyecto_campo AS
SELECT
    u.no_personal,
    CONCAT(u.nombre, ' ', u.apellido_paterno, ' ', u.apellido_materno) AS nombre_docente,
    pe.nombre AS periodo_escolar,
    pc.proyecto_realizado,
    pc.impacto_obtenido,
    pc.lugar,
    pc.nombre_alumnos
FROM
    proyecto_campo pc
    JOIN participacion p ON pc.participacion_id = p.id
    JOIN usuario u ON p.docente_id = u.id
    JOIN periodo_escolar pe ON p.periodo_escolar_id = pe.id;

CREATE VIEW v_pladea AS
SELECT
    u.no_personal,
    CONCAT(u.nombre, ' ', u.apellido_paterno, ' ', u.apellido_materno) AS nombre_docente,
    pe.nombre AS periodo_escolar,
    pl.acciones,
    pl.eje_estrategico,
    pl.metas,
    pl.objetivos_generales,
    pl.programa_estrategico
FROM
    pladea pl
    JOIN participacion p ON pl.participacion_id = p.id
    JOIN usuario u ON p.docente_id = u.id
    JOIN periodo_escolar pe ON p.periodo_escolar_id = pe.id;

DELIMITER $$

CREATE PROCEDURE SP_registrar_participacion_imparticion(
    IN no_personal VARCHAR(255),
    IN periodo_escolar VARCHAR(255),
    IN experiencia_educativa VARCHAR(255),
    IN bloque VARCHAR(255),
    IN creditos INT,
    IN horas INT,
    IN meses INT,
    IN seccion INT,
    IN semanas INT,
    IN programa_educativo VARCHAR(255)
)
BEGIN
    DECLARE nuevo_id INT;
    DECLARE docente_id INT;
    DECLARE periodo_escolar_id INT;
    DECLARE programa_educativo_id INT;
    DECLARE comprobar_id INT;
    
    START TRANSACTION;

    -- Obtener el id del docente
    SELECT id INTO docente_id FROM usuario u WHERE u.no_personal = no_personal;
    
    IF docente_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se encontró el docente';
        ROLLBACK;
    END IF;

    -- Obtener el id del periodo escolar
    SELECT id INTO periodo_escolar_id FROM periodo_escolar pe WHERE pe.nombre = periodo_escolar;
    
    IF periodo_escolar_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se encontró el periodo escolar';
    END IF;

    -- Insertar en la tabla participacion
    INSERT INTO participacion (tipo_participacion, docente_id, periodo_escolar_id)
    VALUES ('Impartición de experiencia educativa', docente_id, periodo_escolar_id);
    
    SET nuevo_id = LAST_INSERT_ID();
    
    -- Obtener el id del programa educativo
    SELECT id INTO programa_educativo_id FROM programa_educativo p WHERE p.nombre = programa_educativo;
    
    IF programa_educativo_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se encontró el programa educativo';
        ROLLBACK;
    END IF;

    -- Insertar en la tabla imparticion_ee
    INSERT INTO imparticion_ee (participacion_id, experiencia_educativa, bloque, creditos, horas, meses, seccion, semanas, programa_educativo_id)
    VALUES (nuevo_id, experiencia_educativa, bloque, creditos, horas, meses, seccion, semanas, programa_educativo_id);

    SET comprobar_id = LAST_INSERT_ID();
    
    IF comprobar_id != nuevo_id THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se pudo registrar la participación';
      ROLLBACK;
    END IF;
    
    -- Si todo va bien, se realiza el COMMIT
    COMMIT;

END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE SP_registrar_participacion_jurado(
    IN no_personal VARCHAR(255),
    IN periodo_escolar VARCHAR(255),
    IN titulo_trabajo VARCHAR(255),
    IN fecha_presentacion DATE,
    IN modalidad VARCHAR(255),
    IN nombre_alumnos VARCHAR(255),
    IN resultado_obtenido VARCHAR(255)
)
BEGIN
    DECLARE nuevo_id INT;
    DECLARE docente_id INT;
    DECLARE periodo_escolar_id INT;
    DECLARE comprobar_id INT;
    
    START TRANSACTION;

    -- Obtener el id del docente
    SELECT id INTO docente_id FROM usuario u WHERE u.no_personal = no_personal;
    
    IF docente_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se encontró el docente';
        ROLLBACK;
    END IF;

    -- Obtener el id del periodo escolar
    SELECT id INTO periodo_escolar_id FROM periodo_escolar pe WHERE pe.nombre = periodo_escolar;
    
    IF periodo_escolar_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se encontró el periodo escolar';
    END IF;

    -- Insertar en la tabla participacion
    INSERT INTO participacion (tipo_participacion, docente_id, periodo_escolar_id)
    VALUES ('Jurado', docente_id, periodo_escolar_id);
    
    SET nuevo_id = LAST_INSERT_ID();

    -- Insertar en la tabla jurado
    INSERT INTO jurado (participacion_id, titulo_trabajo, fecha_presentacion, modalidad, nombre_alumnos, resultado_obtenido)
    VALUES (nuevo_id,titulo_trabajo, fecha_presentacion, modalidad, nombre_alumnos, resultado_obtenido);

    SET comprobar_id = LAST_INSERT_ID();
    
    IF comprobar_id != nuevo_id THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se pudo registrar la participación';
      ROLLBACK;
    END IF;
    
    -- Si todo va bien, se realiza el COMMIT
    COMMIT;

END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE SP_registrar_participacion_proyecto(
    IN no_personal VARCHAR(255),
    IN periodo_escolar VARCHAR(255),
    IN proyecto_realizado VARCHAR(255),
    IN impacto_obtenido VARCHAR(255),
    IN lugar VARCHAR(255),
    IN nombre_alumnos VARCHAR(255)
)
BEGIN
    DECLARE nuevo_id INT;
    DECLARE docente_id INT;
    DECLARE periodo_escolar_id INT;
    DECLARE comprobar_id INT;
    
    START TRANSACTION;

    -- Obtener el id del docente
    SELECT id INTO docente_id FROM usuario u WHERE u.no_personal = no_personal;
    
    IF docente_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se encontró el docente';
        ROLLBACK;
    END IF;

    -- Obtener el id del periodo escolar
    SELECT id INTO periodo_escolar_id FROM periodo_escolar pe WHERE pe.nombre = periodo_escolar;
    
    IF periodo_escolar_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se encontró el periodo escolar';
    END IF;

    -- Insertar en la tabla participacion
    INSERT INTO participacion (tipo_participacion, docente_id, periodo_escolar_id)
    VALUES ('Proyecto de campo', docente_id, periodo_escolar_id);
    
    SET nuevo_id = LAST_INSERT_ID();

    -- Insertar en la tabla proyecto_campo
    INSERT INTO proyecto_campo (participacion_id, proyecto_realizado, impacto_obtenido, lugar, nombre_alumnos)
    VALUES (nuevo_id, proyecto_realizado, impacto_obtenido, lugar, nombre_alumnos);

    SET comprobar_id = LAST_INSERT_ID();
    
    IF comprobar_id != nuevo_id THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se pudo registrar la participación';
      ROLLBACK;
    END IF;
    
    -- Si todo va bien, se realiza el COMMIT
    COMMIT;

END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE SP_registrar_participacion_pladea(
    IN no_personal VARCHAR(255),
    IN periodo_escolar VARCHAR(255),
    IN acciones VARCHAR(255),
    IN eje_estrategico VARCHAR(255),
    IN metas VARCHAR(255),
    IN objetivos_generales VARCHAR(255),
    IN programa_estrategico VARCHAR(255)
)
BEGIN
    DECLARE nuevo_id INT;
    DECLARE docente_id INT;
    DECLARE periodo_escolar_id INT;
    DECLARE comprobar_id INT;
    
    START TRANSACTION;

    -- Obtener el id del docente
    SELECT id INTO docente_id FROM usuario u WHERE u.no_personal = no_personal;
    
    IF docente_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se encontró el docente';
        ROLLBACK;
    END IF;

    -- Obtener el id del periodo escolar
    SELECT id INTO periodo_escolar_id FROM periodo_escolar pe WHERE pe.nombre = periodo_escolar;
    
    IF periodo_escolar_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se encontró el periodo escolar';
    END IF;

    -- Insertar en la tabla participacion
    INSERT INTO participacion (tipo_participacion, docente_id, periodo_escolar_id)
    VALUES ('PLADEA', docente_id, periodo_escolar_id);
    
    SET nuevo_id = LAST_INSERT_ID();

    -- Insertar en la tabla pladea
    INSERT INTO pladea (participacion_id, acciones, eje_estrategico, metas, objetivos_generales, programa_estrategico)
    VALUES (nuevo_id, acciones, eje_estrategico, metas, objetivos_generales, programa_estrategico);

    SET comprobar_id = LAST_INSERT_ID();
    
    IF comprobar_id != nuevo_id THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se pudo registrar la participación';
      ROLLBACK;
    END IF;
    
    -- Si todo va bien, se realiza el COMMIT
    COMMIT;

END$$

DELIMITER ;
