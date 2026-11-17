package pe.edu.sigestec.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Contratos HTTP de los CU existentes; DTO independientes del modelo JPA.
 */
public final class Forms {
    private Forms() {
    }

    public record Login(@NotBlank String username, @NotBlank String password) {
    }

    public record ClienteForm(@NotBlank @Pattern(regexp = "[0-9]{11}") String ruc,
                              @NotBlank String razonSocial, String telefono, String contacto) {
    }

    public record TipoForm(@NotBlank String nombre, String descripcion) {
    }

    public record PersonalForm(@NotBlank String nombre, @NotBlank String perfil) {
    }

    public record SolicitudForm(@NotNull Long clienteId, @NotNull Long tipoServicioId,
                                @NotBlank String descripcion, String observaciones,
                                Long representanteId, String lugarServicio) {
    }

    public record DetalleCotizacionForm(@NotBlank String descripcion,
                                        @NotNull @DecimalMin("0.01") BigDecimal cantidad,
                                        @NotNull @DecimalMin("0.00") BigDecimal precioUnitario,
                                        String unidadMedida) {
    }

    public record CotizacionForm(@NotNull Long solicitudId, String descripcionTrabajo,
                                 String observaciones, @NotEmpty List<@Valid DetalleCotizacionForm> detalles) {
    }

    public record EstadoForm(@NotBlank String estado) {
    }

    public record DetalleOrdenCompraForm(@NotBlank String descripcion,
                                         @NotNull @DecimalMin("0.01") BigDecimal cantidad,
                                         @NotNull @DecimalMin("0.00") BigDecimal precioUnitario,
                                         String unidadMedida) {
    }

    public record AsignacionForm(@NotNull Long solicitudId, @NotNull Long tecnicoId,
                                 @NotNull Long ssomaId, Long apoyoId, List<Long> tecnicosAdicionales) {
    }

    public record CambioEquipoForm(@NotNull Long tecnicoId, @NotNull Long ssomaId,
                                   Long apoyoId, List<Long> tecnicosAdicionales) {
    }

    public record AtsEtapa(@NotBlank String etapa, @NotBlank String peligro,
                           @NotBlank String riesgo, @NotBlank String nivel,
                           @NotBlank String medidaControl) {
    }

    public record AtsForm(@NotNull Long ordenTrabajoId,
                          @NotBlank String condicionesArea,
                          @NotEmpty List<@NotBlank String> peligros,
                          @NotEmpty List<@NotBlank String> epp,
                          String herramientas,
                          @NotEmpty List<@Valid AtsEtapa> etapas) {
    }
}
