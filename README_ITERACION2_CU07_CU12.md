# SIGESTEC — Ampliación de Iteración 1 a CU12

**Base:** los archivos ZIP del frontend y backend CU01–CU06 proporcionados por el equipo.
**Versión:** funcionalidad Login y CU01–CU12 (CU07–CU12 añadidos sobre la estructura existente).

## Alcance implementado

| Código | Caso de uso | Angular | Backend/servicio | Tablas principales |
|---|---|---|---|---|
| CU07 | Registrar aprobación del ATS | `pages/aprobaciones-ats.ts/html` | `AprobacionAtsController`, `AprobacionAtsServiceImpl` | `APROBACION_ATS`; actualiza `ANALISIS_TRABAJO_SEGURO.estado` |
| CU08 | Registrar ejecución del servicio | `pages/ejecuciones.ts/html` | `EjecucionServicioController`, `EjecucionServicioServiceImpl` | `EJECUCION_SERVICIO`, `EVIDENCIA_EJECUCION`, `MATERIAL_UTILIZADO` |
| CU09 | Elaborar informe técnico | `pages/informes.ts/html` | `InformeTecnicoController`, `InformeTecnicoServiceImpl` | `INFORME_TECNICO`, `EVIDENCIA_INFORME_TECNICO` |
| CU10 | Registrar conformidad del servicio | `pages/conformidades.ts/html` | `ConformidadServicioController`, `ConformidadServicioServiceImpl` | `CONFORMIDAD_SERVICIO` |
| CU11 | Gestionar movimientos de materiales | `pages/materiales-almacen.ts/html` | `AlmacenController`, `MovimientoMaterialAlmacenServiceImpl` | `MATERIAL`, `MOVIMIENTO_MATERIAL_ALMACEN` |
| CU12 | Gestionar movimientos de herramientas | `pages/herramientas-almacen.ts/html` | `AlmacenController`, `MovimientoHerramientaEquipoServiceImpl` | `HERRAMIENTA_EQUIPO` (reutilizada), `MOVIMIENTO_HERRAMIENTA_EQUIPO` |

Se conservaron las carpetas `controller`, `service`, `service/impl`, `repository`, `entity` y los componentes Angular y estilos globales. No se modificó el diseño del login ni de las pantallas originales.

## Puesta en marcha en Windows

### Backend (IntelliJ)

1. Respalda el proyecto y los archivos locales existentes antes de sustituirlo.
2. Extrae **SIGESTEC_Backend_CU01_CU12.zip** en una carpeta. Abre **esa carpeta** en IntelliJ: en la raíz debe aparecer `pom.xml`.
3. Configura **JDK 21** (el proyecto usa fuente Java 21; con JDK 27 también puede compilar si Maven está configurado adecuadamente).
4. Ejecuta `SigestecApplication` de `pe.edu.sigestec`. El servidor escucha en `http://localhost:8080`.
5. En desarrollo H2, `ddl-auto=update` crea las tablas CU07–CU12 sin eliminar tablas previas. Para conservar la base H2 original, respalda `sigestec-demo-v2.mv.db` y arranca desde la carpeta donde está ubicada, o configura una ruta absoluta en `spring.datasource.url`.
6. El usuario de demostración `almacenero` (contraseña local configurada con `SIGESTEC_DEMO_PASSWORD`, por defecto `Cambiar2026!`) se crea automáticamente cuando `app.demo.enabled=true`, incluso si ya existen las cuatro cuentas anteriores. **No se crea en Oracle**.

### Frontend (Visual Studio Code)

1. Extrae **SIGESTEC_Frontend_CU01_CU12.zip** y abre la carpeta que contiene `package.json`.
2. En la terminal PowerShell de esa carpeta ejecuta:

   ```powershell
   npm ci
   npm start
   ```

3. Abre `http://localhost:4200/login`. El backend debe estar encendido en `localhost:8080`.
4. En Windows usa Node compatible con Angular CLI y `npm` según `package.json`. No ejecutes los comandos en la carpeta padre que no contiene `package.json`.

## Roles y navegación

- `GERENTE`: CU01–CU03.
- `SUPERVISOR`: CU04, CU05, CU07–CU10.
- `SSOMA`: CU06.
- `ALMACENERO`: CU11–CU12.
- `ADMIN`: acceso de prueba a todos los módulos.

El rol y el empleado deben existir en Oracle antes de acceder; el seeder demo solo se habilita en H2 local.

## Proceso recomendado para probar

