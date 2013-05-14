package com.planetmedia.infonavit;

import com.planetmedia.infonavit.datamodel.dto.UsuarioDTO;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OpcionesUsuarioActivity extends Activity{
	
	private UsuarioDTO user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_opciones_usuario);
		
		user = UsuarioDTO.getUniqueUser();
//		user.seek("1");
		
		final TextView nombre = (TextView)findViewById(R.id.lbl_usuario_nombre);
		final TextView domicilio = (TextView)findViewById(R.id.lbl_usuario_domicilio);
		final TextView nss = (TextView)findViewById(R.id.lbl_usuario_nss);
		final TextView credito = (TextView)findViewById(R.id.lbl_usuario_credito);
		final TextView rfc = (TextView)findViewById(R.id.lbl_usuario_rfc);
		final TextView email = (TextView)findViewById(R.id.lbl_usuario_email);
		final TextView telefono = (TextView)findViewById(R.id.lbl_usuario_telefono);
		
		nombre.setText(user.getNombrePila());
		domicilio.setText(user.getDireccion());
		nss.setText(user.getNumeroSS());
		credito.setText(user.getCCnumber());
		rfc.setText(user.getRfc());
		email.setText(user.getCorreoElectronico());
		telefono.setText(user.getNumTelefonico());
		
		final Button borrarCredencial = (Button)findViewById(R.id.btn_borrar_credencial);
		
		borrarCredencial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
//				user.delete();
//				user.seek("1");
				setResult(RESULT_OK, null);
				finish();
			}
		});
		
	}

}
