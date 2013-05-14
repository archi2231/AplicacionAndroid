package com.planetmedia.infonavit;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.planetmedia.infonavit.EstadoDeCuenta.IFEstadoDeCuentaGenerico;
import com.planetmedia.infonavit.EstadoDeCuenta.IFR40Generico;
import com.planetmedia.infonavit.EstadoDeCuenta.IFREANormal;
import com.planetmedia.infonavit.EstadoDeCuenta.IFReestructuraGenerico;
import com.planetmedia.infonavit.WSConsume.IFParentRequest;
import com.planetmedia.infonavit.WSConsume.IFParentResponse;
import com.planetmedia.infonavit.WSConsume.IFserviceManager;
import com.planetmedia.infonavit.datamodel.dao.EstadoCuentaDAO;
import com.planetmedia.infonavit.datamodel.dao.UsuarioDAO;
import com.planetmedia.infonavit.datamodel.dto.UsuarioDTO;

public class EstadoCuentaActivity extends Activity{
	
	private boolean isDatosCreditoExpanded;
	private boolean isResumenMovimientosExpanded;
	private boolean isDatosReestructuraExpanded;
	private boolean isApoyoInteresExpanded;
	private boolean isAvisoImportanteExpanded;
	
	private ProgressDialog _dialogo;
	
	UsuarioDTO user;
	
	private List<Map<String, String>> _datosCredito;
	private List<Map<String, String>> _datosResumenMovimentos;
	private List<Map<String, String>> _datosReestructura;
	private List<Map<String, String>> _datosR40;
	private static IFEstadoDeCuentaGenerico _estadoDeCuenta = null;
//	private static boolean _estaConsultando = false;
	
	Thread _threadServicio;
	
	private LinearLayout listaExpandibleDatosCredito;
	private LinearLayout listaExpandibleResumenMovimientos;
	private LinearLayout listaExpandibleDatosReestructura;
	private LinearLayout listaExpandibleApoyoInteres;
	private LinearLayout listaExpandibleAvisoImportante;
	
	private ImageButton btnDatosCredito;
	private ImageButton btnResumenMovimientos;
	private ImageButton btnDatosReestructura;
	private ImageButton btnApoyoInteres;
	private ImageButton btnAvisoImportante;
	
	private TextView txt_periodo;
	private TextView txt_fecha_limite_pago;
	
	private ScrollView sv;
	
	
	@Override
	protected void onPause() {
	
		super.onPause();
		if(_dialogo != null && _dialogo.isShowing())
			_dialogo.dismiss();
		if (_threadServicio != null && _threadServicio.isAlive())
			_threadServicio.interrupt();
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		 
		setContentView(R.layout.layout_estado_cuenta);		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header); 
		
		
		txt_periodo = (TextView)findViewById(R.id.txt_periodo);
		txt_fecha_limite_pago = (TextView)findViewById(R.id.txt_fecha_limite_pago);	    
		
//		txt_periodo.setText("Periodo del:" + edocuenta.getPeriodo());
//		txt_fecha_limite_pago.setText("Fecha limite de pago:" + edocuenta.getFechaLimiteDePago());
		
		btnDatosCredito = (ImageButton)findViewById(R.id.btn_datos_credito);
		btnResumenMovimientos = (ImageButton)findViewById(R.id.btn_resumen_movimientos);
		btnDatosReestructura = (ImageButton)findViewById(R.id.btn_datos_reestructura);
		btnApoyoInteres = (ImageButton)findViewById(R.id.btn_apoyo_interes);
		btnAvisoImportante = (ImageButton)findViewById(R.id.btn_aviso_importante);
		
		final ImageView btnExpanderDatosCredito = (ImageView)findViewById(R.id.btn_expander_datos_credito);
		final ImageView btnExpanderResumenMovimiento = (ImageView)findViewById(R.id.btn_expander_resumen_movimientos);
		final ImageView btnExpanderDatosReestructura = (ImageView)findViewById(R.id.btn_expander_datos_reestructura);
		final ImageView btnExpanderApoyoInteres = (ImageView)findViewById(R.id.btn_expander_apoyo_interes);
		final ImageView btnExpanderAvisoImportante = (ImageView)findViewById(R.id.btn_expander_aviso_importante);
		
