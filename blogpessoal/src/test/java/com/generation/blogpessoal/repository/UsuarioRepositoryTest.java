package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

//obs.: não é uma interface porque não é possível testa-la, somente dentro de uma class. 

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)//Execute nossa class e localize uma porta livre para rodar o teste
@TestInstance(TestInstance.Lifecycle.PER_CLASS) /*configura o ciclo de vida por class, metodo 
BeforeAll, AfterAll ou os dois*/

public class UsuarioRepositoryTest {
	
	@Autowired //Injeção de dados da interface
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll //Será exe antes de rodar todos os testes, 1º mét. 
	void start(){ //iniciar, void pq nao retornará anda. 
        
        usuarioRepository.deleteAll(); //apaga qualquer registro ou dado na tb_usuario. Pq no teste o ambiente precisa ser controlado.

        //criação de 4 objetos da classe usuario e persisti-los no BD:
        	//save->
        	//L -> Long
        	//
		usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278",
                                           "https://i.imgur.com/FETvs2O.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278", 
                                           "https://i.imgur.com/NtyGneo.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278",
                                           "https://i.imgur.com/mB3VM2N.jpg"));

        usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", 
                                           "https://i.imgur.com/JR7kUFU.jpg"));

	}
	//Criação do teste: 
	
	@Test //-->mét. de teste indicado;
	@DisplayName("Retorna 1 usuario") 
	public void deveRetornarUmUsuario() {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
		//assertTrue -> é vdd que o mét executado, encontrou o objeto usuario joao@email.com.br ?! Se sim preenche com os dados do Joao.
	}
	
	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
		
	}
}
