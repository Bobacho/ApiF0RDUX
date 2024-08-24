package services.f0rdux.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import services.f0rdux.models.Producto;
import services.f0rdux.models.ProductoCaracteristicas;
import services.f0rdux.repositories.ProductoCaracteristicasRepository;
import services.f0rdux.repositories.ProductoRepository;

@Service
@RequiredArgsConstructor
public class ProductoService {
	private final ProductoRepository repository;
	private final ProductoCaracteristicasRepository caracteristicasRepository;

	public Boolean existProducto(UUID producto) {
		Optional<Producto> productoOpt = repository.findById(producto);
		return productoOpt.isPresent() && productoOpt.get().getActivo();
	}

	public Producto findById(UUID id) {
		return repository.findById(id).orElse(null);
	}

	public ResponseEntity<String> createProducto(Producto request) {
		request.setActivo(true);

		repository.save(request);
		return ResponseEntity.ok("Producto creado exitosamente");
	}

	public ResponseEntity<Long> countProductoByCaracteristicas(UUID id) {
		Optional<ProductoCaracteristicas> caracteristicasOpt = caracteristicasRepository.findById(id);
		if (!caracteristicasOpt.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		ProductoCaracteristicas caracteristicas = caracteristicasOpt.get();
		return ResponseEntity.ok(repository
				.count(Example.of(Producto.builder().activo(true).caracteristicas(caracteristicas).build())));
	}

	public ResponseEntity<List<Producto>> findAll() {
		List<Producto> productos = repository.findAll(
				Example.of(Producto.builder().activo(true).build()));
		System.out.println(productos);
		return ResponseEntity.ok(productos);
	}

	public ResponseEntity<String> deleteProductoById(UUID id) {
		Optional<Producto> productoOpt = repository.findById(id);
		if (!productoOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Producto producto = productoOpt.get();
		producto.setActivo(false);
		repository.save(producto);
		return ResponseEntity.ok("Producto eliminado exitosamente");
	}
}
