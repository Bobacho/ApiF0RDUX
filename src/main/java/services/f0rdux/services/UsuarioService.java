package services.f0rdux.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import services.f0rdux.models.Usuario;
import services.f0rdux.repositories.UsuarioRepository;
import services.f0rdux.security.models.UserDetailsImpl;
import services.f0rdux.security.services.JwtService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {
	private final UsuarioRepository repository;

	@Lazy
	private final NotificationService notificationService;
	@Lazy
	private final JwtService jwtService;

	@Lazy
	private final BCryptPasswordEncoder encoder;

	public ResponseEntity<String> createUsuario(Usuario usuario) {
		if (repository.existsByCorreo(usuario.getCorreo())) {
			return ResponseEntity.badRequest().body("Usuario ya existente");
		}
		usuario.setId(UUID.randomUUID());
		usuario.setContraseña(encoder.encode(usuario.getContraseña()));
		usuario.setAdministrador(false);
		System.out.println(usuario.toString());
		repository.save(usuario);
		return ResponseEntity.ok("Usuario creado");
	}

	public ResponseEntity<String> login(String correo, String contraseña) {
		System.out.println(correo + ": " + contraseña);
		Boolean existUsuario = repository.existsByCorreo(correo);
		if (!existUsuario) {
			return ResponseEntity.noContent().build();
		}
		Usuario usuario = repository.findByCorreo(correo);
		if (!encoder.matches(contraseña, usuario.getContraseña())) {
			return ResponseEntity.noContent().build();
		}
		String token = jwtService.generateToken(UserDetailsImpl.builder().usuario(usuario).build());
		notificationService.sendNotification("emerson.palacios@unmsm.edu.pe", "Usuario registrado",
				"Ya funciona las notificaciones, vamos sheyla lima " + token);
		return ResponseEntity.ok(token);
	}

	public ResponseEntity<String> loginAdmin(String correo, String contraseña) {
		Boolean existUsuario = repository.existsByCorreoAndContraseña(correo, encoder.encode(contraseña));
		if (!existUsuario) {
			return ResponseEntity.noContent().build();
		}
		Usuario usuario = repository.findByCorreo(correo);
		if (!usuario.getAdministrador()) {

			return ResponseEntity.of(Optional.of("Usuario no authorizado"))
					.status(401)
					.build();
		}
		String token = jwtService.generateToken(UserDetailsImpl.builder().usuario(usuario).build());
		return ResponseEntity.ok(token);
	}

	public ResponseEntity<Usuario> findUsuarioById(UUID id) {
		return ResponseEntity.ok(findUsuario(id));
	}

	public Usuario findUsuarioByCorreo(String correo) {
		return repository.findByCorreo(correo);
	}

	public Usuario findUsuario(UUID id) {
		return repository.findById(id).orElse(null);
	}
}
