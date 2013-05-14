package com.planetmedia.infonavit;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

import com.planetmedia.infonavit.tabsactivities.TabAhorroActivity;
import com.planetmedia.infonavit.tabsactivities.TabEstadodecuentaActivity;
import com.planetmedia.infonavit.tabsactivities.TabInformacioncreditoActivity;
import com.planetmedia.infonavit.tabsactivities.TabNotificacionesActivity;
import com.planetmedia.infonavit.tabsactivities.TabSaldoActivity;

public class MainTabsActivity extends TabActivity implements TabHost.TabContentFactory {
	
	public static Context app;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tab_mainframework);
        
        final TabHost tabHost = getTabHost();        
        
        // Tab for Ahorro
        TabSpec ahorro = tabHost.newTabSpec("Ahorro");
        //ahorro.setIndicator("Ahorro", getResources().getDrawable(R.drawable.bike));
        View tabviewAhorro = createTabView(tabHost.getContext(), "Ahorro");
        ahorro.setIndicator(tabviewAhorro);
        Intent ahorroIntent = new Intent(this, TabAhorroActivity.class);
        ahorro.setContent(ahorroIntent);
        
        // Tab for Estado de cuenta
        TabSpec estadocuenta = tabHost.newTabSpec("Estadodecuenta");     
        //estadocuenta.setIndicator("Estado de cuenta", getResources().getDrawable(R.drawable.bus));
        View tabviewCuenta = createTabView(tabHost.getContext(), "Cuenta");
        estadocuenta.setIndicator(tabviewCuenta);
        Intent edocuentaIntent = new Intent(this, TabEstadodecuentaActivity.class);
        estadocuenta.setContent(edocuentaIntent);
        
        // Tab for informacion de credito
        TabSpec informacioncredito = tabHost.newTabSpec("InformacionCredito");
        //informacioncredito.setIndicator("Informacion de mi crédito", getResources().getDrawable(R.drawable.bike));
        View tabviewCredito = createTabView(tabHost.getContext(), "Crédito");
        informacioncredito.setIndicator(tabviewCredito);
        Intent infcreditoIntent = new Intent(this, TabInformacioncreditoActivity.class);
        informacioncredito.setContent(infcreditoIntent);
        
        // Tab for informacion de notificaciones
        TabSpec notificaciones = tabHost.newTabSpec("Notificaciones");
        //notificaciones.setIndicator("Notificaciones", getResources().getDrawable(R.drawable.bike));
        View tabviewNotificaciones = createTabView(tabHost.getContext(), "Notificaciones");
        notificaciones.setIndicator(tabviewNotificaciones);
        Intent notificacionesIntent = new Intent(this, TabNotificacionesActivity.class);
        notificaciones.setContent(notificacionesIntent);
        
        //Tab for informacion de credito
        TabSpec saldo = tabHost.newTabSpec("Saldo");
        //saldo.setIndicator("Saldo", getResources().getDrawable(R.drawable.bike));
        View tabviewSaldo = createTabView(tabHost.getContext(), "Saldo");
        saldo.setIndicator(tabviewSaldo);
        Intent saldoIntent = new Intent(this, TabSaldoActivity.class);
        saldo.setContent(saldoIntent);
                
        tabHost.addTab(ahorro); 
        tabHost.addTab(estadocuenta);
        tabHost.addTab(informacioncredito);
        tabHost.addTab(notificaciones);
        tabHost.addTab(saldo);        
    }
	
	private static View createTabView(final Context context, final String text) {
		/*View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		view.setBackgroundResource(R.drawable.tab_bag_ahorros_selector);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;*/
		return null;
	}

    public View createTabContent(String tag) {
       return null;
    }

}
