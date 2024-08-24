package services.f0rdux.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import services.f0rdux.models.Usuario;
import services.f0rdux.services.UsuarioService;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UsuarioController {
	private final UsuarioService service;
	
	
	@GetMapping(path = "/auth/login")
	public ResponseEntity<String> loginUsuario(@RequestParam String correo, @RequestParam String contraseña) {
		return service.login(correo, contraseña);
	}

	@PostMapping(path = "/auth/sign-in")
	public ResponseEntity<String> createUsuario(@RequestBody Usuario usuario) {
		return service.createUsuario(usuario);
	}

	@GetMapping(path = "/usuario")
	public ResponseEntity<Usuario> findUsuarioById(@RequestParam UUID id) {
		return service.findUsuarioById(id);
	}
}
