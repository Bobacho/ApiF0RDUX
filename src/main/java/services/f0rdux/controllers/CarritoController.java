package services.f0rdux.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import services.f0rdux.models.Carrito;
import services.f0rdux.services.CarritoService;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
	private final CarritoService service;

	public CarritoController(CarritoService service) {
		this.service = service;
	}

	@GetMapping("/create")
	public ResponseEntity<UUID> createCarrito(@RequestParam String correo) {
		return service.createCarrito(correo);
	}

	@DeleteMapping("/buy")
	public ResponseEntity<String> deleteCarrito(@RequestParam UUID id) {
		return service.doBuying(id);
	}

	@GetMapping("/list")
	public ResponseEntity<List<Carrito>> findAll() {
		return service.findAll();
	}

	@GetMapping
	public ResponseEntity<Carrito> findById(@RequestParam String id) {
		return service.findById(UUID.fromString(id));
	}

	@GetMapping("/insert-product")
	public ResponseEntity<String> insertProducto(@RequestParam UUID id, @RequestParam UUID producto) {
		return service.insertProducto(id, producto);
	}
}
