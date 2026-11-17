-- DATOS DE PRUEBA SOLO EN UN ESQUEMA LOCAL VACIO (NO PRODUCCION)
-- Crea 4 usuarios (admin, gerente, supervisor, ssoma) con clave: Cambiar2026!
-- Cambiar las contraseñas al terminar la demostración.
DECLARE
  v_perfil NUMBER; v_persona NUMBER; v_empleado NUMBER; v_usuario NUMBER; v_rol NUMBER; v_cargo NUMBER;
BEGIN
  FOR x IN (SELECT 'admin' usuario, 'ADMIN' rol FROM dual UNION ALL
            SELECT 'gerente','GERENTE' FROM dual UNION ALL
            SELECT 'supervisor','SUPERVISOR' FROM dual UNION ALL
            SELECT 'ssoma','SSOMA' FROM dual) LOOP
    SELECT COUNT(*) INTO v_usuario FROM USUARIO WHERE nombre_usuario=x.usuario;
    IF v_usuario=0 THEN
      SELECT COUNT(*) INTO v_rol FROM ROL WHERE nombre=x.rol;
      IF v_rol=0 THEN INSERT INTO ROL(nombre,estado) VALUES(x.rol,'A'); END IF;
      SELECT id_rol INTO v_rol FROM ROL WHERE nombre=x.rol;
      SELECT COUNT(*) INTO v_cargo FROM CARGO WHERE nombre=x.rol;
      IF v_cargo=0 THEN INSERT INTO CARGO(nombre,estado) VALUES(x.rol,'A'); END IF;
      SELECT id_cargo INTO v_cargo FROM CARGO WHERE nombre=x.rol;
      INSERT INTO PERFIL(tipo_perfil,estado) VALUES('NATURAL','A') RETURNING id_perfil INTO v_perfil;
      INSERT INTO PERSONA_NATURAL(id_perfil,nombres,apellidos,estado)
      VALUES(v_perfil,'Usuario',x.usuario,'A') RETURNING id_persona_natural INTO v_persona;
      INSERT INTO EMPLEADO(id_persona_natural,id_cargo,codigo_empleado,fecha_ingreso,estado)
      VALUES(v_persona,v_cargo,'EMP-'||UPPER(x.usuario),SYSDATE,'A') RETURNING id_empleado INTO v_empleado;
      INSERT INTO USUARIO(id_persona_natural,nombre_usuario,clave_hash,estado)
      VALUES(v_persona,x.usuario,'$2y$10$CNWnKEzsum.Jpa.YT9FOHehMQvwiBPgz2EznnbpG3jRPvyiZ9Pl9G','A') RETURNING id_usuario INTO v_usuario;
      INSERT INTO USUARIO_ROL(id_usuario,id_rol) VALUES(v_usuario,v_rol);
    END IF;
  END LOOP;
END;
/
COMMIT;
