CREATE DATABASE IF NOT EXISTS sistema_constancias;

USE sistema_constancias;

CREATE TABLE `CATEGORIA`(
    `id` INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL
);
CREATE TABLE `IMPARTICION_EE`(
    `id_participacion` INT NOT NULL,
    `experiencia_educativa` VARCHAR(255) NOT NULL,
    `bloque` VARCHAR(255) NOT NULL,
    `creditos` INT NOT NULL,
    `horas` INT NOT NULL,
    `meses` INT NOT NULL,
    `programa_educativo` INT NOT NULL,
    `seccion` INT NOT NULL,
    `semanas` INT NOT NULL
);
CREATE TABLE `TIPO_USUARIO`(
    `id` INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL
);
CREATE TABLE `PLADEA`(
    `id_participacion` INT NOT NULL,
    `acciones` VARCHAR(255) NOT NULL,
    `eje_estrategico` VARCHAR(255) NOT NULL,
    `metas` VARCHAR(255) NOT NULL,
    `objetivos_generales` VARCHAR(255) NOT NULL,
    `programa_estrategico` VARCHAR(255) NOT NULL
);
CREATE TABLE `PARTICIPACION`(
    `id` INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `constatacion` VARCHAR(255) NOT NULL,
    `fecha_inicio` DATE NOT NULL,
    `fecha_fin` DATE NOT NULL,
    `tipo_participacion` VARCHAR(255) NOT NULL,
    `id_docente` INT NOT NULL
);
CREATE TABLE `TIPO_CONTRATACION`(
    `id` INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL
);
CREATE TABLE `PROYECTO_CAMPO`(
    `id_participacion` INT NOT NULL,
    `proyecto_realizado` VARCHAR(255) NOT NULL,
    `impacto_obtenido` VARCHAR(255) NOT NULL,
    `lugar` VARCHAR(255) NOT NULL,
    `nombre_alumnos` VARCHAR(255) NOT NULL
);
CREATE TABLE `USUARIO`(
    `id` INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `no_personal` VARCHAR(255) NOT NULL,
    `nombre` VARCHAR(255) NOT NULL,
    `apellido_paterno` VARCHAR(255) NOT NULL,
    `apellido_materno` VARCHAR(255) NULL,
    `correo_electronico` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `firma_digital` VARCHAR(255) NULL,
    `id_tipo_usuario` INT NOT NULL,
    `id_categoria` INT NULL,
    `id_tipo_contratacion` INT NULL
);
CREATE TABLE `JURADO`(
    `id_participacion` INT NOT NULL,
    `titulo_trabajo` VARCHAR(255) NOT NULL,
    `fecha_presentacion` DATE NOT NULL,
    `modalidad` VARCHAR(255) NOT NULL,
    `nombre_alumnos` VARCHAR(255) NOT NULL,
    `resultado_obtenido` VARCHAR(255) NOT NULL
);
ALTER TABLE
    `USUARIO` ADD CONSTRAINT `usuario_id_tipo_usuario_foreign` FOREIGN KEY(`id_tipo_usuario`) REFERENCES `TIPO_USUARIO`(`id`);
ALTER TABLE
    `USUARIO` ADD CONSTRAINT `usuario_id_categoria_foreign` FOREIGN KEY(`id_categoria`) REFERENCES `CATEGORIA`(`id`);
ALTER TABLE
    `IMPARTICION_EE` ADD CONSTRAINT `imparticion_ee_id_participacion_foreign` FOREIGN KEY(`id_participacion`) REFERENCES `PARTICIPACION`(`id`);
ALTER TABLE
    `PLADEA` ADD CONSTRAINT `pladea_id_participacion_foreign` FOREIGN KEY(`id_participacion`) REFERENCES `PARTICIPACION`(`id`);
ALTER TABLE
    `PARTICIPACION` ADD CONSTRAINT `participacion_id_docente_foreign` FOREIGN KEY(`id_docente`) REFERENCES `USUARIO`(`id`);
ALTER TABLE
    `JURADO` ADD CONSTRAINT `jurado_id_participacion_foreign` FOREIGN KEY(`id_participacion`) REFERENCES `PARTICIPACION`(`id`);
ALTER TABLE
    `USUARIO` ADD CONSTRAINT `usuario_id_tipo_contratacion_foreign` FOREIGN KEY(`id_tipo_contratacion`) REFERENCES `TIPO_CONTRATACION`(`id`);
ALTER TABLE
    `PROYECTO_CAMPO` ADD CONSTRAINT `proyecto_campo_id_participacion_foreign` FOREIGN KEY(`id_participacion`) REFERENCES `PARTICIPACION`(`id`);