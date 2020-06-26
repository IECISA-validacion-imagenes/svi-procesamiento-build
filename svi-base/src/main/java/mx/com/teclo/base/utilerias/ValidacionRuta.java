package mx.com.teclo.base.utilerias;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.com.teclo.base.cargaArchivosVO.DetalleLoteVO;
import mx.com.teclo.base.cargaArchivosVO.PTArchivocsvVO;
import mx.com.teclo.base.cargaArchivosVO.resultadoMapeoVO;

public class ValidacionRuta {
	public String regExp = "^([0-9]{6})$";
	public String PT  = "^([P][T])([0-9]{3})$";
	
	public String comprobarRuta(String cadenaRuta){			
		int tamano = cadenaRuta.length();
		String ruta="";
		String anio = cadenaRuta.substring(tamano-6 , tamano);
		String punto = cadenaRuta.substring(tamano-5 , tamano);
		
		Pattern regex = Pattern.compile(regExp);
		Matcher entrega = regex.matcher(anio);
		
		Pattern pt = Pattern.compile(PT);
		Matcher puntotactico = pt.matcher(punto);
		
		if(entrega.find()){
			return "año";
		}else if(puntotactico.find()){
			return "pt";
		}else{
			return "año";
		}
		
	}
	
	public String obtenerPT(String cadena){
		Pattern pattern = Pattern.compile("PT([0-9]{3})");
		Matcher matcher = pattern.matcher(cadena);
		String x = null;

		if (matcher.find()) {
		    //Coincidió => obtener el valor del grupo 1
		     x = matcher.group(1);    
		}
		return x;
	}
	
	public List<resultadoMapeoVO> getPeriodosUnico(List<DetalleLoteVO> listaElementos){
		List<resultadoMapeoVO> response = new ArrayList<resultadoMapeoVO>();
		
		
		for(DetalleLoteVO elemento : listaElementos){
			resultadoMapeoVO lista = new resultadoMapeoVO();
			lista.setPuntoTactico(elemento.getNbPtLote());
			lista.setFechaCracion(elemento.getIdEntrega().getFhCreacion());
			lista.setPeriodo(elemento.getIdEntrega().getTxEntrega());
			response.add(lista);
			
		}
		
		return response;
	}
	
//	public List<resultadoMapeoVO> getPeriodosUnico(List<DetalleLoteVO> listaElementos){
//		List<resultadoMapeoVO> response = new ArrayList<resultadoMapeoVO>();
//		resultadoMapeoVO p = new resultadoMapeoVO();
//		
//		//String periodo = listaElementos.get(0).getTxCarpetaImg().substring(0,6);
//		//"Punto Tactico","Archivos SCV", "Carpeta Imagenes "," Carpetas Siluetas","Fecha Creación"
//		String pt = "PT" + obtenerPT(listaElementos.get(0).getNbPtLote());
//		String aux="", ptaux="";
//		
//		/* Agrego el valor de Pt gral */
//		//p.setPeriodo(periodo);
//		p.setPuntoTactico(pt);
//		p.setFechaCracion(listaElementos.get(0).getFhCreacion());
//		response.add(p);
//		
//		for(DetalleLoteVO elemento : listaElementos){
//			resultadoMapeoVO obj = new resultadoMapeoVO();
//			aux = elemento.get//getTxCarpetaImg().substring(0,6);
//			ptaux = "PT" + obtenerPT(elemento.getTxCarpetaImg());
//			
//			if(periodo.equals(aux) && pt.equals(ptaux)){
//				obj.setArchivoSCV(elemento.getNbArchivoCsv());
//				obj.setCarpetaImg(elemento.getNbCarpetaImg());
//				obj.setCarpetaSil(elemento.getNbCarpetaSil());
//				response.add(obj);
//				
//			}else{
//				periodo = elemento.getTxCarpetaImg().substring(0, 6);
//				pt = "PT" + obtenerPT(elemento.getTxCarpetaImg());
//				
//				obj.setPeriodo(periodo);
//				obj.setPuntoTactico(pt);
//				obj.setFechaCracion(elemento.getFhCreacion());
//				response.add(obj);
//				
//				resultadoMapeoVO fil = new resultadoMapeoVO();
//				fil.setArchivoSCV(elemento.getNbArchivoCsv());
//				fil.setCarpetaImg(elemento.getNbCarpetaImg());
//				fil.setCarpetaSil(elemento.getNbCarpetaSil());
//				response.add(fil);
//			}
//			
//			
//		}
//		
//		return response;
//	}
}
