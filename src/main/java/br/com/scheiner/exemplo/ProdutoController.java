package br.com.scheiner.exemplo;


import java.util.ArrayList;
import java.util.List;

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

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private List<Produto> produtos = new ArrayList<>();

    @GetMapping
    public List<Produto> getAllProdutos() {
        return produtos;
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
}
