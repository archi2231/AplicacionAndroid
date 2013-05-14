package com.planetmedia.infonavit.WSConsume;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

import com.planetmedia.infonavit.MainActivity;
import com.planetmedia.infonavit.EstadoDeCuenta.IFEstadoDeCuentaGenerico;
import com.planetmedia.infonavit.EstadoDeCuenta.IFR40Generico;
import com.planetmedia.infonavit.EstadoDeCuenta.IFREANormal;
import com.planetmedia.infonavit.EstadoDeCuenta.IFREAR40;
import com.planetmedia.infonavit.EstadoDeCuenta.IFREAR40Reestructura;
import com.planetmedia.infonavit.EstadoDeCuenta.IFREAReestructura;
import com.planetmedia.infonavit.EstadoDeCuenta.IFROANormal;
import com.planetmedia.infonavit.EstadoDeCuenta.IFROAR40;
import com.planetmedia.infonavit.EstadoDeCuenta.IFROAR40Reestructura;
import com.planetmedia.infonavit.EstadoDeCuenta.IFROAReestructura;
import com.planetmedia.infonavit.EstadoDeCuenta.IFReestructuraGenerico;
import com.planetmedia.infonavit.datamodel.Ahorros;
import com.planetmedia.infonavit.datamodel.ResumenCredito;
import com.planetmedia.infonavit.datamodel.dto.ControlCatalogoDTO;
import com.planetmedia.infonavit.datamodel.dto.DetalleOficinaDTO;
import com.planetmedia.infonavit.datamodel.dto.UsuarioDTO;
import com.planetmedia.infonavit.localizacion.OficinaDTO;

public class IFParentResponse {
	
	private static Document elements;
	
