package com.planetmedia.infonavit;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class OPTiendasActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.layout_opcion_pagos_tiendas);		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_solo); 
		
	}

}
