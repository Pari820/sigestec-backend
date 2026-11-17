package pe.edu.sigestec.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.sigestec.repository.*;
import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.exception.NegocioException;

@Service
public class UsuarioActualService {
    private final UsuarioRepository usuarios;
    private final EmpleadoRepository empleados;

    public UsuarioActualService(UsuarioRepository usuarios, EmpleadoRepository empleados) {
        this.usuarios = usuarios;
        this.empleados = empleados;
    }

    public Empleado empleado() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) throw NegocioException.invalido("Sesión requerida");
        Usuario u = usuarios.findByNombreUsuario(auth.getName()).orElseThrow(() -> NegocioException.noExiste("Usuario no encontrado"));
        return empleados.findByPersonaNaturalId(u.getPersonaNatural().getId())
                .orElseThrow(() -> NegocioException.invalido("El usuario no tiene un empleado asociado"));
    }
}
