package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.RepresentanteEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepresentanteEmpresaRepository extends JpaRepository<RepresentanteEmpresa, Long> {
    java.util.List<RepresentanteEmpresa> findByPersonaJuridicaId(Long id);
}
