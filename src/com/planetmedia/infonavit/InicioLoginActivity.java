package com.planetmedia.infonavit;

import java.io.InputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.planetmedia.infonavit.WSConsume.Autentificacion;
import com.planetmedia.infonavit.WSConsume.IFParentRequest;
import com.planetmedia.infonavit.WSConsume.IFParentResponse;
import com.planetmedia.infonavit.WSConsume.IFserviceManager;
import com.planetmedia.infonavit.datamodel.dto.UsuarioDTO;
import com.planetmedia.infonavit.localizacion.LocalizacionOficinasActivity;

public class InicioLoginActivity extends Activity {

	private ProgressDialog dialogAutentificando;

	private static final int REQ_ACLARAR_DUDAS = 0;

	private static final int MENU_OPCIONES_PAGO = 1;
	private static final int REQ_OFICINAS_ATENCION = 2;
	private static final int MENU_INFONATEL = 3;

	private static final int PAGOS_CENTRO_PAGO = 4;
	private static final int PAGOS_BANCOS = 5;
	private static final int PAGOS_TIENDAS = 6;
	private static final int PAGOS_USACANADA = 7;

	private Typeface eurostile;

	private boolean isPagosExpanded;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.layout_inicio_login);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header);

		final ScrollView sv = (ScrollView) findViewById(R.id.scrollView1);

		eurostile = Typeface.createFromAsset(getAssets(), "eurostile.ttf");

		final TextView txtUsuario = (TextView) findViewById(R.id.txt_login_usuario);
		final TextView txtPassword = (TextView) findViewById(R.id.txt_login_password);
		final TextView txtRecordar = (TextView) findViewById(R.id.txt_recordar_acceso);

		final ToggleButton swtRecordar = (ToggleButton) findViewById(R.id.swt_recordar_acceso);

		final TextView txtBienvenido1 = (TextView) findViewById(R.id.txt_bienvenido1);
		final TextView txtBienvenido2 = (TextView) findViewById(R.id.txt_bienvenido2);

		final TextView txtPagos1 = (TextView) findViewById(R.id.txt_pago1);
		final TextView txtpagos2 = (TextView) findViewById(R.id.txt_pago2);
		final TextView txtAtencion1 = (TextView) findViewById(R.id.txt_atncion1);
		final TextView txtAtencion2 = (TextView) findViewById(R.id.txt_atencion2);
		final TextView txtInfonatel1 = (TextView) findViewById(R.id.txt_infonatel);
		final TextView txtInfonatel2 = (TextView) findViewById(R.id.txt_inonatel2);

		txtUsuario.setTypeface(eurostile);
		txtPassword.setTypeface(eurostile);
		txtRecordar.setTypeface(eurostile);
		swtRecordar.setTypeface(eurostile);

		txtBienvenido1.setTypeface(eurostile);
		txtBienvenido2.setTypeface(eurostile);

		txtPagos1.setTypeface(eurostile);
		txtpagos2.setTypeface(eurostile);
		txtAtencion1.setTypeface(eurostile);
		txtAtencion2.setTypeface(eurostile);
		txtInfonatel1.setTypeface(eurostile);
		txtInfonatel2.setTypeface(eurostile);

		final ImageButton btnExpandList = (ImageButton) findViewById(R.id.btn_pagos);
		final LinearLayout listaExpandible = (LinearLayout) findViewById(R.id.listaExpandible);
		listaExpandible.setVisibility(View.GONE);
		final ImageView btnExpanderArrow = (ImageView) findViewById(R.id.btn_expander_arrow);

		final ImageButton btnAclararDudas = (ImageButton) findViewById(R.id.btn_aclarar_dudas);
		final ImageButton btnInfonatel = (ImageButton) findViewById(R.id.btn_infonatel);
		final ImageButton btnOfincinas = (ImageButton) findViewById(R.id.btn_oficinas);

		final TextView lblCentrosPago = (TextView) findViewById(R.id.txt_label_centrosdepago);
		final TextView lblBancos = (TextView) findViewById(R.id.txt_label_bancos);
		final TextView lblAutoservicios = (TextView) findViewById(R.id.txt_label_autoservicios);
		final TextView lblEstasdosUnidos = (TextView) findViewById(R.id.txt_label_estadosunidos);

		lblCentrosPago.setTypeface(eurostile);
		lblBancos.setTypeface(eurostile);
		lblAutoservicios.setTypeface(eurostile);
		lblEstasdosUnidos.setTypeface(eurostile);

		final ImageButton btnPagosBancos = (ImageButton) findViewById(R.id.btn_pagos_bancos);
		btnPagosBancos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(InicioLoginActivity.this,
						OPBancosActivity.class));
			}
		});

		final ImageButton btnPagosCentros = (ImageButton) findViewById(R.id.btn_pagos_centros);
		btnPagosCentros.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(InicioLoginActivity.this,
						OPCentrosPagoActivity.class));
			}
		});

		final ImageButton btnPagosTiendas = (ImageButton) findViewById(R.id.btn_pagos_tiendas);
		btnPagosTiendas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(InicioLoginActivity.this,
						OPTiendasActivity.class));

			}
		});

		final ImageButton btnPagosEstadosunidos = (ImageButton) findViewById(R.id.btn_pagos_estadosunidos);
		btnPagosEstadosunidos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(InicioLoginActivity.this,
						OPEstadosunidosActivity.class));
			}
		});

		btnAclararDudas.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentAclararDudas = new Intent(
						InicioLoginActivity.this, AclararDudasActivity.class);
				startActivityForResult(intentAclararDudas, REQ_ACLARAR_DUDAS);
			}
		});

		btnInfonatel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_DIAL, Uri
						.parse("tel:018000083900")));
				// startActivity(new Intent(InicioLoginActivity.this,
				// RegisterActivity.class));
			}
		});

		btnOfincinas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TOD: Corregir Mapas!!!
				Intent intentOficinas = new Intent(InicioLoginActivity.this,
						LocalizacionOficinasActivity.class);
