package com.planetmedia.infonavit;

import java.io.InputStream;
import java.util.ArrayList;

import com.planetmedia.infonavit.WSConsume.IFParentRequest;
import com.planetmedia.infonavit.WSConsume.IFParentResponse;
import com.planetmedia.infonavit.WSConsume.IFserviceManager;
import com.planetmedia.infonavit.datamodel.ResumenCredito;
import com.planetmedia.infonavit.datamodel.dto.UsuarioDTO;
import com.planetmedia.infonavit.listadapters.ResumenCreditoListAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.IntentSender.OnFinished;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ResumenCreditoActivity extends Activity{
	
	private Handler handlerRequestResumen;
	
	private ProgressDialog dialogGettingResumen;
	
	private ArrayList<String> groups;
	private ArrayList<ArrayList<ArrayList<String>>> childs;
	private ExpandableListAdapter adapterResumenCredito;
	
	private TextView txtNombre;
	private TextView txtNumero;
	private ExpandableListView expandibleResumenCredito;
	
	UsuarioDTO user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		 
		setContentView(R.layout.layout_resumencredito_activity);		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header);    
		
		final UsuarioDTO user = UsuarioDTO.getUniqueUser();	
//		user.seek("1");
		
		txtNombre = (TextView)findViewById(R.id.txt_resumen_credito_nombre);
	    txtNumero = (TextView)findViewById(R.id.txt_resumen_credito_numero);	    
		expandibleResumenCredito = (ExpandableListView)findViewById(R.id.lista_resumen_credito);
		
		txtNombre.setText(user.getNombrePila());
		txtNumero.setText("Crédito: " + user.getCCnumber());
		
		handlerRequestResumen = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	        	ResumenCredito resumen = (ResumenCredito)msg.obj;
	        	
	        	despliegaResumenCredito(resumen);
	        	
	            Toast.makeText(ResumenCreditoActivity.this, resumen.getNumCredito(), Toast.LENGTH_SHORT).show();	 
	            	            
	        }
	    };	    	
	    
	    lanzaServicioResumenCredito();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(dialogGettingResumen.isShowing())
			dialogGettingResumen.dismiss();
	}
	
	private void lanzaServicioResumenCredito(){
				
		dialogGettingResumen = new ProgressDialog(ResumenCreditoActivity.this);
        dialogGettingResumen.setIcon(android.R.drawable.stat_notify_sync);
        dialogGettingResumen.setTitle("Infonavit");
        dialogGettingResumen.setMessage("Obteniendo resumen de mi credito...");
        dialogGettingResumen.setCancelable(false);
       								
		dialogGettingResumen.show();
		final Message msgResumen = new Message();
		
		new Thread() {
        	public void run() {
	        	try{	        		        		    		        		
	        		    		        		
	        		IFParentRequest request = new IFParentRequest();
	        		request.generarRequestAcreditado("0411001085");
	        		
	        		IFserviceManager manager = new IFserviceManager();
	        		InputStream message = manager.execWebService(request);
	        		
	        		IFParentResponse response = new IFParentResponse(message);
	        		ResumenCredito resumen = response.obtenResumenDeCredito();
	        		    		        	    		        		    		        		    		        				                    
	        		resumen.seek("1");
	        		resumen.print("1");
                    
                    
                    msgResumen.obj = resumen; 
                    handlerRequestResumen.sendMessage(msgResumen);    		        		   		        		    		        	    		        		  		       
	        	} 
	        	catch (Exception e) {	        		
	        		e.printStackTrace();
	        		msgResumen.obj = "Sin conexión a internet!"; 
	        		handlerRequestResumen.sendMessage(msgResumen);
	        	}    
	        	finally{
	        		dialogGettingResumen.dismiss();  
	        	}
        	}
        }.start();
	}
	
	private void despliegaResumenCredito(ResumenCredito resumen){
		
		
		groups = new ArrayList<String>();
    	childs = new ArrayList<ArrayList<ArrayList<String>>>();
 
    	groups.add("El monto de mi deuda");
        groups.add("¿Cómo y cúanto debo pagar?");
        
        //HAPPY PATH
        resumen.setTipoEscenario("02");				
		
		if(resumen.getTipoEscenario().equals("01")){
			
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Tu debes en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoAcreditadoPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Tu debes en VSM");
	        childs.get(0).get(2).add(resumen.getSaldoAcreditadoVSM());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("El Infonavit pagará en pesos");
	        childs.get(0).get(3).add(resumen.getSaldoInfonavitPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(4).add("El Infonavit pagará en VSM");
	        childs.get(0).get(4).add(resumen.getSaldoInfonavitVSM());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(5).add("Saldo total en pesos");
	        childs.get(0).get(5).add(resumen.getSaldoTotalPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(6).add("Saldo total en VSM");
	        childs.get(0).get(6).add(resumen.getSaldoTotalVSM());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(7).add("Fecha de inicio de la REES");
	        childs.get(0).get(7).add(resumen.getFechaDeReestructura());
	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de Pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Total a descontar de sueldo mensual en pesos");
	        childs.get(1).get(1).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Total a descontar de sueldo mensual en VSM");
	        childs.get(1).get(2).add(resumen.getPagoEsteMesVSM());			 	        
		}
		
		else if(resumen.getTipoEscenario().equals("02")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Tu debes");
	        childs.get(0).get(1).add(resumen.getSaldoAcreditadoPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("El Infonavit pagará");
	        childs.get(0).get(2).add(resumen.getSaldoInfonavitPesos());	       
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("Saldo total");
	        childs.get(0).get(3).add(resumen.getSaldoTotalPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(4).add("Fecha de inicio de la REES");
	        childs.get(0).get(4).add(resumen.getFechaDeReestructura());
	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de Pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Total a descontar de sueldo mensual");
	        childs.get(1).get(1).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Linea de Captura");
	        childs.get(1).get(2).add(resumen.getLineaCaptura());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Responsable de la Información");
	        childs.get(1).get(3).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("03")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoTotalPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Saldo total en VSM");
	        childs.get(0).get(2).add(resumen.getSaldoTotalVSM());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Pago este mes pesos");
	        childs.get(1).get(1).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Pago este mes VSM");
	        childs.get(1).get(2).add(resumen.getPagoEsteMesVSM());
		}
		
		else if(resumen.getTipoEscenario().equals("04")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total");
	        childs.get(0).get(1).add(resumen.getSaldoTotalPesos());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de Pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Total a descontar de sueldo mensual");
	        childs.get(1).get(1).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("05")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Tu debes en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoAcreditadoPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Tu debes en VSM");
	        childs.get(0).get(2).add(resumen.getSaldoAcreditadoVSM());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("El Infonavit pagará en pesos");
	        childs.get(0).get(3).add(resumen.getSaldoInfonavitPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(4).add("El Infonavit pagará en VSM");
	        childs.get(0).get(4).add(resumen.getSaldoAcreditadoVSM());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(5).add("Saldo total en pesos");
	        childs.get(0).get(5).add(resumen.getSaldoTotalPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(6).add("Saldo total en VSM");
	        childs.get(0).get(6).add(resumen.getSaldoTotalVSM());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(7).add("Fecha de inicio de la reestructura");
	        childs.get(0).get(7).add(resumen.getFechaDeReestructura());
	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Pago de mensualidad en pesos");
	        childs.get(1).get(1).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Pago de mensualidad en VSM");
	        childs.get(1).get(2).add(resumen.getPagoEsteMesVSM());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Linea de captura");
	        childs.get(1).get(3).add(resumen.getLineaCaptura());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Fecha limite de pago");
	        childs.get(1).get(4).add(resumen.getFechaLimitePago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(5).add("Responsable de la información");
	        childs.get(1).get(5).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("06")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Tu debes en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoAcreditadoPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Tu debes en VSM");
	        childs.get(0).get(2).add(resumen.getSaldoAcreditadoVSM());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("El Infonavit pagará en pesos");
	        childs.get(0).get(3).add(resumen.getSaldoInfonavitPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(4).add("El Infonavit pagará en VSM");
	        childs.get(0).get(4).add(resumen.getSaldoInfonavitVSM());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(5).add("Saldo total en pesos");
	        childs.get(0).get(5).add(resumen.getSaldoTotalPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(6).add("Saldo total en VSM");
	        childs.get(0).get(6).add(resumen.getSaldoTotalVSM());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(7).add("Fecha de inicio de la reestructura");
	        childs.get(0).get(7).add(resumen.getFechaDeReestructura());
	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Numero de pagos pendientes");
	        childs.get(1).get(1).add(resumen.getMesesOmisos());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Pago de mensualidad en pesos");
	        childs.get(1).get(2).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Pago de mensualidad en VSM");
	        childs.get(1).get(3).add(resumen.getPagoEsteMesVSM());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Total a pagar para estar al corriente en pesos");
	        childs.get(1).get(4).add(resumen.getMtoPagPendientesD());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(5).add("Total a pagar para estar al corriente en VSM");
	        childs.get(1).get(5).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(6).add("Fecha limite de pago");
	        childs.get(1).get(6).add(resumen.getFechaLimitePago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(7).add("Mensaje");
	        childs.get(1).get(7).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(8).add("Responsable de la Información");
	        childs.get(1).get(8).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("07")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Tu debes");
	        childs.get(0).get(1).add(resumen.getSaldoAcreditadoPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("El Infonavit pagará");
	        childs.get(0).get(2).add(resumen.getSaldoInfonavitPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("Saldo total");
	        childs.get(0).get(3).add(resumen.getSaldoTotalPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(4).add("Fecha de inicio de la reestructura");
	        childs.get(0).get(4).add(resumen.getFechaDeReestructura());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Pago de mensualidad");
	        childs.get(1).get(1).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Fecha limite de pago");
	        childs.get(1).get(2).add(resumen.getFechaLimitePago());
		}
		
		else if(resumen.getTipoEscenario().equals("08")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Tu debes");
	        childs.get(0).get(1).add(resumen.getSaldoAcreditadoPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("El Infonavit pagará");
	        childs.get(0).get(2).add(resumen.getSaldoInfonavitPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("Saldo total");
	        childs.get(0).get(3).add(resumen.getSaldoTotalPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(4).add("Fecha de inicio de la reestructura");
	        childs.get(0).get(4).add(resumen.getFechaDeReestructura());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de Pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Numero de pagos pendientes");
	        childs.get(1).get(1).add(resumen.getMesesOmisos());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Pago de mensualidad");
	        childs.get(1).get(2).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Total a pagar para estar al corriente");
	        childs.get(1).get(3).add(resumen.getMtoPagPendientesD());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Fecha limite de pago");
	        childs.get(1).get(4).add(resumen.getFechaLimitePago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(5).add("Mensaje");
	        childs.get(1).get(5).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("09")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());	        
	        childs.get(0).get(1).add("Saldo total en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoTotalPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Saldo total en VSM");
	        childs.get(0).get(2).add(resumen.getSaldoTotalVSM());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Pago de mensualidad en pesos");
	        childs.get(1).get(1).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Pago de mensualidad en VSM");
	        childs.get(1).get(2).add(resumen.getPagoEsteMesVSM());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Linea de captura");
	        childs.get(1).get(3).add(resumen.getLineaCaptura());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Fecha límite de pago");
	        childs.get(1).get(4).add(resumen.getFechaLimitePago());	        
		}
		
		else if(resumen.getTipoEscenario().equals("10")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoTotalPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Saldo total en VSM");
	        childs.get(0).get(2).add(resumen.getSaldoTotalVSM());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Numero de pagos pendientes");
	        childs.get(1).get(1).add(resumen.getMesesOmisos());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Pago de mensualidad pesos");
	        childs.get(1).get(2).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Pago de mensualidad VSM");
	        childs.get(1).get(3).add(resumen.getPagoEsteMesVSM());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Total a pagar para estar al corriente");
	        childs.get(1).get(4).add(resumen.getMtoPagPendientesD());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(5).add("Fecha límite de pago");
	        childs.get(1).get(5).add(resumen.getFechaLimitePago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(6).add("Mensaje");
	        childs.get(1).get(6).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(7).add("Responsable de la información");
	        childs.get(1).get(7).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("11")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoTotalPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Saldo total en VSM");
	        childs.get(0).get(2).add(resumen.getSaldoTotalVSM());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Factor de Descuento Mensual");
	        childs.get(1).get(1).add(resumen.getFactoresReaPesos());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Pago de mensualidad pesos");
	        childs.get(1).get(2).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Fecha límite de pago");
	        childs.get(1).get(3).add(resumen.getFechaLimitePago());
		}
		
		else if(resumen.getTipoEscenario().equals("12")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoAcreditadoPesos());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Número de pagos pendientes");
	        childs.get(1).get(1).add(resumen.getMesesOmisos());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Monto de pagos pendientes");
	        childs.get(1).get(2).add(resumen.getSaldoOmisosPesos());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Pago de mensualidad de");
	        childs.get(1).get(3).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Total a pagar para estar al corriente");
	        childs.get(1).get(4).add(resumen.getPagoEsteMesVSM());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(5).add("Fecha límite de pago");
	        childs.get(1).get(5).add(resumen.getFechaLimitePago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(6).add("Mensaje");
	        childs.get(1).get(6).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(7).add("Responsable de la Información");
	        childs.get(1).get(7).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("13")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoTotalPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Saldo total en VSM");
	        childs.get(0).get(2).add(resumen.getSaldoTotalVSM());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de Pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Fecha de término de prórroga");
	        childs.get(1).get(1).add(resumen.getFchFinUltProrroga());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Mensaje Informativo Personalizado");
	        childs.get(1).get(2).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Pago mensual en pesos");
	        childs.get(1).get(3).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Pago mensual en VSM");
	        childs.get(1).get(4).add(resumen.getPagoEsteMesVSM());
		}
		
		else if(resumen.getTipoEscenario().equals("14")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoTotalPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Saldo total en VSM");
	        childs.get(0).get(2).add(resumen.getSaldoTotalVSM());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Fecha de término de prórroga");
	        childs.get(1).get(1).add(resumen.getFchFinUltProrroga());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Mensaje Informativo Personalizado");
	        childs.get(1).get(2).add("pndiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Pago mensual pesos");
	        childs.get(1).get(3).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Pago mensual VSM");
	        childs.get(1).get(4).add(resumen.getPagoEsteMesVSM());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(5).add("Mensaje Informativo General");
	        childs.get(1).get(5).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("15")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total");
	        childs.get(0).get(1).add(resumen.getSaldoTotalPesos());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de Pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Fecha de término de prórroga");
	        childs.get(1).get(1).add(resumen.getFchFinUltProrroga());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Mensaje Informativo Personalizado");
	        childs.get(1).get(2).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Pago mensual");
	        childs.get(1).get(3).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Mensaje Informativo General");
	        childs.get(1).get(4).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("16")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total");
	        childs.get(0).get(1).add(resumen.getSaldoTotalPesos());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de Pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Fecha de término de prórroga");
	        childs.get(1).get(1).add(resumen.getFchFinUltProrroga());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Mensaje Informativo Personalizado");
	        childs.get(1).get(2).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Pago mensual");
	        childs.get(1).get(3).add(resumen.getPagoEsteMesPes());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Mensaje Informativo General");
	        childs.get(1).get(4).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("17")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total");
	        childs.get(0).get(1).add(resumen.getSaldoTotalPesos());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de Pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Total a descontar de sueldo mensual");
	        childs.get(1).get(1).add(resumen.getTotalDescAlDia());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Mensaje Informativo");
	        childs.get(1).get(2).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("18")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total");
	        childs.get(0).get(1).add(resumen.getSaldoTotalPesos());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de Pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Número de pagos pendientes");
	        childs.get(1).get(1).add(resumen.getMesesOmisos());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Monto de pagos pendientes");
	        childs.get(1).get(2).add(resumen.getMesesOmisos());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Fecha Límite de pago");
	        childs.get(1).get(3).add(resumen.getFechaLimitePago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Mensaje Informativo Personalizado");
	        childs.get(1).get(4).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(5).add("Mensaje Informativo general");
	        childs.get(1).get(5).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("19")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        
	        childs.add(new ArrayList<ArrayList<String>>());   		        	        
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Mensaje Informativo Personalizado");
	        childs.get(1).get(0).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Mensaje Informativo general");
	        childs.get(1).get(1).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("20")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        
	        childs.add(new ArrayList<ArrayList<String>>());   		        	        
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Mensaje Informativo Personalizado");
	        childs.get(1).get(0).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Mensaje Informativo general");
	        childs.get(1).get(1).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("21")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        
	        childs.add(new ArrayList<ArrayList<String>>());   		        	        
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Mensaje segun caso");
	        childs.get(1).get(0).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("22")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Tu pago mensual");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Tu saldo");
	        childs.get(0).get(1).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Mensaje Informativo");
	        childs.get(0).get(2).add("pendiente");	 
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("Dia de meses a pagar");
	        childs.get(0).get(3).add("pendiente");	 
	        
	        childs.add(new ArrayList<ArrayList<String>>());   		        	        
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("");
	        childs.get(1).get(0).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("23")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        
	        childs.add(new ArrayList<ArrayList<String>>());   		        	        
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Mensaje Informativo Personalizado");
	        childs.get(1).get(0).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Mensaje Informativo general");
	        childs.get(1).get(1).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("99")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Exepciones");
	        childs.get(0).get(0).add("pendiente");
	        
	        childs.add(new ArrayList<ArrayList<String>>());   		        	        
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Mensaje segun caso");
	        childs.get(1).get(0).add("pendiente");
		}
		
		adapterResumenCredito = new ResumenCreditoListAdapter(ResumenCreditoActivity.this, groups, childs);      		        	            		        	          
		expandibleResumenCredito.setAdapter(adapterResumenCredito);
		
		setListViewHeightBasedOnChildren(expandibleResumenCredito);   
	}
	
	private void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		
		float scale = getResources().getDisplayMetrics().density; 
		int pad = (int) (11 * scale + 0.5f);
		
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + pad;
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
}


