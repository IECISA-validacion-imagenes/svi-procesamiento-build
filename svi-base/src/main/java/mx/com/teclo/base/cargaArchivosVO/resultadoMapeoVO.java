package mx.com.teclo.base.cargaArchivosVO;

import java.util.Date;

public class resultadoMapeoVO {
//	"Periodo","Punto Tactico","Archivos SCV", "Carpeta Imagenes "," Carpetas Siluetas","Fecha Creación"
	private String periodo;
	private String puntoTactico;
	private String archivoSCV;
	private String carpetaImg;
	private String carpetaSil;
	private Date fechaCracion;
	
	private String status;
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the periodo
	 */
	public String getPeriodo() {
		return periodo;
	}
	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	/**
	 * @return the puntoTactico
	 */
	public String getPuntoTactico() {
		return puntoTactico;
	}
	/**
	 * @param puntoTactico the puntoTactico to set
	 */
	public void setPuntoTactico(String puntoTactico) {
		this.puntoTactico = puntoTactico;
	}
	/**
	 * @return the archivoSCV
	 */
	public String getArchivoSCV() {
		return archivoSCV;
	}
	/**
	 * @param archivoSCV the archivoSCV to set
	 */
	public void setArchivoSCV(String archivoSCV) {
		this.archivoSCV = archivoSCV;
	}
	/**
	 * @return the carpetaImg
	 */
	public String getCarpetaImg() {
		return carpetaImg;
	}
	/**
	 * @param carpetaImg the carpetaImg to set
	 */
	public void setCarpetaImg(String carpetaImg) {
		this.carpetaImg = carpetaImg;
	}
	/**
	 * @return the carpetaSil
	 */
	public String getCarpetaSil() {
		return carpetaSil;
	}
	/**
	 * @param carpetaSil the carpetaSil to set
	 */
	public void setCarpetaSil(String carpetaSil) {
		this.carpetaSil = carpetaSil;
	}
	/**
	 * @return the fechaCracion
	 */
	public Date getFechaCracion() {
		return fechaCracion;
	}
	/**
	 * @param fechaCracion the fechaCracion to set
	 */
	public void setFechaCracion(Date fechaCracion) {
		this.fechaCracion = fechaCracion;
	}
	
	
}
