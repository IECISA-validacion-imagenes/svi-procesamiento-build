package mx.com.teclo.base.appconfig;

public class AppConfiguracion {
	
	private static AppConfiguracion instancia = null;
	
	private String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpZWNzaXNhcGkiLCJmdW5jaW9uUGFzc3dvcmQiOiJxV1I0Tlh6PyIsImNyZWF0ZWQiOjE1NTExMjMzMzg5OTh9.wWC6KRP7vFiKzaCzgbTtEJWMdXu97WHGOHBkNsfSOKnEo0CHad9K1-Ww7DG4uxG1aV0CMCyUFBlszxNytapFNw";
	private String baseurl="http://localhost:9080/svi-desk-wsw";
	private String baseAppurl="http://localhost:9080/svi-app_1.0.0";
	
	private AppConfiguracion(String baseUrl, String baseAppUrl){
		this.baseurl = baseUrl;
		this.baseAppurl = baseAppUrl;
	}
	
	public static AppConfiguracion configInstance(String url, String appUrl){
		if(instancia == null){
			instancia = new AppConfiguracion(url, appUrl);
		}
		return instancia;
	}
	
	public static AppConfiguracion getInstance(){
		return instancia;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getBaseurl() {
		return baseurl;
	}
	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}
	public String getBaseAppurl() {
		return baseAppurl;
	}
	public void setBaseAppurl(String baseAppurl) {
		this.baseAppurl = baseAppurl;
	}
	
	
}
