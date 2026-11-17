# SIGESTEC — Backend Iteración 1 (CU01–CU06)

Actualización del backend original para coincidir con el modelo UML y la BD ADS de ACOIELSAC. Sigue el patrón de LP: **Controller → Service → ServiceImpl → Repository → Entity**, con DTO, validaciones y JWT.

## Alcance implementado

Login, CU01 Registrar solicitud, CU02 Registrar cotización, CU03 Registrar orden de compra, CU04 Asignar personal, CU05 Programar orden de trabajo y CU06 Elaborar análisis de trabajo seguro. **NO se implementan CU07–CU12, aprobaciones de ATS, ejecución, informes ni movimientos de almacén**. Las tablas de almacén del esquema ADS permanecen intactas; solo se referencia `HERRAMIENTA_EQUIPO` desde CU06.

## Desarrollo local H2

Requisitos: Java 21, Maven 3.9+.

```bash
mvn clean test
mvn spring-boot:run
```

Por defecto H2 utiliza `./sigestec-demo-v2` para evitar alterar la BD H2 anterior. La primera ejecución de demostración crea usuarios `gerente`, `supervisor`, `ssoma`, `admin` con clave `Cambiar2026!`. **Es una clave de demostración local; no debe usarse en despliegues reales**. Puede cambiarse con `SIGESTEC_DEMO_PASSWORD`. El archivo de la BD no debe subirse al repositorio.

## Conexión a Oracle SIGESTEC_ADS

1. Si **ya creaste las 45 tablas del esquema ADS**, **NO EJECUTES** `01_TABLAS_ITERACION1_SOLO_ESQUEMA_VACIO.sql`: este archivo es una alternativa para una BD nueva de Iteración 1. Usa `scripts/oracle/02_VALIDAR_TABLAS_ADS.sql` para revisar la estructura existente.
2. Si tienes un usuario Oracle completamente **vacío** para CU01–CU06, puedes utilizar `01_TABLAS_ITERACION1_SOLO_ESQUEMA_VACIO.sql` (26 tablas); el modelo completo ADS de 45 tablas sigue siendo la referencia final.
3. Ejecuta opcionalmente `03_USUARIOS_DEMO_OPCIONAL.sql` **solo en una base de prácticas**, para agregar credenciales demo si no hay usuarios.
4. Define variables de entorno `ORACLE_URL`, `ORACLE_USER`, `ORACLE_PASSWORD`, `JWT_SECRET_BASE64`, `SIGESTEC_STORAGE_DIR` y `SIGESTEC_PUBLIC_BASE_URL`.
5. Ejecuta `mvn spring-boot:run -Dspring-boot.run.profiles=oracle`.

Con el perfil `oracle`, JPA utiliza `ddl-auto=validate` y **no altera las tablas**. Si la validación detecta diferencias entre el script ADS local y el mapeo JPA, hay que conciliar nombres y tipos antes de comenzar; no utilizar `update` para forzar cambios en Oracle.

## Archivos adjuntos

Los PDF y croquis se guardan en un directorio local externo a la BD (configurable con `SIGESTEC_STORAGE_DIR`). Oracle guarda solamente `url_archivo`, `url_archivo_adjunto`, etc. La URL apunta a `/api/v1/archivos/{uuid}`; este endpoint requiere JWT. La descarga de OC/PT/IPER/croquis conserva las rutas del frontend. En producción deberá configurarse almacenamiento persistente y respaldo (o sustituir el adaptador por S3/Drive con control de acceso).

## Decisiones de correspondencia UML

- `PERSONA_NATURAL` y `PERSONA_JURIDICA` pertenecen a `PERFIL`.
- `EMPLEADO` → `PERSONA_NATURAL`; el usuario autenticado determina `id_empleado_registra` de forma segura.
- `COTIZACION` → `DETALLE_COTIZACION`: 1 a N; una solicitud admite varias cotizaciones, pero solo una aceptada.
- `ORDEN_COMPRA` → `DETALLE_ORDEN_COMPRA`: 1 a N, sus detalles pueden editarse según la documentación del cliente.
- La OT se crea **PENDIENTE** en CU04 y pasa a **ASIGNADA** al guardar `ASIGNACION_PERSONAL`; en CU05 pasa a **PROGRAMADA**.
- `DOCUMENTO_PLANIFICACION` tiene un registro por PT, IPER y croquis; no hay `BLOB`.
- `ANALISIS_TRABAJO_SEGURO` incorpora `DETALLE_ANALISIS_RIESGO`, `ANALISIS_EQUIPO_PROTECCION_PERSONAL` y `ANALISIS_HERRAMIENTA_EQUIPO`.
- El estado del análisis queda `PENDIENTE` de aprobación. No se implementa el CU07.

## Pruebas

`src/test/java/pe/edu/sigestec/Iteracion1Test.java` incluye pruebas H2 de cotización con detalle y flujo CU01–CU06 hasta ATS estructurado. No se ejecutaron con Maven en el entorno de generación por falta de Maven y dependencias Spring instaladas. Se verificó la sintaxis Java, un análisis de tipos del núcleo con stubs y la sintaxis TypeScript del frontend.

Consulta `../MATRIZ_CORRESPONDENCIA_UML_CODIGO.md` en el paquete combinado para los nombres y endpoints.


## Ampliación CU07–CU12

Consultar `README_ITERACION2_CU07_CU12.md` para instrucciones, reglas de negocio, scripts Oracle y pruebas.
