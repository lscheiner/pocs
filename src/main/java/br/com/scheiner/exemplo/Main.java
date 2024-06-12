package br.com.scheiner.exemplo;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	
	static ObjectMapper objectMapper = new ObjectMapper();

	
	   public static <T> T parseJson(String json, Class<T> type) throws Exception {
          
		   return objectMapper.readValue(json, type);

	    }
	
	
	public static void main(String[] args) {
        
		String json = "{\"live_shows\":[{\"showid\":\"show1\",\"time\":\"02216629\",\"provider\":0,\"sponser\":\"governmental\"},{\"showid\":\"show2\",\"time\":\"00050340\",\"provider\":2,\"sponser\":\"business\"}],\"month\":\"April\"}";
		
		
		String json2 = "[{\"live_shows\":[{\"showid\":\"show1\",\"time\":\"02216629\",\"provider\":0,\"sponser\":\"governmental\"},{\"showid\":\"show2\",\"time\":\"00050340\",\"provider\":2,\"sponser\":\"business\"}],\"month\":\"April\"}]";

		
		try {
            var listOfMaps = objectMapper.readValue(json, new TypeReference<>(){});
            
            var listOfMaps2 = objectMapper.readValue(json2, new TypeReference<>(){});

            
//            for (Map<String, Object> map : listOfMaps) {
//                System.out.println(map);
//            }
            
            System.out.println(listOfMaps);
            System.out.println(listOfMaps2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
