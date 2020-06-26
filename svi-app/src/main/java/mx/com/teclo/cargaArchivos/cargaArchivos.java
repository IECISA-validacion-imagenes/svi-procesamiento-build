package mx.com.teclo.cargaArchivos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import mx.com.teclo.base.cargaArchivosVO.DetalleArchivoPTVO;
import mx.com.teclo.base.cargaArchivosVO.DetalleLoteVO;
import mx.com.teclo.base.cargaArchivosVO.InformacionEnviarVO;
import mx.com.teclo.base.cargaArchivosVO.LotesyDetalleVO;
import mx.com.teclo.base.cargaArchivosVO.PTArchivocsvVO;
import mx.com.teclo.base.cargaArchivosVO.resultadoMapeoVO;
import mx.com.teclo.base.frames.CircleProgressPanel;
import mx.com.teclo.base.funciones.LeerValidarCSV;
import mx.com.teclo.base.utilerias.ValidacionRuta;
import mx.com.teclo.directorios.services.CargaCSVRestClientService;


@SuppressWarnings("serial")
public class cargaArchivos extends JPanel {
	
	public String ruta="",rutaSCV ="";
	public String direccionCarpeta="";
	public List<PTArchivocsvVO> detallePT = new ArrayList<PTArchivocsvVO>();
	public List<DetalleLoteVO> detalleLote = new ArrayList<DetalleLoteVO>();
	
//	public Boolean botonBuscar = MainFrame.verBtnCarga;
//	public Boolean botonEnviar = MainFrame.verBtnExplorar;
	
	public int tamañoRuta=0;
	private String idUsuario;
	//private JTable table;
	private static String ERROR_CSV ="El archivo tiene renglones vacios";
	private static String VALIDACION_OK = " CSV Correcto ";
	
	public String[] columnNames = {"Punto Tactico","Archivos SCV", "Carpeta Imagenes "," Carpetas Siluetas","Fecha Creación"};
	public String[] columnas = {"Periodo","Punto Tactico","Archivos SCV", "Estatus Archivo"};
	
	public DefaultTableModel tableModel = new DefaultTableModel();
	
	public boolean hayErrorCSV = false;
	
	
	final JPanel btnBuscar = new JPanel();
	final JPanel btnCargar = new JPanel();
	final ValidacionRuta regresaRuta = new ValidacionRuta();
	final JTable table = new JTable();
	final JPanel pnl_Info = new JPanel();
	final JScrollPane scrollPane = new JScrollPane();
	final JLabel imgHome = new JLabel(" ");
	

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cargaArchivos frame = new cargaArchivos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public cargaArchivos(String idUsuario) {
		this();
		this.idUsuario = idUsuario;
	}
	
	public void showBtn(Boolean btnBuscarVisible, Boolean btnCargaVisible){
		btnBuscar.setVisible(btnBuscarVisible); //carga
		btnCargar.setVisible(btnCargaVisible); //fileExplorere	
	};
	
	public void hayPendientes(List<DetalleLoteVO> DetalleCSV){
		if(!DetalleCSV.isEmpty()){
			detalleLote = DetalleCSV;
			imgHome.setVisible(false);

			
			DibujarTablaDatos();
		}
		
	};
	
    public void DibujarTablaDatos(){
         List<resultadoMapeoVO> cuerpoTabla = regresaRuta.getPeriodosUnico(detalleLote);
		 tableModel.setColumnIdentifiers(columnNames);
		 Object[] fila = new Object[tableModel.getColumnCount()];
		 for(resultadoMapeoVO elemento: cuerpoTabla){
			 fila[0] = elemento.getPuntoTactico();
			 fila[1] = elemento.getPeriodo();
			 tableModel.addRow(fila);
		 }
		 table.setModel(tableModel);
		 table.setVisible(true); 
		 pnl_Info.add(scrollPane, BorderLayout.CENTER);
		 scrollPane.setVisible(true);
		};
	
