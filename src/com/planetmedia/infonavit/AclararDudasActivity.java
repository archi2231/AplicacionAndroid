package com.planetmedia.infonavit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class AclararDudasActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.layout_aclarar_dudas);		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_solo);  
		
		Button btnRegsitrate = (Button)findViewById(R.id.btn_ayuda_registrate);
		
		final Typeface eurostile = Typeface.createFromAsset(getAssets(), "eurostile.ttf");  
		
		btnRegsitrate.setTypeface(eurostile);
		
		btnRegsitrate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(getString(R.string.url_registrate))));  				
			}
		});
	}

}
