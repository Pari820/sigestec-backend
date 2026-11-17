package pe.edu.sigestec.service;
import pe.edu.sigestec.dto.Iteracion2Forms;
import pe.edu.sigestec.entity.*;
public interface MovimientoHerramientaEquipoService {
    HerramientaEquipo crear(Iteracion2Forms.HerramientaCatalogo datos);
    MovimientoHerramientaEquipo registrar(Iteracion2Forms.MovimientoHerramienta datos);
}
