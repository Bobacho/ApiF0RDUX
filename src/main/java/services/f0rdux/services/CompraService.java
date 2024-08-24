package services.f0rdux.services;

import java.util.UUID;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import services.f0rdux.models.Compra;
import services.f0rdux.repositories.CompraRepository;

@Service
@RequiredArgsConstructor
public class CompraService {
	public final CompraRepository repository;

	public UUID createCompra(Compra request) {
		request.setId(UUID.randomUUID());
		repository.save(request);
		return request.getId();
	}
 
	public ResponseEntity<List<Compra>> findAll(){
		return ResponseEntity.ok(repository.findAll());
	}
}
