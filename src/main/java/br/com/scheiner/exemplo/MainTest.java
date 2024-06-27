package br.com.scheiner.exemplo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainTest {

	public static void main(String[] args) {
		var a  = new Animal();
		a.setId(1);
		a.setName("nome");
		
		var a2  = new Animal();
		a2.setId(1);
		a2.setName("nome");
		
		System.out.println(convertListAfterJava8(Arrays.asList(a,a2)));
		
		
	}
	
	 public static Map<Integer, String> convertListAfterJava8(List<Animal> list) {
		    return list.stream()
		      .collect(Collectors.toMap(Animal::getId, Animal::getName , 
		    		  (a1, a2) -> {
		                  System.out.println("duplicate key found!");
		                  return a1;
		              }));
		}
}
