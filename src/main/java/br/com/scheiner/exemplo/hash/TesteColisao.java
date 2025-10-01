package br.com.scheiner.exemplo.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TesteColisao {
	
	

    public static String gerarHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            String base64 = Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
            return base64.substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash", e);
        }
    }

    public static void gerar() {
    	
        int numPessoas = 100;

    	List<String> pessoas = new ArrayList<>();

        for (int i = 0; i < numPessoas; i++) {
            pessoas.add(UUID.randomUUID().toString());
        }
    	int numDias = 365 * 5;
        Set<String> hashesGerados = new HashSet<>();
        int colisoes = 0;

        LocalDate inicio = LocalDate.of(2025, 1, 1);

        for (int d = 0; d < numDias; d++) {
            LocalDate data = inicio.plusDays(d);
            String dataStr = data.toString().replace("-", ""); // yyyyMMdd

            for (String pessoa : pessoas) {
                String chave = pessoa + "_" + dataStr;
                String hash = gerarHash(chave);

                if (!hashesGerados.add(hash)) {
                    colisoes++;
                }
            }
        }

//        System.out.println("Total hashes gerados: " + (numPessoas * numDias));
//        System.out.println("Hashes únicos: " + hashesGerados.size());
        
        if (colisoes > 0)
            System.out.println("Colisões: " + colisoes);
    }
    
    public static void main(String[] args) {
    	
        int interacoes = 10000;
        int threads = 10;

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < interacoes; i++) {
            executor.submit(() -> gerar());
        }

        executor.shutdown();
    }
}