	public IFParentResponse(InputStream aData) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			elements = db.parse(aData);
		} catch (ParserConfigurationException e) {			
			e.printStackTrace();
		} catch (SAXException e) {		
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	private static String getTagValue(String tag){
		try{
			return elements.getElementsByTagName(tag).item(0).getTextContent();
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d("Le tag viene vacio", tag);
			return "";
		}
	}
	
	public void obtenLineaCaptura(){
		
		//NodeList linea = elements.getElementsByTagName("p571:OPLineaCapturaReturn");
		
		Log.d("leolog", "primer nodo" + elements.getFirstChild().getNodeName());
		
		/*for(int i=0; i < linea.getLength(); i++){
			Node nodo = linea.item(i);
			NodeList nodos = nodo.getChildNodes();
			Log.d("nodos ", nodos.item(1).getTextContent());
			//Log.d(nodo.getNodeName(), nodo.getTextContent());
		}*/
        //String mensaje = nl.item(0).getTextContent();
		
	}
	
	public DetalleOficinaDTO obtenDetalleOficinas(){
		
		DetalleOficinaDTO oficina = new DetalleOficinaDTO();
				
		oficina.setCalle(getTagValue("calle"));
		oficina.setNumExt(getTagValue("numExt"));
		oficina.setNumInt(getTagValue("numInt"));
		oficina.setColonia(getTagValue("colonia"));
		oficina.setMunicipio(getTagValue("municipio"));
		oficina.setEntreCalle(getTagValue("entreCalle"));
		oficina.setyCalle(getTagValue("YCalle"));
		oficina.setReferencia(getTagValue("ref"));
		oficina.setCodigoPostal(getTagValue("CP"));
		oficina.setLada(getTagValue("lada"));
		oficina.setTelefono(getTagValue("tel"));
		oficina.setExtension(getTagValue("ext"));
		oficina.setHorario(getTagValue("horario"));
		
		return oficina;
		
	}
	
	public String obtenOficinasCatalogoVersion(){
		
		String version = getTagValue("getVersionCatalogoCesisReturn");
		
		return version;
		
	}
	
	public List<OficinaDTO> obtenOficinasCatalogo(){
		List<OficinaDTO> lista = new ArrayList<OficinaDTO>();
		NodeList list_cesis = elements.getElementsByTagName("InfoCesi");
		
		for(int i=0; i<list_cesis.getLength(); i++){
		
			NodeList node_cesi =  list_cesis.item(i).getChildNodes();
			OficinaDTO oficina = new OficinaDTO();
			for(int j=0; j<node_cesi.getLength(); j++){				
				
				if(node_cesi.item(j).getNodeName().equals("idDelEdo"))
					oficina.setIdEntidadFederativa(node_cesi.item(j).getTextContent());
				
				else if(node_cesi.item(j).getNodeName().equals("idPlaza"))
					oficina.setIdPlaza(node_cesi.item(j).getTextContent());
					
				else if(node_cesi.item(j).getNodeName().equals("delCesis"))
					oficina.setTipoOficina(node_cesi.item(j).getTextContent());
				
				else if(node_cesi.item(j).getNodeName().equals("delEdo"))
					oficina.setNombreEstado(node_cesi.item(j).getTextContent());
				
				else if(node_cesi.item(j).getNodeName().equals("nombre"))
					oficina.setNombreMunicipio(node_cesi.item(j).getTextContent());
				
				else if(node_cesi.item(j).getNodeName().equals("latitud"))
					oficina.setLatitud(node_cesi.item(j).getTextContent());
				
				else if(node_cesi.item(j).getNodeName().equals("longitud"))
					oficina.setLongitud(node_cesi.item(j).getTextContent());
				
			}
			lista.add(oficina);
			
			oficina = null;
			
//			cesi.save();									
		}
		
		return lista;
		
	}
	
	public UsuarioDTO obtenAutentificacion(){
		
		UsuarioDTO usuario = null;
		try{
			String code = elements.getElementsByTagName("code").item(0).getTextContent();		
			String message = elements.getElementsByTagName("message").item(0).getTextContent();
			
			if(code.equals("1000")){
				
				usuario = UsuarioDTO.getUniqueUser();
				
				usuario.setNombrePila(getTagValue("givenName"));
				usuario.setCCnumber(getTagValue("infonavitCCnumber"));
				usuario.setNumeroSS(getTagValue("nss"));		
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		 return usuario;		
	}
	
	public ResumenCredito obtenResumenDeCredito(){
		
		ResumenCredito resumencredito = new ResumenCredito(MainActivity.dbInfonavit);
		
		try{		
			String RETORNO = getTagValue("RETORNO");
			String DESCRIPCION = getTagValue("DESCRIPCION");			
			String NUMCRD = getTagValue("NUMCRD");
			String REGIMEN = getTagValue("REGIMEN");
			String NOMBRE = getTagValue("NOMBRE");
			String SALDOTOTALPESOS = getTagValue("SALDOTOTALPESOS");
			String saldototalvsm = getTagValue("saldototalvsm");
			String factorrea = getTagValue("factorrea");
			String factorroa = getTagValue("factorroa");
			String POOL = getTagValue("POOL");
			String fechadereestructura = getTagValue("fechadereestructura");
			String saldoacreditadovsm = getTagValue("saldoacreditadovsm");
			String saldoacreditadopesos = getTagValue("saldoacreditadopesos");
			String saldoinfonavitvsm = getTagValue("saldoinfonavitvsm");
			String saldoinfonavitpesos = getTagValue("saldoinfonavitpesos");
			String mesesomisos = getTagValue("mesesomisos");
			String saldodeomisosvsm = getTagValue("saldodeomisosvsm");
			String saldodeomisospesos = getTagValue("saldodeomisospesos");
			String factoresroapesos = getTagValue("factoresroapesos");
			String factoresreapesos = getTagValue("factoresreapesos");
			String capitalenvsm = getTagValue("capitalenvsm");
			String capitalenpesos = getTagValue("capitalenpesos");
			String interesenvsm = getTagValue("interesenvsm");
			String interesenpesos = getTagValue("interesenpesos");
			String accesoriosenvsm = getTagValue("accesoriosenvsm");
			String accesoriosenpesos = getTagValue("accesoriosenpesos");
			String fechaproceso = getTagValue("fechaproceso");
			String tipoescenario = getTagValue("tipoescenario");
			String mcamotivcierre = getTagValue("mcamotivcierre");
			String marcacredcomp = getTagValue("marcacredcomp");
			String seguroadmivsm = getTagValue("seguroadmivsm");
			String seguroadmipesos = getTagValue("seguroadmipesos");
			String fondoppagovsm = getTagValue("fondoppagovsm");
			String fondoppagopes = getTagValue("fondoppagopes");
			String comisadmonvsm = getTagValue("comisadmonvsm");
			String comisadmonpes = getTagValue("comisadmonpes");
			String pagocorrivsm = getTagValue("pagocorrivsm");
			String pagocorripes = getTagValue("pagocorripes");
			String Fchfinultprorroga = getTagValue("Fchfinultprorroga");
			String Mcareestructura = getTagValue("Mcareestructura");
			String Mcaperdidarellab = getTagValue("Mcaperdidarellab");
			String mcafinprorroga01 = getTagValue("mcafinprorroga01");
			String mcafinprorroga02 = getTagValue("mcafinprorroga02");
			String Mcaopcionespago = getTagValue("Mcaopcionespago");
			String Pagoestemesvsm = getTagValue("Pagoestemesvsm");
			String pagoestemespes = getTagValue("pagoestemespes");
			String Mntoultccvsm = getTagValue("Mntoultccvsm");
			String Mntoultccpes = getTagValue("Mntoultccpes");
			String Formadepago = getTagValue("Formadepago");
			String Fechlimitepago = getTagValue("Fechlimitepago");
			String Lineacaptura = getTagValue("Lineacaptura");
			String Marcadecierre = getTagValue("Marcadecierre");
			String Tipodepago = getTagValue("Tipodepago");
			String Marcasegcredito = getTagValue("Marcasegcredito");
			String Mtopagpendientesd = getTagValue("Mtopagpendientesd");
			String Facreamensuald = getTagValue("Facreamensuald");
			String Facroamensuald = getTagValue("Facroamensuald");
			String Segdaniosd = getTagValue("Segdaniosd");
			String Fondoppd = getTagValue("Fondoppd");
			String Credccd = getTagValue("Credccd");
			String Totadescaldia = getTagValue("Totadescaldia");
			String Pagoalcorrd = getTagValue("Pagoalcorrd");
			
			resumencredito.setNombre(NOMBRE); 
			resumencredito.setNumCredito(NUMCRD); 
			resumencredito.setFechaProceso(fechaproceso); 
			resumencredito.setSaldoTotalPesos(SALDOTOTALPESOS); 
			resumencredito.setSaldoTotalVSM(saldototalvsm); 
			resumencredito.setFormaPago(Formadepago); 
			//resumencredito.setFechaUltProrroga(fechaUltProrroga); 
			resumencredito.setPagoEsteMesVSM(Pagoestemesvsm); 
			resumencredito.setPagoEsteMesPesos(pagoestemespes); 
			resumencredito.setTotalDescAlDia(Totadescaldia);
			resumencredito.setMesesOmisos(mesesomisos);
			resumencredito.setSaldoOmisosPesos(saldodeomisospesos); 
			resumencredito.setFechaLimitePago(Fechlimitepago); 
			resumencredito.setRegimen(REGIMEN); 
			resumencredito.setFactorRea(factorrea); 
			resumencredito.setFactorRoa(factorroa); 
			resumencredito.setFechaDeReestructura(fechadereestructura); 
			resumencredito.setSaldoAcreditadoVSM(saldoacreditadovsm); 
			resumencredito.setSaldoAcreditadoPesos(saldoacreditadopesos); 
			resumencredito.setSaldoInfonavitVSM(saldoinfonavitvsm); 
			resumencredito.setSaldoInfonavitPesos(saldoinfonavitpesos); 
			resumencredito.setSaldoDeOmisosVSM(saldodeomisosvsm); 
			resumencredito.setFactoresRoaPesos(factoresroapesos); 
			resumencredito.setFactoresReaPesos(factoresreapesos); 
			resumencredito.setCapitalEnVSM(capitalenvsm); 
			resumencredito.setCapitalEnPesos(capitalenpesos); 
			resumencredito.setInteresEnVSM(interesenvsm); 
			resumencredito.setInteresEnPesos(interesenpesos); 
			resumencredito.setAccesoriosEnVSM(accesoriosenvsm); 
			resumencredito.setAccesoriosEnPesos(accesoriosenpesos); 
			resumencredito.setTipoEscenario(tipoescenario); 
			resumencredito.setMcaMotivCierre(mcamotivcierre); 
			resumencredito.setSeguroAdmiVSM(seguroadmivsm); 
			resumencredito.setSeguroAdmiPesos(seguroadmipesos); 
			resumencredito.setFondoPPagoVSM(fondoppagovsm); 
			resumencredito.setFondoPPagoPesos(fondoppagopes); 
			resumencredito.setComisAdmonVSM(comisadmonvsm); 
			resumencredito.setComisAdmonPes(comisadmonpes); 
			resumencredito.setPagoCorriVSM(pagocorrivsm); 
			resumencredito.setPagoCorriPes(pagocorripes); 
			resumencredito.setFchFinUltProrroga(Fchfinultprorroga); 
			resumencredito.setMcaReestructura(Mcareestructura); 
			resumencredito.setMcaPerdidaRellab(Mcaperdidarellab); 
			resumencredito.setMcaFinProrroga01(mcafinprorroga01); 
			resumencredito.setMcaFinProrroga02(mcafinprorroga02); 
			resumencredito.setMcaOpcionesPago(Mcaopcionespago); 
			resumencredito.setPagoEsteMesPes(pagoestemespes); 
			resumencredito.setMntoultccVSM(Mntoultccvsm); 
			resumencredito.setMntoultccPes(Mntoultccpes); 
			resumencredito.setLineaCaptura(Lineacaptura); 
			resumencredito.setMarcaCierre(Marcadecierre); 
			resumencredito.setTipoDePago(Tipodepago); 
			resumencredito.setMarcSegCredito(Marcasegcredito); 
			resumencredito.setMtoPagPendientesD(Mtopagpendientesd); 
			resumencredito.setFacReaMensualD(Facreamensuald); 
			resumencredito.setFacRoaMensualD(Facroamensuald); 
			resumencredito.setSegDaniosD(Segdaniosd); 
			resumencredito.setFondoPPD(Fondoppd); 
			resumencredito.setCredCCD(Credccd); 
			resumencredito.setPagoAlCorrd(Pagoalcorrd);
			
			resumencredito.save();
			
			Log.d("Resultado", DESCRIPCION);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return resumencredito;
	}
	
	public IFEstadoDeCuentaGenerico obtenEstadoDeCuenta(){
		
		//TODO: Quitar esto
//		String xml = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.AMPCECJL.FDECAWBE.Request.com\"> <SOAP-ENV:Body> <AMPCECJLOperationResponse xmlns=\"http://www.AMPCECJL.FDECAWBE.Response.com\"> <reg_salida_eca> <sec_respuesta_codigo> <sec_codigo>00000000</sec_codigo> <sec_des_msj>ESTA ES LA INFORMACION ACTUALIZADA DE TU ESTADO DE</sec_des_msj> </sec_respuesta_codigo> <sec_datos_salida> <sec_datos_generales> <sec_tipo_formato>08</sec_tipo_formato> <sec_credito>0911372667</sec_credito> <sec_nombre>ARISTA SANCHEZ LIDIA</sec_nombre> <sec_calle>BASALTO 18 - 103</sec_calle> <sec_colonia>PEDREGAL DE SANTO DOMINGO</sec_colonia> <sec_cod_pos>4369</sec_cod_pos> <sec_poblacion>COYOACAN</sec_poblacion> <sec_num_nss>94078207720</sec_num_nss> <sec_cve_rfc>AISL8203311Z3</sec_cve_rfc> <sec_pool>9001</sec_pool> <sec_cve_curp>AISL820331MDFRND07</sec_cve_curp> <sec_linea_captura>0911372667017151209</sec_linea_captura> <sec_porcentaje_monto>$   11,107.40</sec_porcentaje_monto> <sec_tasa_int>10.00%</sec_tasa_int> <sec_fecha_otorgamiento>01 FEB 2012</sec_fecha_otorgamiento> </sec_datos_generales> <sec_saldos_corte> <sec_mto_sdo_al_corte_pes>$  879,380.88</sec_mto_sdo_al_corte_pes> <sec_mto_sdo_venc_pes></sec_mto_sdo_venc_pes> <sec_fch_limite_pago>28 FEB 2013</sec_fch_limite_pago> <sec_periodo_del>1 AGO AL 31 ENE 2013</sec_periodo_del> </sec_saldos_corte> <sec_resumen_movs> <sec_mto_sdo_ant_vsm>567.380</sec_mto_sdo_ant_vsm> <sec_mto_sdo_ant_pes>1,117,001.00</sec_mto_sdo_ant_pes> <sec_mto_sdo_int_per_vsm>26.893</sec_mto_sdo_int_per_vsm> <sec_mto_sdo_int_per_pes>52,944.24</sec_mto_sdo_int_per_pes> <sec_mto_sdo_com_car_vsm>1.482</sec_mto_sdo_com_car_vsm> <sec_mto_sdo_com_car_pes>2,917.61</sec_mto_sdo_com_car_pes> <sec_mto_sdo_tu_pago_vsm>140.752</sec_mto_sdo_tu_pago_vsm> <sec_mto_sdo_tu_pago_pes>277,098.46</sec_mto_sdo_tu_pago_pes> <sec_mto_sdo_pag_pat_vsm>8.322</sec_mto_sdo_pag_pat_vsm> <sec_mto_sdo_pag_pat_pes>16,383.52</sec_mto_sdo_pag_pat_pes> <sec_mto_sdo_pag_inf_vsm>0.000</sec_mto_sdo_pag_inf_vsm> <sec_mto_sdo_pag_inf_pes></sec_mto_sdo_pag_inf_pes> <sec_mto_sdo_tra_abo_vsm>0.000</sec_mto_sdo_tra_abo_vsm> <sec_mto_sdo_tra_abo_pes></sec_mto_sdo_tra_abo_pes> <sec_mto_sdo_mto_ven_vsm>0.000</sec_mto_sdo_mto_ven_vsm> <sec_mto_sdo_mto_ven_pes></sec_mto_sdo_mto_ven_pes> <sec_mto_sdo_act_vsm>446.681</sec_mto_sdo_act_vsm> <sec_mto_sdo_act_pes>879,380.88</sec_mto_sdo_act_pes> <sec_mto_sdo_acr_vsm>0.000</sec_mto_sdo_acr_vsm> <sec_mto_sdo_acr_pes>0.00</sec_mto_sdo_acr_pes> <sec_mto_sdo_inf_vsm>0.000</sec_mto_sdo_inf_vsm> <sec_mto_sdo_inf_pes>0.00</sec_mto_sdo_inf_pes> <sec_mto_sdo_res_vsm>0.000</sec_mto_sdo_res_vsm> <sec_mto_sdo_res_pes>0.00</sec_mto_sdo_res_pes> <sec_des_men_cartera>FELICIDADES, TE INVITAMOS A MANTENER EL SANO MANEJO DE TU CREDITO HIPOTECARIO.</sec_des_men_cartera> <sec_des_men_general>A PARTIR DEL 1 DE ENERO DE 2013, EL SALARIO MINIMO SE ACTUALIZO, POR LO QUE EL SALDO DE TU CREDITO SE AJUSTO EN LA MISMA PROPORCION EN QUE AUMENTO EL SALARIO MINIMO VIGENTE EN EL DISTRITO FEDERAL.</sec_des_men_general> </sec_resumen_movs> <sec_como_voy> <sec_con_per_pag_12>2</sec_con_per_pag_12> <sec_con_per_pag_11>1</sec_con_per_pag_11> <sec_con_per_pag_10>1</sec_con_per_pag_10> <sec_con_per_pag_09>1</sec_con_per_pag_09> <sec_con_per_pag_08>1</sec_con_per_pag_08> <sec_con_per_pag_07>1</sec_con_per_pag_07> <sec_con_per_pag_06>1</sec_con_per_pag_06> <sec_con_per_pag_05>1</sec_con_per_pag_05> <sec_con_per_pag_04>1</sec_con_per_pag_04> <sec_con_per_pag_03>1</sec_con_per_pag_03> <sec_con_per_pag_02>1</sec_con_per_pag_02> <sec_con_per_pag_01>2</sec_con_per_pag_01> <sec_num_men_ven>000</sec_num_men_ven> <sec_niv_cumpl>AL CORRIENTE</sec_niv_cumpl> <sec_pag_efect>11</sec_pag_efect> <sec_pag_pend>349</sec_pag_pend> <sec_mto_aju_per_ant_vsm>0.000</sec_mto_aju_per_ant_vsm> <sec_mto_aju_per_ant_pes>.01</sec_mto_aju_per_ant_pes> <sec_mto_sdo_ajus_vsm>567.380</sec_mto_sdo_ajus_vsm> <sec_mto_sdo_ajus_pes>1,117,001.01</sec_mto_sdo_ajus_pes> <sec_cre_demandado></sec_cre_demandado> <sec_max_tran>019</sec_max_tran> <sec_mto_sdo_tot_tran>282,470.18</sec_mto_sdo_tot_tran> <sec_mto_sdo_tot_com>0.00</sec_mto_sdo_tot_com> <sec_mto_sdo_tot_seg>-2,808.14</sec_mto_sdo_tot_seg> <sec_mto_sdo_tot_int>-52,471.68</sec_mto_sdo_tot_int> <sec_mto_sdo_tot_cap>-227,190.35</sec_mto_sdo_tot_cap> </sec_como_voy> <sec_datos_regla40> <sec_tas_int_anu_r40>0.00%</sec_tas_int_anu_r40> <sec_tas_int_sub_r40>0.00%</sec_tas_int_sub_r40> <sec_int_real_per_r40>0.000</sec_int_real_per_r40> <sec_int_sub_per_r40>0.000</sec_int_sub_per_r40> <sec_fec_fin_aplica_1>/  /</sec_fec_fin_aplica_1> <sec_fec_ini_aplica_2>/  /</sec_fec_ini_aplica_2> <sec_tasa_int_aplica_1>0.00%</sec_tasa_int_aplica_1> <sec_fec_fin_aplica_2>/  /</sec_fec_fin_aplica_2> <sec_int_sub_pag_inf>0.000</sec_int_sub_pag_inf> <sec_tasa_int_aplica_2>0.00%</sec_tasa_int_aplica_2> <sec_fec_ini_aplica_1>/  /</sec_fec_ini_aplica_1> <sec_tipo_pie>4</sec_tipo_pie> </sec_datos_regla40> <sec_datos_generales_trans> <sec_transacciones> <sec_tip_transaccion>1</sec_tip_transaccion> <sec_fch_transaccion>07/SEP/2012</sec_fch_transaccion> <sec_cve_transaccion>8880</sec_cve_transaccion> <sec_descripcion>PAGO REGULAR</sec_descripcion> <sec_mto_imp_trn_pes>5,313.10</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>-468.02</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>-4,845.08</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>1</sec_tip_transaccion> <sec_fch_transaccion>07/SEP/2012</sec_fch_transaccion> <sec_cve_transaccion>8880</sec_cve_transaccion> <sec_descripcion>PAGO REGULAR</sec_descripcion> <sec_mto_imp_trn_pes>22,233.95</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>-468.02</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>-12,947.38</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>-8,818.54</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>1</sec_tip_transaccion> <sec_fch_transaccion>07/NOV/2012</sec_fch_transaccion> <sec_cve_transaccion>8880</sec_cve_transaccion> <sec_descripcion>PAGO REGULAR</sec_descripcion> <sec_mto_imp_trn_pes>5,227.84</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>-468.02</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>-4,759.81</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>1</sec_tip_transaccion> <sec_fch_transaccion>07/NOV/2012</sec_fch_transaccion> <sec_cve_transaccion>8880</sec_cve_transaccion> <sec_descripcion>PAGO REGULAR</sec_descripcion> <sec_mto_imp_trn_pes>22,233.95</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>-468.02</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>-12,884.85</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>-8,881.07</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>1</sec_tip_transaccion> <sec_fch_transaccion>28/DIC/2012</sec_fch_transaccion> <sec_cve_transaccion>8880</sec_cve_transaccion> <sec_descripcion>PAGO REGULAR</sec_descripcion> <sec_mto_imp_trn_pes>199,999.51</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>-468.02</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>-14,872.53</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>-184,658.95</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>1</sec_tip_transaccion> <sec_fch_transaccion>07/ENE/2013</sec_fch_transaccion> <sec_cve_transaccion>8880</sec_cve_transaccion> <sec_descripcion>PAGO REGULAR</sec_descripcion> <sec_mto_imp_trn_pes>5,227.84</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>-468.02</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>-2,162.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>-2,597.81</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>1</sec_tip_transaccion> <sec_fch_transaccion>07/ENE/2013</sec_fch_transaccion> <sec_cve_transaccion>8880</sec_cve_transaccion> <sec_descripcion>PAGO REGULAR</sec_descripcion> <sec_mto_imp_trn_pes>22,233.95</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>0.00</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>-22,233.95</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>3</sec_tip_transaccion> <sec_fch_transaccion>31/AGO/2012</sec_fch_transaccion> <sec_cve_transaccion>7011</sec_cve_transaccion> <sec_descripcion>SEGURO/COMISION</sec_descripcion> <sec_mto_imp_trn_pes>255.80</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>255.80</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>3</sec_tip_transaccion> <sec_fch_transaccion>31/AGO/2012</sec_fch_transaccion> <sec_cve_transaccion>7011</sec_cve_transaccion> <sec_descripcion>SEGURO/COMISION</sec_descripcion> <sec_mto_imp_trn_pes>212.22</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>212.22</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>3</sec_tip_transaccion> <sec_fch_transaccion>30/SEP/2012</sec_fch_transaccion> <sec_cve_transaccion>7011</sec_cve_transaccion> <sec_descripcion>SEGURO/COMISION</sec_descripcion> <sec_mto_imp_trn_pes>255.80</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>255.80</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>3</sec_tip_transaccion> <sec_fch_transaccion>30/SEP/2012</sec_fch_transaccion> <sec_cve_transaccion>7011</sec_cve_transaccion> <sec_descripcion>SEGURO/COMISION</sec_descripcion> <sec_mto_imp_trn_pes>212.22</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>212.22</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>3</sec_tip_transaccion> <sec_fch_transaccion>31/OCT/2012</sec_fch_transaccion> <sec_cve_transaccion>7011</sec_cve_transaccion> <sec_descripcion>SEGURO/COMISION</sec_descripcion> <sec_mto_imp_trn_pes>255.80</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>255.80</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>3</sec_tip_transaccion> <sec_fch_transaccion>31/OCT/2012</sec_fch_transaccion> <sec_cve_transaccion>7011</sec_cve_transaccion> <sec_descripcion>SEGURO/COMISION</sec_descripcion> <sec_mto_imp_trn_pes>212.22</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>212.22</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>3</sec_tip_transaccion> <sec_fch_transaccion>30/NOV/2012</sec_fch_transaccion> <sec_cve_transaccion>7011</sec_cve_transaccion> <sec_descripcion>SEGURO/COMISION</sec_descripcion> <sec_mto_imp_trn_pes>255.80</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>255.80</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>3</sec_tip_transaccion> <sec_fch_transaccion>30/NOV/2012</sec_fch_transaccion> <sec_cve_transaccion>7011</sec_cve_transaccion> <sec_descripcion>SEGURO/COMISION</sec_descripcion> <sec_mto_imp_trn_pes>212.22</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>212.22</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>3</sec_tip_transaccion> <sec_fch_transaccion>31/DIC/2012</sec_fch_transaccion> <sec_cve_transaccion>7011</sec_cve_transaccion> <sec_descripcion>SEGURO/COMISION</sec_descripcion> <sec_mto_imp_trn_pes>255.80</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>255.80</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>3</sec_tip_transaccion> <sec_fch_transaccion>31/DIC/2012</sec_fch_transaccion> <sec_cve_transaccion>7011</sec_cve_transaccion> <sec_descripcion>SEGURO/COMISION</sec_descripcion> <sec_mto_imp_trn_pes>212.22</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>212.22</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>3</sec_tip_transaccion> <sec_fch_transaccion>31/ENE/2013</sec_fch_transaccion> <sec_cve_transaccion>7011</sec_cve_transaccion> <sec_descripcion>SEGURO/COMISION</sec_descripcion> <sec_mto_imp_trn_pes>265.77</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>265.77</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion>3</sec_tip_transaccion> <sec_fch_transaccion>31/ENE/2013</sec_fch_transaccion> <sec_cve_transaccion>7011</sec_cve_transaccion> <sec_descripcion>SEGURO/COMISION</sec_descripcion> <sec_mto_imp_trn_pes>220.49</sec_mto_imp_trn_pes> <sec_mto_imp_com_pes>0.00</sec_mto_imp_com_pes> <sec_mto_imp_seg_pes>220.49</sec_mto_imp_seg_pes> <sec_mto_imp_int_pes>0.00</sec_mto_imp_int_pes> <sec_mto_imp_cap_pes>0.00</sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> <sec_transacciones> <sec_tip_transaccion></sec_tip_transaccion> <sec_fch_transaccion></sec_fch_transaccion> <sec_cve_transaccion></sec_cve_transaccion> <sec_descripcion></sec_descripcion> <sec_mto_imp_trn_pes></sec_mto_imp_trn_pes> <sec_mto_imp_com_pes></sec_mto_imp_com_pes> <sec_mto_imp_seg_pes></sec_mto_imp_seg_pes> <sec_mto_imp_int_pes></sec_mto_imp_int_pes> <sec_mto_imp_cap_pes></sec_mto_imp_cap_pes> </sec_transacciones> </sec_datos_generales_trans> </sec_datos_salida> </reg_salida_eca> </AMPCECJLOperationResponse> </SOAP-ENV:Body> </SOAP-ENV:Envelope>";
//			try {
//				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//				DocumentBuilder db = dbf.newDocumentBuilder();
//				elements = db.parse(new InputSource(new StringReader(xml)));
//			} catch (ParserConfigurationException e) {			
//				e.printStackTrace();
//			} catch (SAXException e) {		
//				e.printStackTrace();
//			} catch (IOException e) {			
//				e.printStackTrace();
//			}
		
		//TODO: Quitar Esto
						
		String tipoFormato = getTagValue("sec_tipo_formato");
		tipoFormato = tipoFormato.trim();
		System.out.println("--->" + tipoFormato + "<---");
		IFEstadoDeCuentaGenerico _estadoDeCuenta = null;
		
		if (tipoFormato.equals("01")) {
			
			_estadoDeCuenta = new IFREANormal();
			
		} else if (tipoFormato.equals("02")) {
			
			_estadoDeCuenta = new IFROANormal();
			
		} else if (tipoFormato.equals("03")) {
			
			_estadoDeCuenta = new IFREAReestructura();
            
		} else if (tipoFormato.equals("04")) {
			
			_estadoDeCuenta = new IFROAReestructura();
            
		} else if (tipoFormato.equals("07")) {
			
			_estadoDeCuenta = new IFREAR40();
			
		} else if (tipoFormato.equals("08")) {
			
			_estadoDeCuenta = new IFROAR40();
			
		} else if (tipoFormato.equals("09")) {
			
			_estadoDeCuenta = new IFREAR40Reestructura();
			
		} else if (tipoFormato.equals("10")) {
			
			_estadoDeCuenta = new IFROAR40Reestructura();
			
		}

		if (_estadoDeCuenta != null){
			_estadoDeCuenta.setTipoCuenta(tipoFormato);
			//Datos Generales
			_estadoDeCuenta.setNombre(getTagValue("sec_nombre"));
			_estadoDeCuenta.setNumeroCredito(getTagValue("sec_credito"));
			_estadoDeCuenta.setTasaInteresAnual(getTagValue("sec_tasa_int"));
			_estadoDeCuenta.setFechaOtorgamiento(getTagValue("sec_fecha_otorgamiento"));
			_estadoDeCuenta.setPagoContratado(getTagValue("sec_porcentaje_monto"));
			
			//Saldos Corte
			_estadoDeCuenta.setPeriodo(getTagValue("sec_periodo_del"));
			_estadoDeCuenta.setMontoVencido(getTagValue("sec_mto_sdo_venc_pes"));
			
			//C�mo voy
			_estadoDeCuenta.setNumMensualidadesVencidas(getTagValue("sec_num_men_ven"));
			_estadoDeCuenta.setNivelCumplimientoPago(getTagValue("sec_niv_cumpl"));
			_estadoDeCuenta.setSaldoAjustado(getTagValue("sec_mto_sdo_ajus_pes"));
			_estadoDeCuenta.setSaldoAjustadoVSM(getTagValue("sec_mto_sdo_ajus_vsm"));
			_estadoDeCuenta.setAjustePeriodosAnteriores(getTagValue("sec_mto_aju_per_ant_pes"));
			_estadoDeCuenta.setAjustePeriodosAnterioresVSM(getTagValue("sec_mto_aju_per_ant_vsm"));
			
			//Resumen Movimientos
			_estadoDeCuenta.setInteresesPeriodo(getTagValue("sec_mto_sdo_int_per_pes"));
			_estadoDeCuenta.setInteresesPeriodoVSM(getTagValue("sec_mto_sdo_int_per_vsm"));
			_estadoDeCuenta.setComisionesYOtrosCargos(getTagValue("sec_mto_sdo_com_car_pes"));
			_estadoDeCuenta.setComisionesYOtrosCargosVSM(getTagValue("sec_mto_sdo_com_car_vsm"));
			_estadoDeCuenta.setTraspasosYOtrosAbonos(getTagValue("sec_mto_sdo_tra_abo_pes"));
			_estadoDeCuenta.setTraspasosYOtrosAbonosVSM(getTagValue("sec_mto_sdo_tra_abo_vsm"));
			_estadoDeCuenta.setSaldoActual(getTagValue("sec_mto_sdo_act_pes"));
			_estadoDeCuenta.setSaldoActualVSM(getTagValue("sec_mto_sdo_act_vsm"));
			_estadoDeCuenta.setSaldoAnterior(getTagValue("sec_mto_sdo_ant_pes"));
			_estadoDeCuenta.setSaldoAnteriorVSM(getTagValue("sec_mto_sdo_ant_vsm"));
			_estadoDeCuenta.setMensajeCartera(getTagValue("sec_des_men_cartera"));
			_estadoDeCuenta.setMensajeGeneral(getTagValue("sec_des_men_general"));
			_estadoDeCuenta.setUltimoPago(getTagValue("sec_mto_sdo_tu_pago_pes"));
			_estadoDeCuenta.setUltimoPagoVSM(getTagValue("sec_mto_sdo_tu_pago_vsm"));
			
			//Es un REA
			if(_estadoDeCuenta instanceof IFREANormal){
				((IFREANormal)_estadoDeCuenta).setLineaCaptura(getTagValue("sec_linea_captura"));
				((IFREANormal)_estadoDeCuenta).setFechaLimitePago(getTagValue("sec_fch_limite_pago"));
			}
			
			//Es un ROA
			if(_estadoDeCuenta instanceof IFROANormal){
				((IFROANormal)_estadoDeCuenta).setpagoDeTuPatron(getTagValue("sec_mto_sdo_pag_pat_pes"));
				((IFROANormal)_estadoDeCuenta).setPagoDeTuPatronVSM(getTagValue("sec_mto_sdo_pag_inf_vsm"));
			}
			
			//Es un Reestructura
			if(IFReestructuraGenerico.class.isAssignableFrom(_estadoDeCuenta.getClass())){
				((IFReestructuraGenerico)_estadoDeCuenta).setSaldoACargoAcreditado(getTagValue("sec_mto_sdo_acr_pes"));
				((IFReestructuraGenerico)_estadoDeCuenta).setSaldoACargoAcreditadoVSM(getTagValue("sec_mto_sdo_acr_vsm"));
				((IFReestructuraGenerico)_estadoDeCuenta).setSaldoACargoInfonavit(getTagValue("sec_mto_sdo_inf_pes"));
				((IFReestructuraGenerico)_estadoDeCuenta).setSaldoACargoInfonavitVSM("sec_mto_sdo_inf_vsm");
				((IFReestructuraGenerico)_estadoDeCuenta).setSaldoActualReestructura(getTagValue("sec_mto_sdo_res_pes"));
				((IFReestructuraGenerico)_estadoDeCuenta).setSaldoActualReestructuraVSM(getTagValue("sec_mto_sdo_res_vsm"));
				((IFReestructuraGenerico)_estadoDeCuenta).setUltimoPagoInfonavit(getTagValue("sec_mto_sdo_inf_pes"));
				((IFReestructuraGenerico)_estadoDeCuenta).setUltimoPagoInfonavitVSM(getTagValue("sec_mto_sdo_inf_vsm"));
			}
					
			//Es un R40
			if(IFR40Generico.class.isAssignableFrom(_estadoDeCuenta.getClass())){
				((IFR40Generico)_estadoDeCuenta).setTasaInteresAnualCobrada(getTagValue("sec_tasa_int"));
				((IFR40Generico)_estadoDeCuenta).setInteresesGeneradosEnPeriodo(getTagValue("sec_mto_sdo_int_per_pes"));
				((IFR40Generico)_estadoDeCuenta).setInteresesGeneradosEnPeriodoVSM(getTagValue("sec_mto_sdo_int_per_vsm"));
				((IFR40Generico)_estadoDeCuenta).setInteresesCubiertosInfonavit(getTagValue("sec_mto_sdo_inf_pes"));
				((IFR40Generico)_estadoDeCuenta).setInteresesCubiertosInfonavitVSM(getTagValue("sec_mto_sdo_inf_vsm"));
				((IFR40Generico)_estadoDeCuenta).setInteresesCubiertosAcreditado(getTagValue("sec_mto_sdo_int_per_pes"));
				((IFR40Generico)_estadoDeCuenta).setInteresesCubiertosAcreditadoVSM(getTagValue("sec_mto_sdo_int_per_vsm"));
				((IFR40Generico)_estadoDeCuenta).setTasaInteresInicioAplicada1(getTagValue("sec_fec_ini_aplica_1"));
				((IFR40Generico)_estadoDeCuenta).setTasaInteresInicioAplicada2(getTagValue("sec_fec_ini_aplica_2"));
				((IFR40Generico)_estadoDeCuenta).setTasaInteresFinAplicada1(getTagValue("sec_fec_fin_aplica_1"));
				((IFR40Generico)_estadoDeCuenta).setTasaInteresFinAplicada2(getTagValue("sec_fec_fin_aplica_2"));
				((IFR40Generico)_estadoDeCuenta).setDescuentoTasaInteres(getTagValue("sec_tas_int_sub_r40"));
			}
		}
		
		return _estadoDeCuenta;
	}
	
	public Ahorros obtenAhorro() {
		
		Ahorros ahorros = new Ahorros();

		try {
			
			ahorros.setDescripcion(getTagValue("p223:mensaje"));

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return ahorros;
	}

}
