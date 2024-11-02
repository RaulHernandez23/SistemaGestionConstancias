DELIMITER //
CREATE PROCEDURE generarNoPersonal(IN usuarioID INT)
BEGIN
    DECLARE nuevoNoPersonal VARCHAR(255);

    -- Genera el número de personal combinando el prefijo "NP" y el ID del usuario
    SET nuevoNoPersonal = CONCAT('NP', usuarioID);

    -- Actualiza el campo 'no_personal' del usuario con el ID proporcionado
    UPDATE USUARIO
    SET no_personal = nuevoNoPersonal
    WHERE id = usuarioID;
END //
DELIMITER ;