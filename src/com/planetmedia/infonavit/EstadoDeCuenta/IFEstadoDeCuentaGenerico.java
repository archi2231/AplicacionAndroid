package com.planetmedia.infonavit.EstadoDeCuenta;

public class IFEstadoDeCuentaGenerico {

	private int idEstadoCuenta;
	private int idUsuario;
	private String boletos;
	private String mensajeCartera;
	private String mensajeGeneral;

	private String periodo;
	private String tipoCuenta;

	// Datos Crédito
	private String nombre;
	private String numeroCredito;
	private String pagoContratado;
	private String tasaInteresAnual;
	private String fechaOtorgamiento;
	private String numMensualidadesVencidas;
	private String nivelCumplimientoPago;
	private String montoVencido;

	// Resumen Movimientos
	private String saldoAnterior;
	private String ajustePeriodosAnteriores;
	private String saldoAjustado;
	private String interesesPeriodo;
	private String comisionesYOtrosCargos;
	private String ultimoPago;
	private String traspasosYOtrosAbonos;
	private String saldoActual;

	// Resumen Movimientos VSM
	private String saldoAnteriorVSM;
	private String ajustePeriodosAnterioresVSM;
	private String saldoAjustadoVSM;
	private String interesesPeriodoVSM;
	private String comisionesYOtrosCargosVSM;
	private String ultimoPagoVSM;
	private String traspasosYOtrosAbonosVSM;
	private String saldoActualVSM;

	public IFEstadoDeCuentaGenerico() {
		super();
	}

	public int getIdEstadoCuenta() {
		return idEstadoCuenta;
	}

	public void setIdEstadoCuenta(int idEstadoCuenta) {
		this.idEstadoCuenta = idEstadoCuenta;
	}

	public String getBoletos() {
		return boletos;
	}

	public void setBoletos(String boletos) {
		this.boletos = boletos;
	}

	public String getMensajeCartera() {
		return mensajeCartera;
	}

	public void setMensajeCartera(String mensajeCartera) {
		this.mensajeCartera = mensajeCartera;
	}

	public String getMensajeGeneral() {
		return mensajeGeneral;
	}

	public void setMensajeGeneral(String mensajeGeneral) {
		this.mensajeGeneral = mensajeGeneral;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumeroCredito() {
		return numeroCredito;
	}

	public void setNumeroCredito(String numeroCredito) {
		this.numeroCredito = numeroCredito;
	}

	public String getPagoContratado() {
		return pagoContratado;
	}

	public void setPagoContratado(String pagoContratado) {
		this.pagoContratado = pagoContratado;
	}

	public String getTasaInteresAnual() {
		return tasaInteresAnual;
	}

	public void setTasaInteresAnual(String tasaInteresAnual) {
		this.tasaInteresAnual = tasaInteresAnual;
	}

	public String getFechaOtorgamiento() {
		return fechaOtorgamiento;
	}

	public void setFechaOtorgamiento(String fechaOtorgamiento) {
		this.fechaOtorgamiento = fechaOtorgamiento;
	}

	public String getNumMensualidadesVencidas() {
		return numMensualidadesVencidas;
	}

	public void setNumMensualidadesVencidas(String numMensualidadesVencidas) {
		this.numMensualidadesVencidas = numMensualidadesVencidas;
	}

	public String getNivelCumplimientoPago() {
		return nivelCumplimientoPago;
	}

	public void setNivelCumplimientoPago(String nivelCumplimientoPago) {
		this.nivelCumplimientoPago = nivelCumplimientoPago;
	}

	public String getMontoVencido() {
		return montoVencido;
	}

	public void setMontoVencido(String montoVencido) {
		this.montoVencido = montoVencido;
	}

	public String getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(String saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public String getAjustePeriodosAnteriores() {
		return ajustePeriodosAnteriores;
	}

	public void setAjustePeriodosAnteriores(String ajustePeriodosAnteriores) {
		this.ajustePeriodosAnteriores = ajustePeriodosAnteriores;
	}

	public String getSaldoAjustado() {
		return saldoAjustado;
	}

	public void setSaldoAjustado(String saldoAjustado) {
		this.saldoAjustado = saldoAjustado;
	}

	public String getInteresesPeriodo() {
		return interesesPeriodo;
	}

	public void setInteresesPeriodo(String interesesPeriodo) {
		this.interesesPeriodo = interesesPeriodo;
	}

	public String getComisionesYOtrosCargos() {
		return comisionesYOtrosCargos;
	}

	public void setComisionesYOtrosCargos(String comisionesYOtrosCargos) {
		this.comisionesYOtrosCargos = comisionesYOtrosCargos;
	}

	public String getUltimoPago() {
		return ultimoPago;
	}

	public void setUltimoPago(String ultimoPago) {
		this.ultimoPago = ultimoPago;
	}

	public String getTraspasosYOtrosAbonos() {
		return traspasosYOtrosAbonos;
	}

	public void setTraspasosYOtrosAbonos(String traspasosYOtrosAbonos) {
		this.traspasosYOtrosAbonos = traspasosYOtrosAbonos;
	}

	public String getSaldoActual() {
		return saldoActual;
	}

	public void setSaldoActual(String saldoActual) {
		this.saldoActual = saldoActual;
	}

	public String getSaldoAnteriorVSM() {
		return saldoAnteriorVSM;
	}

	public void setSaldoAnteriorVSM(String saldoAnteriorVSM) {
		this.saldoAnteriorVSM = saldoAnteriorVSM;
	}

	public String getAjustePeriodosAnterioresVSM() {
		return ajustePeriodosAnterioresVSM;
	}

	public void setAjustePeriodosAnterioresVSM(
			String ajustePeriodosAnterioresVSM) {
		this.ajustePeriodosAnterioresVSM = ajustePeriodosAnterioresVSM;
	}

	public String getSaldoAjustadoVSM() {
		return saldoAjustadoVSM;
	}

	public void setSaldoAjustadoVSM(String saldoAjustadoVSM) {
		this.saldoAjustadoVSM = saldoAjustadoVSM;
	}

	public String getInteresesPeriodoVSM() {
		return interesesPeriodoVSM;
	}

	public void setInteresesPeriodoVSM(String interesesPeriodoVSM) {
		this.interesesPeriodoVSM = interesesPeriodoVSM;
	}

	public String getComisionesYOtrosCargosVSM() {
		return comisionesYOtrosCargosVSM;
	}

	public void setComisionesYOtrosCargosVSM(String comisionesYOtrosCargosVSM) {
		this.comisionesYOtrosCargosVSM = comisionesYOtrosCargosVSM;
	}

	public String getUltimoPagoVSM() {
		return ultimoPagoVSM;
	}

	public void setUltimoPagoVSM(String ultimoPagoVSM) {
		this.ultimoPagoVSM = ultimoPagoVSM;
	}

	public String getTraspasosYOtrosAbonosVSM() {
		return traspasosYOtrosAbonosVSM;
	}

	public void setTraspasosYOtrosAbonosVSM(String traspasosYOtrosAbonosVSM) {
		this.traspasosYOtrosAbonosVSM = traspasosYOtrosAbonosVSM;
	}

	public String getSaldoActualVSM() {
		return saldoActualVSM;
	}

	public void setSaldoActualVSM(String saldoActualVSM) {
		this.saldoActualVSM = saldoActualVSM;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

}