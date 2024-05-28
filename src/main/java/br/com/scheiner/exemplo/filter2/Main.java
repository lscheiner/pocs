package br.com.scheiner.exemplo.filter2;


import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;

public class Main {
    public static void main(String[] args) {
        // Criar um exemplo de HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.add("Header1", "Value1");
        headers.add("Header2", "Value2");
        headers.add("Header2", "Value3");
        headers.add("Header3", "Value4");

        // Lista de exclusão de cabeçalhos
        List<String> exclusionList = Arrays.asList("Header2", "Header3");

        // Usar stream para iterar sobre todos os cabeçalhos, excluindo os da lista de exclusão
        headers.entrySet().stream()
            .filter(entry -> !exclusionList.contains(entry.getKey()))
            .forEach(entry -> {
                String headerName = entry.getKey();
                List<String> headerValues = entry.getValue();
                System.out.println("Header: " + headerName);
                headerValues.forEach(value -> System.out.println("Value: " + value));
            });
    }
}