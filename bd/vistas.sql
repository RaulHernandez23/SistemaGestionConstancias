CREATE VIEW vista_usuarios AS
SELECT
  u.no_personal,
  u.nombre,
  u.apellido_paterno,
  u.apellido_materno,
  u.correo_electronico,
  u.`password`,
  u.firma_digital,
  t_u.nombre AS tipo_usuario,
  t_c.nombre AS tipo_contratacion,
  c.nombre AS categoria 
FROM
  usuario u
  LEFT JOIN tipo_usuario t_u ON u.id_tipo_usuario = t_u.id
  LEFT JOIN categoria c ON u.id_categoria = c.id
  LEFT JOIN tipo_contratacion t_c ON u.id_tipo_contratacion = t_c.id;