package services.f0rdux.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import services.f0rdux.models.Carrito;
import services.f0rdux.models.Producto;
import services.f0rdux.models.Usuario;
import services.f0rdux.repositories.CarritoRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarritoService {
	private final CarritoRepository repository;
	private final ProductoService productoService;

	private final UsuarioService usuarioService;

	public ResponseEntity<UUID> createCarrito(String correo) {
		Carrito request = new Carrito();
		request.setId(UUID.randomUUID());
		Usuario usuario = usuarioService.findUsuarioByCorreo(correo);
		if (usuario == null) {
			return ResponseEntity.noContent().build();
		}
		request.setUsuario(usuario);
		request.setActivo(true);
		request.setProductos(new ArrayList<>());
		repository.save(request);
		return ResponseEntity.ok(request.getId());
	}

	public ResponseEntity<List<Carrito>> findAll() {
		return ResponseEntity.ok(repository.findAll(
				Example.of(Carrito.builder().activo(true).build())));
	}

	public ResponseEntity<Carrito> findById(UUID id) {
		Optional<Carrito> carritoOpt = repository.findById(id);
		if (!carritoOpt.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(carritoOpt.get());
	}

	public ResponseEntity<String> doBuying(UUID id) {
		Optional<Carrito> carritoOpt = repository.findById(id);
		if (!carritoOpt.isPresent()) {
			return ResponseEntity.badRequest().body("Carrito no encontrado");
		}
		Carrito carrito = carritoOpt.get();
		carrito.setActivo(false);
		repository.save(carrito);
		return ResponseEntity.ok("Compra realizada con exito");
	}

	public ResponseEntity<String> insertProducto(UUID id, UUID idProducto) {
		Optional<Carrito> carritoOpt = repository.findById(id);
		if (!productoService.existProducto(idProducto)) {
			return ResponseEntity.badRequest().body("Producto no encontrado");
		}
		if (!carritoOpt.isPresent()) {
			return ResponseEntity.badRequest().body("Carrito no encontrado");
		}
		Producto producto = productoService.findById(idProducto);
		Carrito carrito = carritoOpt.get();
		carrito.getProductos().add(producto);
		repository.save(carrito);
		return ResponseEntity.ok("Producto insertado exitosamente");
	}

	public ResponseEntity<String> deleteCarrito(UUID id) {
		Optional<Carrito> carritoOpt = repository.findById(id);
		if (!carritoOpt.isPresent()) {
			return ResponseEntity.badRequest().body("Carrito no encontrado");
		}
		Carrito carrito = carritoOpt.get();
		carrito.setActivo(false);
		repository.save(carrito);
		return ResponseEntity.ok("Carrito eliminado exitosamente");
	}
}
