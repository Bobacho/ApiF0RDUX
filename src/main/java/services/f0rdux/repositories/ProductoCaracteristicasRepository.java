package services.f0rdux.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import services.f0rdux.models.ProductoCaracteristicas;
import java.util.UUID;

@Repository
public interface ProductoCaracteristicasRepository extends JpaRepository<ProductoCaracteristicas, UUID> {
}
