package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    java.util.Optional<pe.edu.sigestec.entity.Material> findByCodigoIgnoreCase(String codigo);
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select m from Material m where m.id = :id")
    java.util.Optional<pe.edu.sigestec.entity.Material> buscarParaActualizar(@org.springframework.data.repository.query.Param("id") Long id);
}
