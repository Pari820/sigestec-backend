package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.AnalisisEquipoProteccionPersonal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalisisEquipoProteccionPersonalRepository extends JpaRepository<AnalisisEquipoProteccionPersonal, Long> {
    java.util.List<AnalisisEquipoProteccionPersonal> findByAnalisisTrabajoSeguroId(Long id);
}
