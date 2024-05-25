package br.com.scheiner.exemplo;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@RequestMapping
public class TesteController {

	@GetMapping("/mensagem")
	public ResponseEntity<TesteDto>  getTesteDto() throws JsonProcessingException {
		
		var teste =  new TesteDto();
		
		var json = "{\"id\":0,\"category\":{\"id\":2.3240,\"name\":\"string\"},\"name\":\"doggie\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],\"status\":\"available\"}";
		
		var objectMapper =  new ObjectMapper();
		var convert = objectMapper.readValue(json, new TypeReference <Map<String,Object>>(){});
		teste.setJson(convert);
		
		return ResponseEntity.ok(teste);
	}
	
	@PostMapping("/mensagem")
	public ResponseEntity<String>  teste(@RequestBody Pessoa pessoa )   {
		
		System.out.println(pessoa.getNome());
		
		
		
		
		return ResponseEntity.ok("sucesso");

	}
	

}
