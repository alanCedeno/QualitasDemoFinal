package com.planetmedia.qualitas.DAO;

import java.util.ArrayList;

import com.planetmedia.qualitas.DTO.Ciudad;
import com.planetmedia.qualitas.DTO.Estado;
import com.planetmedia.qualitas.DTO.Marca;
import com.planetmedia.qualitas.DTO.ModeloDTO;
import com.planetmedia.qualitas.DTO.VersionDTO;
import com.planetmedia.qualitas.DTO.Year;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GenericDAO {
	private SQLiteDatabase db;
	public GenericDAO(SQLiteDatabase db){
		this.db=db;		
	}

	public ArrayList<Marca> getMarcas(int id_tipo_vehiculo) {
		ArrayList<Marca> marca = new ArrayList<Marca>();
		
		String sql= "SELECT * FROM tbmar002 WHERE id_tipo_vehiculo ="+String.valueOf(id_tipo_vehiculo)+";";
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst()) {
		     do {
		    	 Marca temp = new Marca();
		    	 temp.setMarca(c.getString(0));
		    	 temp.setId_tipo_vehiculo(c.getInt(1));
		    	 temp.setImagen(c.getInt(2));
		    	 marca.add(temp);		          		          
		     } while(c.moveToNext());
		}
		c.close();
		return marca;
	}

	public ArrayList<ModeloDTO> getModelos(int position) {
		ArrayList<ModeloDTO> modelo = new ArrayList<ModeloDTO>();
		String sql= "SELECT * FROM tbmod003 WHERE id_marca ="+String.valueOf(position)+";";
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst()) {
		     do {
		    	 ModeloDTO temp = new ModeloDTO();
		    	 temp.setModelo(c.getString(0));
		    	 temp.setId_marca(c.getInt(1));
		    	 modelo.add(temp);		          		          
		     } while(c.moveToNext());
		}
		c.close();
		return modelo;
		
	}

	public ArrayList<VersionDTO> getVersiones(int position) {
		ArrayList<VersionDTO> version = new ArrayList<VersionDTO>();
		String sql= "SELECT * FROM tbver004 WHERE id_modelo ="+String.valueOf(position)+";";
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst()) {
		     do {
		    	 VersionDTO temp = new VersionDTO();
		    	 temp.setVersion(c.getString(0));
		    	 temp.setId_year(c.getInt(1));
		    	 temp.setId_modelo(c.getInt(2));
		    	 version.add(temp);		          		          
		     } while(c.moveToNext());
		}
		c.close();
		return version;
	}

	public ArrayList<Year> getYear(int position) {
		ArrayList<Year> year = new ArrayList<Year>();
		String sql= "SELECT * FROM tbyea005 WHERE id_version ="+String.valueOf(position)+";";
		Cursor c = db.rawQuery(sql, null);
		if(position>3)
			position=2;
		if (c.moveToFirst()) {
		     do {
		    	 Year temp = new Year();
		    	 temp.setYear(c.getInt(0));
		    	 temp.setId_version(c.getInt(1));
		    	 year.add(temp);		          		          
		     } while(c.moveToNext());
		}
		c.close();
		return year;
	}

	public ArrayList<Estado> getEstados(int position) {
		ArrayList<Estado> estado = new ArrayList<Estado>();
		String sql= "SELECT distinct d_estado , c_estado FROM cps ORDER BY d_estado;";
		Cursor c = db.rawQuery(sql, null);

		if (c.moveToFirst()) {
		     do {
		    	 Estado temp = new Estado();
		    	 temp.setNombre(c.getString(0));
		    	 temp.setId_estado(c.getString(1));
		    	 estado.add(temp);	          		          
		     } while(c.moveToNext());
		}
		c.close();
		return estado;
	}

	public ArrayList<Ciudad> getCiudades(String idEstado) {
		ArrayList<Ciudad> ciudad = new ArrayList<Ciudad>();
		String sql= "SELECT distinct d_mnpio , c_mnpio,c_estado  FROM cps WHERE c_estado='"+idEstado+"';";
		Cursor c = db.rawQuery(sql, null);

		if (c.moveToFirst()) {
		     do {
		    	 Ciudad temp = new Ciudad();
		    	 temp.setNombre(c.getString(0));
		    	 temp.setId(c.getString(1));
		    	 temp.setId_estado(c.getString(2));
		    	 ciudad.add(temp);	          		          
		     } while(c.moveToNext());
		}
		c.close();
		return ciudad;
	}

	public ArrayList<String> getCps(String idMnpio,String idEstado) {
		ArrayList<String> Cps= new ArrayList<String>();
		String sql= "SELECT c_codigo  FROM cps WHERE c_mnpio='"+idMnpio+"' AND c_estado='"+idEstado+"';";
		Cursor c = db.rawQuery(sql, null);

		if (c.moveToFirst()) {
		     do {
		    	 Cps.add(c.getString(0));
		    	           		          
		     } while(c.moveToNext());
		}
		c.close();
		return Cps;
	}

	public String BuscaCP(String cp) {
		String codPos = "Codigo Postal No encontrado";
		String sql= "SELECT c_codigo  FROM cps WHERE c_codigo='"+cp+"';";
		Cursor c = db.rawQuery(sql, null);

		if (c.moveToFirst()) {
		     do {
		    	 codPos=c.getString(0);		    	           		          
		     } while(c.moveToNext());
		     if(codPos.equals(cp)){
		    	 
		     }else{
		    	 codPos="Codigo Postal No encontrado";
		     }
		}
		c.close();		
		return codPos;
	}


	
	
}
