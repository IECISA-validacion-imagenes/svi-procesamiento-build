package mx.com.teclo.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import mx.com.teclo.base.appconfig.AppConfiguracion;
import mx.com.teclo.base.cargaArchivosVO.DetalleLoteVO;
import mx.com.teclo.cargaArchivos.cargaArchivos;
import mx.com.teclo.directorios.services.CargaCSVRestClientService;
import mx.com.teclo.validacion.frames.ValidacionFrame;

public class MainFrame extends JFrame {

	private JPanel main;
	private JPanel infoApp;
	private static String idUsuario;
	cargaArchivos clote;
	ValidacionFrame vlote;
	JPanel vCarga = new JPanel();
	public static List<DetalleLoteVO> detalleLote = new ArrayList<DetalleLoteVO>();
	public static Boolean verBtnBuscar = true;
	public static Boolean verBtnCargar = false;
	
	final JLabel imgValidar = new JLabel("");
	final JLabel imgCargar = new JLabel("");
	final JLabel lblValidar = new JLabel("Validar Directorios");
	final JLabel lblCargar = new JLabel("Cargar CSV");
	final JPanel btnCargar = new JPanel();
	final JPanel btnValidar = new JPanel();
	
	
	final JLabel imgLoading = new JLabel(" ");
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		idUsuario = args[1];
		AppConfiguracion.configInstance(args[2], args[3]);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	
	public MainFrame() {
		vlote = new ValidacionFrame(idUsuario);
		clote = new cargaArchivos(idUsuario);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	    setUndecorated(true);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setBounds(100, 100, 600, 400);
		main = new JPanel();
		setContentPane(main);
		main.setLayout(new BorderLayout(0, 0));
		
		JPanel header = new JPanel();
		header.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(192, 192, 192)));
		header.setBackground(new Color(255, 255, 255));
		main.add(header, BorderLayout.NORTH);
		header.setLayout(new BorderLayout(0, 0));
		
		JPanel ept_3 = new JPanel();
		ept_3.setBackground(new Color(255, 255, 255));
		FlowLayout flowLayout = (FlowLayout) ept_3.getLayout();
		flowLayout.setVgap(1);
		flowLayout.setHgap(0);
		header.add(ept_3, BorderLayout.NORTH);
		
		JPanel imgToggle = new JPanel();
		imgToggle.setBackground(new Color(255, 255, 255));
		header.add(imgToggle, BorderLayout.WEST);
		imgToggle.setLayout(new BorderLayout(25, 0));
		
		JPanel pnlToggle = new JPanel();
		pnlToggle.setBackground(new Color(255, 255, 255));
		imgToggle.add(pnlToggle, BorderLayout.CENTER);
		pnlToggle.setLayout(new BorderLayout(0, 0));
		
		JLabel imgTogg = new JLabel("");
		imgTogg.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlToggle.add(imgTogg);
		imgTogg.setHorizontalAlignment(SwingConstants.CENTER);
		imgTogg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Menu_32px.png")));
		
		JPanel ept_9 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) ept_9.getLayout();
		ept_9.setBackground(Color.WHITE);
		imgToggle.add(ept_9, BorderLayout.WEST);
		
		JPanel ept_10 = new JPanel();
		ept_10.setBackground(Color.WHITE);
		imgToggle.add(ept_10, BorderLayout.EAST);
		
		JPanel title = new JPanel();
		title.setBackground(new Color(255, 255, 255));
		header.add(title, BorderLayout.CENTER);
		title.setLayout(new BorderLayout(0, 0));
		
		JPanel logo = new JPanel();
		logo.setBackground(new Color(255, 255, 255));
		title.add(logo, BorderLayout.WEST);
		logo.setLayout(new BorderLayout(0, 0));
		
		JLabel imgLogoTeclo = new JLabel("");
		imgLogoTeclo.setHorizontalAlignment(SwingConstants.CENTER);
		imgLogoTeclo.setHorizontalAlignment(SwingConstants.CENTER);
		imgLogoTeclo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LogotipoTeclo5.png")));
		logo.add(imgLogoTeclo);
		
		JPanel titleApp = new JPanel();
		titleApp.setBackground(new Color(255, 255, 255));
		title.add(titleApp, BorderLayout.CENTER);
		titleApp.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTituloAplicacion = new JLabel("Sistema de Validación de Expedientes Digitales");
		titleApp.add(lblTituloAplicacion);
		lblTituloAplicacion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTituloAplicacion.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTituloAplicacion.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel options = new JPanel();
		options.setBackground(new Color(255, 255, 255));
		header.add(options, BorderLayout.EAST);
		
		JLabel imgMinimize = new JLabel("");
		imgMinimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				minimizeWin();
			}
		});
		imgMinimize.setHorizontalAlignment(SwingConstants.CENTER);
		imgMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Minus_32px.png")));
		options.add(imgMinimize);
		
		JLabel imgExit = new JLabel("");
		imgExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "¿Estas seguro de que quieres salir de la aplicación?");
				if (confirm == 0) {
					System.exit(1);
				}
			}
		});
		imgExit.setHorizontalAlignment(SwingConstants.CENTER);
		imgExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Cancel_32px.png")));
		options.add(imgExit);
		
		JPanel ept_4 = new JPanel();
		ept_4.setBackground(new Color(255, 255, 255));
		FlowLayout flowLayout_1 = (FlowLayout) ept_4.getLayout();
		flowLayout_1.setVgap(1);
		flowLayout_1.setHgap(0);
		header.add(ept_4, BorderLayout.SOUTH);
		
		JPanel menu = new JPanel();
		menu.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(192, 192, 192)));
		menu.setBackground(Color.WHITE);
		menu.setAlignmentX(Component.LEFT_ALIGNMENT);
		main.add(menu, BorderLayout.WEST);
		menu.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		menu.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(5, 5));
		
		JPanel pnlMenu = new JPanel();
		
		
		lblCargar.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel.add(pnlMenu, BorderLayout.CENTER);
		pnlMenu.setLayout(new BorderLayout(0, 0));
		
		pnlMenu.add(btnValidar, BorderLayout.NORTH);
		btnValidar.setBackground(new Color(211,26,43));
		btnValidar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cambiarVistaValidacion();
				imgValidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Test_Passed_alt_50px.png")));
				btnValidar.setBackground(new Color(211,26,43));
				lblValidar.setForeground(Color.WHITE);
				imgCargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_CSV_50px.png")));
				btnCargar.setBackground(new Color(240, 240, 240));
				lblCargar.setForeground(Color.BLACK);
			}
		});
		btnValidar.setLayout(new BorderLayout(25, 5));
		btnValidar.add(lblValidar, BorderLayout.SOUTH);
		lblValidar.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		lblValidar.setForeground(Color.WHITE);
		
		
		btnValidar.add(imgValidar, BorderLayout.CENTER);
		imgValidar.setHorizontalAlignment(SwingConstants.CENTER);
		imgValidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Test_Passed_alt_50px.png")));
		
		JPanel ept_11 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) ept_11.getLayout();
		flowLayout_2.setVgap(0);
		flowLayout_2.setHgap(0);
		btnValidar.add(ept_11, BorderLayout.WEST);
		
		JPanel ept_12 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) ept_12.getLayout();
		flowLayout_4.setVgap(0);
		flowLayout_4.setHgap(0);
		btnValidar.add(ept_12, BorderLayout.EAST);
		
		JPanel ept_13 = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) ept_13.getLayout();
		flowLayout_5.setVgap(0);
		flowLayout_5.setHgap(0);
		btnValidar.add(ept_13, BorderLayout.NORTH);
		
		pnlMenu.add(btnCargar, BorderLayout.CENTER);
		
		btnCargar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				imgLoading.setHorizontalAlignment(SwingConstants.CENTER);
				imgLoading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loading.gif")));
				
				new Thread(new Runnable(){
				@Override
	        	public void run() {	
					infoApp.removeAll();	
					infoApp.add(imgLoading, BorderLayout.CENTER);
					infoApp.revalidate();
					consultaCargasIncompletas();
				}
			}).start();				
			}
		});
		
		btnCargar.setLayout(new BorderLayout(25, 5));		
		btnCargar.add(lblCargar, BorderLayout.SOUTH);
		
		
		imgCargar.setHorizontalAlignment(SwingConstants.CENTER);
		imgCargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_CSV_50px.png")));
		btnCargar.add(imgCargar, BorderLayout.CENTER);
		
		JPanel ept_14 = new JPanel();
		FlowLayout flowLayout_8 = (FlowLayout) ept_14.getLayout();
		flowLayout_8.setHgap(0);
		flowLayout_8.setVgap(0);
		btnCargar.add(ept_14, BorderLayout.NORTH);
		
		JPanel ept_15 = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) ept_15.getLayout();
		flowLayout_6.setVgap(0);
		flowLayout_6.setHgap(0);
		btnCargar.add(ept_15, BorderLayout.WEST);
		
		JPanel ept_16 = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) ept_16.getLayout();
		flowLayout_7.setVgap(0);
		flowLayout_7.setHgap(0);
		btnCargar.add(ept_16, BorderLayout.EAST);
		
		JPanel content = new JPanel();
		content.setAlignmentX(Component.RIGHT_ALIGNMENT);
		main.add(content, BorderLayout.CENTER);
		content.setLayout(new BorderLayout(0, 0));
		
		infoApp = new JPanel();
		infoApp.setBorder(new LineBorder(new Color(192, 192, 192)));
		infoApp.setBackground(new Color(255, 255, 255));
		FlowLayout fl_infoApp = (FlowLayout) infoApp.getLayout();
		content.add(infoApp, BorderLayout.CENTER);
		
		JPanel ept_5 = new JPanel();
		content.add(ept_5, BorderLayout.NORTH);
		
		JPanel ept_6 = new JPanel();
		content.add(ept_6, BorderLayout.WEST);
		
		JPanel ept_8 = new JPanel();
		content.add(ept_8, BorderLayout.SOUTH);
		
		JPanel ept_7 = new JPanel();
		content.add(ept_7, BorderLayout.EAST);
		
		JPanel footer = new JPanel();
		footer.setBackground(new Color(211,26,43));
		main.add(footer, BorderLayout.SOUTH);
		footer.setLayout(new BorderLayout(20, 5));
		
		JPanel ept_2 = new JPanel();
		ept_2.setBackground(new Color(211,26,43));
		footer.add(ept_2, BorderLayout.NORTH);
		
		JPanel copyright = new JPanel();
		copyright.setBackground(new Color(211,26,43));
		footer.add(copyright, BorderLayout.EAST);
		copyright.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCopyright = new JLabel("Derechos de autor ® Teclo Mexicana       ");
		lblCopyright.setForeground(new Color(255, 255, 255));
		
		copyright.add(lblCopyright);
		
		JPanel ept_1 = new JPanel();
		ept_1.setBackground(new Color(211,26,43));
		footer.add(ept_1, BorderLayout.SOUTH);
		
		infoApp.setLayout(new BorderLayout());
		infoApp.add(vlote);
		infoApp.revalidate();
		infoApp.repaint();
	}
	
	public void cambiarVistaValidacion(){
		infoApp.removeAll();
		infoApp.add(vlote);
		infoApp.revalidate();
		infoApp.repaint();
	}
	

	
	public void minimizeWin() {
		this.setState(MainFrame.ICONIFIED);
	}
	
	public void consultaCargasIncompletas(){				
				CargaCSVRestClientService obtenerDatosPT = new CargaCSVRestClientService();
				detalleLote = obtenerDatosPT.obtenerDetallePT("ErrorCarga");	
				if(!detalleLote.isEmpty()){
					JOptionPane.showMessageDialog(null, "Se continuara con la carga ","Carga de CSV", JOptionPane.INFORMATION_MESSAGE);
					   verBtnBuscar = false;
					   verBtnCargar = true;
					   clote.showBtn(verBtnBuscar, verBtnCargar);
					   clote.hayPendientes(detalleLote);
		
				}
				
				btnValidar.setBackground(new Color(240, 240, 240));
				lblValidar.setForeground(Color.BLACK);
				
				btnCargar.setBackground(new Color(211,26,43));
				lblCargar.setForeground(Color.WHITE);
				
				infoApp.removeAll();
				infoApp.add(clote);
			    infoApp.repaint();
     			infoApp.revalidate();
			}
	}
	