	@SuppressWarnings("unused")
	public cargaArchivos() {
		setBackground(Color.WHITE);
		setBounds(100, 100, 450, 300);
		setLayout(new BorderLayout(0, 0));
		
		
		pnl_Info.setBackground(Color.WHITE);
		add(pnl_Info, BorderLayout.CENTER);
		pnl_Info.setLayout(new BorderLayout(0, 0));
		
		
		pnl_Info.add(scrollPane, BorderLayout.SOUTH);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		table.setFillsViewportHeight(true);
		table.setBounds(-17, 136, 467, -136);
		table.setBackground(Color.WHITE);
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Periodo","Punto Tactico","Archivos SCV", "Estatus Archivo"
				}
			));
		
		table.setColumnSelectionAllowed(true);
		scrollPane.setVisible(false);
		scrollPane.setViewportView(table);
		
		
		imgHome.setHorizontalAlignment(SwingConstants.CENTER);
		imgHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/coverapptmp.png")));
		pnl_Info.add(imgHome);
		
		final JLabel imgLoading = new JLabel(" ");
		imgLoading.setHorizontalAlignment(SwingConstants.CENTER);
		imgLoading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loading.gif")));
		
		JPanel pnl_Carga = new JPanel();
		pnl_Carga.setBackground(Color.WHITE);
		add(pnl_Carga, BorderLayout.SOUTH);
		pnl_Carga.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		btnBuscar.setBackground(new Color(211,26,43));
		btnBuscar.setVisible(true);
		pnl_Carga.add(btnBuscar);
		
		
		btnBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnBuscar.setEnabled(false);
				tableModel.setRowCount(0);
			
				//Ponemos la comunicacion
				pnl_Info.removeAll();
				pnl_Info.add(imgLoading, BorderLayout.CENTER);
				pnl_Info.repaint();
				pnl_Info.revalidate();
				
				new Thread(new Runnable(){
					@Override
		        	public void run() {
						 CargaCSVRestClientService obtenerDatosPT = new CargaCSVRestClientService();
						 
						 try{
							 detalleLote = obtenerDatosPT.obtenerDetallePT("CargaNormal");
							 if(detalleLote.size()>0){
								 DibujarTablaDatos();
								 JOptionPane.showMessageDialog(null, "Existen PT a cargar ", "IECISA", JOptionPane.INFORMATION_MESSAGE); 
								 btnBuscar.setVisible(false);
								 btnCargar.setVisible(true);
								 
								 pnl_Info.removeAll();
								 pnl_Info.add(scrollPane, BorderLayout.CENTER);
								 scrollPane.setVisible(true);
								 pnl_Info.repaint();
								 pnl_Info.revalidate();
							 }else{
								 JOptionPane.showMessageDialog(null, "No Existen PT a cargar ", "IECISA", JOptionPane.INFORMATION_MESSAGE);	
								 btnBuscar.setVisible(true);
								 btnCargar.setVisible(false);
								 pnl_Info.removeAll();
								 pnl_Info.add(imgHome, BorderLayout.CENTER);
								 imgHome.setVisible(true);
							 }
							
							 
//							 pnl_Info.removeAll();
//							 pnl_Info.add(scrollPane, BorderLayout.CENTER);
//							 scrollPane.setVisible(true);
//							 pnl_Info.repaint();
//							 pnl_Info.revalidate();
						 }catch(Exception e){
							 e.printStackTrace();
							 pnl_Info.removeAll();
							 pnl_Info.add(imgHome, BorderLayout.CENTER);
							 imgHome.setVisible(true);
							 pnl_Info.repaint();
							 pnl_Info.revalidate();
							 JOptionPane.showMessageDialog(null, "Hubo problemas de comunicación ", "IECISA", JOptionPane.INFORMATION_MESSAGE);
						 }
		        	}
		        }).start();
		}
			
			
		   @Override
			public void mousePressed(MouseEvent e) {
				btnBuscar.setBackground(new Color(172, 26, 43));
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				btnBuscar.setBackground(new Color(211,26,43));
			}});

