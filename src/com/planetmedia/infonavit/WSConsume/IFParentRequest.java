package com.planetmedia.infonavit.WSConsume;

import com.planetmedia.infonavit.MainActivity;
import com.planetmedia.infonavit.R;

public class IFParentRequest {

	private String _soapAction;
	private StringBuilder _soapRequest;
	private String WSUrl;
	
	private String serviceUsername;
	private String servicePassword; 
	
	public IFParentRequest() {
		this._soapRequest = new StringBuilder();
		this.serviceUsername = MainActivity.getAppString(R.string.USERNAME);
		this.servicePassword = MainActivity.getAppString(R.string.PASSWORD);
	}

	public void generaRequestLineaCaptura(String numeroCredito) {
		
		getSoapRequest().append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bean=\"http://beanmodelo.www.infonavit.org.mx.sms\">");
		getSoapRequest().append("<soapenv:Header/>");
		getSoapRequest().append("<soapenv:Body>");
		getSoapRequest().append("<bean:OPLineaCaptura>");

		getSoapRequest().append("<bean:credito>" + numeroCredito + "</bean:credito>");
	    
		getSoapRequest().append("</bean:OPLineaCaptura>");
		getSoapRequest().append("</soapenv:Body>");
		getSoapRequest().append("</soapenv:Envelope>");
	    
