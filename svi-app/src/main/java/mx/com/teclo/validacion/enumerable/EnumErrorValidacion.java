package mx.com.teclo.validacion.enumerable;

public enum EnumErrorValidacion {

	NAME(1L, "El nombre esta incoscistente"), 
	NOT_FOUND_FOLDER(2L, "No se encontro la carpeta correspondiente"),
	EMPTY_FOLDER(3L, "La carpeta esta vacia"),
	CSV_NOT_FOUND(4L, "Se encontraron mas carpetas de imagenes que CSV"),
	FILES_CSV(5L, "Inconsistencias en los archivos CSV"),
	IMAGES(6L, "Inconsistencias en las carpetas de imagenes"),
	OVERSIZE_IMAGES(7L, "Falta archivo CSV"),
	CSV_PT_OR_PERIOD(8L, "El CSV no corresponde al Punto Tactico o el periodo");
	
	private long idError;
	private String mensajeError;
	
	private EnumErrorValidacion(long idError, String mensajeError){
		this.idError = idError;
		this.mensajeError = mensajeError;
	}
	

	public long getIdError() {
		return idError;
	}

	public void setIdError(long idError) {
		this.idError = idError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
}
