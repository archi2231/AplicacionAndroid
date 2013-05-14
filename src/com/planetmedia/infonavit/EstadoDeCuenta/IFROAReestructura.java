package com.planetmedia.infonavit.EstadoDeCuenta;

public class IFROAReestructura extends IFROANormal implements
		IFReestructuraGenerico {

	private String ultimoPagoInfonavit;
	private String saldoACargoAcreditado;
	private String saldoACargoInfonavit;
	private String saldoActualReestructura;

	private String ultimoPagoInfonavitVSM;
	private String saldoACargoAcreditadoVSM;
	private String saldoACargoInfonavitVSM;
	private String saldoActualReestructuraVSM;

	public IFROAReestructura() {
		super();
	}

	public String getUltimoPagoInfonavit() {
		return ultimoPagoInfonavit;
	}

	public void setUltimoPagoInfonavit(String ultimoPagoInfonavit) {
		this.ultimoPagoInfonavit = ultimoPagoInfonavit;
	}

	public String getSaldoACargoAcreditado() {
		return saldoACargoAcreditado;
	}

	public void setSaldoACargoAcreditado(String saldoACargoAcreditado) {
		this.saldoACargoAcreditado = saldoACargoAcreditado;
	}

	public String getSaldoACargoInfonavit() {
		return saldoACargoInfonavit;
	}

	public void setSaldoACargoInfonavit(String saldoACargoInfonavit) {
		this.saldoACargoInfonavit = saldoACargoInfonavit;
	}

	public String getSaldoActualReestructura() {
		return saldoActualReestructura;
	}

	public void setSaldoActualReestructura(String saldoActualReestructura) {
		this.saldoActualReestructura = saldoActualReestructura;
	}

	public String getUltimoPagoInfonavitVSM() {
		return ultimoPagoInfonavitVSM;
	}

	public void setUltimoPagoInfonavitVSM(String ultimoPagoInfonavitVSM) {
		this.ultimoPagoInfonavitVSM = ultimoPagoInfonavitVSM;
	}

	public String getSaldoACargoAcreditadoVSM() {
		return saldoACargoAcreditadoVSM;
	}

	public void setSaldoACargoAcreditadoVSM(String saldoACargoAcreditadoVSM) {
		this.saldoACargoAcreditadoVSM = saldoACargoAcreditadoVSM;
	}

	public String getSaldoACargoInfonavitVSM() {
		return saldoACargoInfonavitVSM;
	}

	public void setSaldoACargoInfonavitVSM(String saldoACargoInfonavitVSM) {
		this.saldoACargoInfonavitVSM = saldoACargoInfonavitVSM;
	}

	public String getSaldoActualReestructuraVSM() {
		return saldoActualReestructuraVSM;
	}

	public void setSaldoActualReestructuraVSM(String saldoActualReestructuraVSM) {
		this.saldoActualReestructuraVSM = saldoActualReestructuraVSM;
	}

}