//				 startActivityForResult(intentOficinas,
//				 REQ_OFICINAS_ATENCION);
				startActivity(intentOficinas);
			}
		});

		btnExpandList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!isPagosExpanded) {
					isPagosExpanded = true;
					listaExpandible.setVisibility(View.VISIBLE);
					btnExpandList.setSelected(true);
					btnExpanderArrow.setSelected(true);
					/*
					 * sv.post(new Runnable() { public void run() {
					 * sv.smoothScrollTo(0, sv.getBottom()); } });
					 */
				} else {
					isPagosExpanded = false;
					listaExpandible.setVisibility(View.GONE);
					btnExpandList.setSelected(false);
					btnExpanderArrow.setSelected(false);
				}
			}
		});

		makeLabelsLinkeables();


//		if (user.getContrasenia() != null) {
//			txtUsuario.setText(user.getNombreUsuario());
//			txtPassword.setText(user.getContrasenia());
//			swtRecordar.setChecked(true);
//		}

		Button btnGuardar = (Button) findViewById(R.id.btn_login_guardar);
		btnGuardar.setTypeface(eurostile);

		btnGuardar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// HAPPY PATH
				// startActivity(new Intent(InicioLoginActivity.this,
				// ResumenCreditoActivity.class));

				dialogAutentificando = new ProgressDialog(
						InicioLoginActivity.this);
				dialogAutentificando
						.setIcon(android.R.drawable.stat_notify_sync);
				dialogAutentificando.setTitle("Autenticando");
				dialogAutentificando.setMessage("Validando credenciales...");
				dialogAutentificando.setCancelable(false);

				if (txtUsuario.getText().toString().equals("") || txtPassword.getText().toString().equals("")) {
					Toast.makeText(InicioLoginActivity.this,
							"Ingresa todos tus datos.", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				dialogAutentificando.show();

				new Thread() {
					public void run() {
						try {

							IFParentRequest request = new IFParentRequest();

							request.generaRequestAccesoUsuario(txtUsuario.getText().toString(),
									txtPassword.getText().toString());

							IFserviceManager manager = new IFserviceManager();
							InputStream message = manager
									.execWebService(request);

							IFParentResponse response = new IFParentResponse(
									message);

							UsuarioDTO usuario = response.obtenAutentificacion();

							if (usuario!=null) {

								//TODO: Revisar esto
								// Intent intentMainFramework = new
								// Intent(InicioLoginActivity.this,
								// ResumenCreditoActivity2.class);
								// startActivity(intentMainFramework);
								Intent estadoCuenta = new Intent(InicioLoginActivity.this,
										EstadoCuentaActivity.class);
								startActivity(estadoCuenta);
								
//								Intent ahorro = new Intent(InicioLoginActivity.this, AhorrosActivity.class);
//								startActivity(ahorro);
							
							}
						} catch (Exception e) {
							e.printStackTrace();

							InicioLoginActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									
									Toast.makeText(InicioLoginActivity.this, "Login Exitoso",
											Toast.LENGTH_SHORT).show();	
								}
							});
						} finally {
							dialogAutentificando.dismiss();
						}
					}
				}.start();
			}
		});
	}

	private void openLink(String url) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}

	private void makeLabelsLinkeables() {
		
//		Typeface typeFace = Typeface.createFromAsset(getApplicationContext().getAssets(), "eurostile.ttf");
		((TextView)findViewById(R.id.lbl_info_no_registro)).setTypeface(eurostile);
		((TextView)findViewById(R.id.lbl_info_olvidaste_contrasenia)).setTypeface(eurostile);
		TextView linkeable = (TextView) findViewById(R.id.lbl_info_activar);
		linkeable.setText(Html.fromHtml("<b>Hazlo aqui</b>"));
		linkeable.setTypeface(eurostile);
		linkeable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openLink(getString(R.string.url_registrate));
			}
		});

		linkeable = (TextView) findViewById(R.id.lbl_info_recuperar);
		linkeable.setText(Html.fromHtml("<b>Recuperar</b>"));
		linkeable.setTypeface(eurostile);
		linkeable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openLink(getString(R.string.url_olvidaste));
			}
		});
	}

}
