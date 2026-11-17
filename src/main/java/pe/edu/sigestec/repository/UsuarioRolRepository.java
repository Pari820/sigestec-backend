package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
    java.util.List<UsuarioRol> findByUsuarioId(Long id);
}
