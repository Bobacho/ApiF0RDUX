package services.f0rdux.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import services.f0rdux.models.Producto;
import services.f0rdux.services.ProductoService;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {
	private final ProductoService service;

	public ProductoController(ProductoService service) {
		this.service = service;
	}

	@GetMapping("/count")
	public ResponseEntity<Long> getCountProducto(@RequestParam UUID caracteristicasId) {
		return service.countProductoByCaracteristicas(caracteristicasId);
	}

	@PostMapping
	public ResponseEntity<String> createProducto(@RequestBody Producto producto) {
		return service.createProducto(producto);
	}

	@GetMapping("/health")
	public ResponseEntity<String> healthCheck() {
		return ResponseEntity.ok("Hola Producto");
	}

	@GetMapping("/list")
	public ResponseEntity<List<Producto>> findAll() {
		return service.findAll();
	}

	@DeleteMapping
	public ResponseEntity<String> deleteById(@RequestParam UUID id) {
		return service.deleteProductoById(id);
	}
}
