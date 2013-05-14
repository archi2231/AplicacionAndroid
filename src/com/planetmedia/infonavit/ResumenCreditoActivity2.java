package com.planetmedia.infonavit;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.planetmedia.infonavit.WSConsume.IFParentRequest;
import com.planetmedia.infonavit.WSConsume.IFParentResponse;
import com.planetmedia.infonavit.WSConsume.IFserviceManager;
import com.planetmedia.infonavit.datamodel.ResumenCredito;
import com.planetmedia.infonavit.datamodel.dto.UsuarioDTO;

public class ResumenCreditoActivity2 extends Activity{
	
	private boolean isMontoExpanded;
	private boolean isCuantoExpanded;
	
	private Handler handlerRequestResumen;
	
	private ProgressDialog dialogGettingResumen;
	
	UsuarioDTO user;
	
	private ArrayList<ArrayList<ArrayList<String>>> childs;
	
	private LinearLayout listaExpandibleMonto;
	private LinearLayout listaExpandibleDeuda;
	
	private TextView txtNombre;
	private TextView txtNumero;
	
	private ScrollView sv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		 
		setContentView(R.layout.layout_resumencredito_activity2);		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header);    
		
		final UsuarioDTO user = UsuarioDTO.getUniqueUser();	
//		user.seek("1");

		txtNombre = (TextView)findViewById(R.id.txt_resumen_credito_nombre);
	    txtNumero = (TextView)findViewById(R.id.txt_resumen_credito_numero);	    
		
		txtNombre.setText(user.getNombrePila());
		txtNumero.setText("CrŽdito: " + user.getCCnumber());
		
		final ImageButton btnMonto = (ImageButton)findViewById(R.id.btn_pagos_head);
		final ImageButton btnDeuda = (ImageButton)findViewById(R.id.btn_cuanto_pagar);
		final ImageView btnExpanderArrow = (ImageView)findViewById(R.id.btn_expander_arrow);
		final ImageView btnExpanderDeuda = (ImageView)findViewById(R.id.btn_expander_pago);
		
		sv = (ScrollView)findViewById(R.id.ScrollViewResumen);
		
		listaExpandibleMonto = (LinearLayout)findViewById(R.id.row_info_banamex);
		listaExpandibleMonto.setVisibility(View.GONE);
		
		listaExpandibleDeuda = (LinearLayout)findViewById(R.id.listaExpandibleDeuda);
		listaExpandibleDeuda.setVisibility(View.GONE);
		
		handlerRequestResumen = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	        	ResumenCredito resumen = (ResumenCredito)msg.obj;
	        	
	        	despliegaResumenCredito(resumen);
	        	
	            Toast.makeText(ResumenCreditoActivity2.this, resumen.getNumCredito(), Toast.LENGTH_SHORT).show();	 
	            	            
	        }
	    };	    	
	    
	    lanzaServicioResumenCredito();
		
		childs = new ArrayList<ArrayList<ArrayList<String>>>();				 	 	

		btnMonto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {	
				
				if(!isMontoExpanded){
					isMontoExpanded = true;
					listaExpandibleMonto.setVisibility(View.VISIBLE);
					btnMonto.setSelected(true);
					btnExpanderArrow.setSelected(true);	
					scrollBottom();
				}
				else{
					isMontoExpanded = false;
					listaExpandibleMonto.setVisibility(View.GONE);
					btnMonto.setSelected(false);
					btnExpanderArrow.setSelected(false);					
				}				
			}
		});
		
		btnDeuda.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {	
				
				if(!isCuantoExpanded){
					isCuantoExpanded = true;
					listaExpandibleDeuda.setVisibility(View.VISIBLE);
					btnDeuda.setSelected(true);
					btnExpanderDeuda.setSelected(true);	
					scrollBottom();
				}
				else{
					isCuantoExpanded = false;
					listaExpandibleDeuda.setVisibility(View.GONE);
					btnDeuda.setSelected(false);
					btnExpanderDeuda.setSelected(false);					
				}				
			}
		});
	}
	
	private void lanzaServicioResumenCredito(){
		
		dialogGettingResumen = new ProgressDialog(ResumenCreditoActivity2.this);
        dialogGettingResumen.setIcon(android.R.drawable.stat_notify_sync);
        dialogGettingResumen.setTitle("Infonavit");
        dialogGettingResumen.setMessage("Obteniendo resumen de mi credito...");
        dialogGettingResumen.setCancelable(false);
       								
		dialogGettingResumen.show();
		
		new Thread() {
        	public void run() {
	        	try{	        	
	        		if (user == null) {
	        			user = UsuarioDTO.getUniqueUser();
//	        			user.seek("1");
					}
	        		
	        		IFParentRequest request = new IFParentRequest();
	        		request.generarRequestAcreditado(user.getCCnumber());
	        		
	        		IFserviceManager manager = new IFserviceManager();
	        		InputStream message = manager.execWebService(request);
	        		
	        		IFParentResponse response = new IFParentResponse(message);
	        		ResumenCredito resumen = response.obtenResumenDeCredito();
	        		    		        	    		        		    		        		    		        				                    
	        		resumen.seek("1");
	        		resumen.print("1");
                    
                    Message msgResumen = new Message();
                    msgResumen.obj = resumen; 
                    handlerRequestResumen.sendMessage(msgResumen);    		        		   		        		    		        	    		        		  		       
	        	} 
	        	catch (Exception e) {
	        		Log.d("conexion error: ", e.getMessage());
	        		e.printStackTrace();
	        	}    
	        	finally{
	        		dialogGettingResumen.dismiss();  
	        	}
        	}
        }.start();
	}
	
	private View getInflateChild(String key, String value){
		
		View child = getLayoutInflater().inflate(R.layout.row_child_resumen_credito, null);
		((ImageButton)child.findViewById(R.id.img_row_body_internal)).setBackgroundResource(R.drawable.selector_row_internal); 
		((TextView)child.findViewById(R.id.textView9)).setText(key);
		((TextView)child.findViewById(R.id.txt_row_child_value)).setText(value);
		
		return child;
	}
	
	private View getInflateLastChild(String key, String value){
		
		View child = getLayoutInflater().inflate(R.layout.row_child_resumen_credito, null);
		((ImageButton)child.findViewById(R.id.img_row_body_internal)).setBackgroundResource(R.drawable.selector_row_internal_ultimo); 
		((TextView)child.findViewById(R.id.textView9)).setText(key);
		((TextView)child.findViewById(R.id.txt_row_child_value)).setText(value);
		
		return child;
	}
	
	private void makeExpandableList(LinearLayout expandable, ArrayList<ArrayList<ArrayList<String>>> childs, int index){
		
		View childview = null;
        
        for(int i=0; i<childs.get(index).size(); i++){
        	ArrayList<String> list = childs.get(index).get(i);	        	
        	if(childs.get(index).size()-1 == i)
        		childview = getInflateLastChild(list.get(0), list.get(1));
        	else
        		childview = getInflateChild(list.get(0), list.get(1));
        	expandable.addView(childview);
        }
	}

	private void scrollBottom(){
		
		sv.post(new Runnable() { 
	        public void run() { 				      
	        	sv.smoothScrollTo(0, sv.getBottom());
	        } 
		});
	}
	
	private void despliegaResumenCredito(ResumenCredito resumen){
		
    	childs = new ArrayList<ArrayList<ArrayList<String>>>();        
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
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Tu debes");
	        childs.get(0).get(1).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("El Infonavit pagará");
	        childs.get(0).get(2).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("El Infonavit pagará");
	        childs.get(0).get(3).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(4).add("Saldo total");
	        childs.get(0).get(4).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(5).add("Fecha de inicio de la REES");
	        childs.get(0).get(5).add(resumen.getFechaDeReestructura());
	        
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
	        childs.get(0).get(1).add("pendiente");
	           		        	        
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
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Tu debes en pesos");
	        childs.get(0).get(1).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Tu debes en VSM");
	        childs.get(0).get(2).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("El Infonavit pagará en pesos");
	        childs.get(0).get(3).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(4).add("El Infonavit pagará en VSM");
	        childs.get(0).get(4).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(5).add("Saldo total en pesos");
	        childs.get(0).get(5).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(6).add("Saldo total en VSM");
	        childs.get(0).get(6).add("pendiente");
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
		}
		
		else if(resumen.getTipoEscenario().equals("06")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Tu debes en pesos");
	        childs.get(0).get(1).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Tu debes en VSM");
	        childs.get(0).get(2).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("El Infonavit pagará en pesos");
	        childs.get(0).get(3).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(4).add("El Infonavit pagará en VSM");
	        childs.get(0).get(4).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(5).add("Saldo total en pesos");
	        childs.get(0).get(5).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(6).add("Saldo total en VSM");
	        childs.get(0).get(6).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(7).add("Fecha de inicio de la reestructura");
	        childs.get(0).get(7).add(resumen.getFechaDeReestructura());
	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Numero de pagos pendientes");
	        childs.get(1).get(1).add("pendiente");
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
	        childs.get(1).get(6).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(7).add("Mensaje");
	        childs.get(1).get(7).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("07")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Tu debes");
	        childs.get(0).get(1).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("El Infonavit pagará");
	        childs.get(0).get(2).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("Saldo total");
	        childs.get(0).get(3).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(4).add("Fecha de inicio de la reestructura");
	        childs.get(0).get(4).add(resumen.getFechaDeReestructura());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Pago de mensualidad");
	        childs.get(1).get(1).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Fecha limite de pago");
	        childs.get(1).get(2).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("08")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Tu debes");
	        childs.get(0).get(1).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("El Infonavit pagará");
	        childs.get(0).get(2).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("Saldo total");
	        childs.get(0).get(3).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(4).add("Fecha de inicio de la reestructura");
	        childs.get(0).get(4).add(resumen.getFechaDeReestructura());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de Pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Numero de pagos pendientes");
	        childs.get(1).get(1).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Pago de mensualidad");
	        childs.get(1).get(2).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Total a pagar para estar al corriente");
	        childs.get(1).get(3).add(resumen.getMtoPagPendientesD());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Fecha limite de pago");
	        childs.get(1).get(4).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(5).add("Mensaje");
	        childs.get(1).get(5).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("09")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total");
	        childs.get(0).get(1).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Saldo total en pesos");
	        childs.get(0).get(2).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(3).add("Saldo total en VSM");
	        childs.get(0).get(3).add("pendiente");
	           		        	        
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
	        childs.get(1).get(4).add("pendiente");	        
		}
		
		else if(resumen.getTipoEscenario().equals("10")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add(resumen.getFechaProceso());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoAcreditadoPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Saldo total en VSM");
	        childs.get(0).get(2).add(resumen.getSaldoAcreditadoVSM());
	           		        	        
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
		}
		
		else if(resumen.getTipoEscenario().equals("11")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total en pesos");
	        childs.get(0).get(1).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Saldo total en VSM");
	        childs.get(0).get(2).add("pendiente");
	           		        	        
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
	        childs.get(1).get(3).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("12")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoAcreditadoPesos());
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Número de pagos pendientes");
	        childs.get(1).get(1).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Monto de pagos pendientes");
	        childs.get(1).get(2).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Pago de mensualidad de");
	        childs.get(1).get(3).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Total a pagar para estar al corriente");
	        childs.get(1).get(4).add(resumen.getPagoEsteMesVSM());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(5).add("Fecha límite de pago");
	        childs.get(1).get(5).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(6).add("Mensaje");
	        childs.get(1).get(6).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("13")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total en pesos");
	        childs.get(0).get(1).add(resumen.getSaldoAcreditadoPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Saldo total en VSM");
	        childs.get(0).get(2).add(resumen.getSaldoAcreditadoVSM());
	           		        	        
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
	        childs.get(1).get(3).add("pendiente");
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
	        childs.get(0).get(1).add(resumen.getSaldoAcreditadoPesos());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(2).add("Saldo total en VSM");
	        childs.get(0).get(2).add(resumen.getSaldoAcreditadoVSM());
	           		        	        
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
	        childs.get(1).get(4).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(5).add("Mensaje Informativo General");
	        childs.get(1).get(5).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("15")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total");
	        childs.get(0).get(1).add("pendiente");
	           		        	        
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
	        childs.get(1).get(3).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Mensaje Informativo General");
	        childs.get(1).get(4).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("16")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total");
	        childs.get(0).get(1).add("pendiente");
	           		        	        
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
	        childs.get(1).get(3).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(4).add("Mensaje Informativo General");
	        childs.get(1).get(4).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("17")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total");
	        childs.get(0).get(1).add("pendiente");
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de Pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Total a descontar de sueldo mensual");
	        childs.get(1).get(1).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Mensaje Informativo");
	        childs.get(1).get(2).add("pendiente");
		}
		
		else if(resumen.getTipoEscenario().equals("18")){  			    		        				
			childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(0).add("Fecha de corte");
	        childs.get(0).get(0).add("pendiente");
	        childs.get(0).add(new ArrayList<String>());
	        childs.get(0).get(1).add("Saldo total");
	        childs.get(0).get(1).add("pendiente");
	           		        	        
	        childs.add(new ArrayList<ArrayList<String>>());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(0).add("Forma de Pago");
	        childs.get(1).get(0).add(resumen.getFormaPago());
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(1).add("Número de pagos pendientes");
	        childs.get(1).get(1).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(2).add("Monto de pagos pendientes");
	        childs.get(1).get(2).add("pendiente");
	        childs.get(1).add(new ArrayList<String>());
	        childs.get(1).get(3).add("Fecha Límite de pago");
	        childs.get(1).get(3).add("pendiente");
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
	        childs.get(0).get(0).add("pendiente");
	        
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
	        childs.get(0).get(0).add("pendiente");
	        
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
	        childs.get(0).get(0).add("pendiente");
	        
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
	        childs.get(0).get(0).add("pendiente");
	        
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
		
		makeExpandableList(listaExpandibleMonto, childs, 0);
        makeExpandableList(listaExpandibleDeuda, childs, 1);	 
	}
}
