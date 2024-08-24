package services.f0rdux.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import services.f0rdux.models.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, UUID> {
}
