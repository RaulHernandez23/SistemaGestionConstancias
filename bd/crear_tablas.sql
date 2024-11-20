CREATE DATABASE IF NOT EXISTS sistema_constancias;
USE sistema_constancias;

CREATE TABLE `CATEGORIA`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL
);
CREATE TABLE `IMPARTICION_EE`(
    `participacion_id` INT NOT NULL,
    `experiencia_educativa` VARCHAR(255) NOT NULL,
    `bloque` VARCHAR(255) NOT NULL,
    `creditos` INT NOT NULL,
    `horas` INT NOT NULL,
    `meses` INT NOT NULL,
    `programa_educativo` INT NOT NULL,
    `seccion` INT NOT NULL,
    `semanas` INT NOT NULL
);
CREATE TABLE `PLADEA`(
    `participacion_id` INT NOT NULL,
    `acciones` VARCHAR(255) NOT NULL,
    `eje_estrategico` VARCHAR(255) NOT NULL,
    `metas` VARCHAR(255) NOT NULL,
    `objetivos_generales` VARCHAR(255) NOT NULL,
    `programa_estrategico` VARCHAR(255) NOT NULL
);
CREATE TABLE `PROYECTO_CAMPO`(
    `participacion_id` INT NOT NULL,
    `proyecto_realizado` VARCHAR(255) NOT NULL,
    `impacto_obtenido` VARCHAR(255) NOT NULL,
    `lugar` VARCHAR(255) NOT NULL,
    `nombre_alumnos` VARCHAR(255) NOT NULL
);
CREATE TABLE `JURADO`(
    `participacion_id` INT NOT NULL,
    `titulo_trabajo` VARCHAR(255) NOT NULL,
    `fecha_presentacion` DATE NOT NULL,
    `modalidad` VARCHAR(255) NOT NULL,
    `nombre_alumnos` VARCHAR(255) NOT NULL,
    `resultado_obtenido` VARCHAR(255) NOT NULL
);
CREATE TABLE `TIPO_CONTRATACION`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL
);
CREATE TABLE `TIPO_USUARIO`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL
);
CREATE TABLE `USUARIO`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `no_personal` VARCHAR(255) NOT NULL,
    `nombre` VARCHAR(255) NOT NULL,
    `apellido_paterno` VARCHAR(255) NOT NULL,
    `apellido_materno` VARCHAR(255) NULL,
    `correo_electronico` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `firma_digital` VARCHAR(255) NULL,
    `categoria_id` INT NULL,
    `tipo_contratacion_id` INT NOT NULL
);
CREATE TABLE `USUARIO_TIPO_USUARIO`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `tipo_usuario_id` INT NOT NULL,
    `usuario_id` INT NOT NULL
);
CREATE TABLE `PERIODO_ESCOLAR`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `fecha_inicio` DATE NOT NULL,
    `fecha_fin` DATE NOT NULL,
    `nombre` VARCHAR(255) NOT NULL
);
CREATE TABLE `PARTICIPACION`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `constatacion` VARCHAR(255) NOT NULL,
    `tipo_participacion` VARCHAR(255) NOT NULL,
    `docente_id` INT NOT NULL,
    `periodo_escolar_id` INT NOT NULL
);
ALTER TABLE
    `PROYECTO_CAMPO` ADD CONSTRAINT `FK_proyecto_campo_participacion` FOREIGN KEY(`participacion_id`) REFERENCES `PARTICIPACION`(`id`);
ALTER TABLE
    `JURADO` ADD CONSTRAINT `FK_jurado_participacion` FOREIGN KEY(`participacion_id`) REFERENCES `PARTICIPACION`(`id`);
ALTER TABLE
    `USUARIO` ADD CONSTRAINT `FK_usuario_tipo_contratacion` FOREIGN KEY(`tipo_contratacion_id`) REFERENCES `TIPO_CONTRATACION`(`id`);
ALTER TABLE
    `PARTICIPACION` ADD CONSTRAINT `FK_participacion_docente` FOREIGN KEY(`docente_id`) REFERENCES `USUARIO`(`id`);
ALTER TABLE
    `USUARIO_TIPO_USUARIO` ADD CONSTRAINT `FK_usuario_tipo_usuario_tipo_usuario` FOREIGN KEY(`tipo_usuario_id`) REFERENCES `TIPO_USUARIO`(`id`);
ALTER TABLE
    `IMPARTICION_EE` ADD CONSTRAINT `FK_imparticion_ee_participacion` FOREIGN KEY(`participacion_id`) REFERENCES `PARTICIPACION`(`id`);
ALTER TABLE
    `PLADEA` ADD CONSTRAINT `FK_pladea_participacion` FOREIGN KEY(`participacion_id`) REFERENCES `PARTICIPACION`(`id`);
ALTER TABLE
    `PARTICIPACION` ADD CONSTRAINT `FK_participacion_periodo_escolar` FOREIGN KEY(`periodo_escolar_id`) REFERENCES `PERIODO_ESCOLAR`(`id`);
ALTER TABLE
    `USUARIO_TIPO_USUARIO` ADD CONSTRAINT `FK_usuario_tipo_usuario_usuario` FOREIGN KEY(`usuario_id`) REFERENCES `USUARIO`(`id`);
ALTER TABLE
    `USUARIO` ADD CONSTRAINT `FK_usuario_categoria` FOREIGN KEY(`categoria_id`) REFERENCES `CATEGORIA`(`id`);