		sv = (ScrollView)findViewById(R.id.ScrollViewResumen);
		
		listaExpandibleDatosCredito = (LinearLayout)findViewById(R.id.listaExpandibleDatosCredito);
		listaExpandibleDatosCredito.setVisibility(View.GONE);
		
		listaExpandibleResumenMovimientos = (LinearLayout)findViewById(R.id.listaExpandibleResumenMovimientos);
		listaExpandibleResumenMovimientos.setVisibility(View.GONE);
		
		listaExpandibleDatosReestructura = (LinearLayout)findViewById(R.id.listaExpandibleDatosReestructura);
		listaExpandibleDatosReestructura.setVisibility(View.GONE);
		
		listaExpandibleApoyoInteres = (LinearLayout)findViewById(R.id.listaExpandibleApoyoInteres);
		listaExpandibleApoyoInteres.setVisibility(View.GONE);
		
		listaExpandibleAvisoImportante = (LinearLayout)findViewById(R.id.listaExpandibleAvisoImportante);
		listaExpandibleAvisoImportante.setVisibility(View.GONE);
		
		
//	    if(_estadoDeCuenta == null && !_estaConsultando){
		if(_estadoDeCuenta == null){
//	    	_estaConsultando = true;
	    	lanzaServicioEstadoCuenta();
	    
	    } else {
	    	despliegaEstadoCuenta();
	    }

		btnDatosCredito.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {	
				
				if(!isDatosCreditoExpanded){
					isDatosCreditoExpanded = true;
					listaExpandibleDatosCredito.setVisibility(View.VISIBLE);
					btnDatosCredito.setSelected(true);
					btnExpanderDatosCredito.setSelected(true);	
					scrollBottom();
				}
				else{
					isDatosCreditoExpanded = false;
					listaExpandibleDatosCredito.setVisibility(View.GONE);
					btnDatosCredito.setSelected(false);
					btnExpanderDatosCredito.setSelected(false);					
				}				
			}
		});
		
		
		btnResumenMovimientos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {	
				
				if(!isResumenMovimientosExpanded){
					isResumenMovimientosExpanded = true;
					listaExpandibleResumenMovimientos.setVisibility(View.VISIBLE);
					btnResumenMovimientos.setSelected(true);
					btnExpanderResumenMovimiento.setSelected(true);	
					scrollBottom();
				}
				else{
					isResumenMovimientosExpanded = false;
					listaExpandibleResumenMovimientos.setVisibility(View.GONE);
					btnResumenMovimientos.setSelected(false);
					btnExpanderResumenMovimiento.setSelected(false);					
				}				
			}
		});
		
		btnDatosReestructura.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {	
				
				if(!isDatosReestructuraExpanded){
					isDatosReestructuraExpanded = true;
					listaExpandibleDatosReestructura.setVisibility(View.VISIBLE);
					btnDatosReestructura.setSelected(true);
					btnExpanderDatosReestructura.setSelected(true);	
					scrollBottom();
				}
				else{
					isDatosReestructuraExpanded = false;
					listaExpandibleDatosReestructura.setVisibility(View.GONE);
					btnDatosReestructura.setSelected(false);
					btnExpanderDatosReestructura.setSelected(false);					
				}				
			}
		});
		
		btnApoyoInteres.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {	
				
				if(!isApoyoInteresExpanded){
					isApoyoInteresExpanded = true;
					listaExpandibleApoyoInteres.setVisibility(View.VISIBLE);
					btnApoyoInteres.setSelected(true);
					btnExpanderApoyoInteres.setSelected(true);	
					scrollBottom();
				}
				else{
					isApoyoInteresExpanded = false;
					listaExpandibleDatosReestructura.setVisibility(View.GONE);
					btnApoyoInteres.setSelected(false);
					btnExpanderApoyoInteres.setSelected(false);					
				}				
			}
		});
		btnAvisoImportante.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {	
				
				if(!isAvisoImportanteExpanded){
					isAvisoImportanteExpanded = true;
					listaExpandibleAvisoImportante.setVisibility(View.VISIBLE);
					btnAvisoImportante.setSelected(true);
					btnExpanderAvisoImportante.setSelected(true);	
					scrollBottom();
				}
				else{
					isAvisoImportanteExpanded = false;
					listaExpandibleAvisoImportante.setVisibility(View.GONE);
					btnAvisoImportante.setSelected(false);
					btnExpanderAvisoImportante.setSelected(false);					
				}				
			}
		});
	}
	
	
		private void lanzaServicioEstadoCuenta(){
		
		_dialogo = new ProgressDialog(EstadoCuentaActivity.this);
		_dialogo.setIcon(android.R.drawable.stat_notify_sync);
		_dialogo.setTitle("Infonavit");
		_dialogo.setMessage("Obteniendo Estado de Cuenta ...");
		_dialogo.setCancelable(false);
       								
		_dialogo.show();		
		
		_threadServicio = new Thread() {
			public void run() {
	        	try{	   
	        		Log.d("logleo", "here we go!");    		        		    		        		    		        		    		        		
	        		
	            	IFParentRequest request = new IFParentRequest();
	            	//TODO: Hacerlo din�mico
	        		request.generaRequestEstadoCuenta("0402006504");
	        		
	        		//TODO: QUITAR ESTO
	        		
	        		UsuarioDTO usuario = UsuarioDTO.getUniqueUser();
	        		usuario.setNombrePila("Juan Manuel");
	        		UsuarioDAO daouser = new UsuarioDAO();
	        		daouser.insertaRegistro(usuario);
	        		usuario = daouser.getRegistro();
	        		//TODO: QUITAR ESTO
	        		IFserviceManager manager = new IFserviceManager();
	        		InputStream message = manager.execWebService(request);
	        		
	        		IFParentResponse response = new IFParentResponse(message);
	        		_estadoDeCuenta = response.obtenEstadoDeCuenta();
	        		EstadoCuentaDAO dao = new EstadoCuentaDAO();
	        		if (_estadoDeCuenta != null){
	        				        			
	        			_estadoDeCuenta.setIdUsuario(UsuarioDTO.getUniqueUser().getId());
	        			dao.borraRegistro(0);
	        			dao.insertaRegistro(_estadoDeCuenta);
	        			
	        			EstadoCuentaActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								
								despliegaEstadoCuenta();
							}
						});
	        			
	        		} else {
	        			_estadoDeCuenta = dao.getRegistro();
	        			if (_estadoDeCuenta != null){
	        				EstadoCuentaActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									
									despliegaEstadoCuenta();
								}
							});
	        			}
	        		}
	        		
