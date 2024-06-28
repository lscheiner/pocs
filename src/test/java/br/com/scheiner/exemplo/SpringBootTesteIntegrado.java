package br.com.scheiner.exemplo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class SpringBootTesteIntegrado {

	protected void testeConcorrencia(int quantidadeThreads, Consumer<String> consumer) throws InterruptedException {
        CountDownLatch inicio = new CountDownLatch(1);
        CountDownLatch fim = new CountDownLatch(quantidadeThreads);

        ExecutorService executorService = Executors.newFixedThreadPool(quantidadeThreads);

        IntStream.range(0, quantidadeThreads).forEach(i -> {
            executorService.submit(() -> {
                try {
                    inicio.await();
                    consumer.accept(String.valueOf(i));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    fim.countDown();
                }
            });
        });

        inicio.countDown();
        fim.await();
        executorService.shutdown();
    }
}