-- SIGESTEC - Iteracion 1. Ejecutar DESPUES de 01_esquema.sql en Oracle SQL Developer (F5).
-- Objetos PL/SQL de apoyo. La API Java hace validaciones equivalentes de reglas de negocio.
-- La función devuelve 1 cuando no se cruza con otro servicio (intervalos [inicio, fin)).
CREATE OR REPLACE FUNCTION fn_tecnico_disponible (
 p_tecnico_id IN NUMBER,p_inicio IN TIMESTAMP,p_fin IN TIMESTAMP,p_excluir_ot IN NUMBER DEFAULT NULL
) RETURN NUMBER IS v_cantidad NUMBER;
BEGIN
 SELECT COUNT(*) INTO v_cantidad FROM orden_trabajo
 WHERE tecnico_id=p_tecnico_id AND inicio IS NOT NULL AND fin IS NOT NULL
 AND p_inicio < fin AND p_fin > inicio
 AND (p_excluir_ot IS NULL OR id <> p_excluir_ot);
 IF v_cantidad=0 THEN RETURN 1; ELSE RETURN 0; END IF;
END;
/
-- Procedimiento para validar la precondicion de una nueva programación.
CREATE OR REPLACE PROCEDURE sp_validar_programacion (
 p_tecnico_id IN NUMBER,p_inicio IN TIMESTAMP,p_fin IN TIMESTAMP,p_excluir_ot IN NUMBER DEFAULT NULL
) IS
BEGIN
 IF p_inicio IS NULL OR p_fin IS NULL OR p_inicio >= p_fin THEN
  RAISE_APPLICATION_ERROR(-20041,'Rango de programación inválido');
 END IF;
 IF fn_tecnico_disponible(p_tecnico_id,p_inicio,p_fin,p_excluir_ot)=0 THEN
  RAISE_APPLICATION_ERROR(-20042,'Técnico ocupado en el horario propuesto');
 END IF;
END;
/
-- Regla de protección a nivel de BD: solo cotizaciones aceptadas pueden originar OC.
CREATE OR REPLACE TRIGGER trg_oc_cotizacion_aceptada
 BEFORE INSERT OR UPDATE OF cotizacion_id ON orden_compra FOR EACH ROW
DECLARE v_estado VARCHAR2(255);
BEGIN
 SELECT estado INTO v_estado FROM cotizacion WHERE id=:NEW.cotizacion_id;
 IF v_estado<>'ACEPTADA' THEN RAISE_APPLICATION_ERROR(-20043,'OC requiere cotización ACEPTADA'); END IF;
END;
/
-- En esta iteración un ATS nace exclusivamente en ELABORADO.
CREATE OR REPLACE TRIGGER trg_ats_estado_inicial
 BEFORE INSERT ON ats FOR EACH ROW
BEGIN
 IF :NEW.estado IS NULL THEN :NEW.estado:='ELABORADO'; END IF;
 IF :NEW.estado<>'ELABORADO' THEN RAISE_APPLICATION_ERROR(-20044,'Estado inicial de ATS debe ser ELABORADO'); END IF;
END;
/
-- NOTA: El backend invoca validación de horarios desde GestionService y no ejecuta sp_validar_programacion.
-- Este procedimiento es un objeto demostrativo para el curso, no debe presentarse como integrado.
