package services.f0rdux.models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {
	@Id
	private UUID id;

	@OneToOne
	private Usuario usuario;

	@OneToMany
	private List<Producto> productos;

	private Boolean activo;
}