1. CU01: gerente crea solicitud.
2. CU02: gerente registra cotización con detalle y la acepta.
3. CU03: gerente registra la OC correspondiente.
4. CU04: supervisor asigna técnico y SSOMA, creando OT.
5. CU05: supervisor programa OT con PT/IPER y aprobación del cliente.
6. CU06: SSOMA elabora ATS con riesgos y EPP.
7. **CU07:** supervisor registra la revisión del ATS; para resultado `APROBADO` adjunta acta PDF/PNG/JPG con evidencia de las firmas del cliente. Los nombres de supervisor y SSOMA del cliente son **datos declarados**, NO firmas digitales verificadas.
8. **CU08:** supervisor registra la ejecución de la OT; el backend comprueba que el ATS esté `APROBADO`. Adjunta al menos una foto y declara materiales usados si corresponde. El estado de la OT pasa a `EJECUTADA`.
9. **CU09:** supervisor crea informe técnico de una OT ejecutada y registra la fecha de envío. PDF y fotos adicionales son opcionales.
10. **CU10:** supervisor registra el acta firmada; solo si el resultado es `CONFORME`, la OT pasa a `ATENDIDA`.
11. **CU11:** almacenero crea un material con stock inicial y registra `SALIDA`, `DEVOLUCION` o `ENTRADA`. Se impide stock negativo y devolver más de lo retirado para cada OT. Al registrar movimientos físicos se actualiza la validación de materiales declarados en CU08 según la salida neta.
12. **CU12:** almacenero registra herramientas, ingresos, préstamos y devoluciones vinculadas a técnico y OT. No se puede devolver más de lo prestado. Si la devolución es `DANADO`, la herramienta no vuelve al stock disponible.

### API REST (base `/api/v1`)

| Método | Ruta | Rol |
|---|---|---|
| GET | `/ats/pendientes` | SUPERVISOR |
| GET | `/ats/aprobaciones` | SUPERVISOR, SSOMA |
| PUT multipart | `/ats/{id}/aprobacion` | SUPERVISOR |
| GET / POST multipart | `/ejecuciones` | SUPERVISOR (consulta también SSOMA) |
| GET / POST multipart | `/informes` | SUPERVISOR |
| GET / POST multipart | `/conformidades` | SUPERVISOR |
| GET / POST JSON | `/almacen/materiales` | ALMACENERO (consulta también SUPERVISOR) |
| GET / POST JSON | `/almacen/movimientos` | ALMACENERO |
| GET / POST JSON | `/almacen/herramientas` | ALMACENERO |
| GET / POST JSON | `/almacen/herramientas/movimientos` | ALMACENERO |

Para solicitudes multipart, `datos` es una parte JSON `application/json`; los demás nombres (`acta`, `antes`, `durante`, `despues`, `pdf`, `fotos`) son partes de archivo. Las rutas están protegidas por JWT Bearer.

## Oracle ADS: ampliación del esquema

1. Respalda el esquema Oracle existente.
2. Si aún no existen las tablas originales, aplica primero el script `scripts/oracle/01_TABLAS_ITERACION1_SOLO_ESQUEMA_VACIO.sql` en una base vacía.
3. Sobre el esquema original, ejecuta **una sola vez** `scripts/oracle/04_EXTENSION_CU07_CU12.sql` (10 tablas nuevas; no elimina datos).
4. Ejecuta `scripts/oracle/05_VALIDAR_CU07_CU12.sql`; devuelve 10 tablas.
5. Configura `ORACLE_URL`, `ORACLE_USER`, `ORACLE_PASSWORD` y el perfil `oracle`. Este perfil utiliza `ddl-auto=validate`, **no migra automáticamente**.
6. Crea el rol aplicativo `ALMACENERO` y la cuenta asociada siguiendo el mismo esquema `ROL`, `USUARIO`, `USUARIO_ROL`, `EMPLEADO` y `CARGO`, utilizando BCrypt; no reutilices contraseñas de demostración en producción.

## Limitaciones y comprobaciones pendientes

- Se verificó la sintaxis/tipos de 43 archivos Java nuevos y modificados mediante compilación aislada con clases originales y sustitutos mínimos de dependencias; **no es una compilación Maven real ni prueba Spring Boot/H2/Oracle**. El compilador TypeScript y el compilador de plantillas Angular completaron sin errores. No se logró ejecutar el build completo de Angular CLI debido a la versión de Node disponible en el entorno de generación.
- Se agregó `src/test/java/pe/edu/sigestec/Iteracion2Test.java` como prueba de integración con H2 para la cadena CU07–CU12; todavía **no fue ejecutada** en este entorno por no disponer de Maven instalado. Se realizaron 11 comprobaciones aisladas de reglas de inventario de CU11–CU12, satisfactorias.
- **No** se ha comprobado la ejecución funcional de los seis CU con la base Oracle del equipo.
- Los diagramas UML incluían un servicio externo de correo. **No se incorporó un proveedor de correo SMTP/API real** porque no hay configuración ni credenciales; el registro de cada operación sí persiste y es consultable. Las aprobaciones externas se registran como acta adjunta y nombres de firmantes, no como firmas digitales verificadas.
- En esta versión un ATS rechazado requiere intervención posterior para elaborar una versión corregida; no se implementó un historial completo de revisiones ni reenvío automático. Una conformidad observada queda registrada, pero no hay aún un flujo de subsanación/reaprobación.
- Los documentos se almacenan en el disco local fuera de la BD y Oracle conserva sus URLs; usa una ruta persistente y respaldada para producción.
- No se modificó CU13 ni los casos posteriores.

**Nota:** conserva los datos H2 y los PDFs existentes de la máquina original; estos ZIP contienen código y configuración, no copias de datos personales o archivos anteriores.
