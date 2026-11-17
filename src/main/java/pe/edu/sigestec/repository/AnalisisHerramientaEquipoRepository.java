package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.AnalisisHerramientaEquipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalisisHerramientaEquipoRepository extends JpaRepository<AnalisisHerramientaEquipo, Long> {
    java.util.List<AnalisisHerramientaEquipo> findByAnalisisTrabajoSeguroId(Long id);
}
