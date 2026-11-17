package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoServicioRepository extends JpaRepository<TipoServicio, Long> {
    java.util.Optional<TipoServicio> findByNombreIgnoreCase(String nombre);
}
