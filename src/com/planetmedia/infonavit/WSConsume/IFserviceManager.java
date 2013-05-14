package com.planetmedia.infonavit.WSConsume;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class IFserviceManager {
	
	private InputStream contentBody;
	
	public IFserviceManager() {
		
	}
	
	public InputStream execWebService(IFParentRequest request){			
		
		
		DefaultHttpClient client = new DefaultHttpClient();
		DefaultHttpClient httpclient = new DefaultHttpClient();
		
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory ssf = new EasySSLSocketFactory(trustStore);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	        ClientConnectionManager ccm = client.getConnectionManager();
	        SchemeRegistry sr = ccm.getSchemeRegistry();
	        sr.register(new Scheme("https", ssf, 443));
	        
	        httpclient = new DefaultHttpClient(ccm, client.getParams());
			
		} catch (KeyStoreException e) {			
			e.printStackTrace();
		} catch (KeyManagementException e) {			
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {			
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
       
        HttpPost httppost = new HttpPost(request.getWSUrl());             
        
        httppost.addHeader("Accept", "text/xml");
        httppost.addHeader("Content-Type", "text/xml;charset=UTF-8");
        httppost.addHeader("SOAPAction", request.getSoapAction());
        
        //httppost.addHeader("Host", "sms.infonavit.org.mx:80");
        //httppost.addHeader("Content-Length", "" + theRequest.getSoapRequest().length());        
        
		try {
			HttpEntity entity = new StringEntity(request.getSoapRequest().toString());
			httppost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		          
        try {
			HttpResponse response = httpclient.execute(httppost);					
			this.contentBody = response.getEntity().getContent();
			//Log.d("request ", request.getSoapRequest().toString()); 
			//Log.d("respuesta ", convertStreamToString(this.contentBody));
			return this.contentBody;
	                
		} catch (ClientProtocolException e) {		
			e.printStackTrace();
		} catch (IOException e) {		
			e.printStackTrace();
		}
        
        return null;
	}

	public InputStream getResponse(){
		return this.contentBody;
	}
	
	private static String convertStreamToString(InputStream is) {
		
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	
	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}

}
