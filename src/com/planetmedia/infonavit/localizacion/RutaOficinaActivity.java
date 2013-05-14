package com.planetmedia.infonavit.localizacion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.planetmedia.infonavit.R;

public class RutaOficinaActivity extends Activity {

	double _origenLat;
	double _origenLon;
	double _destinoLat;
	double _destinoLon;
	private WebView _webView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.layout_ruta_oficina);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.header_solo);
		
		_origenLat = getIntent().getExtras().getDouble("origenLat");
		_origenLon = getIntent().getExtras().getDouble("origenLon");
		_destinoLat = getIntent().getExtras().getDouble("destinoLat");
		_destinoLon = getIntent().getExtras().getDouble("destinoLon");
		
		_webView = (WebView) findViewById(R.id.webViewRutaOficina);
		_webView.getSettings().setJavaScriptEnabled(true);
		_webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				dibujaRuta();
			}
		});

		_webView.loadUrl("file:///android_asset/webRuta.html");

	}

	private void dibujaRuta() {

		_webView.loadUrl("javascript:route(" + _origenLat + "," + _origenLon + ","	+ _destinoLat + "," + _destinoLon + ")");

	}

}
