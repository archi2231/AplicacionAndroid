package com.planetmedia.infonavit.localizacion;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.planetmedia.infonavit.R;
import com.planetmedia.infonavit.WSConsume.IFParentRequest;
import com.planetmedia.infonavit.WSConsume.IFParentResponse;
import com.planetmedia.infonavit.WSConsume.IFserviceManager;
import com.planetmedia.infonavit.datamodel.dao.ControlCatalogoDAO;
import com.planetmedia.infonavit.datamodel.dao.DetalleOficinaDAO;
import com.planetmedia.infonavit.datamodel.dao.OficinaDAO;
import com.planetmedia.infonavit.datamodel.dto.ControlCatalogoDTO;

public class LocalizacionOficinasActivity extends FragmentActivity implements LocationListener, InfoWindowAdapter, OnInfoWindowClickListener, OnClickListener, TextWatcher {

	ProgressDialog _dialogo;
	  // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 0;//1000 * 5; // 5 Segundos
    
	private static LocationManager _locationManager;
	private Location _userLocation;
	private GoogleMap _mapa;
	
	private List<OficinaDTO> _listaOficinas;

	private LinearLayout _listViewOficinas;
	
	private ImageButton _btnCentrarMapa;
	private ImageButton _btnZoomCompleto;
	private ToggleButton _btnListaCercanas;
	private ToggleButton _btnMapaOficinas;
	private EditText _etBusqueda;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.layout_localizacion_oficinas);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.header_solo);
		
		setUserLocation();
		_btnCentrarMapa = (ImageButton)findViewById(R.id.btn_mapa_pin);
		_btnCentrarMapa.setOnClickListener(this);
		
		_btnZoomCompleto = (ImageButton)findViewById(R.id.btn_mapa_mex);
		_btnZoomCompleto.setOnClickListener(this);
		
		_btnListaCercanas = (ToggleButton)findViewById(R.id.btn_lista_cercanas);
		_btnListaCercanas.setOnClickListener(this);
		_btnListaCercanas.setChecked(false);
		
		_btnMapaOficinas = (ToggleButton)findViewById(R.id.btn_mapa_oficinas);
		_btnMapaOficinas.setOnClickListener(this);
		_btnMapaOficinas.setChecked(true);
		
		_etBusqueda = (EditText) findViewById(R.id.txt_filter_oficinas);
		_etBusqueda.addTextChangedListener(this);
		_etBusqueda.setSelected(false);
		
		_listViewOficinas = (LinearLayout) findViewById(R.id.listaOficinas);
		
		_mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa)).getMap();
		_mapa.setMyLocationEnabled(true);
		
		_mapa.getUiSettings().setCompassEnabled(false);
		_mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		_mapa.setInfoWindowAdapter(this);
		_mapa.setOnInfoWindowClickListener(this);
		
		ControlCatalogoDAO daoControl = new ControlCatalogoDAO();
		ControlCatalogoDTO controlDTO = daoControl.getRegistro();
		
		if(controlDTO == null || !controlDTO.getMarcaActualizacion().equals("0")){
			DetalleOficinaDAO daoDetalle = new DetalleOficinaDAO();
			daoDetalle.borraRegistro(0);
			consultaServicio();
		} else {
			OficinaDAO daoOficina = new OficinaDAO();
			_listaOficinas = daoOficina.getRegistros();
			if (_listaOficinas != null && !_listaOficinas.isEmpty()){
				ponMarcas();
			}
		}
		
		centraMapa();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		_locationManager.removeUpdates(this);
	}

	private void setUserLocation(){
		//TODO: Siempre regresa Nulo
		_locationManager =  (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if (_locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
			if (_locationManager != null){
				_userLocation = _locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}
		} else {
			_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
			if (_locationManager != null){
				_userLocation = _locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
		}
//		_userLocation = _mapa.getMyLocation();
	}
	
	private void consultaServicio() {

		_dialogo = new ProgressDialog(LocalizacionOficinasActivity.this);

		_dialogo.setIcon(android.R.drawable.stat_notify_sync);
		_dialogo.setTitle("Infonavit");
		_dialogo.setMessage("Obteniendo Listado de Oficinas ...");
		_dialogo.setCancelable(false);
		_dialogo.show();
		
		new Thread() {
			@Override
			public void run() {

				try {
					IFParentRequest request = new IFParentRequest();
					request.generaRequestCatalogoOficinas();

					IFserviceManager manager = new IFserviceManager();
					InputStream message = manager.execWebService(request);

					IFParentResponse response = new IFParentResponse(message);
					_listaOficinas = response.obtenOficinasCatalogo();
					

					if (_listaOficinas != null) {
						
						OficinaDAO dao = new OficinaDAO();
						dao.borraRegistro(0);
						for (OficinaDTO oficina : _listaOficinas) {
							dao.insertaRegistro(oficina);
						}
						
						ControlCatalogoDAO daoCtl = new ControlCatalogoDAO();
						ControlCatalogoDTO dtoCtl = daoCtl.getRegistro();
						daoCtl.borraRegistro(0);
						dtoCtl.setMarcaActualizacion("0");
						daoCtl.insertaRegistro(dtoCtl);
						
						LocalizacionOficinasActivity.this.runOnUiThread(new Runnable() {
						    public void run() {
						        ponMarcas();
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
	
	private void ponMarcas(){
		Iterator<OficinaDTO> iterador = _listaOficinas.iterator();
		while(iterador.hasNext()){
			OficinaDTO oficina = iterador.next();
			if (!oficina.getLatitud().equals("") && !oficina.getLongitud().equals("")){
				MarkerOptions marca = new MarkerOptions();
				marca.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_infonavit));
				marca.position(new LatLng(Double.parseDouble(oficina.getLatitud()), Double.parseDouble(oficina.getLongitud())));
				marca.title(oficina.getNombreMunicipio());
				marca.snippet(oficina.getNombreEstado());
				
				_mapa.addMarker(marca);
			}
		}
	}

	private void ponListaCercanas() {
		
		_dialogo = new ProgressDialog(LocalizacionOficinasActivity.this);
		
		_dialogo.setIcon(android.R.drawable.stat_notify_sync);
		_dialogo.setTitle("Infonavit");
		_dialogo.setMessage("Generando Listado de Oficinas ...");
		_dialogo.setCancelable(false);
		_dialogo.show();
		
		List<OficinaDTO> oficinasCercanas = _listaOficinas;
		if (_listaOficinas != null) {
			for (int i = 0; i < oficinasCercanas.size() - 1; i++) {
				for (int j = i; j < oficinasCercanas.size(); j++) {
					OficinaDTO tmpOficina1 = oficinasCercanas.get(i);
					OficinaDTO tmpOficina2 = oficinasCercanas.get(j);
					
					if (tmpOficina1.getLatitud().equals("") || tmpOficina1.getLongitud().equals("")) {
					
						oficinasCercanas.set(i, tmpOficina2);
						oficinasCercanas.set(j, tmpOficina1);

					} else if (tmpOficina2.getLatitud().equals("") || tmpOficina2.getLongitud().equals("")) {
						
						continue;
						
					} else {
					
						Location loc1 = new Location("punto1");
						loc1.setLatitude(Double.parseDouble(tmpOficina1.getLatitud()));
						loc1.setLongitude(Double.parseDouble(tmpOficina1.getLongitud()));
						
						Location loc2 = new Location("punto2");
						loc2.setLatitude(Double.parseDouble(tmpOficina2.getLatitud()));
						loc2.setLongitude(Double.parseDouble(tmpOficina2.getLongitud()));
						
						double distancia1 = _userLocation.distanceTo(loc1); 
						double distancia2 = _userLocation.distanceTo(loc2);
						
						if (distancia1 > distancia2){
							oficinasCercanas.set(i, tmpOficina2);
							oficinasCercanas.set(j, tmpOficina1);							
						}
					}
				}
			}
		}
		
		_listViewOficinas.removeAllViews();
		
		for (int i = 0; i < 10; i++){
			OficinaDTO office = oficinasCercanas.get(i);
			
			View view = getLayoutInflater().inflate(R.layout.layout_oficina_lista, null);
			view.setOnClickListener(new ListenerTabla(office));
			TextView title = (TextView)view.findViewById(R.id.itemLista_Title);
			TextView subtitle = (TextView)view.findViewById(R.id.itemLista_Subtitle);
			subtitle.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "eurostile.ttf"));
			title.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "eurostile.ttf"));
	
			title.setText(office.getNombreMunicipio());
			subtitle.setText(office.getNombreEstado());
			
			_listViewOficinas.addView(view);
		}
		_dialogo.dismiss();
	}
	
	private void centraMapaCompleto(){		
		LatLng posicion = new LatLng(20.683421,-103.39448);
		
		_mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 3.5f));
	}
	
	private void centraMapa(){
		
		if(_userLocation == null)
			setUserLocation();
		
		LatLng posicion = new LatLng(_userLocation.getLatitude(),_userLocation.getLongitude());
//		LatLng posicion = new LatLng(_mapa.getMyLocation().getLatitude(), _mapa.getMyLocation().getLongitude());
		
		_mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 15));
		
	}

	/**
	 * MŽtodos del Location Listener
	 */
	@Override
	public void onLocationChanged(Location location) {		
		_userLocation = location;
		_locationManager.removeUpdates(this);
		centraMapa();
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
		
	}

	/**
	 * MŽtodos del Info Window Adapter
	 */
	@Override
	public View getInfoWindow(Marker marker) {//Se llama primero
		return null;
	}
	
	@Override
	public View getInfoContents(Marker marker) {
		
		View infoWindow = getLayoutInflater().inflate(R.layout.info_window_layout, null);
		TextView title = (TextView)infoWindow.findViewById(R.id.infoWindow_Title);
		TextView subtitle = (TextView)infoWindow.findViewById(R.id.infoWindow_Subtitle);
		subtitle.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "eurostile.ttf"));
		title.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "eurostile.ttf"));
		title.setText(marker.getTitle());
		subtitle.setText(marker.getSnippet());
		
		return infoWindow;
	}

	/**
	 * MŽtodos del Info Window Click Listener
	 */
	@Override
	public void onInfoWindowClick(Marker marker) {
//		Oficina oficina = null;
		for (OficinaDTO oficina : _listaOficinas) {
			if (oficina.getLatitud().equals("") || oficina.getLongitud().equals(""))
				continue;
//			if (marker.getTitle().equals(oficina.getNombreMunicipio()) && marker.getSnippet().equals(oficina.getNombreEstado())){
			if (Double.parseDouble(oficina.getLatitud()) == marker.getPosition().latitude && Double.parseDouble(oficina.getLongitud()) == marker.getPosition().longitude){
				Intent intentDetalleOficina = new Intent(this,DetalleOficinaActivity.class);
				intentDetalleOficina.putExtra("oficina", oficina);
				startActivity(intentDetalleOficina);
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(v.equals(_btnCentrarMapa)){
			_listViewOficinas.removeAllViews();
			_btnMapaOficinas.setChecked(true);
			_btnListaCercanas.setChecked(false);
			centraMapa();
		} else if (v.equals(_btnZoomCompleto)){
			_listViewOficinas.removeAllViews();
			_btnMapaOficinas.setChecked(true);
			_btnListaCercanas.setChecked(false);
			centraMapaCompleto();
		} else if (v.equals(_btnListaCercanas)){
			if(_btnListaCercanas.isChecked()){
				ponListaCercanas();
				_btnMapaOficinas.setChecked(false);
			} else {
				_btnListaCercanas.setChecked(true);
			}
		} else if (v.equals(_btnMapaOficinas)) {
			_listViewOficinas.removeAllViews();
			_btnMapaOficinas.setChecked(true);
			_btnListaCercanas.setChecked(false);
		}
	}

	/**
	 * MŽtodos del Text Whatcher
	 * Se llaman en ese orden
	 */
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}
	
	@Override
	public void afterTextChanged(Editable s) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
		_listViewOficinas.removeAllViews();
		if (s.length() > 0){
			for (OficinaDTO oficina:_listaOficinas){
				
				Locale local = new Locale("es","MX");
				if (oficina.getNombreEstado().toLowerCase(local).contains(s) || oficina.getNombreMunicipio().toLowerCase(local).contains(s)){
					if(!oficina.getLatitud().equals("") && !oficina.getLongitud().equals("")){
					
						View view = getLayoutInflater().inflate(R.layout.layout_oficina_lista, null);
						view.setOnClickListener(new ListenerTabla(oficina));
						TextView title = (TextView)view.findViewById(R.id.itemLista_Title);
						TextView subtitle = (TextView)view.findViewById(R.id.itemLista_Subtitle);
						subtitle.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "eurostile.ttf"));
						title.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "eurostile.ttf"));
	
						title.setText(oficina.getNombreMunicipio());
						subtitle.setText(oficina.getNombreEstado());
						
						_listViewOficinas.addView(view);
					}
				}
				
			}
		}
}
	
	public class ListenerTabla implements OnClickListener {

		private OficinaDTO oficinaListener;
		
		public ListenerTabla(OficinaDTO oficina) {
			super();
			this.oficinaListener = oficina;
		}

		@Override
		public void onClick(View v) {
			if (oficinaListener.getLatitud().equals("")	|| oficinaListener.getLongitud().equals("")) {
				//NO HACER NADA
			} else {
				Intent intentDetalleOficina = new Intent(LocalizacionOficinasActivity.this, DetalleOficinaActivity.class);
				intentDetalleOficina.putExtra("oficina", oficinaListener);
				startActivity(intentDetalleOficina);
			}
		}

	}

}
