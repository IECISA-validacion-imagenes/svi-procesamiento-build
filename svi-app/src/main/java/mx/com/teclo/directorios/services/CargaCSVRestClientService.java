package mx.com.teclo.directorios.services;

import java.util.List;

import mx.com.teclo.base.cargaArchivosVO.DetalleLoteVO;
import mx.com.teclo.base.cargaArchivosVO.InformacionEnviarVO;
import mx.com.teclo.base.cargaArchivosVO.LotesyDetalleVO;
import mx.com.teclo.base.context.BeanLocator;
import mx.com.teclo.cargadatos.restclient.cargarInformacionRestClientImp;

public class CargaCSVRestClientService {
	private cargarInformacionRestClientImp cargarInfRestClient;
	
	public List<DetalleLoteVO> obtenerDetallePT(String tipo){
		cargarInfRestClient = (cargarInformacionRestClientImp) BeanLocator.getService("cargarInformacionRestClientConexion");
		List<DetalleLoteVO> lotes = cargarInfRestClient.obtenerInformacionPT(tipo);
		return lotes;
		
	}

	public void cargarInformacionPT (LotesyDetalleVO  informacionPT){
		cargarInfRestClient = (cargarInformacionRestClientImp) BeanLocator.getService("cargarInformacionRestClientConexion");
	    cargarInfRestClient.guardarContenidoPTCSV(informacionPT);
		//return respuesta;
	}
}
