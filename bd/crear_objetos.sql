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

DELIMITER $$

CREATE PROCEDURE SP_registrar_participacion_imparticion(
    IN tipo_participacion VARCHAR(255),
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
    VALUES (tipo_participacion, docente_id, periodo_escolar_id);
    
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