	    this.setSoapAction("OPLineaCaptura");
	    this.WSUrl = MainActivity.getAppString(R.string.SMSWSIURL);
	    
	}

	public void generaRequestRecaudacion(String claveAhorro) {
		
		getSoapRequest().append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bean=\"http://beanmodelo.www.infonavit.org.mx.sms\">");
		getSoapRequest().append("<soapenv:Header><wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"><wsse:UsernameToken wsu:Id=\"UsernameToken-4\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsse:Username>"+this.serviceUsername+"</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"+this.servicePassword+"</wsse:Password><wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">H/VziPeeHV22zecxa4OkHg==</wsse:Nonce><wsu:Created>2013-03-13T16:53:40.492Z</wsu:Created></wsse:UsernameToken></wsse:Security></soapenv:Header>");
		getSoapRequest().append("<soapenv:Body>");
		getSoapRequest().append("<bean:OPRecaudacion>");
		    
		getSoapRequest().append("<bean:clave>" + claveAhorro + "</bean:clave>");
		    
		getSoapRequest().append("</bean:OPRecaudacion>");
		getSoapRequest().append("</soapenv:Body>");
		getSoapRequest().append("</soapenv:Envelope>");
		    
		this.setSoapAction("OPRecaudacion");
		this.WSUrl = MainActivity.getAppString(R.string.SMSWSIURL);

	}

	public void generaRequestSaldo(String claveSaldo, String tipoConsulta) {
		
		getSoapRequest().append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bean=\"http://beanmodelo.www.infonavit.org.mx.sms\">");
	    getSoapRequest().append("<soapenv:Header/>");
	    getSoapRequest().append("<soapenv:Body>");
	    getSoapRequest().append("<bean:OPSaldo>");
	    
	    getSoapRequest().append("<bean:tipoConsulta>" + tipoConsulta + "</bean:tipoConsulta>");
	    getSoapRequest().append("<bean:clave>" + claveSaldo + "</bean:clave>");
	    
	    getSoapRequest().append("</bean:OPSaldo>");
	    getSoapRequest().append("</soapenv:Body>");
	    getSoapRequest().append("</soapenv:Envelope>");
	    
	    this.setSoapAction("OPSaldo");
	    this.WSUrl = MainActivity.getAppString(R.string.SMSWSIURL);;
	    
	}

	public void generaRequestEstadoCuenta(String numeroCredito) {
		
		getSoapRequest().append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.AMPCECJL.FDECAWBE.Request.com\">");
		getSoapRequest().append("<soapenv:Header><wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"><wsse:UsernameToken wsu:Id=\"UsernameToken-4\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsse:Username>"+this.serviceUsername+"</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"+this.servicePassword+"</wsse:Password><wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">H/VziPeeHV22zecxa4OkHg==</wsse:Nonce><wsu:Created>2013-03-13T16:53:40.492Z</wsu:Created></wsse:UsernameToken></wsse:Security></soapenv:Header>");
		getSoapRequest().append("<soapenv:Body>");
	    getSoapRequest().append("<amp:AMPCECJLOperation>");
	    getSoapRequest().append("<amp:reg_entrada_eca>");

	    getSoapRequest().append("<amp:sen_num_credito>" + numeroCredito + "</amp:sen_num_credito>");

	    getSoapRequest().append("</amp:reg_entrada_eca>");
	    getSoapRequest().append("</amp:AMPCECJLOperation>");
	    getSoapRequest().append("</soapenv:Body>");
	    getSoapRequest().append("</soapenv:Envelope>");
	    
	    this.setSoapAction("AMPCECJLOperation");
	    this.WSUrl = MainActivity.getAppString(R.string.ESTADODECUENTAIMPRESOURL);
	}
	
	public void generaRequestBoletos(String tipoConsulta, String clave) {
	    
	    getSoapRequest().append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bean=\"http://beanmodelo.www.infonavit.org.mx.sms\">");
	    getSoapRequest().append("<soapenv:Header/>");
	    getSoapRequest().append("<soapenv:Body>");
	    getSoapRequest().append("<bean:OPBoletos>");
	    getSoapRequest().append("<bean:tipoConsulta>"+tipoConsulta+"</bean:tipoConsulta>");
	    getSoapRequest().append("<bean:clave>"+clave+"</bean:clave>");
	    getSoapRequest().append("</bean:OPBoletos>");
	    getSoapRequest().append("</soapenv:Body>");
	    getSoapRequest().append("</soapenv:Envelope>");
	    
	    this.setSoapAction("OPBoletos");
	    this.WSUrl = MainActivity.getAppString(R.string.SMSWSIURL);
	}

	public void generaRequestAccesoUsuario(String nombreUsuario, String password) {		
		
		getSoapRequest().append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mic=\"http://miCuenta.ldap.jaxws.infonavit.com.mx\">");	    
		getSoapRequest().append("<soapenv:Header><wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsse:UsernameToken wsu:Id=\"UsernameToken-2\"><wsse:Username>"+this.serviceUsername+"</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"+this.servicePassword+"</wsse:Password><wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">1ZEWIRYBjuba1Z9e1MjCJA==</wsse:Nonce><wsu:Created>2013-03-12T15:53:00.716Z</wsu:Created></wsse:UsernameToken></wsse:Security></soapenv:Header>");
		
		getSoapRequest().append("<soapenv:Body>");
	    getSoapRequest().append("<mic:autenticate>");
	    

	    getSoapRequest().append("<arg0>" + nombreUsuario + "</arg0>");
	    getSoapRequest().append("<arg1>" + password + "</arg1>");
	    
	    getSoapRequest().append("</mic:autenticate>");
	    getSoapRequest().append("</soapenv:Body>");
	    getSoapRequest().append("</soapenv:Envelope>");

	    this.setSoapAction("autenticate");
	    this.WSUrl = MainActivity.getAppString(R.string.MICUENTAURL);
	}
	
	public void generarRequestAcreditado(String numeroCredito) {
	    
	    getSoapRequest().append("<soapenv:Envelope xmlns:con=\"http://ConsultarAcreditadoMediationAls/ConsultarV5\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">");
	    getSoapRequest().append("<soapenv:Header><wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsse:UsernameToken wsu:Id=\"UsernameToken-2\"><wsse:Username>"+this.serviceUsername+"</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"+this.servicePassword+"</wsse:Password><wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">cxjzEWaPdzSnY3Bjl1bAKw==</wsse:Nonce><wsu:Created>2013-04-03T14:04:31.011Z</wsu:Created></wsse:UsernameToken></wsse:Security></soapenv:Header>");
	    getSoapRequest().append("<soapenv:Body>");
	    getSoapRequest().append("<con:consultarv5>");
	    getSoapRequest().append("<credito>"+numeroCredito+"</credito>");
	    getSoapRequest().append("</con:consultarv5>");
	    getSoapRequest().append("</soapenv:Body>");
	    getSoapRequest().append("</soapenv:Envelope>");
	    
	    this.setSoapAction("");
	    this.WSUrl = MainActivity.getAppString(R.string.ACREDITADOURL);
	}
	
	public void generaRequestCatalogoOficinas() {
	    
	    getSoapRequest().append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://servicio.consultaCesi.infonavit.org.mx\">");
	    getSoapRequest().append("<soapenv:Header/>");
	    getSoapRequest().append("<soapenv:Body>");	    
	    getSoapRequest().append("<ser:getCatalogoCesis/>");
	    getSoapRequest().append("</soapenv:Body>");
	    getSoapRequest().append("</soapenv:Envelope>");
	    
	    this.setSoapAction("getCatalogoCesis");
	    this.WSUrl = MainActivity.getAppString(R.string.OFICINASCATALOGOURL);
	}
	
	public void generaRequestCatalogoVersion(String idCatalogo) {
	    
	    getSoapRequest().append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://servicio.consultaCesi.infonavit.org.mx\">");
	    getSoapRequest().append("<soapenv:Header/>");
	    getSoapRequest().append("<soapenv:Body>");	    
	    getSoapRequest().append("<ser:getVersionCatalogoCesis>");
	    getSoapRequest().append("<idCatalogo>"+idCatalogo+"</idCatalogo>");
	    getSoapRequest().append("</ser:getVersionCatalogoCesis>");
	    getSoapRequest().append("</soapenv:Body>"); 
	    getSoapRequest().append("</soapenv:Envelope>");
	    
	    this.setSoapAction("getVersionCatalogoCesis");
	    this.WSUrl = MainActivity.getAppString(R.string.OFICINASCATALOGOURL);
	}
	
	public void generaRequestDetalleOficina(String idEstado, String idPlaza) {
	    
	    getSoapRequest().append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://servicio.consultaCesi.infonavit.org.mx\">");
	    getSoapRequest().append("<soapenv:Header/>");
	    getSoapRequest().append("<soapenv:Body>");	    
	    getSoapRequest().append("<ser:getDetalleCesi>");
	    getSoapRequest().append("<idDelEdo>"+idEstado+"</idDelEdo>");
	    getSoapRequest().append("<idPlaza>"+idPlaza+"</idPlaza>");
	    getSoapRequest().append("</ser:getDetalleCesi>");
	    getSoapRequest().append("</soapenv:Body>"); 
	    getSoapRequest().append("</soapenv:Envelope>");
	    
	    this.setSoapAction("getDetalleCesi");
	    this.WSUrl = MainActivity.getAppString(R.string.OFICINASCATALOGOURL);
	}

	public String getSoapAction() {
		return _soapAction;
	}

	public void setSoapAction(String _soapAction) {
		this._soapAction = _soapAction;
	}

	public StringBuilder getSoapRequest() {
		return _soapRequest;
	}

	public void setSoapRequest(StringBuilder _soapRequest) {
		this._soapRequest = _soapRequest;
	}
	
	public String getWSUrl(){
		return this.WSUrl;
	}

}
