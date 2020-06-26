package mx.com.teclo.base.funciones;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.csvreader.CsvReader;

import mx.com.teclo.base.cargaArchivosVO.DetalleArchivoPTVO;
import mx.com.teclo.base.cargaArchivosVO.EntregaVO;
import mx.com.teclo.base.cargaArchivosVO.LotesyDetalleVO;
import mx.com.teclo.base.cargaArchivosVO.PTArchivocsvVO;
import mx.com.teclo.base.utilerias.ManejoFechas;

public class LeerValidarCSV {

	public long totalRegistros= 0;
	public long totalImagenes = 0;
	public long totalSiluetas = 0;
	public String direccion="";
		
	/*@param nombrePT
	 * @Param */
	
	@SuppressWarnings("unused")
	public List<LotesyDetalleVO> RegistrosyArchivosCSV(String nombrePT,long idPTLote,String rutaArchivo, boolean statusActivo,long usuarioFirmado, EntregaVO idEntrega){
		 ManejoFechas fecha = new ManejoFechas();
		 Date fechaCreacion = fecha.fechaActual();
		 List<DetalleArchivoPTVO> cargaCSV = new ArrayList<DetalleArchivoPTVO>();
		 List<LotesyDetalleVO> registros = new ArrayList<LotesyDetalleVO>();	
		 boolean conError = false ;
		 
		 /* Validacion */
		 String placaD="";
		 String placaT="";
		 String entidadD="";
		 String entidadTrasera ="";
		 String ulrPlacaDelantera="", urlPlacaTrasera="",urlPlacaConsuctor="", urlImagenAmbiental="",urlImgPerfil="";
		 long carril,nodoVPN;
		 int cont=0;
		 
		 List<String> carriles = new ArrayList<String>();
		 
		 Long totalImagenes = totalimagenes(rutaArchivo);
		 
		 rutaArchivo = rutaArchivo+"\\CSV_FINAL.csv";
		 List<Long> carrilesFile = new ArrayList<Long>(); 
	
		 String rutaImagens = idEntrega.getNbEntrega()+ "/"+ nombrePT + "/imagenes";
         HashMap <Long, List<DetalleArchivoPTVO>> map = new HashMap <Long, List<DetalleArchivoPTVO>> ();
         
			 File archivo = new File(rutaArchivo);
			 if (archivo.exists()) {
				 try {  
	            CsvReader csv = new CsvReader(rutaArchivo);
	            csv.readHeaders();
	            while (csv.readRecord()) {
	            	if(csv.get(0)!="" || !csv.get(0).isEmpty()){	            		
	            		placaD = csv.get(4); //csv.get(4).equals("null") || csv.get(4).equals("") ? SIN_PLACA : csv.get(4);
		            	entidadD = csv.get(5); //csv.get(5).equals("null") || csv.get(5).equals("") ? SIN_ENTIDAD : csv.get(5);
		            	placaT = csv.get(6);//csv.get(6).equals("null") || csv.get(6).equals("") ? SIN_PLACA : csv.get(6);
		            	entidadTrasera = csv.get(7); //csv.get(7).equals("null") || csv.get(7).equals("") ? SIN_ENTIDAD : csv.get(7);
		            	carril = Long.parseLong(csv.get(3));
		            	nodoVPN = Long.parseLong(csv.get(0));
		            	ulrPlacaDelantera = csv.get(8) ;
		            	urlPlacaTrasera =  csv.get(9);
		            	urlPlacaConsuctor =csv.get(10);
		            	urlImagenAmbiental =  csv.get(11);
		            	urlImgPerfil = csv.get(13);
		            	
		            	if(map.containsKey(carril)){
		            	    System.out.println("Existe la clave ");
		            	    List<DetalleArchivoPTVO> contenidoCSV  = new ArrayList<DetalleArchivoPTVO>();
		            	    contenidoCSV = map.get(carril);
		            	    contenidoCSV.add(new DetalleArchivoPTVO(nodoVPN,csv.get(1),csv.get(2),carril,placaD,entidadD,placaT,entidadTrasera,
		            				ulrPlacaDelantera,urlPlacaTrasera,urlPlacaConsuctor,urlImagenAmbiental,csv.get(12),urlImgPerfil,
		            				statusActivo,fechaCreacion,usuarioFirmado));
		            		map.put(carril,contenidoCSV);
		            	}else{
		            		List<DetalleArchivoPTVO> contenidoCSV  = new ArrayList<DetalleArchivoPTVO>();
		            		contenidoCSV.add(new DetalleArchivoPTVO(nodoVPN,csv.get(1),csv.get(2),carril,placaD,entidadD,placaT,entidadTrasera,
		            				ulrPlacaDelantera,urlPlacaTrasera,urlPlacaConsuctor,urlImagenAmbiental,csv.get(12),urlImgPerfil,
		            				statusActivo,fechaCreacion,usuarioFirmado));
		            		map.put(carril,contenidoCSV);
		            	}
	            	}else{
	            		conError = true;            		
	            		break;
	            	}  	
	            }   
	            csv.close();           
	        } catch (FileNotFoundException e1) {
	            e1.printStackTrace();
	        } catch (IOException exc) {
	            exc.printStackTrace();
	        }
	    }
			 
		if(conError){
			return null;
		}else{
			for (Entry<Long, List<DetalleArchivoPTVO>> entry : map.entrySet()) {
					System.out.println("carril =" + entry.getKey() + ", valor=" + entry.getValue());
					PTArchivocsvVO Tabla2 = new PTArchivocsvVO();
					
					String nombreArchivo = "CSV_FINALC" + entry.getKey()+".csv";
					Long totalRegistros = (long) entry.getValue().size();
					
					Tabla2.setIdPtLote(idPTLote);
					Tabla2.setIdEntrega(idEntrega);
					Tabla2.setNbArchivoCsv(nombreArchivo);
					Tabla2.setNuRegistrosCsv(totalRegistros);
					Tabla2.setNbCarpetaImg("imagenes");
					Tabla2.setTxCarpetaImg(rutaImagens);
					Tabla2.setNuImagenes(totalImagenes);
					Tabla2.setNbCarpetaSil("");
					Tabla2.setTxCarpetaSil("");
					Tabla2.setNuSiluetas(0l);
					Tabla2.setStValidacion(false);
					Tabla2.setStActivo(false);
					Tabla2.setFhCreacion(fechaCreacion);
					Tabla2.setIdUsrCreacion(usuarioFirmado);
					Tabla2.setFhModificacion(new Date());
					Tabla2.setIdUsrModifica(usuarioFirmado);	
					Tabla2.setTotalRegistroxLote((long)cont);
					
					registros.add(new LotesyDetalleVO(entry.getValue() , Tabla2));				
				}
		}
			return registros;
		}
		
	
	public Long totalimagenes(String ruta){
		long valor =  0;
		String rutaCarpeta = ruta + "\\imagenes"; 
		File archivo = new File(rutaCarpeta);
		if(archivo.exists()){
			File carpeta = new File(rutaCarpeta);
			File[]  carpetaImagenes = carpeta.listFiles();
			valor +=carpetaImagenes.length;
		}
		return valor;
	}
	public PTArchivocsvVO obtenerTotaleImgSiluetas(PTArchivocsvVO elemento,String rutaFolders,String rutaArchivoSCV){
		File carpeta = null;
		File[] carpetaImagenes;
		String rutaSiluetas = rutaFolders;
		
		File archivocsv = new File(rutaArchivoSCV);
		
	if (archivocsv.exists()) {
		if(elemento.getNbCarpetaImg()!=null){
			//direccion =  elemento.getTxCarpetaImg().replace("/", "\\");
			rutaFolders = rutaFolders +"\\"+ elemento.getNbCarpetaImg();
			System.out.println("ruta folder imagenes" + rutaFolders);
			File archivo = new File(rutaFolders);
			if(archivo.exists()){
				carpeta = new File(rutaFolders);
				carpetaImagenes = carpeta.listFiles();
				totalImagenes+=carpetaImagenes.length + elemento.getNuImagenes();
			}
		}
		System.out.print("valor de carpeta sil "+ elemento.getNbCarpetaSil());
	
		if(elemento.getNbCarpetaSil()!=null){
			rutaSiluetas = rutaSiluetas +"\\"+ elemento.getNbCarpetaSil();
			File archivo = new File(rutaFolders);
			if(archivo.exists()){
				carpeta = new File(rutaSiluetas);
				carpetaImagenes = carpeta.listFiles();
				totalSiluetas+= carpetaImagenes.length + elemento.getNuSiluetas();
			}
		}
	
		elemento.setNuImagenes(totalImagenes);
		//elemento.setNuSiluetas(totalSiluetas);
		 System.out.print(elemento.getNbArchivoCsv()+ " "+ elemento.getNuImagenes() + " " + elemento.getNuSiluetas() );
	}
	 
		return elemento; 
	}
	
	


}
