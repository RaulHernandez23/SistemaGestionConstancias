CREATE VIEW vista_usuarios AS
SELECT
  u.no_personal,
  u.nombre,
  u.apellido_paterno,
  u.apellido_materno,
  u.correo_electronico,
  u.firma_digital,
  t_u.nombre AS tipo_usuario,
  t_c.nombre AS tipo_contratacion,
  c.nombre AS categoria 
FROM
  usuario u
  LEFT JOIN usuario_tipo_usuario u_t_u ON u.id = u_t_u.usuario_id
  LEFT JOIN tipo_usuario t_u ON t_u.id = u_t_u.tipo_usuario_id
  LEFT JOIN categoria c ON u.categoria_id = c.id
  LEFT JOIN tipo_contratacion t_c ON u.tipo_contratacion_id = t_c.id;