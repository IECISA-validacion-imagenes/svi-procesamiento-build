package mx.com.teclo.base.cargaArchivosVO;

import java.util.List;

public class LotesyDetalleVO {
	
	private List<DetalleArchivoPTVO> detalleArchivosCSV;
    private PTArchivocsvVO listaDetalleElemento;
    
    
    public LotesyDetalleVO (List<DetalleArchivoPTVO> detalleArchivosCSV,  PTArchivocsvVO listaDetalleElemento){
    	this.detalleArchivosCSV= detalleArchivosCSV;
    	this.listaDetalleElemento = listaDetalleElemento;
    	
    }
    
	
	/**
	 * @return the detalleArchivosCSV
	 */
	public List<DetalleArchivoPTVO> getDetalleArchivosCSV() {
		return detalleArchivosCSV;
	}
	/**
	 * @param detalleArchivosCSV the detalleArchivosCSV to set
	 */
	public void setDetalleArchivosCSV(List<DetalleArchivoPTVO> detalleArchivosCSV) {
		this.detalleArchivosCSV = detalleArchivosCSV;
	}
	/**
	 * @return the listaDetalleElemento
	 */
	public PTArchivocsvVO getListaDetalleElemento() {
		return listaDetalleElemento;
	}
	/**
	 * @param listaDetalleElemento the listaDetalleElemento to set
	 */
	public void setListaDetalleElemento(PTArchivocsvVO listaDetalleElemento) {
		this.listaDetalleElemento = listaDetalleElemento;
	}
    

}
