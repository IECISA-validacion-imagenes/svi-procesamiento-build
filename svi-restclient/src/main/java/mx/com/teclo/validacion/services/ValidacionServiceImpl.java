package mx.com.teclo.validacion.services;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.com.teclo.base.appconfig.AppConfiguracion;
import mx.com.teclo.base.context.BeanLocator;
import mx.com.teclo.validacion.vo.LotesVO;
import mx.com.teclo.validacion.vo.PuntoTacticoVO;

@Service("validacionService")
public class ValidacionServiceImpl implements ValidacionService{

	AppConfiguracion conf = AppConfiguracion.getInstance();
	
	@Override
	public boolean enviarDatosARest(LotesVO listaPtVO){
		String rest_url = conf.getBaseurl()+"/validacion/informacionImagen";
			
		RestTemplate restTemplate = (RestTemplate) BeanLocator.getService("restTemplate");	
			
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", conf.getToken());
			
		HttpEntity<?> entity = new HttpEntity<>(listaPtVO, headers);
			
		ResponseEntity<Boolean> response = new RestTemplate().postForEntity(rest_url, entity, Boolean.class);
			
		Boolean answer = (Boolean) response.getBody();
		System.out.println(answer);
		return answer;
	}
}
