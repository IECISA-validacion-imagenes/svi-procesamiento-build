package mx.com.teclo.base.cargaArchivosVO;

import java.util.List;

public class ArchivosCSVyDetallesVO {
	
	private ArchivosPTCSVO tabla2;
	private List<DetalleArchivoPTVO> contenidoCSV;
	
	public ArchivosCSVyDetallesVO (ArchivosPTCSVO tabla2, List<DetalleArchivoPTVO> contenidoCSV){
		this.tabla2 = tabla2;
		this.contenidoCSV = contenidoCSV;	
	}

	/**
	 * @return the tabla2
	 */
	public ArchivosPTCSVO getTabla2() {
		return tabla2;
	}

	/**
	 * @param tabla2 the tabla2 to set
	 */
	public void setTabla2(ArchivosPTCSVO tabla2) {
		this.tabla2 = tabla2;
	}

	/**
	 * @return the contenidoCSV
	 */
	public List<DetalleArchivoPTVO> getContenidoCSV() {
		return contenidoCSV;
	}

	/**
	 * @param contenidoCSV the contenidoCSV to set
	 */
	public void setContenidoCSV(List<DetalleArchivoPTVO> contenidoCSV) {
		this.contenidoCSV = contenidoCSV;
	}

	
}
