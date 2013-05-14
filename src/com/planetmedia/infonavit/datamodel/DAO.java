package com.planetmedia.infonavit.datamodel;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DAO {
	
	private SQLiteDatabase database;
	
	public DAO(SQLiteDatabase database) {
		this.database = database;
	}
	
	public void insertAhorros(String id, String nombre, String nss, String numDeCredito, String fechaDeCorte, String saldoEnPesos, String saldoVSM, String idUsuario){
		
		ContentValues nuevoRegistro = new ContentValues();
	    nuevoRegistro.put("id", id);
	    nuevoRegistro.put("nombre", nombre);
	    nuevoRegistro.put("nss", nss);
	    nuevoRegistro.put("numDeCredito", numDeCredito);
	    nuevoRegistro.put("fechaDeCorte", fechaDeCorte);
	    nuevoRegistro.put("saldoEnPesos", saldoEnPesos);
	    nuevoRegistro.put("saldoVSM", saldoVSM);
	    nuevoRegistro.put("idUsuario", idUsuario);
	   
	    database.insert("Ahorros", null, nuevoRegistro);
		
	}
	
	public void insertAreas(String id, String nombre, String descripcion){
		
		ContentValues nuevoRegistro = new ContentValues();
	    nuevoRegistro.put("id", id);
	    nuevoRegistro.put("nombre", nombre);
	    nuevoRegistro.put("descripcion", descripcion);

	    database.insert("Areas", null, nuevoRegistro);
	}
	
	public void insertAvisos(String id, String contenido, String fechaDescarga){
		
		ContentValues nuevoRegistro = new ContentValues();
	    nuevoRegistro.put("id", id);
	    nuevoRegistro.put("contenido", contenido);
	    nuevoRegistro.put("fechaDescarga", fechaDescarga);

	    database.insert("Avisos", null, nuevoRegistro);
	}

	public void insertUsuario(String id, String nombreUsuario, String estatus, String CCnumber, String numeroSS, String direccion, String correoElectronico, String rfc){
	
		ContentValues nuevoRegistro = new ContentValues();
	    nuevoRegistro.put("id", id);
	    nuevoRegistro.put("nombreUsuario", nombreUsuario);
	    nuevoRegistro.put("estatus", estatus);
	    nuevoRegistro.put("CCnumber", CCnumber);
	    nuevoRegistro.put("numeroSS", numeroSS);
	    nuevoRegistro.put("direccion", direccion);
	    nuevoRegistro.put("correoElectronico", correoElectronico);
	    nuevoRegistro.put("rfc", rfc);
	   
	    database.insert("Usuario", null, nuevoRegistro);
	}
	
	public void insertLugarPago(String id, String tipo, String nombre, String procedimiento){
		
		ContentValues nuevoRegistro = new ContentValues();
	    nuevoRegistro.put("id", id);
	    nuevoRegistro.put("tipo", tipo);
	    nuevoRegistro.put("nombre", nombre);
	    nuevoRegistro.put("procedimiento", procedimiento);

	    database.insert("LugarPago", null, nuevoRegistro);
	}
	
	public void insertOficina(String id, String idOficina, String nombreOficina, String direccion, String entidadFederativa,
			String tipoOficina, String latitud, String longitud, String codigoPostal, String delegacion, String colonia, String referencia, 
			String telefono, String horario, String areas){
				
		ContentValues nuevoRegistro = new ContentValues();
		nuevoRegistro.put("id", id);
		nuevoRegistro.put("idOficina", idOficina);
		nuevoRegistro.put("nombreOficina", nombreOficina);
		nuevoRegistro.put("direccion", direccion);
		nuevoRegistro.put("entidadFederativa", entidadFederativa);
		nuevoRegistro.put("tipoOficina", tipoOficina);
		nuevoRegistro.put("latitud", latitud);
		nuevoRegistro.put("longitud", longitud);
		nuevoRegistro.put("codigoPostal", codigoPostal);
		nuevoRegistro.put("delegacion", delegacion);
		nuevoRegistro.put("colonia", colonia);
		nuevoRegistro.put("referencia", referencia);
		nuevoRegistro.put("telefono", telefono);
		nuevoRegistro.put("horario", horario);
		nuevoRegistro.put("areas", areas);		
		
		database.insert("Oficina", null, nuevoRegistro);
	}
	
	public void insertEstadoDeCuenta(String id, String idUsuario, String nombreUsuario, String fechaDescarga, String tipoDeCuenta,
		   String periodo, String fechaLimiteDePago, String nombreCliente, String noCredito, String lineaDeCaptura, String pagoContratado, 
		   String tasaInteresAnual, String fechaOtorgamiento, String noDeMensualidadesVencidas, String nivelCumplimientoPago, 
		   String montoVencido, String descuentoTasaInteres, String tasaInteresAnualCobrada, String saldoAnterior, 
		   String ajustePeriodosAnteriores, String saldoAjustado, String interesPeriodo, String comisionesOtrosCargos, String ultimoPago,
		   String traspasosOtroAbonos, String saldoActualResumen, String ultimoPagoInfonavit, String pagoDeTuPatron, String interesesGeneradosPeriodo, 
		   String interesCubiertoInfonavit, String interesCubiertoAcreditado, String saldoAcargoAcreditado, String saldoAcargoInfonavit, 
		   String saldoActualReestructura, String tasaInteresAplicada1, String tasaInteresAplicada1_FechaInicio, String tasaInteresAplicada1_FechaFin, 
		   String tasaInteresAplicada2, String tasaInteresAplicada2_FechaInicio, String tasaInteresAplicada2_FechaFin){
		
		ContentValues nuevoRegistro = new ContentValues();
		
		nuevoRegistro.put("idUsuario",idUsuario);
		nuevoRegistro.put("nombreUsuario",nombreUsuario);
		nuevoRegistro.put("fechaDescarga",fechaDescarga);
		nuevoRegistro.put("tipoDeCuenta",tipoDeCuenta);
		nuevoRegistro.put("periodo",periodo);
		nuevoRegistro.put("fechaLimiteDePago",fechaLimiteDePago);
		nuevoRegistro.put("nombreCliente",nombreCliente);
		nuevoRegistro.put("noCredito",noCredito);
		nuevoRegistro.put("lineaDeCaptura",lineaDeCaptura);
		nuevoRegistro.put("pagoContratado",pagoContratado);
		nuevoRegistro.put("tasaInteresAnual",tasaInteresAnual);
		nuevoRegistro.put("fechaOtorgamiento",fechaOtorgamiento);
		nuevoRegistro.put("noDeMensualidadesVencidas",noDeMensualidadesVencidas);
		nuevoRegistro.put("nivelCumplimientoPago",nivelCumplimientoPago);
		nuevoRegistro.put("montoVencido",montoVencido);
		nuevoRegistro.put("descuentoTasaInteres",descuentoTasaInteres);
		nuevoRegistro.put("tasaInteresAnualCobrada",tasaInteresAnualCobrada);
		nuevoRegistro.put("saldoAnterior",saldoAnterior);
		nuevoRegistro.put("ajustePeriodosAnteriores",ajustePeriodosAnteriores);
		nuevoRegistro.put("saldoAjustado",saldoAjustado);
		nuevoRegistro.put("interesPeriodo",interesPeriodo);
		nuevoRegistro.put("comisionesOtrosCargos",comisionesOtrosCargos);
		nuevoRegistro.put("ultimoPago",ultimoPago);
		nuevoRegistro.put("traspasosOtroAbonos",traspasosOtroAbonos);
		nuevoRegistro.put("saldoActualResumen",saldoActualResumen);
		nuevoRegistro.put("ultimoPagoInfonavit",ultimoPagoInfonavit);
		nuevoRegistro.put("pagoDeTuPatron",pagoDeTuPatron);
		nuevoRegistro.put("interesesGeneradosPeriodo",interesesGeneradosPeriodo);
		nuevoRegistro.put("interesCubiertoInfonavit",interesCubiertoInfonavit);
		nuevoRegistro.put("interesCubiertoAcreditado",interesCubiertoAcreditado);
		nuevoRegistro.put("saldoAcargoAcreditado",saldoAcargoAcreditado);
		nuevoRegistro.put("saldoAcargoInfonavit",saldoAcargoInfonavit);
		nuevoRegistro.put("saldoActualReestructura",saldoActualReestructura);
		nuevoRegistro.put("tasaInteresAplicada1",tasaInteresAplicada1);
		nuevoRegistro.put("tasaInteresAplicada1_FechaInicio",tasaInteresAplicada1_FechaInicio);
		nuevoRegistro.put("tasaInteresAplicada1_FechaFin",tasaInteresAplicada1_FechaFin);
		nuevoRegistro.put("tasaInteresAplicada2",tasaInteresAplicada2);
		nuevoRegistro.put("tasaInteresAplicada2_FechaInicio",tasaInteresAplicada2_FechaInicio);
		nuevoRegistro.put("tasaInteresAplicada2_FechaFin",tasaInteresAplicada2_FechaFin);
		
		database.insert("EstadoDeCuenta", null, nuevoRegistro);
	}
	
	/*public void saveROAReestructura(){
		
		IFROAReestructura  cuenta = new IFROAReestructura();
		
		cuenta.setAjustePeriodosAnteriores(ajustePeriodosAnteriores);
		cuenta.setComisionesYOtrosCargos(comisionesYOtrosCargos);
		cuenta.setFechaOtorgamiento(fechaOtorgamiento);
		cuenta.setInteresesPeriodo(interesesPeriodo);
		cuenta.setMontoVencido(montoVencido);
		cuenta.setNivelCunmplimientoPago(nivelCunmplimientoPago);
		cuenta.setNombre(nombre);
	}*/

}
