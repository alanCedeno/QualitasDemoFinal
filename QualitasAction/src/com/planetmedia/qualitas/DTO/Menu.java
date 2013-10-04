package com.planetmedia.qualitas.DTO;

public class Menu {

	private int id_tipo_vehiculo;
	private String tipo_vehiculo;
	private char status;
	public int getId_tipo_vehiculo() {
		return id_tipo_vehiculo;
	}
	public void setId_tipo_vehiculo(int id_tipo_vehiculo) {
		this.id_tipo_vehiculo = id_tipo_vehiculo;
	}
	public String getTipo_vehiculo() {
		return tipo_vehiculo;
	}
	public void setTipo_vehiculo(String tipo_vehiculo) {
		this.tipo_vehiculo = tipo_vehiculo;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
}
