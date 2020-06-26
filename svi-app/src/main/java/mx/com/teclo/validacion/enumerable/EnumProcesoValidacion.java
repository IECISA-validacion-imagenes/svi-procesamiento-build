package mx.com.teclo.validacion.enumerable;

public enum EnumProcesoValidacion {

	VALIDA_ROOT(1L, "Validando ubicacion proporcionada"), REUBICA_ROOT(2L, "Reubicando directorio"),
	MAPEANDO(3L, "Mapeando y validando directorios y carpetas");
	
	private Long idProceso;
	private String nbProceso;
	
	private EnumProcesoValidacion(Long idProceso, String nbProceso){
		this.idProceso = idProceso;
		this.nbProceso = nbProceso;
	}

	public Long getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}
	public String getNbProceso() {
		return nbProceso;
	}
	public void setNbProceso(String nbProceso) {
		this.nbProceso = nbProceso;
	}
}
