package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

public class ClientTest {

	private HttpServer server;
	
	@After
	public void mataServidor() {
		server.stop();
	}
	
	@Before
	public void startaServidor() {
		this.server = Servidor.inicializaServidor();
	}

	
	@Test
	public void testaQueAConexaoComOServidorFunciona() {

		
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
		carrinho.setRua("Rua Vergueiro");
		carrinho.setCidade("Sao paulo");
		String xml = carrinho.toXML();
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		
//		String conteudo = target.path("/carrinhos/1").request().get(String.class);		
//		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
//		System.out.println(carrinho.getRua());
		
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		Response response = target.path("/carrinhos").request().post(entity);
		
		Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));

	}

}
