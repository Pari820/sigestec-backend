package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.InformeTecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformeTecnicoRepository extends JpaRepository<InformeTecnico, Long> {
    boolean existsByOrdenTrabajoId(Long id);
    java.util.Optional<pe.edu.sigestec.entity.InformeTecnico> findByOrdenTrabajoId(Long id);
}
