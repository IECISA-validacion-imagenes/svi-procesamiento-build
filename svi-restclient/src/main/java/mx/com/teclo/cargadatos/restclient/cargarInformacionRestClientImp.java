package mx.com.teclo.cargadatos.restclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import mx.com.teclo.base.appconfig.AppConfiguracion;
import mx.com.teclo.base.cargaArchivosVO.DetalleLoteVO;
import mx.com.teclo.base.cargaArchivosVO.InformacionEnviarVO;
import mx.com.teclo.base.cargaArchivosVO.LotesyDetalleVO;
import mx.com.teclo.base.context.BeanLocator;

@Service("cargarInformacionRestClientConexion")
public class cargarInformacionRestClientImp {
	AppConfiguracion conf = AppConfiguracion.getInstance();

	public List<DetalleLoteVO> obtenerInformacionPT(String tipo){	 
		 String rest_url = conf.getBaseurl() +"/cargaInformacioncsvPT/obtenerInformacion";
		 
		 UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rest_url).queryParam("tipo", tipo);
		 RestTemplate restTemplate = (RestTemplate) BeanLocator.getService("restTemplate");
		 HttpHeaders headers = new HttpHeaders();
		 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 headers.add("Authorization", conf.getToken());
		 headers.add("Content-Type", "application/json");
		 
		 HttpEntity<?> entity = new HttpEntity<>(headers);
		 ResponseEntity<List<DetalleLoteVO>> response = restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity,new ParameterizedTypeReference<List<DetalleLoteVO>>() {});
		 List<DetalleLoteVO> detallePT = response.getBody();
		 
		 return detallePT;	
	}

	
	public Boolean guardarContenidoPTCSV(LotesyDetalleVO informacionPT){
		//List<InformacionEnviarVO> informacionPT = new ArrayList<InformacionEnviarVO>();
		String rest_url = conf.getBaseurl()+"/cargaInformacioncsvPT/guardarInformacionPT";
		RestTemplate restTemplate = (RestTemplate) BeanLocator.getService("restTemplate");	
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", conf.getToken());
		headers.add("Content-Type", "application/json");
		HttpEntity<LotesyDetalleVO> entity = new HttpEntity<>(informacionPT, headers);
		ResponseEntity<Boolean> response = new RestTemplate().postForEntity(rest_url, entity,Boolean.class);

	return response.getBody() ;
	}


}
