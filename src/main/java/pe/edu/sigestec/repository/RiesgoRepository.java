package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.Riesgo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiesgoRepository extends JpaRepository<Riesgo, Long> {
    java.util.Optional<Riesgo> findByNombreIgnoreCase(String nombre);
}
