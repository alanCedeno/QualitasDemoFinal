package com.planetmedia.qualitas.DTO;

public class VersionDTO {

	private int id_version;
	private int id_year;
	private int id_modelo;
	private String version;
	public int getId_version() {
		return id_version;
	}
	public void setId_version(int id_version) {
		this.id_version = id_version;
	}
	public int getId_year() {
		return id_year;
	}
	public void setId_year(int id_year) {
		this.id_year = id_year;
	}
	public int getId_modelo() {
		return id_modelo;
	}
	public void setId_modelo(int id_modelo) {
		this.id_modelo = id_modelo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
