package services.f0rdux.repositories;

import java.util.UUID;
import services.f0rdux.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
	Usuario findByCorreo(String correo);

	Boolean existsByCorreo(String correo);

	Boolean existsByCorreoAndContraseña(String correo, String contraseña);
}
