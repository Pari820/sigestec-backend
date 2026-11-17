package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.MaterialUtilizado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialUtilizadoRepository extends JpaRepository<MaterialUtilizado, Long> {
    java.util.List<pe.edu.sigestec.entity.MaterialUtilizado> findByEjecucionServicioId(Long id);
    @org.springframework.data.jpa.repository.Query("select mu from MaterialUtilizado mu where mu.ejecucionServicio.ordenTrabajo.id = :ot and mu.material.id = :material")
    java.util.List<pe.edu.sigestec.entity.MaterialUtilizado> findByOtYMaterial(
        @org.springframework.data.repository.query.Param("ot") Long ot,
        @org.springframework.data.repository.query.Param("material") Long material);
}
