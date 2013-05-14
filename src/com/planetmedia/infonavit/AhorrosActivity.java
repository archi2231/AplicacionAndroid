package com.planetmedia.infonavit;

import java.io.InputStream;

import com.planetmedia.infonavit.WSConsume.IFParentRequest;
import com.planetmedia.infonavit.WSConsume.IFParentResponse;
import com.planetmedia.infonavit.WSConsume.IFserviceManager;

import com.planetmedia.infonavit.datamodel.Ahorros;
import com.planetmedia.infonavit.datamodel.dto.UsuarioDTO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


public class AhorrosActivity extends Activity {

	private TextView txtNombreUsuario;
	private TextView txtNumeroCredito;
	private TextView txtDescripcion;
	
	private ProgressDialog dialogGettingAhorros;
	private Typeface eurostile;
	private static Ahorros _ahorros;
	private UsuarioDTO _user;
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.layout_ahorros);		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header2);    
		
		
		eurostile = Typeface.createFromAsset(getAssets(), "eurostile.ttf");
		
		txtNombreUsuario = (TextView)findViewById(R.id.txt_nombre_ahorro);
	    txtNumeroCredito = (TextView)findViewById(R.id.txt_credito_ahorro);
	    txtDescripcion = (TextView)findViewById(R.id.txt_mensaje_ahorro);
	   
		txtNombreUsuario.setTypeface(eurostile);
		txtNumeroCredito.setTypeface(eurostile);
		txtDescripcion.setTypeface(eurostile);
		
		_user = UsuarioDTO.getUniqueUser();
		
		if (_ahorros == null)
			consultaServicio();
		else 
			pintalayout();
	
	}
	
	private void consultaServicio(){
		dialogGettingAhorros = new ProgressDialog(AhorrosActivity.this);
        dialogGettingAhorros.setIcon(android.R.drawable.stat_notify_sync);
        dialogGettingAhorros.setTitle("Infonavit");
        dialogGettingAhorros.setMessage("Obteniendo mis ahorros...");
        dialogGettingAhorros.setCancelable(false);
       								
		dialogGettingAhorros.show();
		
		new Thread() {
        	public void run() {
	        	try{	        		        		    		        		
	        		    		        		
	        		IFParentRequest request = new IFParentRequest();
	        		//TODO: Hacerlo din‡mico
//	        		request.generaRequestRecaudacion(_user.getCCnumber());
	        		request.generaRequestRecaudacion("0911372667");
	        		
	        		IFserviceManager manager = new IFserviceManager();
	        		InputStream message = manager.execWebService(request);
	        		
	        		IFParentResponse response = new IFParentResponse(message);
	        		 _ahorros = response.obtenAhorro();	        		
	        		
//                    Message msgAhorros = new Message();
//                    msgAhorros.obj = "Listo"; 
//                    handlerRequestAhorros.sendMessage(msgAhorros);   
                    
	        	AhorrosActivity.this.runOnUiThread(new Runnable() {
	        	          public void run() {
	        	              pintalayout();
	        	          }
	        	      });
	        		
	        	} 
	        	catch (Exception e) {
	        		Log.d("conexion error: ", e.getMessage());
	        		e.printStackTrace();
	        	}    
	        	finally{
	        		dialogGettingAhorros.dismiss();  
	        	}
        	}
        }.start();
	}
	
	private void pintalayout() {
		
		UsuarioDTO user = UsuarioDTO.getUniqueUser();
		txtNombreUsuario.setText(user.getNombrePila());
		txtNumeroCredito.setText("CrŽdito:       " + user.getCCnumber());
		txtDescripcion.setText(_ahorros.getDescripcion());
		
		System.out.println("-->>" + _ahorros.getDescripcion());
		
	}

}
