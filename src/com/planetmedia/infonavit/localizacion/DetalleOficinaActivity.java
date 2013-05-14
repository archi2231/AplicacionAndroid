package com.planetmedia.infonavit.localizacion;

import java.io.InputStream;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.planetmedia.infonavit.R;
import com.planetmedia.infonavit.WSConsume.IFParentRequest;
import com.planetmedia.infonavit.WSConsume.IFParentResponse;
import com.planetmedia.infonavit.WSConsume.IFserviceManager;
import com.planetmedia.infonavit.datamodel.dao.DetalleOficinaDAO;
import com.planetmedia.infonavit.datamodel.dto.DetalleOficinaDTO;

public class DetalleOficinaActivity extends FragmentActivity implements OnClickListener {
	
	ProgressDialog _dialogo;
	    
	private LatLng _officeLocation;
	private GoogleMap _mapa;
	
	private OficinaDTO _oficina;
	private DetalleOficinaDTO _detalleOficina;
	DetalleOficinaDAO _daoDetalle;
	private Button btnRuta;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.layout_detalle_oficina);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.header_solo);
		
		btnRuta = (Button)findViewById(R.id.btnRutaOficina);
		btnRuta.setOnClickListener(this);
		
		_oficina = getIntent().getExtras().getParcelable("oficina");
		
		setOfficeLocation();
		_mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaDetalle)).getMap();
		_mapa.setMyLocationEnabled(true);
		_mapa.getUiSettings().setAllGesturesEnabled(false);
		_mapa.getUiSettings().setZoomControlsEnabled(false);
		_mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		centraMapa();
		
		MarkerOptions marca = new MarkerOptions();
		marca.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_infonavit));
		marca.position(new LatLng(Double.parseDouble(_oficina.getLatitud()), Double.parseDouble(_oficina.getLongitud())));
		marca.title(_oficina.getNombreMunicipio());
		marca.snippet(_oficina.getNombreEstado());
	
		_mapa.addMarker(marca).showInfoWindow();
		
		_daoDetalle = new DetalleOficinaDAO();
		_detalleOficina = _daoDetalle.getRegistroPorOficina(_oficina.getIdOficina());
		if (_detalleOficina == null){
			consultaServicio();
		} else {
			pintaLayout();
		}
	}
	
	private void setOfficeLocation(){
		
		_officeLocation = new LatLng(Double.parseDouble(_oficina.getLatitud()), Double.parseDouble(_oficina.getLongitud()));
	}
	
	private void consultaServicio(){
		_dialogo = new ProgressDialog(this);

		_dialogo.setIcon(android.R.drawable.stat_notify_sync);
		_dialogo.setTitle("Infonavit");
		_dialogo.setMessage("Obteniendo Detalle de Oficina ...");
		_dialogo.setCancelable(false);
		_dialogo.show();
		
		new Thread() {
			@Override
			public void run() {

				try {
					IFParentRequest request = new IFParentRequest();
					request.generaRequestDetalleOficina(_oficina.getIdEntidadFederativa(), _oficina.getIdPlaza());

					IFserviceManager manager = new IFserviceManager();
					InputStream message = manager.execWebService(request);

					IFParentResponse response = new IFParentResponse(message);
					_detalleOficina = response.obtenDetalleOficinas();

					if (_detalleOficina != null) {
						_detalleOficina.setIdOficina(_oficina.getIdOficina());
						_daoDetalle.insertaRegistro(_detalleOficina);
						
						DetalleOficinaActivity.this.runOnUiThread(new Runnable() {
						    public void run() {
						        pintaLayout();
						    }
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					_dialogo.dismiss();
				}
			}
		}.start();
	}
	
	private void pintaLayout(){
		
		Typeface typeFace = Typeface.createFromAsset(getApplicationContext().getAssets(), "eurostile.ttf");
		
		((TextView)findViewById(R.id.nombreOficina)).setText(_oficina.getNombreMunicipio());
		((TextView)findViewById(R.id.nombreOficina)).setTypeface(typeFace);
		String direccion = _detalleOficina.getCalle() + 
				"num " + _detalleOficina.getNumExt() + 
				"-" + _detalleOficina.getNumInt() + 
				", colonia " + _detalleOficina.getColonia();
		((TextView)findViewById(R.id.direccionOficina)).setText(direccion);
		((TextView)findViewById(R.id.direccionOficina)).setTypeface(typeFace);
		
		String referencia = "Referencia: " + _detalleOficina.getEntreCalle() +
				" y " + _detalleOficina.getyCalle();
		((TextView)findViewById(R.id.referenciaOficina)).setText(referencia);
		((TextView)findViewById(R.id.referenciaOficina)).setTypeface(typeFace);
		
		((TextView)findViewById(R.id.telefonoOficina)).setText("Teléfono: " + _detalleOficina.getLada() + 
				" - " + _detalleOficina.getTelefono());
		((TextView)findViewById(R.id.telefonoOficina)).setTypeface(typeFace);
		
		((TextView)findViewById(R.id.horarioOficina)).setText("Horario: " + _detalleOficina.getHorario());
		((TextView)findViewById(R.id.horarioOficina)).setTypeface(typeFace);
		
	}
	
	private void centraMapa(){
		
		if(_officeLocation == null)
			setOfficeLocation();
		
//		LatLng posicion = new LatLng(_officeLocation.getLatitude(),_officeLocation.getLongitude());
		
		_mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(_officeLocation, 15));
		
	}

	/**
	 * Método del On Click Listener
	 */
	@Override
	public void onClick(View v) {
		
		Intent rutaOficina = new Intent(this, RutaOficinaActivity.class);
		rutaOficina.putExtra("origenLat", _mapa.getMyLocation().getLatitude());
		rutaOficina.putExtra("origenLon", _mapa.getMyLocation().getLongitude());
		rutaOficina.putExtra("destinoLat", _officeLocation.latitude);
		rutaOficina.putExtra("destinoLon", _officeLocation.longitude);
		
		startActivity(rutaOficina);
		
	}

}
