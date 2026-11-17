package pe.edu.sigestec.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/** Contratos de los casos de uso CU07 a CU12. */
public final class Iteracion2Forms {
    private Iteracion2Forms() {}

    public record Aprobacion(@NotBlank String supervisorCliente,
                             @NotBlank String ssomaCliente,
                             @NotNull LocalDate fechaRevision,
                             @NotBlank @Pattern(regexp = "APROBADO|RECHAZADO") String resultado,
                             String observaciones) {}

    public record MaterialReal(@NotNull Long materialId,
                               @NotNull @DecimalMin("0.01") @Digits(integer=10,fraction=2) BigDecimal cantidadReal) {}

    public record Ejecucion(@NotNull Long ordenTrabajoId,
                            @NotBlank String descripcion,
                            String mediciones, String observaciones,
                            List<@Valid MaterialReal> materiales) {}

    public record Informe(@NotNull Long ordenTrabajoId, String numeroInforme,
                          @NotBlank String diagnostico,
                          @NotBlank String trabajosRealizados,
                          @NotBlank String conclusiones,
                          String recomendaciones,
                          @NotNull LocalDate fechaEnvio) {}

    public record Conformidad(@NotNull Long ordenTrabajoId,
                             @NotBlank String numeroActa,
                             @NotNull LocalDate fechaConformidad,
                             @NotBlank String nombreRepresentante,
                             Long representanteEmpresaId,
                             @NotNull @Min(1) @Max(5) Integer valoracion,
                             @NotBlank @Pattern(regexp = "CONFORME|OBSERVADA") String estadoConformidad,
                             String observaciones) {}

    public record MaterialCatalogo(@NotBlank String codigo, @NotBlank String nombre,
                                   @NotBlank String unidadMedida,
                                   @NotNull @DecimalMin("0.00") @Digits(integer=10,fraction=2) BigDecimal stockInicial,
                                   @NotNull @DecimalMin("0.00") @Digits(integer=10,fraction=2) BigDecimal stockMinimo) {}

    public record MovimientoMaterial(@NotNull Long materialId, Long ordenTrabajoId,
                                    @NotBlank @Pattern(regexp = "SALIDA|DEVOLUCION|ENTRADA") String tipoMovimiento,
                                    @NotNull @DecimalMin("0.01") @Digits(integer=10,fraction=2) BigDecimal cantidad,
                                    String motivo, String observacion) {}

    public record HerramientaCatalogo(@NotBlank String codigo, @NotBlank String nombre,
                                     String descripcion,
                                     @NotNull @DecimalMin("0.00") @Digits(integer=10,fraction=2) BigDecimal cantidadInicial) {}

    public record MovimientoHerramienta(@NotNull Long herramientaId, Long ordenTrabajoId,
                                        Long empleadoResponsableId,
                                        @NotBlank @Pattern(regexp = "INGRESO|PRESTAMO|DEVOLUCION") String tipoMovimiento,
                                        @NotNull @DecimalMin("0.01") @Digits(integer=10,fraction=2) BigDecimal cantidad,
                                        String estadoDevolucion, String observacion) {}
}
