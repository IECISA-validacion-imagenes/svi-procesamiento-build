package mx.com.teclo.validacion.frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import mx.com.teclo.validacion.functions.ValidacionFunction;
import mx.com.teclo.validacion.vo.LotesVO;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import mx.com.teclo.base.frames.CircleProgressPanel;
import java.awt.FlowLayout;
import java.awt.CardLayout;

public class ValidacionFrame extends JPanel {

	private JPanel main;
	
	private String idUsuario;
	
	private static ValidacionFunction validacionFunction;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public ValidacionFrame(String idUsuario) {
		this();
		this.idUsuario = idUsuario;
	}
	
	public ValidacionFrame() {
		validacionFunction = ValidacionFunction.getInstance();
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		JPanel pnl_Valida = new JPanel();
		add(pnl_Valida, BorderLayout.SOUTH);
		pnl_Valida.setLayout(new BorderLayout(0, 0));
		
		final JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		pnl_Valida.add(panel, BorderLayout.NORTH);
		
		final JPanel btnValidar = new JPanel();
		btnValidar.setBackground(new Color(211,26,43));
		panel.add(btnValidar);
		
		JLabel lblValida = new JLabel("    Buscar Directorio    ");
		lblValida.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblValida.setForeground(Color.WHITE);
		btnValidar.add(lblValida);
		final JLabel imgLoading = new JLabel("");
		final JPanel btnConfirma = new JPanel();
		final JPanel pnl_Incidencias = new JPanel();
		final JScrollPane scrollPane = new JScrollPane();
		final JLabel imgHome = new JLabel("");
		pnl_Incidencias.setBackground(Color.WHITE);
		btnConfirma.setVisible(false);
		btnConfirma.setBackground(new Color(211,26,43));
		panel.add(btnConfirma);
		btnConfirma.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pnl_Incidencias.removeAll();
				pnl_Incidencias.add(imgLoading, BorderLayout.CENTER);
				btnValidar.setVisible(false);
				btnConfirma.setVisible(false);
				//scrollPane.setVisible(false);
				//Mandarlos al servicio
				imgLoading.setVisible(true);
				validacionFunction.enviarDatosARest(idUsuario, pnl_Incidencias,imgHome, imgLoading, btnValidar);
				//pnl_Incidencias.setVisible(false);
			};
			
			@Override
			public void mousePressed(MouseEvent e) {
				btnConfirma.setBackground(new Color(172, 26, 43));
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				btnConfirma.setBackground(new Color(211,26,43));
			}
		});
		
		JLabel lblConfirma = new JLabel("    Confirmar    ");
		lblConfirma.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblConfirma.setForeground(Color.WHITE);
		btnConfirma.add(lblConfirma);
		
		final JPanel btnIncidencia = new JPanel();
		btnIncidencia.setVisible(false);
		btnIncidencia.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnIncidencia.setBackground(new Color(211,26,43));
		btnIncidencia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(validacionFunction.getRoot());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				btnIncidencia.setBackground(new Color(172, 26, 43));
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				btnIncidencia.setBackground(new Color(211,26,43));
			}
		});
		panel.add(btnIncidencia);
		
		JLabel lblIncidencia = new JLabel("Abrir en Explorador");
		lblIncidencia.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIncidencia.setForeground(Color.WHITE);
		btnIncidencia.add(lblIncidencia);
		add(pnl_Incidencias, BorderLayout.CENTER);
		pnl_Incidencias.setLayout(new BorderLayout(0, 0));
		
		scrollPane.setVisible(false);
		pnl_Incidencias.add(scrollPane, BorderLayout.SOUTH);
		scrollPane.setBorder(null);
		scrollPane.setBackground(new Color(255, 255, 255));
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		pnl_Incidencias.add(imgHome, BorderLayout.CENTER);
		imgHome.setAlignmentX(Component.CENTER_ALIGNMENT);
		imgHome.setHorizontalAlignment(SwingConstants.CENTER);
		imgHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/coverapptmp.png")));
		imgLoading.setHorizontalAlignment(SwingConstants.CENTER);
		imgLoading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loading.gif")));
		imgLoading.setVisible(false);
		
		btnValidar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				imgLoading.setVisible(false);
				pnl_Incidencias.add(imgHome);
				pnl_Incidencias.revalidate();
				scrollPane.setVisible(false);
				imgHome.setVisible(true);
				btnConfirma.setVisible(false);
				btnIncidencia.setVisible(false);
				jfc.setDialogTitle("Selecciona la carpeta de lotes a cargar: ");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
				int returnValue = jfc.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					if (jfc.getSelectedFile().isDirectory()) {
						imgHome.setVisible(false);
						((DefaultTableModel)table.getModel()).setRowCount(0);
						validacionFunction.validarDirectorioPuntoTactico(jfc.getSelectedFile(), scrollPane, table, btnConfirma, btnIncidencia, pnl_Incidencias);
					}
				}
			};
			
			@Override
			public void mousePressed(MouseEvent e) {
				btnValidar.setBackground(new Color(172, 26, 43));
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				btnValidar.setBackground(new Color(211,26,43));
			}
		});
	}
	
	public JPanel getMain(){
		return main;
	}
}
