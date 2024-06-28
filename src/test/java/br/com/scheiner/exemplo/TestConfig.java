package br.com.scheiner.exemplo;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TestConfig extends SpringBootTesteIntegrado{

	
	@Autowired
	private ConfiguracaoFactory configuracaoFactory;
	
		private List<Integer> list = Collections.synchronizedList(new ArrayList<>());
	
	 	@Test
	    void teste() throws InterruptedException {

	 		testeConcorrencia(2, s -> {
	        	
	        	var hash = System.identityHashCode(configuracaoFactory.createConfiguracao().getLista());
	            System.out.println(hash +" tempo ["+Calendar.getInstance().getTimeInMillis()+"] thread ["+s+"]");
	            list.add(hash);
	        });

	      assertNotEquals(list.get(0), list.get(1));
	    }
	
}
