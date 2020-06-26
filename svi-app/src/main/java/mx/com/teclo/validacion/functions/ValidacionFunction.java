package mx.com.teclo.validacion.functions;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.geom.Dimension2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableModel;

import mx.com.teclo.base.context.BeanLocator;
import mx.com.teclo.base.frames.CircleProgressPanel;
import mx.com.teclo.validacion.enumerable.EnumErrorValidacion;
import mx.com.teclo.validacion.services.ValidacionService;
import mx.com.teclo.validacion.vo.ArchivoCSVVO;
import mx.com.teclo.validacion.vo.ImagenesVO;
import mx.com.teclo.validacion.vo.LotesVO;
import mx.com.teclo.validacion.vo.NodoError;
import mx.com.teclo.validacion.vo.NodoPT;
import mx.com.teclo.validacion.vo.PuntoTacticoVO;
import mx.com.teclo.validacion.vo.SiluetasVO;

public final class ValidacionFunction {
	
	private static final ValidacionFunction instance = new ValidacionFunction();
	
	private ValidacionService validacionService;
	
	private NodoPT incidencias;
	
	private LotesVO res;
	
	private File root;
	
	private String isOnlyOne;
	
	private String idEntregable;
	
	private ValidacionFunction(){
		validacionService = (ValidacionService) BeanLocator.getService("validacionService");
	}
	
	public static ValidacionFunction getInstance(){
		return instance;
	}
	
	/*@SuppressWarnings("unchecked")
	public LotesVO validarDirectorioPuntoTactico(File folder){
		//folder.getParentFile().getName();
		incidencias = null;
		String regExp = "^([P][T])([1-9]{3})$";
		LotesVO res = new LotesVO();
		List<PuntoTacticoVO> pts = new ArrayList<PuntoTacticoVO>();
		
		Pattern regex = Pattern.compile(regExp);
		
		for (final File archivo : folder.listFiles()) {
	        if (archivo.isDirectory()) {
	        	Matcher match = regex.matcher(archivo.getName());
	        	PuntoTacticoVO ptVO = new PuntoTacticoVO();
	        	NodoPT ultimoNodo = buscarUltimoNodo();
	        	NodoPT node = null;
	        	ptVO.setName(archivo.getName());
	        	if(match.find()){
	        		ptVO.setNameValid(true);
	        		
	        		if(archivo.listFiles().length >0){
			        	//Mapeo de Archivos CSV
		        		Map<String, Object> csv = validarArchivosCSVPuntoTactivo(ptVO.getName(), archivo);
		        		
			        	ptVO.setArchivos((List<ArchivoCSVVO>)csv.get("archivoCSV"));
			        	
			        	if(((List<NodoError>)csv.get("error")).size() > 0){
			        		
			        		node = node != null ? node : new NodoPT(ultimoNodo);
			        		
			        		node.setError(EnumErrorValidacion.FILES_CSV);
			        		node.setName(node.getName() == null ? ptVO.getName() : node.getName());
			        		node.setArchivos(((List<NodoError>)csv.get("error")));
			        		if(ultimoNodo == null) incidencias = node;
			        		pts.add(ptVO);
			        		continue;
			        	}
			        	
			        	//Mapeo de Imagenes
			        	Map<String, Object> img = validarImagenesPuntoTactivo(ptVO, archivo);
			        	
			        	ptVO.setImagenes((List<ImagenesVO>)img.get("archivoIMG"));
			        	
			        	if(((List<NodoError>)img.get("error")).size() > 0){
			        		
			        		node = node != null ? node : new NodoPT(ultimoNodo);
			        		
			        		node.setError(EnumErrorValidacion.IMAGES);
			        		node.setName(node.getName() == null ? ptVO.getName() : node.getName());
			        		node.setImagenes(((List<NodoError>)img.get("error")));
			        		if(ultimoNodo == null) incidencias = node;
			        		pts.add(ptVO);
			        		continue;
			        	}
			        	
			        	
			        	//Mapeo de Siluetas (Opcional)
			        	ptVO.setSiluetas(validarSiluetasPuntoTactivo(ptVO, archivo));
			        	
	        		}else{
	        			//La carpeta esta vacia
	        			ptVO.setNameValid(false);
		        		node = node != null ? node : new NodoPT(ultimoNodo);
		        		node.setError(EnumErrorValidacion.EMPTY_FOLDER);
		        		node.setName(node.getName() == null ? ptVO.getName() : node.getName());
		        		if(ultimoNodo == null) incidencias = node;
		        		pts.add(ptVO);
		        		continue;
	        		}
	        	}else{
	        		ptVO.setNameValid(false);
	        		node = node != null ? node : new NodoPT(ultimoNodo);
	        		node.setError(EnumErrorValidacion.NAME);
	        		node.setName(node.getName() == null ? ptVO.getName() : node.getName());
	        		if(ultimoNodo == null) incidencias = node;
	        	}
	        	pts.add(ptVO);
	        }
	    }
		if(validarPuntosTacticos(pts)){
			res.setIdUsuario(0L);
			res.setPuntosTacticos(pts);
			res.setNbEntregable(idEntregable);
			return res;	
		}else{
			return new LotesVO();
		}
	}*/
	
