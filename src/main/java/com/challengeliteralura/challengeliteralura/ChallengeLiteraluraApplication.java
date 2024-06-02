package com.challengeliteralura.challengeliteralura;

import com.challengeliteralura.challengeliteralura.principal.Principal;
import com.challengeliteralura.challengeliteralura.repository.DatosAutorRepository;
import com.challengeliteralura.challengeliteralura.repository.DatosIdiomasRepository;
import com.challengeliteralura.challengeliteralura.repository.DatosLibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ChallengeLiteraluraApplication implements CommandLineRunner {

	@Autowired  // Anotación para que haga inyección de dependencias para el repositorio
	private DatosLibrosRepository librosRepository;
	@Autowired
	private DatosAutorRepository autorRepository;
	@Autowired
	private DatosIdiomasRepository idiomasRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
Principal principal = new Principal(librosRepository, autorRepository, idiomasRepository);
		List<Object[]> resultados = autorRepository.findAllAutoresWithLibros();

	principal.muestraElMenu();
	}
}
