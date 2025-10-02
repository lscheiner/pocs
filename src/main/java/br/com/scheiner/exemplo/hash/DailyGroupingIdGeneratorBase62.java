package br.com.scheiner.exemplo.hash;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class DailyGroupingIdGeneratorBase62 {

	private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final String ALFABETO_BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final BigInteger BASE62 = BigInteger.valueOf(62);
	private static final int TAMANHO_ID = 8;

	public static String gerarId(final UUID id, final LocalDate data) {
		
		if (id == null || data == null) {
			throw new IllegalArgumentException("ID e data não podem ser nulos.");
		}

		final var entrada = id.toString() + "|" + data.format(FORMATADOR_DATA);

		final var hash = gerarSha256(entrada);

		final var bytesReduzidos = new byte[6];
		System.arraycopy(hash, 0, bytesReduzidos, 0, 6);

		return converterParaBase62(bytesReduzidos);
	}

	private static byte[] gerarSha256(String valor) {
		try {
			return MessageDigest.getInstance("SHA-256").digest(valor.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Algoritmo SHA-256 não está disponível.", e);
		}
	}

	private static String converterParaBase62(byte[] bytes) {
		
		var numero = new BigInteger(1, bytes);
		var resultado = new StringBuilder();

		while (numero.compareTo(BigInteger.ZERO) > 0) {
			BigInteger[] divisao = numero.divideAndRemainder(BASE62);
			resultado.append(ALFABETO_BASE62.charAt(divisao[1].intValue()));
			numero = divisao[0];
		}

		while (resultado.length() < TAMANHO_ID) {
			resultado.append('0');
		}

		return resultado.reverse().toString().substring(0, TAMANHO_ID);
	}

	public static void gerar() {

		int numPessoas = 100;

		List<String> colisao = new ArrayList<>();

		List<String> pessoas = new ArrayList<>();

		for (int i = 0; i < numPessoas; i++) {
			pessoas.add(UUID.randomUUID().toString());
		}

		int numDias = 365 * 20;
		Set<String> hashesGerados = new HashSet<>();
		int colisoes = 0;

		LocalDate inicio = LocalDate.of(2025, 1, 1);

		for (int d = 0; d < numDias; d++) {
			LocalDate data = inicio.plusDays(d);
			String dataStr = data.toString().replace("-", ""); // yyyyMMdd

			for (String pessoa : pessoas) {
				String hash = gerarId(UUID.fromString(pessoa), data);

				if (!hashesGerados.add(hash)) {
					colisao.add(hash + "|" + pessoa+"|"+dataStr );
					colisoes++;
				}
			}
		}

//        System.out.println("Total hashes gerados: " + (numPessoas * numDias));
//        System.out.println("Hashes únicos: " + hashesGerados.size());

		if (colisoes > 0)
			System.out.println("Colisões: " + colisoes + " lista " + colisao);
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
