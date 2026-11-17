package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.EquipoProteccionPersonal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipoProteccionPersonalRepository extends JpaRepository<EquipoProteccionPersonal, Long> {
    java.util.Optional<EquipoProteccionPersonal> findByNombreIgnoreCase(String nombre);
}
