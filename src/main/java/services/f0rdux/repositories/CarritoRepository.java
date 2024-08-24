package services.f0rdux.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import services.f0rdux.models.Carrito;
import java.util.UUID;

public interface CarritoRepository extends JpaRepository<Carrito, UUID> {
}