/*Botones de funcionalidad */
		JLabel lblBuscar = new JLabel("  Buscar Archivos  ");
		lblBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBuscar.setForeground(Color.WHITE);
		btnBuscar.add(lblBuscar);

		
		btnCargar.setBackground(new Color(211,26,43));
		btnCargar.setVisible(false);
		pnl_Carga.add(btnCargar);
		
		JLabel lblCargar = new JLabel("  Cargar Archivo  ");
		lblCargar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCargar.setForeground(Color.WHITE);
		btnCargar.add(lblCargar);
		
		btnCargar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {							
				final List<LotesyDetalleVO> enviarInfo = new ArrayList<LotesyDetalleVO>();			
				final LeerValidarCSV leerArchivo = new LeerValidarCSV();
				final ValidacionRuta regresaRuta = new ValidacionRuta();
				List<PTArchivocsvVO> listaEnviar = new ArrayList<PTArchivocsvVO>();
				
				
				final List<InformacionEnviarVO> informacion = new ArrayList<InformacionEnviarVO>();
				final  List<LotesyDetalleVO> renglonenviar = new  ArrayList<LotesyDetalleVO>();// new InformacionEnviarVO();
				
				final boolean status =false;
				
			if(detalleLote.size()>0){
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					jfc.setDialogTitle("Selecciona la carpeta de PT a cargar: ");
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnValue = jfc.showSaveDialog(null);
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						if (jfc.getSelectedFile().isDirectory()) {
							ruta = jfc.getSelectedFile().getAbsolutePath();
							System.out.println(ruta);
					   }
						
						String respuesta = regresaRuta.comprobarRuta(ruta);
						final long usuarioFirmado =Long.parseLong(idUsuario); 
						
						 
						 final List<resultadoMapeoVO> erroresenCSV = new ArrayList<resultadoMapeoVO>();		
						//carga de datos
						
						final CircleProgressPanel progreso = new CircleProgressPanel();
						
						switch(respuesta){				
						case "año": /* Obtener informacion de todos los PT 
						*/
							new Thread(new Runnable(){
					        	@SuppressWarnings("unchecked")
								@Override
					        	public void run() {
					        		//Carga
					        		pnl_Info.removeAll();
					        		pnl_Info.add(progreso, BorderLayout.CENTER);
					        		pnl_Info.revalidate();
					        		
									//for(PTArchivocsvVO elemento: detallePT){
					        		System.out.println("");
					        		
					        		for(int i = 0;i<detalleLote.size();i++){
					        			String nombrePT = detalleLote.get(i).getNbPtLote();
					        			long idPTLote = detalleLote.get(i).getIdPtLote();
					        			
										List<DetalleArchivoPTVO> cargaCSV = new ArrayList<DetalleArchivoPTVO>();		
										PTArchivocsvVO elementosCorregidos = new PTArchivocsvVO();
										
										progreso.UpdateProgress(((i+1)*100)/detalleLote.size());
										progreso.repaint();
//										
//										String nombrePT = regresaRuta.obtenerPT(detalleLote.get(i).getTxCarpetaImg());
										String rutaCompleta = ruta + "\\" +nombrePT;
										informacion.add(new InformacionEnviarVO (leerArchivo.RegistrosyArchivosCSV(nombrePT, idPTLote, rutaCompleta, status,usuarioFirmado, 
												detalleLote.get(i).getIdEntrega())));
										
										//listaEnviar.add(elementosCorregidos);
									//	final ValidacionRuta funcionesRuta = new ValidacionRuta();
										
										if(informacion.size()>0){
											//enviarInfo.add( new LotesyDetalleVO(cargaCSV,elementosCorregidos));
											resultadoMapeoVO errorCSV2 = new resultadoMapeoVO();
						            		errorCSV2.setPeriodo(detalleLote.get(i).getIdEntrega().getNbEntrega()); //.get(i).getTxCarpetaImg().substring(0,6));
						            		errorCSV2.setPuntoTactico(detalleLote.get(i).getNbPtLote());
						            		errorCSV2.setArchivoSCV("detalleLote");
						            		errorCSV2.setStatus(VALIDACION_OK);
						            		erroresenCSV.add(errorCSV2);
										}else{
											//System.out.println("********************************************  error de archivo scv \n");
											hayErrorCSV = true;
											resultadoMapeoVO errorCSV2 = new resultadoMapeoVO();
						            		errorCSV2.setPeriodo(detalleLote.get(i).getIdEntrega().getNbEntrega()); //.get(i).getTxCarpetaImg().substring(0,6));
						            		errorCSV2.setPuntoTactico(detalleLote.get(i).getNbPtLote());
						            		errorCSV2.setArchivoSCV("detalleLote");
						            		errorCSV2.setStatus(ERROR_CSV);
						            		erroresenCSV.add(errorCSV2);
										}		
												
									}
					        		

									int tamano = informacion.size();
									for(int pos = 0; pos< tamano; pos++){
										informacion.get(pos).getDatos();
										for(LotesyDetalleVO e : informacion.get(pos).getDatos()){	
											renglonenviar.add(new LotesyDetalleVO(e.getDetalleArchivosCSV(),
													e.getListaDetalleElemento()));
										}
										
									}
					        		
					        		///fin del for 
									
					        		if(renglonenviar.size()>0){
					        			new Thread(new Runnable(){
								        	@SuppressWarnings("unchecked")
											@Override
								        	public void run() {
								        		//comunicacion
								        		pnl_Info.removeAll();
								        		pnl_Info.add(imgLoading, BorderLayout.CENTER);
								        		pnl_Info.repaint();
								        		pnl_Info.revalidate();
								        		
												CargaCSVRestClientService enviarDatos = new CargaCSVRestClientService();
												try{
													for(LotesyDetalleVO elemento : renglonenviar){
														enviarDatos.cargarInformacionPT(elemento);	
													}
													
									        	//	System.out.println("hay error en CSV " + hayErrorCSV);
													if(hayErrorCSV){
														pnl_Info.removeAll();
										        		pnl_Info.add(scrollPane, BorderLayout.CENTER);
										        		pnl_Info.repaint();
										        		pnl_Info.revalidate();
										        		
														tableModel.setRowCount(0);
														tableModel.setColumnIdentifiers(columnas);
														Object[] fila = new Object[tableModel.getColumnCount()];
														 
														 for(resultadoMapeoVO elemento: erroresenCSV){
															 fila[0] = elemento.getPeriodo();
															 fila[1] = elemento.getPuntoTactico();
															 fila[2] = elemento.getArchivoSCV();
															 fila[3] = elemento.getStatus();
															 tableModel.addRow(fila);
														 }
														 
														 table.setModel(tableModel);
														 table.setVisible(true);
														 
													}else{
													pnl_Info.removeAll();
									        		pnl_Info.add(imgHome, BorderLayout.CENTER);
									        		imgHome.setVisible(true);
									        		pnl_Info.repaint();
									        		pnl_Info.revalidate();							        		
									        		JOptionPane.showMessageDialog(null, "¡Carga de archivos exitoso!","Exito", JOptionPane.INFORMATION_MESSAGE);	
									        		 btnBuscar.setVisible(true);
													 btnCargar.setVisible(false);
													}
													detallePT = new ArrayList<>();
												}catch(Exception e){
													e.printStackTrace();
													pnl_Info.removeAll();
									        		pnl_Info.add(imgHome, BorderLayout.CENTER);
									        		imgHome.setVisible(true);
									        		pnl_Info.repaint();
									        		pnl_Info.revalidate();
									        		JOptionPane.showMessageDialog(null, "Hubo un problema de comunicación","ERROR", JOptionPane.ERROR_MESSAGE);
									        		detallePT = new ArrayList<>();
												}
								        	}}).start();
					        			
					        		}
					        		
					        		pnl_Info.removeAll();
					        		pnl_Info.add(scrollPane, BorderLayout.CENTER);
					        		pnl_Info.repaint();
					        		pnl_Info.revalidate();
					        		
									if(hayErrorCSV){
										tableModel.setRowCount(0);
										tableModel.setColumnIdentifiers(columnas);
										 Object[] fila = new Object[tableModel.getColumnCount()];
										 
										 for(resultadoMapeoVO elemento: erroresenCSV){
											 fila[0] = elemento.getPeriodo();
											 fila[1] = elemento.getPuntoTactico();
											 fila[2] = elemento.getArchivoSCV();
											 fila[3] = elemento.getStatus();
											 tableModel.addRow(fila);
										 }
										 
										 table.setModel(tableModel);
										 table.setVisible(true);	 
									}
									
					        	}}).start();
							

							
							break;								
							
						case "pt": /* tomar campo sin direccion para acompletar */				
							new Thread(new Runnable(){
					        	@SuppressWarnings("unchecked")
								@Override
					        	public void run() {
					        		//Carga
					        		pnl_Info.removeAll();
					        		pnl_Info.add(progreso, BorderLayout.CENTER);
					        		pnl_Info.revalidate();
					        		//for(PTArchivocsvVO elemento: detallePT){
					        		int tamano = detalleLote.size();
					        		
					        		for(int i = 0;i<tamano;i++){
					        			String nombrePT = detalleLote.get(i).getNbPtLote();
					        			long idPTLote = detalleLote.get(i).getIdPtLote();;
										
										progreso.UpdateProgress(((i+1)*100)/detalleLote.size());
										progreso.repaint();
										//System.out.println(rutaFolders);
										rutaSCV = "";
										rutaSCV = ruta;
										direccionCarpeta ="";
										direccionCarpeta = ruta;

										
										String rutaCompleta = ruta;
										informacion.add(new InformacionEnviarVO (leerArchivo.RegistrosyArchivosCSV(nombrePT, idPTLote, rutaCompleta, status,usuarioFirmado, 
												detalleLote.get(i).getIdEntrega())));
										
										if(informacion.size()>0){
											//enviarInfo.add( new LotesyDetalleVO(cargaCSV,elementosCorregidos));
											resultadoMapeoVO errorCSV2 = new resultadoMapeoVO();
						            		errorCSV2.setPeriodo(detalleLote.get(i).getIdEntrega().getNbEntrega()); //.get(i).getTxCarpetaImg().substring(0,6));
						            		errorCSV2.setPuntoTactico(detalleLote.get(i).getNbPtLote());
						            		errorCSV2.setArchivoSCV("detalleLote");
						            		errorCSV2.setStatus(VALIDACION_OK);
						            		erroresenCSV.add(errorCSV2);
										}else{
											//System.out.println("********************************************  error de archivo scv \n");
											hayErrorCSV = true;
											resultadoMapeoVO errorCSV2 = new resultadoMapeoVO();
						            		errorCSV2.setPeriodo(detalleLote.get(i).getIdEntrega().getNbEntrega()); //.get(i).getTxCarpetaImg().substring(0,6));
						            		errorCSV2.setPuntoTactico(detalleLote.get(i).getNbPtLote());
						            		errorCSV2.setArchivoSCV("detalleLote");
						            		errorCSV2.setStatus(ERROR_CSV);
						            		erroresenCSV.add(errorCSV2);
										}		
									}
					        		///FINAL DEL FOR 
					        		
					        		int largo = informacion.size();
									for(int pos = 0; pos< largo; pos++){
										informacion.get(pos).getDatos();
										for(LotesyDetalleVO e : informacion.get(pos).getDatos()){	
											renglonenviar.add(new LotesyDetalleVO(e.getDetalleArchivosCSV(),
													e.getListaDetalleElemento()));
										}
										
									}
									
					        		
					        		if(renglonenviar.size()>0){
					        			new Thread(new Runnable(){
								        	@SuppressWarnings("unchecked")
											@Override
								        	public void run() {
								        		//comunicacion
								        		pnl_Info.removeAll();
								        		pnl_Info.add(imgLoading, BorderLayout.CENTER);
								        		pnl_Info.repaint();
								        		pnl_Info.revalidate();
								        		
												CargaCSVRestClientService enviarDatos = new CargaCSVRestClientService();
												try{
													
													for(LotesyDetalleVO elemento : renglonenviar){
														enviarDatos.cargarInformacionPT(elemento);	
													}
													
													if(hayErrorCSV){
														pnl_Info.removeAll();
										        		pnl_Info.add(scrollPane, BorderLayout.CENTER);
										        		pnl_Info.repaint();
										        		pnl_Info.revalidate();
														tableModel.setRowCount(0);
														tableModel.setColumnIdentifiers(columnas);
														 Object[] fila = new Object[tableModel.getColumnCount()];
														 
														 for(resultadoMapeoVO elemento: erroresenCSV){
															 fila[0] = elemento.getPeriodo();
															 fila[1] = elemento.getPuntoTactico();
															 fila[2] = elemento.getArchivoSCV();
															 fila[3] = elemento.getStatus();
															 tableModel.addRow(fila);
														 }
														 
														 table.setModel(tableModel);
														 table.setVisible(true);	 
													}else{
														pnl_Info.removeAll();
														pnl_Info.add(imgHome, BorderLayout.CENTER);
														imgHome.setVisible(true);
														pnl_Info.repaint();
														pnl_Info.revalidate();
														JOptionPane.showMessageDialog(null, "¡Carga de archivos exitoso!","Exito", JOptionPane.INFORMATION_MESSAGE);
														 btnBuscar.setVisible(true);
														 btnCargar.setVisible(false);
													}
													
													detallePT = new ArrayList<>();
												}catch(Exception e){
													e.printStackTrace();
													pnl_Info.removeAll();
									        		pnl_Info.add(imgHome, BorderLayout.CENTER);
									        		imgHome.setVisible(true);
									        		pnl_Info.repaint();
									        		pnl_Info.revalidate();
									        		JOptionPane.showMessageDialog(null, "Hubo un problema de comunicación","ERROR", JOptionPane.ERROR_MESSAGE);
									        		detallePT = new ArrayList<>();
												}
								        	}}).start();
									}
					        		
					        		pnl_Info.removeAll();
					        		pnl_Info.add(scrollPane, BorderLayout.CENTER);
					        		pnl_Info.repaint();
					        		pnl_Info.revalidate();
					        		
									if(hayErrorCSV){
										tableModel.setRowCount(0);
										tableModel.setColumnIdentifiers(columnas);
										 Object[] fila = new Object[tableModel.getColumnCount()];
										 
										 for(resultadoMapeoVO elemento: erroresenCSV){
											 fila[0] = elemento.getPeriodo();
											 fila[1] = elemento.getPuntoTactico();
											 fila[2] = elemento.getArchivoSCV();
											 fila[3] = elemento.getStatus();
											 tableModel.addRow(fila);
										 }	 
										 table.setModel(tableModel);
										 table.setVisible(true);	 
									}
					        }}).start();
									break;
						
						default:
							JOptionPane.showMessageDialog(null, "Dirección erronea ",
									  "ERROR DE DIRECCION", JOptionPane.ERROR_MESSAGE);
						}
					}
			}else{///final del metodo comprobacion de lista 
				JOptionPane.showMessageDialog(null, "DEBE CONSULTAR LOTES MAPEADOS ",
						  "IECISA", JOptionPane.ERROR_MESSAGE);
			}
			
	}});
	}
}
	


