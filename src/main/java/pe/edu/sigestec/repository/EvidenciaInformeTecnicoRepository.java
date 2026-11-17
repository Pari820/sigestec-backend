package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.EvidenciaInformeTecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvidenciaInformeTecnicoRepository extends JpaRepository<EvidenciaInformeTecnico, Long> {
    java.util.List<pe.edu.sigestec.entity.EvidenciaInformeTecnico> findByInformeTecnicoId(Long id);
}
