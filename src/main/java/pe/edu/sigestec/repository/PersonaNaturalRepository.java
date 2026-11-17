package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.PersonaNatural;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaNaturalRepository extends JpaRepository<PersonaNatural, Long> {
    java.util.Optional<PersonaNatural> findByDni(String dni);
}
