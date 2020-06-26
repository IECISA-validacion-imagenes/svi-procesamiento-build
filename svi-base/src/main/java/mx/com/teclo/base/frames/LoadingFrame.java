/*package mx.com.teclo.base.frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Panel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

public class LoadingFrame extends JFrame {

	private JPanel contentPane;
	CustomPanel2 jprogress = new CustomPanel2();
	
	JPanel btn_close = new JPanel();
	JLabel lbl_msj = new JLabel("Mensaje");
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoadingFrame frame = new LoadingFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 
	public LoadingFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		setBounds(100, 100, 602, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		jprogress.setBounds(140, 65, 308, 260);
		contentPane.add(jprogress);
		btn_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				closeWindow();
			}
		});
		btn_close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		btn_close.setBounds(518, 11, 74, 74);
		contentPane.add(btn_close);
		btn_close.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 74, 50);
		label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Cancel_50px.png")));
		btn_close.add(label);
		
		JLabel lblCerrar = new JLabel("Cerrar");
		lblCerrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblCerrar.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCerrar.setBounds(0, 49, 74, 14);
		btn_close.add(lblCerrar);
		
		
		lbl_msj.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_msj.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_msj.setBounds(10, 344, 582, 37);
		contentPane.add(lbl_msj);
		
		CustomPanel2 customPanel2 = new CustomPanel2();
		customPanel2.setBounds(0, 0, 508, 333);
		contentPane.add(customPanel2);
		
		btn_close.setVisible(false);
		lbl_msj.setVisible(false);
	}
	
	public void updateGraphics(int num) {		
		 jprogress.UpdateProgress(num);
		 jprogress.repaint();
	 }
	
	public void showMensaje(String msj) {
		lbl_msj.setText(msj);
		lbl_msj.setVisible(true);
	}
	
	public void closeWindow() {
		this.dispose();
	}
}*/
