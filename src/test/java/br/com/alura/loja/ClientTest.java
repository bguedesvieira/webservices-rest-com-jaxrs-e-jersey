package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import junit.framework.Assert;

public class ClientTest {
	
	private HttpServer server;
	private Client client;
	private WebTarget target;
	
	@Before
	public void startServidor(){
		server = Servidor.startaServidor();
		ClientConfig config = new ClientConfig();
		config.register(LoggingFilter.class);
		client = ClientBuilder.newClient(config);
		target = client.target("http://localhost:8180");
	}
	
	@After
	public void mataServidor(){
		server.stop();
	}
	
	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		
		Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
	
    @Test
    public void testaQueAConexaoComOServidorFuncionaNoPathDeProjetos() {
        Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
        Assert.assertTrue(projeto.getNome().contains("Minha loja"));
    }
    
    @Test
    public void testaProjetoResourceIdUm(){
    	Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
        Assert.assertEquals("Minha loja", projeto.getNome());
    }
    
    @Test
    public void testaInclusaoNovoCarrinho(){
        
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        
        Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);

        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());
    }
}