//	        		dialogGettingEstadoCuenta.dismiss();
	        	} 
	        	catch (Exception e) {
	        		Log.d("conexion error: ", e.getMessage());
	        		e.printStackTrace();
	        	}    		        
	        	finally{
	        		_dialogo.dismiss();
//	        		_estaConsultando = false;
	        	}
        	}

			
        };
		 
        _threadServicio.start();
        
}
		
		private View getInflateLastChild(String key, String value){
			
			View child = getLayoutInflater().inflate(R.layout.row_child_resumen_credito, null);
			((ImageButton)child.findViewById(R.id.img_row_body_internal)).setBackgroundResource(R.drawable.selector_row_internal_ultimo); 
//			((TextView)child.findViewById(R.id.txt_row_child_key)).setText(key);
			((TextView)child.findViewById(R.id.txt_row_child_value)).setText(value);
			
			return child;
		}
		
		private void makeExpandableList(LinearLayout expandable, List<Map<String, String>> childs, int index){
			
			View childView = null;
			Iterator<Map<String, String>> iterator = childs.iterator();
			while(iterator.hasNext()){
				 Map<String, String> mapAux = iterator.next();
				 childView = getInflateChild(mapAux.get("clave"), mapAux.get("valor"));
				 
				 expandable.addView(childView);
			}

		}
		
		private View getInflateChild(String key, String value){
			
			View child = getLayoutInflater().inflate(R.layout.row_child_resumen_credito, null);
			((ImageButton)child.findViewById(R.id.img_row_body_internal)).setBackgroundResource(R.drawable.selector_row_internal); 
			((TextView)child.findViewById(R.id.txt_row_child_key)).setText(key);
			((TextView)child.findViewById(R.id.txt_row_child_value)).setText(value);
			
			return child;
		}
		
		private void scrollBottom(){
			
//			sv.post(new Runnable() { 
//		        public void run() { 				      
//		        	sv.smoothScrollTo(0, sv.getBottom());
//		        } 
//			});
		}
		
		private void despliegaEstadoCuenta(){
			        
	        //HAPPY PATH
			
			setDatosCredito();
			setDatosResumenMovimientos();
			
			if (IFReestructuraGenerico.class.isAssignableFrom(_estadoDeCuenta.getClass())){
				setDatosReestructura();
			}
			
			if (IFR40Generico.class.isAssignableFrom(_estadoDeCuenta.getClass())){
				setDatosApoyoTasaInteresR40();
			}

			makeExpandableList(listaExpandibleDatosCredito, _datosCredito, 0);
	        makeExpandableList(listaExpandibleResumenMovimientos, _datosResumenMovimentos, 1);
	        
	        if (_datosReestructura != null){
	        	makeExpandableList(listaExpandibleDatosReestructura, _datosReestructura, 2);
	        } else {
	        	listaExpandibleDatosReestructura.setVisibility(View.GONE);
	    		btnDatosReestructura.setVisibility(View.GONE);
	        }
	        
	        if (_datosR40 != null){
	        	makeExpandableList(listaExpandibleApoyoInteres, _datosR40, 3);
	        } else {
	        	listaExpandibleApoyoInteres.setVisibility(View.GONE);
	        	btnApoyoInteres.setVisibility(View.GONE);
	        }
//	        makeExpandableList(listaExpandibleAvisoImportante, childs, 4);
	        	        
		}
		
		private void setDatosCredito(){
			
			_datosCredito = new ArrayList<Map<String,String>>();
			
			Map<String, String> mapaAux = new HashMap<String, String>();
			mapaAux.put("clave", "Nombre");
			mapaAux.put("valor", _estadoDeCuenta.getNombre());
			_datosCredito.add(mapaAux);
			mapaAux = null;
			
			mapaAux = new HashMap<String, String>();
			mapaAux.put("clave", "Cr�dito");
			mapaAux.put("valor", _estadoDeCuenta.getNumeroCredito());
			_datosCredito.add(mapaAux);
			mapaAux = null;
			
			if (_estadoDeCuenta instanceof IFREANormal){
			
				mapaAux = new HashMap<String, String>();
				mapaAux.put("clave", "L�nea de captura");
				mapaAux.put("valor", ((IFREANormal)_estadoDeCuenta).getLineaCaptura());
				_datosCredito.add(mapaAux);
				mapaAux = null;
			
			}
			
			mapaAux = new HashMap<String, String>();
			mapaAux.put("clave", "Pago contratado");
			mapaAux.put("valor", _estadoDeCuenta.getPagoContratado());
			_datosCredito.add(mapaAux);
			mapaAux = null;
			
			mapaAux = new HashMap<String, String>();
			mapaAux.put("clave", "Tasa inter�s anual");
			mapaAux.put("valor", _estadoDeCuenta.getTasaInteresAnual());
			_datosCredito.add(mapaAux);
			mapaAux = null;
			
			mapaAux = new HashMap<String, String>();
			mapaAux.put("clave", "Fecha otorgamiento");
			mapaAux.put("valor", _estadoDeCuenta.getFechaOtorgamiento());
			_datosCredito.add(mapaAux);
			mapaAux = null;
			
			if(IFR40Generico.class.isAssignableFrom(_estadoDeCuenta.getClass())){
				mapaAux = new HashMap<String, String>();
				mapaAux.put("clave", "Descuento en tasa inter�s");
				mapaAux.put("valor", ((IFR40Generico)_estadoDeCuenta).getDescuentoTasaInteres());
				_datosCredito.add(mapaAux);
				mapaAux = null;
				
				mapaAux = new HashMap<String, String>();
				mapaAux.put("clave", "Tasa inter�s anual cobrada");
				mapaAux.put("valor", ((IFR40Generico)_estadoDeCuenta).getTasaInteresAnualCobrada());
				_datosCredito.add(mapaAux);
				mapaAux = null;
			}
			
			mapaAux = new HashMap<String, String>();
			mapaAux.put("clave", "Mensualidades vencidas");
			mapaAux.put("valor", _estadoDeCuenta.getNumMensualidadesVencidas());
			_datosCredito.add(mapaAux);
			mapaAux = null;
			
			mapaAux = new HashMap<String, String>();
			mapaAux.put("clave", "Nivel de cumplimiento");
			mapaAux.put("valor", _estadoDeCuenta.getNivelCumplimientoPago());
			_datosCredito.add(mapaAux);
			mapaAux = null;
			
			mapaAux = new HashMap<String, String>();
			mapaAux.put("clave", "Monto vencido");
			mapaAux.put("valor", _estadoDeCuenta.getMontoVencido());
			_datosCredito.add(mapaAux);
			mapaAux = null;
			
//			mapaAux = new HashMap<String, String>();
//			mapaAux.put("clave", "Boletos");
//			mapaAux.put("valor", _edocuenta.getBoletos());
//			_arregloDatosCredito.add(mapaAux);
//			mapaAux = null;
//			
//			mapaAux = new HashMap<String, String>();
//			mapaAux.put("clave", "Mensaje General");
//			mapaAux.put("valor", _edocuenta.getMensajeGeneral());
//			_arregloDatosCredito.add(mapaAux);
//			mapaAux = null;
//			
//			mapaAux = new HashMap<String, String>();
//			mapaAux.put("clave", "Mensaje Cartera");
//			mapaAux.put("valor", _edocuenta.getMensajeCartera());
//			_arregloDatosCredito.add(mapaAux);
//			mapaAux = null;
		}
		
		private void setDatosResumenMovimientos(){
			_datosResumenMovimentos = new ArrayList<Map<String,String>>();
			
		    Map<String, String> mapAux = new HashMap<String, String>();
		    mapAux.put("clave", "Saldo anterior");
		    mapAux.put("valor1", _estadoDeCuenta.getSaldoAnterior());
		    mapAux.put("valor2", _estadoDeCuenta.getSaldoAnteriorVSM());
		    _datosResumenMovimentos.add(mapAux);
		    mapAux = null;
		    
		    mapAux = new HashMap<String, String>();
		    mapAux.put("clave", "Ajuste periodos anteriores");
		    mapAux.put("valor1", _estadoDeCuenta.getAjustePeriodosAnteriores());
		    mapAux.put("valor2", _estadoDeCuenta.getAjustePeriodosAnterioresVSM());
		    _datosResumenMovimentos.add(mapAux);
		    mapAux = null;
		    
		    mapAux = new HashMap<String, String>();
		    mapAux.put("clave", "Saldo ajustado");
		    mapAux.put("valor1", _estadoDeCuenta.getSaldoAjustado());
		    mapAux.put("valor2", _estadoDeCuenta.getSaldoAjustadoVSM());
		    _datosResumenMovimentos.add(mapAux);
		    mapAux = null;
		    
		    mapAux = new HashMap<String, String>();
		    mapAux.put("clave", "Intereses del periodo");
		    mapAux.put("valor1", _estadoDeCuenta.getInteresesPeriodo());
		    mapAux.put("valor2", _estadoDeCuenta.getInteresesPeriodoVSM());
		    _datosResumenMovimentos.add(mapAux);
		    mapAux = null;
		    
		    if (IFR40Generico.class.isAssignableFrom(_estadoDeCuenta.getClass())) {
		            
		            mapAux = new HashMap<String, String>();
		            mapAux.put("clave", "Inter�s cubierto por Infonavit");
				    mapAux.put("valor1", ((IFR40Generico)_estadoDeCuenta).getInteresesCubiertosInfonavit());
				    mapAux.put("valor2", ((IFR40Generico)_estadoDeCuenta).getInteresesCubiertosInfonavitVSM());
		            _datosResumenMovimentos.add(mapAux);
		            mapAux = null;
		            
		            mapAux = new HashMap<String, String>();
		            mapAux.put("clave", "Inter�s cubierto por acreditado");
				    mapAux.put("valor1", ((IFR40Generico)_estadoDeCuenta).getInteresesCubiertosAcreditado());
				    mapAux.put("valor2", ((IFR40Generico)_estadoDeCuenta).getInteresesCubiertosAcreditadoVSM());
		            _datosResumenMovimentos.add(mapAux);
		            mapAux = null;
		    }
		    
		    mapAux = new HashMap<String, String>();
		    mapAux.put("clave", "Comisiones y otros cargos");
		    mapAux.put("valor1", _estadoDeCuenta.getComisionesYOtrosCargos());
		    mapAux.put("valor2", _estadoDeCuenta.getComisionesYOtrosCargosVSM());
		    _datosResumenMovimentos.add(mapAux);
		    mapAux = null;
		    
		    mapAux = new HashMap<String, String>();
		    mapAux.put("clave", "Tu �ltimo pago");
		    mapAux.put("valor1", _estadoDeCuenta.getComisionesYOtrosCargos());
		    mapAux.put("valor2", _estadoDeCuenta.getComisionesYOtrosCargosVSM());
		    _datosResumenMovimentos.add(mapAux);
		    mapAux = null;
		    
		    if (IFReestructuraGenerico.class.isAssignableFrom(_estadoDeCuenta.getClass())) {
		        
		    	mapAux = new HashMap<String, String>();
		    	mapAux.put("clave", "�ltimo pago Infonavit");
			    mapAux.put("valor1", ((IFReestructuraGenerico)_estadoDeCuenta).getUltimoPagoInfonavit());
			    mapAux.put("valor2", ((IFReestructuraGenerico)_estadoDeCuenta).getUltimoPagoInfonavitVSM());
		        _datosResumenMovimentos.add(mapAux);
		        mapAux = null;
		            
		    }
		    
		    mapAux = new HashMap<String, String>();
		    mapAux.put("clave", "Traspasos y otros abonos");
		    mapAux.put("valor1", _estadoDeCuenta.getTraspasosYOtrosAbonos());
		    mapAux.put("valor2", _estadoDeCuenta.getTraspasosYOtrosAbonosVSM());
		    _datosResumenMovimentos.add(mapAux);
		    mapAux = null;
		    
		    mapAux = new HashMap<String, String>();
		    mapAux.put("clave", "Saldo actual");
		    mapAux.put("valor1", _estadoDeCuenta.getSaldoActual());
		    mapAux.put("valor2", _estadoDeCuenta.getSaldoActualVSM());
		    _datosResumenMovimentos.add(mapAux);
		    mapAux = null;
			
		}

		private void setDatosReestructura(){
		
			_datosReestructura = new ArrayList<Map<String,String>>();
			
			Map<String, String> mapAux = new HashMap<String, String>();
			mapAux.put("clave", "Saldo a cargo acreditado");
		    mapAux.put("valor1", ((IFReestructuraGenerico)_estadoDeCuenta).getSaldoACargoAcreditado());
		    mapAux.put("valor2", ((IFReestructuraGenerico)_estadoDeCuenta).getSaldoACargoAcreditadoVSM());
		    _datosReestructura.add(mapAux);
		    mapAux = null;
		        
	        mapAux = new HashMap<String, String>();
	        mapAux.put("clave", "Saldo a cargo Infonavit");
		    mapAux.put("valor1", ((IFReestructuraGenerico)_estadoDeCuenta).getSaldoACargoInfonavit());
		    mapAux.put("valor2", ((IFReestructuraGenerico)_estadoDeCuenta).getSaldoACargoInfonavitVSM());
	        _datosReestructura.add(mapAux);
	        mapAux = null;

		}

		private void setDatosApoyoTasaInteresR40(){

			_datosR40 = new ArrayList<Map<String,String>>();
			Map<String, String> mapAux = new HashMap<String, String>();
			mapAux.put("clave","Tasa de inter�s aplicada del");
			mapAux.put("valor1", ((IFR40Generico)_estadoDeCuenta).getTasaInteresInicioAplicada1());
		    mapAux.put("valor2", ((IFR40Generico)_estadoDeCuenta).getTasaInteresFinAplicada1());
		    _datosR40.add(mapAux);
		    mapAux = null;
		        
		    mapAux = new HashMap<String, String>();
		    mapAux.put("clave","Tasa de inter�s aplicada del");
			mapAux.put("valor1", ((IFR40Generico)_estadoDeCuenta).getTasaInteresInicioAplicada2());
		    mapAux.put("valor2", ((IFR40Generico)_estadoDeCuenta).getTasaInteresFinAplicada2());
		    _datosR40.add(mapAux);
		    mapAux = null;

		}
}