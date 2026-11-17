package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.PersonaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaJuridicaRepository extends JpaRepository<PersonaJuridica, Long> {
    java.util.Optional<PersonaJuridica> findByRuc(String ruc);
}
