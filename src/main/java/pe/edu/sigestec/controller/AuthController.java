package pe.edu.sigestec.controller;

import pe.edu.sigestec.dto.Forms;
import pe.edu.sigestec.config.TokenService;
import pe.edu.sigestec.entity.Usuario;
import pe.edu.sigestec.repository.*;
import jakarta.validation.Valid;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager manager;
    private final TokenService tokens;
    private final UsuarioRepository users;
    private final UsuarioRolRepository roles;

    public AuthController(AuthenticationManager manager, TokenService tokens, UsuarioRepository users, UsuarioRolRepository roles) {
        this.manager = manager;
        this.tokens = tokens;
        this.users = users;
        this.roles = roles;
    }

    private Map<String, Object> resumen(Usuario u, boolean token) {
        String rol = roles.findByUsuarioId(u.getId()).stream().map(x -> x.getRol().getNombre()).findFirst().orElse("SIN_ROL");
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("rol", rol);
        m.put("nombre", u.getPersonaNatural().getNombres() + " " + u.getPersonaNatural().getApellidos());
        m.put("username", u.getNombreUsuario());
        if (token) {
            m.put("token", tokens.create(u.getNombreUsuario()));
            m.put("expiraEnSegundos", 7200);
        }
        return m;
    }

    @PostMapping("/login")
    public Object login(@Valid @RequestBody Forms.Login x) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(x.username(), x.password()));
        return resumen(users.findByNombreUsuario(x.username()).orElseThrow(), true);
    }

    @GetMapping("/me")
    public Object me(Authentication auth) {
        return resumen(users.findByNombreUsuario(auth.getName()).orElseThrow(), false);
    }
}
