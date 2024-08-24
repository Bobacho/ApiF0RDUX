package services.f0rdux.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import services.f0rdux.models.ProductoCaracteristicas;
import services.f0rdux.services.ProductoCaracteristicasService;

@RestController
@RequestMapping("/api/caracteristicas")
public class ProductoCaracteristicasController {

	private final ProductoCaracteristicasService service;

	public ProductoCaracteristicasController(ProductoCaracteristicasService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<String> createProductoCaracteristicas(@RequestBody ProductoCaracteristicas request) {
		return service.createProductoCaracteristicas(request);
	}

	@GetMapping("/list-search")
	public ResponseEntity<List<ProductoCaracteristicas>> buscarProductoCaracteristicas(
			@RequestParam String nombre) {
		return service.buscarProductoCaracteristicas(nombre);
	}

	@GetMapping
	public ResponseEntity<ProductoCaracteristicas> buscarProductoCaracteristicasById(@RequestParam UUID id) {
		return service.buscarProductoCaracteristicas(id);
	}

	@GetMapping("/list-categorias")
	public ResponseEntity<List<ProductoCaracteristicas>> getProductosCaracteristicasByCategoria(
			@RequestParam String categoria) {
		return service.getProductoCaracteristicasByCategoria(categoria);
	}

	@GetMapping("/list-ranking")
	public ResponseEntity<List<ProductoCaracteristicas>> getProductosCaracteristicasByBusquedas() {
		return service.getProductoCaracteristicasSortByBusquedas();
	}

}
