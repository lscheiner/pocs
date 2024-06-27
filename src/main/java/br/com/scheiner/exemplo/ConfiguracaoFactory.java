package br.com.scheiner.exemplo;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracaoFactory {

	private final ObjectProvider<Configuracao> configuracaoProvider;

    public ConfiguracaoFactory(ObjectProvider<Configuracao> configuracaoProvider) {
        this.configuracaoProvider = configuracaoProvider;
    }

    public Configuracao createConfiguracao() {
        return configuracaoProvider.getObject("tes");
    }
	
}
