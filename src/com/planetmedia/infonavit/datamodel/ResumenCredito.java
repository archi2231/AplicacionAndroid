package com.planetmedia.infonavit.datamodel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ResumenCredito {
	
	private String id;
	private String nombre;
	private String numCredito;
	private String fechaProceso;
	private String saldoTotalPesos;
	private String saldoTotalVSM;
	private String formaPago;
	//este campo se repite con el campo fchFinUltProrroga
	//private String fechaUltProrroga;
	private String pagoEsteMesVSM;
	private String pagoEsteMesPesos;
	private String totalDescAlDia;
	private String mesesOmisos;
	private String saldoOmisosPesos;
	private String fechaLimitePago;
	private String regimen;
	private String factorRea;
	private String factorRoa;
	private String fechaDeReestructura;
	private String saldoAcreditadoVSM;
	private String saldoAcreditadoPesos;
	private String saldoInfonavitVSM;
	private String saldoInfonavitPesos;
	private String saldoDeOmisosVSM;
	private String factoresRoaPesos;
	private String factoresReaPesos;
	private String capitalEnVSM;
	private String capitalEnPesos;
	private String interesEnVSM;
	private String interesEnPesos;
	private String accesoriosEnVSM;
	private String accesoriosEnPesos;
	private String tipoEscenario;
	private String mcaMotivCierre;
	private String seguroAdmiVSM;
	private String seguroAdmiPesos;
	private String fondoPPagoVSM;
	private String fondoPPagoPesos;
	private String comisAdmonVSM;
	private String comisAdmonPes;
	private String pagoCorriVSM;
	private String pagoCorriPes;
	private String fchFinUltProrroga;
	private String mcaReestructura;
	private String mcaPerdidaRellab;
	private String mcaFinProrroga01;
	private String mcaFinProrroga02;
	private String mcaOpcionesPago;
	private String pagoEsteMesPes;
	private String mntoultccVSM;
	private String mntoultccPes;
	private String lineaCaptura;
	private String marcaCierre;
	private String tipoDePago;
	private String marcSegCredito;
	private String mtoPagPendientesD;
	private String facReaMensualD;
	private String facRoaMensualD;
	private String segDaniosD;
	private String fondoPPD;
	private String credCCD;
	private String pagoAlCorrd;
	
	private SQLiteDatabase database;	

	public ResumenCredito(SQLiteDatabase database) {
    	this.database = database;
	}
	
	public void save(){
		
		ContentValues nuevoRegistro = new ContentValues();
		nuevoRegistro.put("id", "1");
		nuevoRegistro.put("nombre", this.nombre);
		nuevoRegistro.put("numCredito", this.numCredito);
		nuevoRegistro.put("fechaProceso", this.fechaProceso);
		nuevoRegistro.put("saldoTotalPesos", this.saldoTotalPesos);
		nuevoRegistro.put("saldoTotalVSM", this.saldoTotalVSM);
		nuevoRegistro.put("formaPago", this.formaPago);
		nuevoRegistro.put("pagoEsteMesVSM", this.pagoEsteMesVSM);
		nuevoRegistro.put("pagoEsteMesPesos", this.pagoEsteMesPesos);
		nuevoRegistro.put("totalDescAlDia", this.totalDescAlDia);
		nuevoRegistro.put("mesesOmisos", this.mesesOmisos);
		nuevoRegistro.put("saldoOmisosPesos", this.saldoOmisosPesos);
		nuevoRegistro.put("fechaLimitePago", this.fechaLimitePago);
		nuevoRegistro.put("regimen", this.regimen);
		nuevoRegistro.put("factorRea", this.factorRea);
		nuevoRegistro.put("factorRoa", this.factorRoa);
		nuevoRegistro.put("fechaDeReestructura", this.fechaDeReestructura);
		nuevoRegistro.put("saldoAcreditadoVSM", this.saldoAcreditadoVSM);
		nuevoRegistro.put("saldoAcreditadoPesos", this.saldoAcreditadoPesos);
		nuevoRegistro.put("saldoInfonavitVSM", this.saldoInfonavitVSM);
		nuevoRegistro.put("saldoInfonavitPesos", this.saldoInfonavitPesos);
		nuevoRegistro.put("saldoDeOmisosVSM", this.saldoDeOmisosVSM);
		nuevoRegistro.put("factoresRoaPesos", this.factoresRoaPesos);
		nuevoRegistro.put("factoresReaPesos", this.factoresReaPesos);
		nuevoRegistro.put("capitalEnVSM", this.capitalEnVSM);
		nuevoRegistro.put("capitalEnPesos", this.capitalEnPesos);
		nuevoRegistro.put("interesEnVSM", this.interesEnVSM);
		nuevoRegistro.put("interesEnPesos", this.interesEnPesos);
		nuevoRegistro.put("accesoriosEnVSM", this.accesoriosEnVSM);
		nuevoRegistro.put("accesoriosEnPesos", this.accesoriosEnPesos);
		nuevoRegistro.put("tipoEscenario", this.tipoEscenario);
		nuevoRegistro.put("mcaMotivCierre", this.mcaMotivCierre);
		nuevoRegistro.put("seguroAdmiVSM", this.seguroAdmiVSM);
		nuevoRegistro.put("seguroAdmiPesos", this.seguroAdmiPesos);
		nuevoRegistro.put("fondoPPagoVSM", this.fondoPPagoVSM);
		nuevoRegistro.put("fondoPPagoPesos", this.fondoPPagoPesos);
		nuevoRegistro.put("comisAdmonVSM", this.comisAdmonVSM);
		nuevoRegistro.put("comisAdmonPes", this.comisAdmonPes);
		nuevoRegistro.put("pagoCorriVSM", this.pagoCorriVSM);
		nuevoRegistro.put("pagoCorriPes", this.pagoCorriPes);
		nuevoRegistro.put("fchFinUltProrroga", this.fchFinUltProrroga);
		nuevoRegistro.put("mcaReestructura", this.mcaReestructura);
		nuevoRegistro.put("mcaPerdidaRellab", this.mcaPerdidaRellab);
		nuevoRegistro.put("mcaFinProrroga01", this.mcaFinProrroga01);
		nuevoRegistro.put("mcaFinProrroga02", this.mcaFinProrroga02);
		nuevoRegistro.put("mcaOpcionesPago", this.mcaOpcionesPago);
		nuevoRegistro.put("pagoEsteMesPes", this.pagoEsteMesPes);
		nuevoRegistro.put("mntoultccVSM", this.mntoultccVSM);
		nuevoRegistro.put("mntoultccPes", this.mntoultccPes);
		nuevoRegistro.put("lineaCaptura", this.lineaCaptura);
		nuevoRegistro.put("marcaCierre", this.marcaCierre);
		nuevoRegistro.put("tipoDePago", this.tipoDePago);
		nuevoRegistro.put("marcSegCredito", this.marcSegCredito);
		nuevoRegistro.put("mtoPagPendientesD", this.mtoPagPendientesD);
		nuevoRegistro.put("facReaMensualD", this.facReaMensualD);
		nuevoRegistro.put("facRoaMensualD", this.facRoaMensualD);
		nuevoRegistro.put("segDaniosD", this.segDaniosD);
		nuevoRegistro.put("fondoPPD", this.fondoPPD);
		nuevoRegistro.put("credCCD", this.credCCD);
		nuevoRegistro.put("pagoAlCorrd", this.pagoAlCorrd);
		
		try{
			database.insertWithOnConflict("RESUMENCREDITO", null, nuevoRegistro, SQLiteDatabase.CONFLICT_REPLACE);
			//database.insert("RESUMENCREDITO", null, nuevoRegistro);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void seek(String id){
    	
    	try{						   						
		    Cursor cur = database.rawQuery("SELECT * FROM RESUMENCREDITO WHERE id = ?", new String[]{id});
		    if(cur.getCount() > 0){
				while(cur.moveToNext()){
					this.setId(cur.getString(cur.getColumnIndex("id")));
					this.nombre = cur.getString(cur.getColumnIndex("nombre"));
					this.numCredito = cur.getString(cur.getColumnIndex("numCredito"));
					this.fechaProceso = cur.getString(cur.getColumnIndex("fechaProceso"));
					this.saldoTotalPesos = cur.getString(cur.getColumnIndex("saldoTotalPesos"));
					this.saldoTotalVSM = cur.getString(cur.getColumnIndex("saldoTotalVSM"));
					this.formaPago = cur.getString(cur.getColumnIndex("formaPago"));
					this.pagoEsteMesVSM = cur.getString(cur.getColumnIndex("pagoEsteMesVSM"));
					this.pagoEsteMesPesos = cur.getString(cur.getColumnIndex("pagoEsteMesPesos"));
					this.totalDescAlDia = cur.getString(cur.getColumnIndex("totalDescAlDia"));
					this.mesesOmisos = cur.getString(cur.getColumnIndex("mesesOmisos"));
					this.saldoOmisosPesos = cur.getString(cur.getColumnIndex("saldoOmisosPesos"));
					this.fechaLimitePago = cur.getString(cur.getColumnIndex("fechaLimitePago"));
					this.regimen = cur.getString(cur.getColumnIndex("regimen"));
					this.factorRea = cur.getString(cur.getColumnIndex("factorRea"));
					this.factorRoa = cur.getString(cur.getColumnIndex("factorRoa"));
					this.fechaDeReestructura = cur.getString(cur.getColumnIndex("fechaDeReestructura"));
					this.saldoAcreditadoVSM = cur.getString(cur.getColumnIndex("saldoAcreditadoVSM"));
					this.saldoAcreditadoPesos = cur.getString(cur.getColumnIndex("saldoAcreditadoPesos"));
					this.saldoInfonavitVSM = cur.getString(cur.getColumnIndex("saldoInfonavitVSM"));
					this.saldoInfonavitPesos = cur.getString(cur.getColumnIndex("saldoInfonavitPesos"));
					this.saldoDeOmisosVSM = cur.getString(cur.getColumnIndex("saldoDeOmisosVSM"));
					this.factoresRoaPesos = cur.getString(cur.getColumnIndex("factoresRoaPesos"));
					this.factoresReaPesos = cur.getString(cur.getColumnIndex("factoresReaPesos"));
					this.capitalEnVSM = cur.getString(cur.getColumnIndex("capitalEnVSM"));
					this.capitalEnPesos = cur.getString(cur.getColumnIndex("capitalEnPesos"));
					this.interesEnVSM = cur.getString(cur.getColumnIndex("interesEnVSM"));
					this.interesEnPesos = cur.getString(cur.getColumnIndex("interesEnPesos"));
					this.accesoriosEnVSM = cur.getString(cur.getColumnIndex("accesoriosEnVSM"));
					this.accesoriosEnPesos = cur.getString(cur.getColumnIndex("accesoriosEnPesos"));
					this.tipoEscenario = cur.getString(cur.getColumnIndex("tipoEscenario"));
					this.mcaMotivCierre = cur.getString(cur.getColumnIndex("mcaMotivCierre"));
					this.seguroAdmiVSM = cur.getString(cur.getColumnIndex("seguroAdmiVSM"));
					this.seguroAdmiPesos = cur.getString(cur.getColumnIndex("seguroAdmiPesos"));
					this.fondoPPagoVSM = cur.getString(cur.getColumnIndex("fondoPPagoVSM"));
					this.fondoPPagoPesos = cur.getString(cur.getColumnIndex("fondoPPagoPesos"));
					this.comisAdmonVSM = cur.getString(cur.getColumnIndex("comisAdmonVSM"));
					this.comisAdmonPes = cur.getString(cur.getColumnIndex("comisAdmonPes"));
					this.pagoCorriVSM = cur.getString(cur.getColumnIndex("pagoCorriVSM"));
					this.pagoCorriPes = cur.getString(cur.getColumnIndex("pagoCorriPes"));
					this.fchFinUltProrroga = cur.getString(cur.getColumnIndex("fchFinUltProrroga"));
					this.mcaReestructura = cur.getString(cur.getColumnIndex("mcaReestructura"));
					this.mcaPerdidaRellab = cur.getString(cur.getColumnIndex("mcaPerdidaRellab"));
					this.mcaFinProrroga01 = cur.getString(cur.getColumnIndex("mcaFinProrroga01"));
					this.mcaFinProrroga02 = cur.getString(cur.getColumnIndex("mcaFinProrroga02"));
					this.mcaOpcionesPago = cur.getString(cur.getColumnIndex("mcaOpcionesPago"));
					this.pagoEsteMesPes = cur.getString(cur.getColumnIndex("pagoEsteMesPes"));
					this.mntoultccVSM = cur.getString(cur.getColumnIndex("mntoultccVSM"));
					this.mntoultccPes = cur.getString(cur.getColumnIndex("mntoultccPes"));
					this.lineaCaptura = cur.getString(cur.getColumnIndex("lineaCaptura"));
					this.marcaCierre = cur.getString(cur.getColumnIndex("marcaCierre"));
					this.tipoDePago = cur.getString(cur.getColumnIndex("tipoDePago"));
					this.marcSegCredito = cur.getString(cur.getColumnIndex("marcSegCredito"));
					this.mtoPagPendientesD = cur.getString(cur.getColumnIndex("mtoPagPendientesD"));
					this.facReaMensualD = cur.getString(cur.getColumnIndex("facReaMensualD"));
					this.facRoaMensualD = cur.getString(cur.getColumnIndex("facRoaMensualD"));
					this.segDaniosD = cur.getString(cur.getColumnIndex("segDaniosD"));
					this.fondoPPD = cur.getString(cur.getColumnIndex("fondoPPD"));
					this.credCCD = cur.getString(cur.getColumnIndex("credCCD"));
					this.pagoAlCorrd = cur.getString(cur.getColumnIndex("pagoAlCorrd"));						
				}
		    }
		    
		    cur.close();
		    //database.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}    	
    }
	
	public void print(String id){
    	
    	try{						   						
		    Cursor cur = database.rawQuery("SELECT * FROM RESUMENCREDITO WHERE id = ?", new String[]{id});
		    
		    cur.moveToFirst();
			for(int counter = 0; counter < cur.getColumnCount(); counter++)			
				Log.d(cur.getColumnName(counter), cur.getString(counter));
		    
		    cur.close();
		    //database.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}    	
    }
	
	public boolean delete(){
    	
    	int result = database.delete("RESUMENCREDITO", "id = ?", new String[]{this.id});
    	
    	if(result > 0)
    		return true;
    	return false;
    }

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNumCredito() {
		return numCredito;
	}
	public void setNumCredito(String numCredito) {
		this.numCredito = numCredito;
	}
	public String getFechaProceso() {
		return fechaProceso;
	}
	public void setFechaProceso(String fechaProceso) {
		this.fechaProceso = fechaProceso;
	}
	public String getSaldoTotalPesos() {
		return saldoTotalPesos;
	}
	public void setSaldoTotalPesos(String saldoTotalPesos) {
		this.saldoTotalPesos = saldoTotalPesos;
	}
	public String getSaldoTotalVSM() {
		return saldoTotalVSM;
	}
	public void setSaldoTotalVSM(String saldoTotalVSM) {
		this.saldoTotalVSM = saldoTotalVSM;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	/*public String getFechaUltProrroga() {
		return fechaUltProrroga;
	}
	public void setFechaUltProrroga(String fechaUltProrroga) {
		this.fechaUltProrroga = fechaUltProrroga;
	}*/
	public String getPagoEsteMesVSM() {
		return pagoEsteMesVSM;
	}
	public void setPagoEsteMesVSM(String pagoEsteMesVSM) {
		this.pagoEsteMesVSM = pagoEsteMesVSM;
	}
	public String getPagoEsteMesPesos() {
		return pagoEsteMesPesos;
	}
	public void setPagoEsteMesPesos(String pagoEsteMesPesos) {
		this.pagoEsteMesPesos = pagoEsteMesPesos;
	}
	public String getTotalDescAlDia() {
		return totalDescAlDia;
	}
	public void setTotalDescAlDia(String totalDescAlDia) {
		this.totalDescAlDia = totalDescAlDia;
	}
	public String getMesesOmisos() {
		return mesesOmisos;
	}
	public void setMesesOmisos(String mesesOmisos) {
		this.mesesOmisos = mesesOmisos;
	}
	public String getSaldoOmisosPesos() {
		return saldoOmisosPesos;
	}
	public void setSaldoOmisosPesos(String saldoOmisosPesos) {
		this.saldoOmisosPesos = saldoOmisosPesos;
	}
	public String getFechaLimitePago() {
		return fechaLimitePago;
	}
	public void setFechaLimitePago(String fechaLimitePago) {
		this.fechaLimitePago = fechaLimitePago;
	}
	public String getRegimen() {
		return regimen;
	}
	public void setRegimen(String regimen) {
		this.regimen = regimen;
	}
	public String getFactorRea() {
		return factorRea;
	}
	public void setFactorRea(String factorRea) {
		this.factorRea = factorRea;
	}
	public String getFactorRoa() {
		return factorRoa;
	}
	public void setFactorRoa(String factorRoa) {
		this.factorRoa = factorRoa;
	}
	public String getFechaDeReestructura() {
		return fechaDeReestructura;
	}
	public void setFechaDeReestructura(String fechaDeReestructura) {
		this.fechaDeReestructura = fechaDeReestructura;
	}
	public String getSaldoAcreditadoVSM() {
		return saldoAcreditadoVSM;
	}
	public void setSaldoAcreditadoVSM(String saldoAcreditadoVSM) {
		this.saldoAcreditadoVSM = saldoAcreditadoVSM;
	}
	public String getSaldoAcreditadoPesos() {
		return saldoAcreditadoPesos;
	}
	public void setSaldoAcreditadoPesos(String saldoAcreditadoPesos) {
		this.saldoAcreditadoPesos = saldoAcreditadoPesos;
	}
	public String getSaldoInfonavitVSM() {
		return saldoInfonavitVSM;
	}
	public void setSaldoInfonavitVSM(String saldoInfonavitVSM) {
		this.saldoInfonavitVSM = saldoInfonavitVSM;
	}
	public String getSaldoInfonavitPesos() {
		return saldoInfonavitPesos;
	}
	public void setSaldoInfonavitPesos(String saldoInfonavitPesos) {
		this.saldoInfonavitPesos = saldoInfonavitPesos;
	}
	public String getSaldoDeOmisosVSM() {
		return saldoDeOmisosVSM;
	}
	public void setSaldoDeOmisosVSM(String saldoDeOmisosVSM) {
		this.saldoDeOmisosVSM = saldoDeOmisosVSM;
	}
	public String getFactoresRoaPesos() {
		return factoresRoaPesos;
	}
	public void setFactoresRoaPesos(String factoresRoaPesos) {
		this.factoresRoaPesos = factoresRoaPesos;
	}
	public String getFactoresReaPesos() {
		return factoresReaPesos;
	}
	public void setFactoresReaPesos(String factoresReaPesos) {
		this.factoresReaPesos = factoresReaPesos;
	}
	public String getCapitalEnVSM() {
		return capitalEnVSM;
	}
	public void setCapitalEnVSM(String capitalEnVSM) {
		this.capitalEnVSM = capitalEnVSM;
	}
	public String getCapitalEnPesos() {
		return capitalEnPesos;
	}
	public void setCapitalEnPesos(String capitalEnPesos) {
		this.capitalEnPesos = capitalEnPesos;
	}
	public String getInteresEnVSM() {
		return interesEnVSM;
	}
	public void setInteresEnVSM(String interesEnVSM) {
		this.interesEnVSM = interesEnVSM;
	}
	public String getInteresEnPesos() {
		return interesEnPesos;
	}
	public void setInteresEnPesos(String interesEnPesos) {
		this.interesEnPesos = interesEnPesos;
	}
	public String getAccesoriosEnVSM() {
		return accesoriosEnVSM;
	}
	public void setAccesoriosEnVSM(String accesoriosEnVSM) {
		this.accesoriosEnVSM = accesoriosEnVSM;
	}
	public String getAccesoriosEnPesos() {
		return accesoriosEnPesos;
	}
	public void setAccesoriosEnPesos(String accesoriosEnPesos) {
		this.accesoriosEnPesos = accesoriosEnPesos;
	}
	public String getTipoEscenario() {
		return tipoEscenario;
	}
	public void setTipoEscenario(String tipoEscenario) {
		this.tipoEscenario = tipoEscenario;
	}
	public String getMcaMotivCierre() {
		return mcaMotivCierre;
	}
	public void setMcaMotivCierre(String mcaMotivCierre) {
		this.mcaMotivCierre = mcaMotivCierre;
	}
	public String getSeguroAdmiVSM() {
		return seguroAdmiVSM;
	}
	public void setSeguroAdmiVSM(String seguroAdmiVSM) {
		this.seguroAdmiVSM = seguroAdmiVSM;
	}
	public String getSeguroAdmiPesos() {
		return seguroAdmiPesos;
	}
	public void setSeguroAdmiPesos(String seguroAdmiPesos) {
		this.seguroAdmiPesos = seguroAdmiPesos;
	}
	public String getFondoPPagoVSM() {
		return fondoPPagoVSM;
	}
	public void setFondoPPagoVSM(String fondoPPagoVSM) {
		this.fondoPPagoVSM = fondoPPagoVSM;
	}
	public String getFondoPPagoPesos() {
		return fondoPPagoPesos;
	}
	public void setFondoPPagoPesos(String fondoPPagoPesos) {
		this.fondoPPagoPesos = fondoPPagoPesos;
	}
	public String getComisAdmonVSM() {
		return comisAdmonVSM;
	}
	public void setComisAdmonVSM(String comisAdmonVSM) {
		this.comisAdmonVSM = comisAdmonVSM;
	}
	public String getComisAdmonPes() {
		return comisAdmonPes;
	}
	public void setComisAdmonPes(String comisAdmonPes) {
		this.comisAdmonPes = comisAdmonPes;
	}
	public String getPagoCorriVSM() {
		return pagoCorriVSM;
	}
	public void setPagoCorriVSM(String pagoCorriVSM) {
		this.pagoCorriVSM = pagoCorriVSM;
	}
	public String getPagoCorriPes() {
		return pagoCorriPes;
	}
	public void setPagoCorriPes(String pagoCorriPes) {
		this.pagoCorriPes = pagoCorriPes;
	}
	public String getFchFinUltProrroga() {
		return fchFinUltProrroga;
	}
	public void setFchFinUltProrroga(String fchFinUltProrroga) {
		this.fchFinUltProrroga = fchFinUltProrroga;
	}
	public String getMcaReestructura() {
		return mcaReestructura;
	}
	public void setMcaReestructura(String mcaReestructura) {
		this.mcaReestructura = mcaReestructura;
	}
	public String getMcaPerdidaRellab() {
		return mcaPerdidaRellab;
	}
	public void setMcaPerdidaRellab(String mcaPerdidaRellab) {
		this.mcaPerdidaRellab = mcaPerdidaRellab;
	}
	public String getMcaFinProrroga01() {
		return mcaFinProrroga01;
	}
	public void setMcaFinProrroga01(String mcaFinProrroga01) {
		this.mcaFinProrroga01 = mcaFinProrroga01;
	}
	public String getMcaFinProrroga02() {
		return mcaFinProrroga02;
	}
	public void setMcaFinProrroga02(String mcaFinProrroga02) {
		this.mcaFinProrroga02 = mcaFinProrroga02;
	}
	public String getMcaOpcionesPago() {
		return mcaOpcionesPago;
	}
	public void setMcaOpcionesPago(String mcaOpcionesPago) {
		this.mcaOpcionesPago = mcaOpcionesPago;
	}
	public String getPagoEsteMesPes() {
		return pagoEsteMesPes;
	}
	public void setPagoEsteMesPes(String pagoEsteMesPes) {
		this.pagoEsteMesPes = pagoEsteMesPes;
	}
	public String getMntoultccVSM() {
		return mntoultccVSM;
	}
	public void setMntoultccVSM(String mntoultccVSM) {
		this.mntoultccVSM = mntoultccVSM;
	}
	public String getMntoultccPes() {
		return mntoultccPes;
	}
	public void setMntoultccPes(String mntoultccPes) {
		this.mntoultccPes = mntoultccPes;
	}
	public String getLineaCaptura() {
		return lineaCaptura;
	}
	public void setLineaCaptura(String lineaCaptura) {
		this.lineaCaptura = lineaCaptura;
	}
	public String getMarcaCierre() {
		return marcaCierre;
	}
	public void setMarcaCierre(String marcaCierre) {
		this.marcaCierre = marcaCierre;
	}
	public String getTipoDePago() {
		return tipoDePago;
	}
	public void setTipoDePago(String tipoDePago) {
		this.tipoDePago = tipoDePago;
	}
	public String getMarcSegCredito() {
		return marcSegCredito;
	}
	public void setMarcSegCredito(String marcSegCredito) {
		this.marcSegCredito = marcSegCredito;
	}
	public String getMtoPagPendientesD() {
		return mtoPagPendientesD;
	}
	public void setMtoPagPendientesD(String mtoPagPendientesD) {
		this.mtoPagPendientesD = mtoPagPendientesD;
	}
	public String getFacReaMensualD() {
		return facReaMensualD;
	}
	public void setFacReaMensualD(String facReaMensualD) {
		this.facReaMensualD = facReaMensualD;
	}
	public String getFacRoaMensualD() {
		return facRoaMensualD;
	}
	public void setFacRoaMensualD(String facRoaMensualD) {
		this.facRoaMensualD = facRoaMensualD;
	}
	public String getSegDaniosD() {
		return segDaniosD;
	}
	public void setSegDaniosD(String segDaniosD) {
		this.segDaniosD = segDaniosD;
	}
	public String getFondoPPD() {
		return fondoPPD;
	}
	public void setFondoPPD(String fondoPPD) {
		this.fondoPPD = fondoPPD;
	}
	public String getCredCCD() {
		return credCCD;
	}
	public void setCredCCD(String credCCD) {
		this.credCCD = credCCD;
	}
	public String getPagoAlCorrd() {
		return pagoAlCorrd;
	}
	public void setPagoAlCorrd(String pagoAlCorrd) {
		this.pagoAlCorrd = pagoAlCorrd;
	}	
}
