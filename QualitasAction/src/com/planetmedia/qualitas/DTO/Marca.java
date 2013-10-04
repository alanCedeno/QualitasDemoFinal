package com.planetmedia.qualitas.DTO;

public class Marca {

	private int id_marca;
	private int id_tipo_vehiculo;
	private String marca;
	private int imagen;
	public int getId_marca() {
		return id_marca;
	}
	public void setId_marca(int id_marca) {
		this.id_marca = id_marca;
	}
	public int getId_tipo_vehiculo() {
		return id_tipo_vehiculo;
	}
	public void setId_tipo_vehiculo(int id_tipo_vehiculo) {
		this.id_tipo_vehiculo = id_tipo_vehiculo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public int getImagen() {
		return imagen;
	}
	public void setImagen(int imagen) {
		this.imagen = imagen;
	}
	
}
