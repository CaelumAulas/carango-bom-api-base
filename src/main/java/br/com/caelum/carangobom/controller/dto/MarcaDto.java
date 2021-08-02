package br.com.caelum.carangobom.controller.dto;


import br.com.caelum.carangobom.modelo.Marca;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
public class MarcaDto {
	
	private Long id;
	private String nome;

	public MarcaDto(Marca marca) {
		this.id = marca.getId();
		this.nome = marca.getNome();
	}

	public static Page<MarcaDto> toList(Page<Marca> marcas) {
		return marcas.map(MarcaDto::new);
	}

}
