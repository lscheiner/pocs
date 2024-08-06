package br.com.scheiner.exemplo.filter3;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.StreamSupport;

public class Main {

	
    public static void main(String[] args) {
        List<String> list = Arrays.asList("apple", "banana", "cherry", "date");

        AtomicBoolean found = new AtomicBoolean(false);

        List<String> result = list.stream()
            .filter(s -> {
                if (s.startsWith("b")) {
                    found.set(true);
                    return true;
                }
                return false;
            })
            .filter(s -> s.length() > 5).toList();

        if (!found.get()) {
            throw new NoSuchElementException();
        }

        System.out.println(result);
        
        Iterator<Integer> iterator = Arrays.asList(1, 2, 3).iterator();
//        List<Integer> actualList = new ArrayList<>();
//        iterator.forEachRemaining(actualList::add);

        Iterable<Integer> iterable = () -> iterator;

        List<String> actualList2 = StreamSupport
        		  .stream(iterable.spliterator(), false).filter(s -> s > 10).map(String::valueOf)
        		  .toList();
        
        System.out.println(actualList2);
        
    }
}