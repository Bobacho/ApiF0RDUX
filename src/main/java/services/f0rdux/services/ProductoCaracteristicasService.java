package services.f0rdux.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import services.f0rdux.models.ProductoCaracteristicas;
import services.f0rdux.repositories.ProductoCaracteristicasRepository;

@Service
@RequiredArgsConstructor
public class ProductoCaracteristicasService {
	private final ProductoCaracteristicasRepository repository;

	public ResponseEntity<String> createProductoCaracteristicas(ProductoCaracteristicas request) {
		request.setId(UUID.randomUUID());
		request.setBusquedas(0);
		repository.save(request);
		return ResponseEntity.ok("Caracteristicas creadas con exito");
	}

	public ResponseEntity<List<ProductoCaracteristicas>> buscarProductoCaracteristicas(String nombre) {
		ProductoCaracteristicas productoCaracteristicas = ProductoCaracteristicas.builder()
				.nombre(nombre)
				.build();

		ExampleMatcher matcher = ExampleMatcher.matching()
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase();

		return ResponseEntity.ok(
				repository.findAll(
						Example.of(productoCaracteristicas, matcher),
						PageRequest.of(0, 15,
								Sort.by(Sort.Direction.DESC, "busquedas")))
						.getContent());
	}

	public ResponseEntity<List<ProductoCaracteristicas>> getProductoCaracteristicasByCategoria(String categoria)
	{
		ProductoCaracteristicas caracteristicas = ProductoCaracteristicas.builder()
			.categoria(categoria)
			.build();
		return ResponseEntity.ok(
			repository.findAll(Example.of(caracteristicas),Sort.by(Sort.Direction.DESC, "nombre"))
		);
	}
	public ResponseEntity<List<ProductoCaracteristicas>> getProductoCaracteristicasSortByBusquedas()
	{
		return ResponseEntity.ok(
			repository.findAll(PageRequest.of(0,6,Sort.by(Sort.Direction.DESC,"busquedas"))).getContent()
		);
	}

	public ResponseEntity<ProductoCaracteristicas> buscarProductoCaracteristicas(UUID id) {

		Optional<ProductoCaracteristicas> caracteristicasOpt = repository.findById(id);

		if (!caracteristicasOpt.isPresent()) {
			return ResponseEntity.badRequest().body(null);
		}
		ProductoCaracteristicas caracteristicas = caracteristicasOpt.get();
		caracteristicas.setBusquedas(caracteristicas.getBusquedas() + 1);
		repository.save(caracteristicas);
		return ResponseEntity.ok(caracteristicas);
	}
}
