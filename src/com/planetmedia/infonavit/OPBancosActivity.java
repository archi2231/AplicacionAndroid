package com.planetmedia.infonavit;

import com.planetmedia.infonavit.utils.ButtonArrow;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class OPBancosActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.layout_opcion_pagos_bancos);		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_solo); 
		
		final ButtonArrow btnBanamex = (ButtonArrow)findViewById(R.id.btn_pagos_banamex);
		LinearLayout infoBanamex = (LinearLayout)findViewById(R.id.row_info_banamex);
		ImageButton arrow1 = (ImageButton)findViewById(R.id.arrow_expander1);
		
		btnBanamex.attachResources(infoBanamex, arrow1);				
		
		final ButtonArrow btnBancoAzteca = (ButtonArrow)findViewById(R.id.btn_pagos_banco_azteca);
		LinearLayout infoBancoAzteca = (LinearLayout)findViewById(R.id.row_info_banco_azteca);
		ImageButton arrow2 = (ImageButton)findViewById(R.id.arrow_expander2);
		
		btnBancoAzteca.attachResources(infoBancoAzteca, arrow2);	
		
		final ButtonArrow btnBancoBanorte = (ButtonArrow)findViewById(R.id.btn_pagos_banco_banorte);
		LinearLayout infoBancoBanorte = (LinearLayout)findViewById(R.id.row_info_banco_banorte);
		ImageButton arrow3 = (ImageButton)findViewById(R.id.arrow_expander3);
		
		btnBancoBanorte.attachResources(infoBancoBanorte, arrow3);	
		
		final ButtonArrow btnBancoSantander = (ButtonArrow)findViewById(R.id.btn_pagos_banco_santander);
		LinearLayout infoBancoSantander = (LinearLayout)findViewById(R.id.row_info_banco_santander);
		ImageButton arrow4 = (ImageButton)findViewById(R.id.arrow_expander4);
		
		btnBancoSantander.attachResources(infoBancoSantander, arrow4);	
		
		final ButtonArrow btnBancoBancomer = (ButtonArrow)findViewById(R.id.btn_pagos_banco_bancomer);
		LinearLayout infoBancoBancomer = (LinearLayout)findViewById(R.id.row_info_banco_bancomer);
		ImageButton arrow5 = (ImageButton)findViewById(R.id.arrow_expander5);
		
		btnBancoBancomer.attachResources(infoBancoBancomer, arrow5);	
		
		final ButtonArrow btnBancoScotiabanck = (ButtonArrow)findViewById(R.id.btn_pagos_banco_scotiabank);
		LinearLayout infoBancoScotiabanck = (LinearLayout)findViewById(R.id.row_info_banco_scotiabanck);
		ImageButton arrow6 = (ImageButton)findViewById(R.id.arrow_expander6);
		
		btnBancoScotiabanck.attachResources(infoBancoScotiabanck, arrow6);	
		
		final ButtonArrow btnBancoBansefi = (ButtonArrow)findViewById(R.id.btn_pagos_banco_bansefi);
		LinearLayout infoBancoBansefi = (LinearLayout)findViewById(R.id.row_info_banco_bansefi);
		ImageButton arrow7 = (ImageButton)findViewById(R.id.arrow_expander7);
		
		btnBancoBansefi.attachResources(infoBancoBansefi, arrow7);
		
		final ButtonArrow btnBancoHSBC = (ButtonArrow)findViewById(R.id.btn_pagos_banco_hsbc);
		LinearLayout infoBancoHSBC = (LinearLayout)findViewById(R.id.row_info_banco_hsbc);
		ImageButton arrow8 = (ImageButton)findViewById(R.id.arrow_expander8);
		
		btnBancoHSBC.attachResources(infoBancoHSBC, arrow8);
	}

}
