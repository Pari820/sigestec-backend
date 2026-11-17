package pe.edu.sigestec.service;
import pe.edu.sigestec.dto.Iteracion2Forms;
import pe.edu.sigestec.entity.*;
public interface MovimientoMaterialAlmacenService {
    Material crear(Iteracion2Forms.MaterialCatalogo datos);
    MovimientoMaterialAlmacen registrar(Iteracion2Forms.MovimientoMaterial datos);
}
