package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.HerramientaEquipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HerramientaEquipoRepository extends JpaRepository<HerramientaEquipo, Long> {
    java.util.Optional<HerramientaEquipo> findByNombreIgnoreCase(String nombre);
    java.util.Optional<HerramientaEquipo> findByCodigoHerramientaIgnoreCase(String codigo);
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select h from HerramientaEquipo h where h.id = :id")
    java.util.Optional<pe.edu.sigestec.entity.HerramientaEquipo> buscarParaActualizar(@org.springframework.data.repository.query.Param("id") Long id);
}
