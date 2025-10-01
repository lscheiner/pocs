package br.com.scheiner.exemplo.hash;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HashComSalt {

	private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String SALT = "550e8400-e29b-41d4-a716-446655440000";
    private static final int HASH_LENGTH = 8;

    public static String gerarHash(String idPessoa, String data) {
        try {
            var entrada = "%s_%s_%s".formatted(idPessoa, data, SALT);
            var digest = MessageDigest.getInstance("SHA-256");
            var hashBytes = digest.digest(entrada.getBytes(StandardCharsets.UTF_8));

            return toBase62(new BigInteger(1, hashBytes));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash", e);
        }
    }

    private static String toBase62(BigInteger value) {
        
    	var sb = new StringBuilder();

        var base = BigInteger.valueOf(62);
        while (value.signum() > 0) {
            sb.append(BASE62.charAt(value.mod(base).intValue()));
            value = value.divide(base);
        }

        while (sb.length() < HASH_LENGTH) sb.append('0');

        return sb.reverse().substring(0, HASH_LENGTH);
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
				String hash = gerarHash(pessoa, dataStr);

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

		int interacoes = 1000;
		int threads = 20;

		ExecutorService executor = Executors.newFixedThreadPool(threads);

		for (int i = 0; i < interacoes; i++) {
			executor.submit(() -> gerar());
		}

		executor.shutdown();
	}
}
