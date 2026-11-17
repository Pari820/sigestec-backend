package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    java.util.Optional<Cargo> findByNombreIgnoreCase(String nombre);
}
