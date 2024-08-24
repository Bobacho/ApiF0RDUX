package services.f0rdux.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import services.f0rdux.models.Compra;
import services.f0rdux.services.CompraService;

@RestController
@RequestMapping("/api/compra")
@RequiredArgsConstructor
public class CompraController {
	private final CompraService service;

	@GetMapping("/list")
	public ResponseEntity<List<Compra>> findAll() {
		return service.findAll();
	}
}
