package mx.com.teclo.restclient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.com.teclo.base.context.BeanLocator;


@Service("restClient")
public class RestClient {
	private String REST_API =  "https://jsonplaceholder.typicode.com/"; //http://172.18.44.153:8080/sspcdmxsai_api_v600r3/
	
	public void consultaTest(){
		RestTemplate restTemplate = (RestTemplate) BeanLocator.getService("restTemplate");
		String rest_url = REST_API + "todos/1";
		System.out.println("RestClient JAR Presente:");
		System.out.println(restTemplate.getForObject(rest_url, String.class));

	}
	
	
}