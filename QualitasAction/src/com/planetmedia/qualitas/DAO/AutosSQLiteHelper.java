package com.planetmedia.qualitas.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import com.planetmedia.qualitas.R;
 
public class AutosSQLiteHelper extends SQLiteOpenHelper {

 
    //Sentencia SQL para crear la tabla de Usuarios
    //String sqlCreate = "CREATE TABLE Usuarios (codigo INTEGER, nombre TEXT)";
    String sqlmenu = "CREATE TABLE tbmen001 (tipo_vehiculo VARCHAR, status CHAR)";
    String sqlmarca = "CREATE TABLE tbmar002 (marca VARCHAR, id_tipo_vehiculo INTEGER, imagen INTEGER)";
    String sqlmodelo = "CREATE TABLE tbmod003 (modelo VARCHAR, id_marca INTEGER)";
    String sqlversion = "CREATE TABLE tbver004 (version VARCHAR, id_year INTEGER, id_modelo INTEGER)";
    String sqlyear = "CREATE TABLE tbyea005 (year INTEGER, id_version INTEGER)";
    
    
    public AutosSQLiteHelper(Context contexto, String nombre,
                               CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlmenu);
        db.execSQL(sqlmarca);
        db.execSQL(sqlmodelo);
        db.execSQL(sqlversion);
        db.execSQL(sqlyear);
        
        insertaDatos(db);
        
    }
 
    private void insertaDatos(SQLiteDatabase db) {
    	
        String sql1 = "INSERT INTO tbmen001 (tipo_vehiculo,status) VALUES (\"Pickup\",\"A\")";
        String sql2 = "INSERT INTO tbmen001 (tipo_vehiculo,status) VALUES (\"Camioneta\",\"A\")";
        String sql3 = "INSERT INTO tbmen001 (tipo_vehiculo,status) VALUES (\"Carrito\",\"E\")";
        
        String sql4 ="INSERT INTO tbmar002 (marca,id_tipo_vehiculo, imagen) VALUES (\"Volkswagen\",\"1\",\""+R.drawable.logo_auto_vw+"\")";
        String sql5 = "INSERT INTO tbmar002 (marca,id_tipo_vehiculo, imagen) VALUES (\"Chrysler\",\"1\",\""+R.drawable.logo_auto_crhysler+"\")";
        String sql6 = "INSERT INTO tbmar002 (marca,id_tipo_vehiculo, imagen) VALUES (\"Chevrolet\",\"1\",\""+R.drawable.logo_auto_chevrolet+"\")";
        
        String sql7 = "INSERT INTO tbmod003 (modelo,id_marca) VALUES (\"Clasico\",\"1\")";
        String sql8 = "INSERT INTO tbmod003 (modelo,id_marca) VALUES (\"The beetle\",\"1\")";
        String sql9 = "INSERT INTO tbmod003 (modelo,id_marca) VALUES (\"Golf\",\"1\")";
        String sql10 = "INSERT INTO tbmod003 (modelo,id_marca) VALUES (\"Sedan Turing 2.4L\",\"2\")";
        String sql11 = "INSERT INTO tbmod003 (modelo,id_marca) VALUES (\"sedan limited 3.6L\",\"2\")";
        String sql12 = "INSERT INTO tbmod003 (modelo,id_marca) VALUES (\"CV8 Hemi\",\"2\")";
        String sql13 = "INSERT INTO tbmod003 (modelo,id_marca) VALUES (\"CAMARO\",\"3\")";
        String sql14 = "INSERT INTO tbmod003 (modelo,id_marca) VALUES (\"MALIBU\",\"3\")";
        String sql15 = "INSERT INTO tbmod003 (modelo,id_marca) VALUES (\"CRUZE\",\"3\")";
        
        String sql16 = "INSERT INTO tbver004 (version,id_year,id_modelo) VALUES (\"6 CILINDROS, 2.0 LITROS 4 PUERTAS\",\"1\", \"1\")";
        String sql17 = "INSERT INTO tbver004 (version,id_year,id_modelo) VALUES (\"4 CILINDROS, 1.8 LITROS 4 PUERTAS\",\"1\", \"1\")";
        String sql18 = "INSERT INTO tbver004 (version,id_year,id_modelo) VALUES (\"4 cilindros de 1.6L, VVT 4 PUERTAS\",\"1\", \"2\")";
        String sql19 = "INSERT INTO tbver004 (version,id_year,id_modelo) VALUES (\"550iA M Sport Automático\",\"1\", \"2\")";
        String sql20 = "INSERT INTO tbver004 (version,id_year,id_modelo) VALUES (\"4 cilindros de 1.6L, VVT 4 PUERTAS\",\"1\", \"3\")";
        String sql21 = "INSERT INTO tbver004 (version,id_year,id_modelo) VALUES (\"CONVERTIBLE\",\"1\", \"3\")";
        
        String sql22 = "INSERT INTO tbyea005 (year,id_version) VALUES(\"1990\",\"1\")";
        String sql23 = "INSERT INTO tbyea005 (year,id_version) VALUES(\"1991\",\"1\")";
        String sql24 = "INSERT INTO tbyea005 (year,id_version) VALUES(\"1992\",\"1\")";
        String sql25 = "INSERT INTO tbyea005 (year,id_version) VALUES(\"2012\",\"2\")";
        String sql26 = "INSERT INTO tbyea005 (year,id_version) VALUES(\"2011\",\"2\")";
        String sql27 = "INSERT INTO tbyea005 (year,id_version) VALUES(\"2010\",\"3\")";
            
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6); 
        db.execSQL(sql7);
        db.execSQL(sql8);
        db.execSQL(sql9);
        db.execSQL(sql10);
        db.execSQL(sql11);
        db.execSQL(sql12);
        db.execSQL(sql13);
        db.execSQL(sql14);
        db.execSQL(sql15);
        db.execSQL(sql16);
        db.execSQL(sql17);
        db.execSQL(sql18);
        db.execSQL(sql19);
        db.execSQL(sql20);
        db.execSQL(sql21);
        db.execSQL(sql22);
        db.execSQL(sql23);
        db.execSQL(sql24);
        db.execSQL(sql25);
        db.execSQL(sql26);
        db.execSQL(sql27);
	}

	@Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, 
                          int versionNueva) {

    }
	
}

