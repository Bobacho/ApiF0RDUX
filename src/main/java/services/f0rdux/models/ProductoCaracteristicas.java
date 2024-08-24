package services.f0rdux.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCaracteristicas {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String nombre;
	private Float precio;
	private String categoria;
	private Integer busquedas;
	private String imageUrl;
}
