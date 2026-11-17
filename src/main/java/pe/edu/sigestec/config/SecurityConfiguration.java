package pe.edu.sigestec.config;

import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.repository.*;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users(UsuarioRepository repo, UsuarioRolRepository roles) {
        return username -> {
            Usuario u = repo.findByNombreUsuario(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            if (!"A".equals(u.getEstado())) throw new UsernameNotFoundException("Usuario inactivo");
            List<SimpleGrantedAuthority> a = roles.findByUsuarioId(u.getId()).stream()
                    .filter(ur -> "A".equals(ur.getRol().getEstado()))
                    .map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.getRol().getNombre())).toList();
            return User.withUsername(u.getNombreUsuario()).password(u.getClaveHash()).authorities(a).build();
        };
    }

    @Bean
    public AuthenticationManager manager(UserDetailsService users, PasswordEncoder encoder) {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider(users);
        p.setPasswordEncoder(encoder);
        return new ProviderManager(p);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration c = new CorsConfiguration();
        c.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:4200"));
        c.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        c.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        c.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", c);
        return source;
    }

    @Bean
    public SecurityFilterChain security(
            HttpSecurity http, JwtFilter jwt, CorsConfigurationSource corsConfigurationSource
    ) throws Exception {
        return http.csrf(x -> x.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(x -> x.authenticationEntryPoint((req, res, e) -> res.sendError(401, "Autenticación requerida")))
                .authorizeHttpRequests(x -> x.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/v1/auth/login").permitAll().anyRequest().authenticated())
                .addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Component
    public static class JwtFilter extends OncePerRequestFilter {
        private final TokenService tokens;
        private final UserDetailsService users;

        public JwtFilter(TokenService tokens, UserDetailsService users) {
            this.tokens = tokens;
            this.users = users;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
                throws java.io.IOException, ServletException {
            String h = req.getHeader("Authorization");
            if (h != null && h.startsWith("Bearer ")) {
                try {
                    String username = tokens.subject(h.substring(7));
                    UserDetails u = users.loadUserByUsername(username);
                    var a = new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
                    org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(a);
                } catch (JwtException | UsernameNotFoundException e) {
                    org.springframework.security.core.context.SecurityContextHolder.clearContext();
                }
            }
            chain.doFilter(req, res);
        }
    }
}