package br.com.scheiner.exemplo.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public final class DailyGroupingIdGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final Base64.Encoder URL_SAFE_ENCODER = Base64.getUrlEncoder().withoutPadding();


    public static String generate(final UUID personId, final LocalDate date) {
        if (personId == null || date == null) {
            throw new IllegalArgumentException("Person ID and date must not be null.");
        }

        try {
            final String input = personId.toString() + "|" + date.format(DATE_FORMATTER);

            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            final byte[] truncatedBytes = new byte[6];
            System.arraycopy(hashBytes, 0, truncatedBytes, 0, 6);

            return URL_SAFE_ENCODER.encodeToString(truncatedBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Critical error: SHA-256 algorithm not available.", e);
        }
    }


	public static void gerar() {

		int numPessoas = 100;

		List<String> colisao = new ArrayList<>();

		
		List<String> pessoas = new ArrayList<>();

		for (int i = 0; i < numPessoas; i++) {
			pessoas.add(UUID.randomUUID().toString());
		}

		int numDias = 365 * 50;
		Set<String> hashesGerados = new HashSet<>();
		int colisoes = 0;

		LocalDate inicio = LocalDate.of(2025, 1, 1);

		for (int d = 0; d < numDias; d++) {
			LocalDate data = inicio.plusDays(d);
			String dataStr = data.toString().replace("-", ""); // yyyyMMdd

			for (String pessoa : pessoas) {
				String hash = generate(UUID.fromString(pessoa) , data);

				if (!hashesGerados.add(hash)) {
					colisao.add(hash);
					colisoes++;
				}
			}
		}

//        System.out.println("Total hashes gerados: " + (numPessoas * numDias));
//        System.out.println("Hashes únicos: " + hashesGerados.size());

		if (colisoes > 0)
			System.out.println("Colisões: " + colisoes + " lista "+ colisao);
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