	public void validarDirectorioPuntoTactico(final File folder, final JScrollPane scrollPane, final JTable tabla, final JPanel btnConfirma, final JPanel btnIncidencia, final JPanel pnlIncidencias) {
        final CircleProgressPanel loadi = new CircleProgressPanel();
        new Thread(new Runnable(){
        	@SuppressWarnings("unchecked")
			@Override
        	public void run() {
        		//configuramos la barra de progreso...
        		pnlIncidencias.removeAll();
        		pnlIncidencias.add(loadi, BorderLayout.CENTER);
        		//loadi.setVisible(true);
        		
        		
        		incidencias = null;
        		idEntregable = null;
        		root = null;
        		isOnlyOne = null;
        		String regExp = "^([P][T])([0-9]{3})$";
        		//String regExpUbicacion = "^([2][0][0-9][0-9])(([0][1-9]|[1][0-2]))$";
        		res = new LotesVO();
        		List<PuntoTacticoVO> pts = new ArrayList<PuntoTacticoVO>();
        		
        		Pattern regex = Pattern.compile(regExp);
        		//Pattern regex2 = Pattern.compile(regExpUbicacion);
        		
        		//Validar ubicación de archivo...
        		Matcher match2 = regex.matcher(folder.getName());
        		Matcher match3 = regex.matcher(folder.listFiles()[0].getName());
        		if(match3.find()){
        			//Todo bien, procede con las validaciones
        			idEntregable = folder.getName();
        			root = folder;
        		}else{
        			if(match2.find()){
            			//SI SOY PT, busco mi anterior y marco que soy solo 1
            			idEntregable = folder.getParentFile().getName();
            			root = folder.getParentFile();
            			isOnlyOne = folder.getName();
            		}else{
            			//Validamos 1 paso atras, en caso de no encontrar mandar incidencia
            			Matcher lastChance = regex.matcher(folder.getParentFile().getName());
            			if(lastChance.find()){
            				//procedemos, pero cambiamos el File a iterar
            				idEntregable = folder.getParentFile().getParentFile().getName();
            				root = folder.getParentFile().getParentFile();
            				isOnlyOne = folder.getParentFile().getName();
            			}else{
            				//Inconsistencia
            				NodoPT node =  new NodoPT(buscarUltimoNodo());
    		        		node.setError(EnumErrorValidacion.NAME);
    		        		node.setName(folder.getName());
    		        		root = folder.getParentFile();
    		        		if(buscarUltimoNodo() == null) incidencias = node;
            			}
            			
            		}
        		}
        		
        		if(idEntregable != null){
	        		//for (final File archivo : folder.listFiles()) {
        			for (int i=0;i<root.listFiles().length;i++) {
	        	        if (root.listFiles()[i].isDirectory()) {
	        	        	if(isOnlyOne != null){
	        					if(!root.listFiles()[i].getName().equals(isOnlyOne))
	        						continue;
	        				}
	        	        	
	        	        	//Actualizamos la barra de proceso
	        	        	loadi.UpdateProgress(((i+1)*100)/root.listFiles().length);
	        	        	loadi.repaint();
	        	        	
	        	        	Matcher match = regex.matcher(root.listFiles()[i].getName());
	        	        	PuntoTacticoVO ptVO = new PuntoTacticoVO();
	        	        	NodoPT ultimoNodo = buscarUltimoNodo();
	        	        	NodoPT node = null;
	        	        	ptVO.setName(root.listFiles()[i].getName());
	        	        	if(match.find()){
	        	        		ptVO.setNameValid(true);
	        	        		
	        	        		if(root.listFiles()[i].listFiles().length >0){
	        			        	//Mapeo de Archivos CSV
	        		        		Map<String, Object> csv = validarArchivosCSVPuntoTactivo(ptVO.getName(), root.listFiles()[i]);
	        		        		
	        			        	ptVO.setArchivos((List<ArchivoCSVVO>)csv.get("archivoCSV"));
	        			        	
	        			        	if(((List<NodoError>)csv.get("error")).size() > 0){
	        			        		
	        			        		node = node != null ? node : new NodoPT(ultimoNodo);
	        			        		
	        			        		node.setError(EnumErrorValidacion.FILES_CSV);
	        			        		node.setName(node.getName() == null ? ptVO.getName() : node.getName());
	        			        		node.setArchivos(((List<NodoError>)csv.get("error")));
	        			        		if(ultimoNodo == null) incidencias = node;
	        			        		pts.add(ptVO);
	        			        		continue;
	        			        	}
	        			        	
	        			        	//Mapeo de Imagenes
	        			        	Map<String, Object> img = validarImagenesPuntoTactivo(ptVO, root.listFiles()[i]);
	        			        	
	        			        	ptVO.setImagenes((List<ImagenesVO>)img.get("archivoIMG"));
	        			        	
	        			        	if(((List<NodoError>)img.get("error")).size() > 0){
	        			        		
	        			        		node = node != null ? node : new NodoPT(ultimoNodo);
	        			        		
	        			        		
	        			        		if(((NodoError)((List<NodoError>)img.get("error")).get(0)).getCodigo() == EnumErrorValidacion.OVERSIZE_IMAGES.getIdError()){
    	        			        		node.setError(EnumErrorValidacion.CSV_NOT_FOUND);
    	        			        		node.setName(node.getName() == null ? ptVO.getName() : node.getName());
    	        			        		node.setImagenes(((List<NodoError>)img.get("error")));
    	        			        		if(ultimoNodo == null) incidencias = node;	
        								}else{
		        			        		node.setError(EnumErrorValidacion.IMAGES);
		        			        		node.setName(node.getName() == null ? ptVO.getName() : node.getName());
		        			        		node.setImagenes(((List<NodoError>)img.get("error")));
		        			        		if(ultimoNodo == null) incidencias = node;
		        			        		pts.add(ptVO);
        								}
	        			        		continue;
	        			        	}
	        			        	
	        			        	//Mapeo de Siluetas (Opcional)
	        			        	ptVO.setSiluetas(validarSiluetasPuntoTactivo(ptVO, root.listFiles()[i]));
	        			        	
	        	        		}else{
	        	        			//La carpeta esta vacia
	        	        			ptVO.setNameValid(false);
	        		        		node = node != null ? node : new NodoPT(ultimoNodo);
	        		        		node.setError(EnumErrorValidacion.EMPTY_FOLDER);
	        		        		node.setName(node.getName() == null ? ptVO.getName() : node.getName());
	        		        		if(ultimoNodo == null) incidencias = node;
	        		        		pts.add(ptVO);
	        		        		continue;
	        	        		}
	        	        	}else{
	        	        		ptVO.setNameValid(false);
	        	        		node = node != null ? node : new NodoPT(ultimoNodo);
	        	        		node.setError(EnumErrorValidacion.NAME);
	        	        		node.setName(node.getName() == null ? ptVO.getName() : node.getName());
	        	        		if(ultimoNodo == null) incidencias = node;
	        	        	}
	        	        	pts.add(ptVO);
	        	        }
	        	    }
        		}
        		
        		if(validarPuntosTacticos(pts)){
        			res.setIdUsuario(0L);
        			res.setPuntosTacticos(pts);
        			res.setNbEntregable(idEntregable);
        			//return res;	
        		}else{
        			res = new LotesVO();
        			//return new LotesVO();
        		}
        		
        		loadi.setVisible(false);
        		pnlIncidencias.removeAll();
        		scrollPane.setViewportView(tabla);
        		
        		pnlIncidencias.add(scrollPane);
        		scrollPane.setVisible(true);
        		scrollPane.setViewportView(tabla);
        		pnlIncidencias.revalidate();
        		
        		if(res.getPuntosTacticos()== null){
					//Generar el reporte malo
        			JOptionPane.showMessageDialog(null, "El directorio esta inconsistente");
        			tabla.setModel(generarReporteIncidencias());
					btnConfirma.setVisible(false);
					btnIncidencia.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null, "El directorio esta correcto");
					//Generar el reporte bueno
					tabla.setModel(generarReporte());
					btnConfirma.setVisible(true);
					btnIncidencia.setVisible(false);
				}
        		
				
				//Terminando todo, vuelvo a poner la tabla
				
        		 //float result=((i+1)*100)/images.size();
        		//loadi.updateGraphics((int) result);
        		//loadi.btn_close.setVisible(true);
        		//loadi.showMensaje("El proceso de renombrado de los archivos se completó satisfactoriamente.");
        		
        	}
        }).start();
	}
	
	public DefaultTableModel generarReporte(){
		int conteo = 0;
		
		for(PuntoTacticoVO ptVO:res.getPuntosTacticos()){
			++conteo;
			conteo += ptVO.getArchivos().size();
		}
		
		Object [][] obj = new Object [conteo][5];
		conteo = 0;
		for(int i=0;i<res.getPuntosTacticos().size();i++){
			obj[conteo][0] = "";
			obj[conteo][1] = res.getPuntosTacticos().get(i).getName();;
			obj[conteo][2] = "";
			obj[conteo][3] = "";
			obj[conteo][4] = "";
			
			if(!res.getPuntosTacticos().get(i).getArchivos().isEmpty() &&
					!res.getPuntosTacticos().get(i).getImagenes().isEmpty())
				for(int j=0;j<res.getPuntosTacticos().get(i).getArchivos().size();j++)
				{
					conteo++;
					obj[conteo][0] = idEntregable;
					obj[conteo][1] = res.getPuntosTacticos().get(i).getName();
					obj[conteo][2] = res.getPuntosTacticos().get(i).getArchivos().get(j).getName();
					obj[conteo][3] = res.getPuntosTacticos().get(i).getImagenes().get(j).getName();
					obj[conteo][4] = !res.getPuntosTacticos().get(i).getSiluetas().isEmpty() ? res.getPuntosTacticos().get(i).getSiluetas().get(j).getName():"";
				}
			conteo++;
		}
		
		return new DefaultTableModel(obj, new String[]{"PERIODO","LOTE","ARCHIVO CSV","IMAGEN","SILUETA"});
	}
	
	public DefaultTableModel generarReporteIncidencias(){
		NodoPT head = incidencias;
		int conteo = 0;
		
		while(head != null){
			if(head.getArchivos() != null)
				for(NodoError ne:head.getArchivos()) {conteo++;}

			if(head.getImagenes() != null)
				for(NodoError ne:head.getImagenes()){conteo++;}

			conteo++;
			head = head.getNext();
		}
		
		Object [][] obj = new Object [conteo][4];
		
		head = incidencias;
		conteo = 0;
		while(head != null){
			obj[conteo][0] = head.getName();
			obj[conteo][1] = head.getError().getMensajeError();
			obj[conteo][2] = "";
			obj[conteo][3] = "";
			
			if(head.getArchivos() != null)
				for(NodoError ne:head.getArchivos())
				{
					conteo++;
					obj[conteo][0] = head.getName();
					obj[conteo][1] = head.getError().getMensajeError();
					obj[conteo][2] = ne.getNombre();
					obj[conteo][3] = ne.getMensaje();
				}
			
			if(head.getImagenes() != null)
				for(NodoError ne:head.getImagenes())
				{
					conteo++;
					obj[conteo][0] = head.getName();
					obj[conteo][1] = head.getError().getMensajeError();
					obj[conteo][2] = ne.getNombre();
					obj[conteo][3] = ne.getMensaje();
					
				}
			
			conteo++;
			head = head.getNext();
		}
		imprimirIncidencias();
		return new DefaultTableModel(obj, new String[]{"LOTE","INCIDENCIA","CAUSA","ORIGEN"});
	}
	
	public void enviarDatosARest(final String idUsuario, final JPanel incidencias, final JLabel home, final JLabel img, final JPanel buscar){
		new Thread(new Runnable(){
			@Override
        	public void run() {
				try{
					res.setIdUsuario(Long.parseLong(idUsuario));
					validacionService.enviarDatosARest(res);
					incidencias.removeAll();
					incidencias.add(home, BorderLayout.CENTER);
					home.setVisible(true);
					incidencias.revalidate();
					buscar.setVisible(true);
					JOptionPane.showMessageDialog(null, "¡Confirmación exitosa!");
				}catch(Exception e){
					e.printStackTrace();
					incidencias.removeAll();
					incidencias.add(home, BorderLayout.CENTER);
					home.setVisible(true);
					incidencias.revalidate();
					buscar.setVisible(true);
					JOptionPane.showMessageDialog(null, "Hubo problemas de comunicación");
					JTextArea msg = new JTextArea(e.getMessage());
					msg.setLineWrap(true);
					msg.setWrapStyleWord(true);
					JScrollPane scrollPane = new JScrollPane(msg);
					JOptionPane.showMessageDialog(null, scrollPane);
					
				}
				
        	}
        }).start();
		
	}
	
	private Map<String, Object> validarArchivosCSVPuntoTactivo(String puntoTactico, File ptFile){
		String numPT = puntoTactico.substring(2);
		//String regExp = "^(([0-9]{9})(C[0-9]{1,})(.csv))$";
		String regExp = "(.csv)";
		//String regExp2 = "^("+numPT+")("+idEntregable.substring(4, 6)+idEntregable.substring(0,4)+"C[0-9]{1,}).csv$";
		//String excluir = "(METADATO)";

		Map<String, Object> res= new HashMap<String, Object>();
		
		List<ArchivoCSVVO> archivos = new ArrayList<ArchivoCSVVO>();
		List<NodoError> archivosErr = new ArrayList<NodoError>();
		
		Pattern regex = Pattern.compile(regExp);
		//Pattern regex2 = Pattern.compile(regExp2);
		
		for (final File csv : ptFile.listFiles()) {
	        if (csv.isFile()) {
	        	Matcher match = regex.matcher(csv.getName());
	        	ArchivoCSVVO aCCSVVO = new ArchivoCSVVO();
	        	aCCSVVO.setName(csv.getName());
	        	if(match.find()){
	        		//Matcher match2 = regex2.matcher(csv.getName());
	        		//if(match2.find()){
	        			aCCSVVO.setNameValid(true);
	        		/*}else{
	        			aCCSVVO.setNameValid(false);
	        			NodoError ne= new NodoError(csv.getName(), EnumErrorValidacion.CSV_PT_OR_PERIOD);
		        		archivosErr.add(ne);
	        		}*/
	        		archivos.add(aCCSVVO);
	        		//idEntregable = idEntregable == null || idEntregable.trim().equals("") ? imagen.getName().substring(5,9)+imagen.getName().substring(3,5) : idEntregable;
	        	}/*else{
	        		aCCSVVO.setNameValid(false);
	        	}
	        	
	        	if(!aCCSVVO.isNameValid()){
	        		Pattern regex2 = Pattern.compile(excluir);
	        	    Matcher ignore = regex2.matcher(aCCSVVO.getName());
	        	    if(!ignore.find()){
	        	    	;
	        	    	NodoError ne= new NodoError(imagen.getName(), EnumErrorValidacion.NAME);
		        		archivosErr.add(ne);
	        	    }else{
	        	    	//Excluimos el matadato
	        	    }
	        	}else{
	        		archivos.add(aCCSVVO);
	        	}*/
	        }
	    }
		res.put("archivoCSV", archivos);
		res.put("error", archivosErr);
		
		return res;
	}
	
	private Map<String, Object> validarImagenesPuntoTactivo(PuntoTacticoVO ptVO, File ptFile){
		
		Map<String, Object> res= new HashMap<String, Object>();
		
		List<ImagenesVO> imagenes = new ArrayList<ImagenesVO>();
		List<NodoError> imagenesErr = new ArrayList<NodoError>();
		
		for(ArchivoCSVVO aCSVVO:ptVO.getArchivos()){
			if(aCSVVO.isNameValid()){
				//File imagen = new File(ptFile.getAbsolutePath()+"\\imagenes"+aCSVVO.getName().substring(9,11));
				File imagen = new File(ptFile.getAbsolutePath()+"\\imagenes");
				ImagenesVO imagenVO = new ImagenesVO();
				imagenVO.setName(imagen.getName());
				if(imagen.exists()){
					if(imagen.listFiles().length >0){
						imagenVO.setDoesExist(true);
						imagenVO.setNameValid(true);
					}else{
						imagenVO.setDoesExist(true);
						imagenVO.setNameValid(false);
						NodoError ne = new NodoError(imagen.getName(), EnumErrorValidacion.EMPTY_FOLDER);
						imagenesErr.add(ne);
					}
					
				}else{
					imagenVO.setDoesExist(false);
					imagenVO.setNameValid(true);
					NodoError ne = new NodoError(imagen.getName(), EnumErrorValidacion.NOT_FOUND_FOLDER);
					imagenesErr.add(ne);
				}
				imagenes.add(imagenVO);
			}
		}
		
		int conteo = 0;
		boolean found  = false;
		String carril = "";
		//Pattern ptrn = Pattern.compile("^(imagenesC[0-9]{1,})$");
		Pattern ptrn = Pattern.compile("^(imagenes)$");
		//Validación extrema
		for(File img: ptFile.listFiles()){
			if(img.isDirectory()){
				Matcher matcher = ptrn.matcher(img.getName());
				if(matcher.find()){
					for(ImagenesVO iVO:imagenes){
						if(iVO.getName().equals(img.getName())){
							found= true;
							conteo++;
						}
					}
					if(!found){
						carril = img.getName();
						conteo++;
					}
				}
			}
		}
		
		if(imagenes.size() != conteo && imagenesErr.isEmpty()){
			//NodoError ne = new NodoError(ptVO.getName().substring(2, 5)+idEntregable.substring(4, 6)+idEntregable.substring(0,4)+carril.substring(8,10)+".csv", EnumErrorValidacion.OVERSIZE_IMAGES);
			NodoError ne = new NodoError("CSV_FINAL.csv", EnumErrorValidacion.OVERSIZE_IMAGES);
			imagenesErr.add(ne);
		}
		
		
		res.put("archivoIMG", imagenes);
		res.put("error", imagenesErr);
		return res;
	}
	
	private List<SiluetasVO> validarSiluetasPuntoTactivo(PuntoTacticoVO ptVO, File ptFile){
		
		List<SiluetasVO> siluetas = new ArrayList<SiluetasVO>();
		
		for(ArchivoCSVVO aCSVVO:ptVO.getArchivos()){
			if(aCSVVO.isNameValid()){
				//File silueta = new File(ptFile.getAbsolutePath()+"\\siluetas"+aCSVVO.getName().substring(9,11));
				File silueta = new File(ptFile.getAbsolutePath()+"\\siluetas");
				SiluetasVO siluetasVO = new SiluetasVO();
				siluetasVO.setName(silueta.getName());
				if(silueta.exists()){
					if(silueta.listFiles().length >0){
						siluetasVO.setNameValid(true);
						siluetas.add(siluetasVO);
					}
				}
			}
		}
		return siluetas;
	}
	
	private NodoPT buscarUltimoNodo(){
		NodoPT ultimo = null;
		if(incidencias !=null){
			 ultimo = incidencias;
		
			while(ultimo.isHasNext()){
				ultimo = ultimo.getNext();
			}
		}
		return ultimo;
	}
	
	private boolean validarPuntosTacticos(List<PuntoTacticoVO> listaPTVO){
		boolean isValid = true;
		if(!listaPTVO.isEmpty()){
			for(PuntoTacticoVO ptVO: listaPTVO){
				isValid = ptVO.isPuntoTactivoValid();
				if(!isValid) break;
			}
		}else{
			isValid = false;
		}
		
		return isValid;
	}
	
	private void imprimirIncidencias(){
		NodoPT pivote = incidencias;
		while(pivote != null){
			System.out.println("["+pivote.getName()+" - "+pivote.getError().getMensajeError()+"]");
			if(pivote.getArchivos() != null){
				System.out.println("== Archivos ==");
				for (NodoError ne: pivote.getArchivos()){
					System.out.println("== "+ne.getNombre()+": "+ne.getMensaje());
				}
			}
			if(pivote.getImagenes() != null){
				System.out.println("");
				System.out.println("== Imagenes ==");
				for (NodoError ne: pivote.getImagenes()){
					System.out.println("== "+ne.getNombre()+": "+ne.getMensaje());
				}
			}
			pivote = pivote.getNext();
		}
	}
	
	public File getRoot(){
		return this.root;
	}
}
