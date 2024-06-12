package br.com.scheiner.exemplo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "exampleClient", url = "http://localhost:8080")
public interface Client {

    @GetMapping("/produtos")
    Object getJsonAsMap();
	
}
