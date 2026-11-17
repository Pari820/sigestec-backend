package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    java.util.Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
