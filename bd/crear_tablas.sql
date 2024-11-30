CREATE DATABASE IF NOT EXISTS sistema_constancias;
USE sistema_constancias;

CREATE TABLE `categoria`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL
);

CREATE TABLE `programa_educativo`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL
);

CREATE TABLE `tipo_contratacion`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL
);

CREATE TABLE `tipo_usuario`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL
);

CREATE TABLE `usuario`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `no_personal` VARCHAR(255) NOT NULL,
    `nombre` VARCHAR(255) NOT NULL,
    `apellido_paterno` VARCHAR(255) NOT NULL,
    `apellido_materno` VARCHAR(255) NULL,
    `correo_electronico` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `firma_digital` VARCHAR(255) NULL,
    `categoria_id` INT NULL,
    `tipo_contratacion_id` INT NOT NULL,
    FOREIGN KEY (`categoria_id`) REFERENCES `categoria`(`id`),
    FOREIGN KEY (`tipo_contratacion_id`) REFERENCES `tipo_contratacion`(`id`)
);

CREATE TABLE `usuario_tipo_usuario`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `tipo_usuario_id` INT NOT NULL,
    `usuario_id` INT NOT NULL,
    FOREIGN KEY (`tipo_usuario_id`) REFERENCES `tipo_usuario`(`id`),
    FOREIGN KEY (`usuario_id`) REFERENCES `usuario`(`id`)
);

CREATE TABLE `periodo_escolar`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `fecha_inicio` DATE NOT NULL,
    `fecha_fin` DATE NOT NULL,
    `nombre` VARCHAR(255) NOT NULL
);

CREATE TABLE `participacion`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `tipo_participacion` VARCHAR(255) NOT NULL,
    `docente_id` INT NOT NULL,
    `periodo_escolar_id` INT NOT NULL,
    FOREIGN KEY (`docente_id`) REFERENCES `usuario`(`id`),
    FOREIGN KEY (`periodo_escolar_id`) REFERENCES `periodo_escolar`(`id`)
);

CREATE TABLE `imparticion_ee`(
    `participacion_id` INT NOT NULL,
    `experiencia_educativa` VARCHAR(255) NOT NULL,
    `bloque` VARCHAR(255) NOT NULL,
    `creditos` INT NOT NULL,
    `horas` INT NOT NULL,
    `meses` INT NOT NULL,
    `seccion` INT NOT NULL,
    `semanas` INT NOT NULL,
    `programa_educativo_id` INT NOT NULL,
    FOREIGN KEY (`participacion_id`) REFERENCES `participacion`(`id`),
    FOREIGN KEY (`programa_educativo_id`) REFERENCES `programa_educativo`(`id`)
);

CREATE TABLE `pladea`(
    `participacion_id` INT NOT NULL,
    `acciones` VARCHAR(255) NOT NULL,
    `eje_estrategico` VARCHAR(255) NOT NULL,
    `metas` VARCHAR(255) NOT NULL,
    `objetivos_generales` VARCHAR(255) NOT NULL,
    `programa_estrategico` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`participacion_id`) REFERENCES `participacion`(`id`)
);

CREATE TABLE `proyecto_campo`(
    `participacion_id` INT NOT NULL,
    `proyecto_realizado` VARCHAR(255) NOT NULL,
    `impacto_obtenido` VARCHAR(255) NOT NULL,
    `lugar` VARCHAR(255) NOT NULL,
    `nombre_alumnos` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`participacion_id`) REFERENCES `participacion`(`id`)
);

CREATE TABLE `jurado`(
    `participacion_id` INT NOT NULL,
    `titulo_trabajo` VARCHAR(255) NOT NULL,
    `fecha_presentacion` DATE NOT NULL,
    `modalidad` VARCHAR(255) NOT NULL,
    `nombre_alumnos` VARCHAR(255) NOT NULL,
    `resultado_obtenido` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`participacion_id`) REFERENCES `participacion`(`id`)
);