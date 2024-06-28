package br.com.scheiner.exemplo;

import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@ConfigurationProperties(prefix = "configuracao")
public class Configuracao {

	private String nome;
	
	private List<Configuracao> lista;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Configuracao> getLista() {
		return lista;
	}

	public void setLista(List<Configuracao> lista) {
		this.lista = lista;
	}

	@Override
	public String toString() {
		return "Configuracao [nome=" + nome + ", lista=" + lista + "]";
	}
}
