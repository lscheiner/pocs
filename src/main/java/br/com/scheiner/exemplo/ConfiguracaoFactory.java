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
        return configuracaoProvider.getObject();
    }
	/*
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
	
	@Value("${thread.corePoolSize}")
	private Integer corePoolSize; 
	
	@Value("${thread.maxPoolSize}")
	private Integer maxPoolSize; 
	
	@Value("${thread.queueCapacity}")
	private Integer queueCapacity; 
	
	@Bean(name = "asyncExecutorProcessamento")
	public Executor customThreadPoolTaskExecutor() {
	    
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(corePoolSize);
	    executor.setMaxPoolSize(maxPoolSize);
	    executor.setQueueCapacity(queueCapacity);
	    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
	    executor.setThreadNamePrefix("Async_Thread_");
	    executor.setWaitForTasksToCompleteOnShutdown(true);
	    executor.setAwaitTerminationSeconds(60);
	    return executor;
	    
	}
}
	*/
}
