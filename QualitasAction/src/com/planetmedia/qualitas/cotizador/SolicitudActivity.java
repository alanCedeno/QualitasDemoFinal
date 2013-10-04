package com.planetmedia.qualitas.cotizador;

import org.w3c.dom.Text;

import com.planetmedia.qualitas.R;
import com.planetmedia.qualitas.R.layout;
import com.planetmedia.qualitas.R.menu;
import com.planetmedia.qualitas.pushNotification.PushNotificationRegister;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class SolicitudActivity extends Activity {

	private EditText etApPaterno;
	private EditText etApMaterno;
	private EditText etNombre;
	private EditText etCalle;
	private EditText etNoExt;
	private EditText etNoInt;
	private EditText etColonia;
	private EditText etEstado;
	private EditText etPoblacion;
	private EditText etCP;
	private EditText etTel;
	private EditText etEmail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

	     // METODO PARA ESCONDER EL TECLADO
	     getWindow().setSoftInputMode(
	        	      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_solicitud);
		
		etApPaterno=(EditText)findViewById(R.id.etApPaterno);
		etApMaterno=(EditText)findViewById(R.id.etApMaterno);
		etNombre=(EditText)findViewById(R.id.etNombre);
		etCalle=(EditText)findViewById(R.id.etCalle);
		etNoExt=(EditText)findViewById(R.id.etNoExt);
		etNoInt=(EditText)findViewById(R.id.etNoInt);
		etColonia=(EditText)findViewById(R.id.etColonia);
		etEstado=(EditText)findViewById(R.id.etEstado);
		etPoblacion=(EditText)findViewById(R.id.etPoblacion);
		etCP=(EditText)findViewById(R.id.etCP);
		etTel=(EditText)findViewById(R.id.etTel);
		etEmail=(EditText)findViewById(R.id.etEmail);
		
		Bundle bundle = getIntent().getExtras();
        bundle.getString("pais");
        etEstado.setText(bundle.getString("estado").toString());
        

        etCP.setText(bundle.getString("cp"));
        
		etPoblacion.setText(bundle.getString("ciudad"));
	}



}
