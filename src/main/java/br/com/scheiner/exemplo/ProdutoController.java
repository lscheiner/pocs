package br.com.scheiner.exemplo;


import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.scheiner.exemplo.feign.Client;
import feign.RetryableException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private List<Produto> produtos = new ArrayList<>();
    
    @Autowired
    private Client client;

    @GetMapping
    public List<Produto> getAllProdutos() {
        return produtos;
    }
    
    @GetMapping("openfeign")
    public  Object getAllProdutoOpenfeign() {
    	 var p = new Produto();
    	
    	FunctionUtils.handleProcess(
    			this::createProduto,
    			null,
                c -> {
                	
                	if (Objects.isNull(c)) {
                		System.out.println("é null");
                	}
                	
                	createProduto(new Produto());
                	}
            );
    	
    	
    	try {
    		
        	return client.getJsonAsMap();

    	}
    	catch (RetryableException e) {

    		if (e.getCause() instanceof SocketTimeoutException) {
        		// invocar um metodo

    		}
    	
    	}
    	
    	return null;
    	
    }
    
    @GetMapping("erro")
    public List<Produto> getAllProdutosErro() {
        
    	if (produtos.isEmpty()) {
    		throw new RuntimeException("vazio");
    	}
    	
    	return produtos;
    }
    
    @GetMapping("map")
    public Map<Long,Produto> getAllProdutosMap() {
    	return produtos.stream().collect(Collectors.toMap(Produto::getId, Function.identity()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        return produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Produto createProduto(@RequestBody Produto produto) {
        produto.setId((long) (produtos.size() + 1));
        produtos.add(produto);
        return produto;
    }
    
    @PostMapping("list")
    public ResponseEntity<List<Produto>> createProdutoList(@RequestBody List<Produto> produto) {
    	
    	for(Produto produto1 : produto) {
            produto1.setId((long) (produtos.size() + 1));
            produtos.add(produto1);
    	}
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("1", "uno");
    	
        return new ResponseEntity<>(produtos ,headers, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
        return produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst()
                .map(produto -> {
                    produto.setNome(produtoDetails.getNome());
                    produto.setPreco(produtoDetails.getPreco());
                    return ResponseEntity.ok(produto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduto(@PathVariable Long id) {
        return produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst()
                .map(produto -> {
                    produtos.remove(produto);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/body/{id}")
    public ResponseEntity<Object> deleteProduto2(@PathVariable Long id , @RequestBody Produto produtoDetails) {
        return produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst()
                .map(produto -> {
                    produtos.remove(produto);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Produto> partiallyUpdateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
        return produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst()
                .map(produto -> {
                    if (produtoDetails.getNome() != null) {
                        produto.setNome(produtoDetails.getNome());
                    }
                    if (produtoDetails.getPreco() != 0) {
                        produto.setPreco(produtoDetails.getPreco());
                    }
                    return ResponseEntity.ok(produto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/teste/{id}")
    public ResponseEntity<Produto> partiallyUpdateProduto(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
    
    
    @GetMapping("timeout")
    public ResponseEntity<String> getTimeout(HttpServletRequest request) throws InterruptedException {
    	
    	Thread.sleep(15000L);
    	
    	System.out.println("terminou");
    	
        return ResponseEntity.ok("ok");
    }
}
