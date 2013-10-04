package com.planetmedia.qualitas.cotizador;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.planetmedia.qualitas.R;
import com.planetmedia.qualitas.DAO.AutosSQLiteHelper;
import com.planetmedia.qualitas.DAO.DataBaseHelper;
import com.planetmedia.qualitas.DAO.GenericDAO;
import com.planetmedia.qualitas.DTO.Ciudad;
import com.planetmedia.qualitas.DTO.Estado;
import com.planetmedia.qualitas.DTO.Marca;
import com.planetmedia.qualitas.DTO.ModeloDTO;
import com.planetmedia.qualitas.DTO.VersionDTO;
import com.planetmedia.qualitas.DTO.Year;
import com.planetmedia.qualitas.pushNotification.PushNotificationRegister;

public class MainActivity extends Activity implements TextWatcher {
	private double primaNeta=0;
	double fraccionado;
	private int sumaAseg=0;
	private SQLiteDatabase db,dbCp;
	private double relCP=1;
	ArrayList<Integer> imagenes = new ArrayList<Integer>();
	TextView tvCP;
	ArrayList<Estado> estado=new ArrayList<Estado>();
	ArrayList<Ciudad> ciudad=new ArrayList<Ciudad>();


	
	private static int posPais,posEstado,posCiudad,posCP;

	TextView tvTotal;
	TextView tvIva;
	TextView tvSubtotal;
	TextView tvDerPoliza;
	TextView tvPagoFracc;
	TextView tvsumDanosMat;
	TextView tvsumRoboTot;
	TextView tvSumOcupantes;
	TextView tvSumAsisVial;
	TextView tvSumExtCob;
	TextView tvSumGastosTrans;
	TextView tvSumRoboPar;
	EditText etSumEqEsp;
	Button btnSumEqEsp;
	TextView tvPrimDanosMat;
	TextView tvPrimRobo;
	TextView tvPrimRespCivil;
	TextView tvPrimComPersonas;
	TextView tvPrimOcupantes;
	TextView tvPrimGastosMedOc;
	TextView tvPrimMuerteCond;
	TextView tvPrimGasLeg;
	TextView tvPrimAsisVial;
	TextView tvPrimExtCob;
	TextView tvPrimGasTrans;
	TextView tvPrimRoboParcial;
	TextView tvPrimDanosMatNeu;
	TextView tvPrimEquipoEspecial;
	TextView tvPrimRespCivExt;
	TextView tvPrimaNeta;
	TextView tvDedComp;
	TextView tvDedOcupantes;
	TextView tvDedAsisVial;
	TextView tvDedExtCob;
	TextView tvDedGastosTrans;
	TextView tvDedRoboPar;
	TextView tvDedEqpEsp;
	Spinner spSumRespCivil;
	Spinner spMarca;
	Spinner spVersion;
	Spinner spAno;
	Spinner spTipo;
	Spinner spCobertura;
	Spinner spPago;
	Spinner spPeriodo;
	Spinner spPais;
	Spinner spEstado;
	Spinner spCiudad;
	Spinner spCP;
	Spinner spGastosMedOcu;
	Spinner spMuerteCond;
	TextView tvClave;
	CheckBox checkCompPer;
	CheckBox checkOcupantes;
	CheckBox checkAsisVial;
	CheckBox checkExtCob;
	CheckBox checkGastosTrans;
	CheckBox checkRoboPar;
	CheckBox checkEqEsp;
	Spinner spCompPer;
	Spinner spDedDanosMat;
	Spinner spDedRoboTot;	
	ImageView logoMarca;
	EditText etColonia;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	     
	   //Registro en Push Notification
			PushNotificationRegister.registra(this);
	     // METODO PARA ESCONDER EL TECLADO
	     getWindow().setSoftInputMode(
	        	      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		setContentView(R.layout.activity_main);
		
		AutosSQLiteHelper usdbh =
	            new AutosSQLiteHelper(this, "DBAutos", null, 1);

		db=usdbh.getWritableDatabase();
        SharedPreferences prefe=getSharedPreferences("datos",Context.MODE_PRIVATE);
        
		
		DataBaseHelper DBcods = new DataBaseHelper(this);
		dbCp=DBcods.getDB();
		
		if(prefe.getString("push", "").equals("1")){	
			new task(this).execute();
			Editor editor=prefe.edit();
	        editor.putString("push","0");
	        editor.commit();
		}
		
		logoMarca= (ImageView)findViewById(R.id.imgLogo);	
		spMarca=(Spinner)findViewById(R.id.spMarca);
		spSumRespCivil=(Spinner)findViewById(R.id.spSumRespCivil);
		spVersion=(Spinner)findViewById(R.id.spVersion);
		spAno=(Spinner)findViewById(R.id.spAno);
		spTipo=(Spinner)findViewById(R.id.spTipo);
		spCobertura=(Spinner)findViewById(R.id.spinner5);
		spPago=(Spinner)findViewById(R.id.spinner6);
		spPeriodo=(Spinner)findViewById(R.id.spinner7);
		spPais=(Spinner)findViewById(R.id.spinner8);
		spEstado = (Spinner)findViewById(R.id.spinner9);
		spCompPer=(Spinner)findViewById(R.id.spCompPersona);
		spCiudad = (Spinner)findViewById(R.id.spinner10);
		spCP = (Spinner)findViewById(R.id.spinner11);
		spGastosMedOcu=(Spinner)findViewById(R.id.spGastosMedOcu);
		spMuerteCond=(Spinner)findViewById(R.id.spMuerteCond);
		spDedDanosMat=(Spinner)findViewById(R.id.spDedDanosMat);
		spDedRoboTot=(Spinner)findViewById(R.id.spDedRoboTot);
		tvsumDanosMat=(TextView)findViewById(R.id.tvSumDatosMat);
		tvsumRoboTot=(TextView)findViewById(R.id.tvSumRoboTotal);
		tvSumOcupantes=(TextView)findViewById(R.id.tvSumOcupantes);
		tvSumAsisVial=(TextView)findViewById(R.id.tvSumAsistenciaVial);
		tvSumExtCob=(TextView)findViewById(R.id.tvSumExtCob);
		tvSumGastosTrans=(TextView)findViewById(R.id.tvSumGastosTrans);
		tvSumRoboPar=(TextView)findViewById(R.id.tvSumRoboParcial);
		tvPrimDanosMat=(TextView)findViewById(R.id.tvPrimDanosMat);
		tvPrimRobo=(TextView)findViewById(R.id.tvPrimRobo);
		tvPrimRespCivil=(TextView)findViewById(R.id.tvPrimRespCivil);
		tvPrimComPersonas=(TextView)findViewById(R.id.tvPrimCompPersonas);
		tvPrimOcupantes=(TextView)findViewById(R.id.tvPrimOcupantes);
		tvPrimGastosMedOc=(TextView)findViewById(R.id.tvPrimGastosMedicos);
		tvPrimMuerteCond=(TextView)findViewById(R.id.tvPrimMuerteCond);
		tvPrimGasLeg=(TextView)findViewById(R.id.tvPrimGasLeg);
		tvPrimAsisVial=(TextView)findViewById(R.id.tvPrimAsisVial);
		tvPrimExtCob=(TextView)findViewById(R.id.tvPrimExtCob);
		tvPrimGasTrans=(TextView)findViewById(R.id.tvPrimGasTrans);
		tvPrimRoboParcial=(TextView)findViewById(R.id.tvPrimRoboParcial);
		tvPrimEquipoEspecial=(TextView)findViewById(R.id.tvPrimEquipoEsc);
		tvPrimRespCivExt=(TextView)findViewById(R.id.textView42);
		tvPrimaNeta=(TextView)findViewById(R.id.tvPrimaNeta);
		tvClave=(TextView)findViewById(R.id.textView17);
		tvCP=(TextView)findViewById(R.id.textView10);
		tvTotal=(TextView)findViewById(R.id.tvTotal);
		tvIva=(TextView)findViewById(R.id.tvIva);
		tvSubtotal=(TextView)findViewById(R.id.tvSubtotal);
		tvPagoFracc=(TextView)findViewById(R.id.tvRecaFracc);
		tvDerPoliza=(TextView)findViewById(R.id.tvDerPoliza);
		tvDedComp=(TextView)findViewById(R.id.tvDedCompPersona);
		tvDedOcupantes=(TextView)findViewById(R.id.tvDedOcupantes);
		tvDedAsisVial=(TextView)findViewById(R.id.tvDedAsistenciaVial);
		tvDedExtCob=(TextView)findViewById(R.id.tvDedExtCob);
		tvDedGastosTrans=(TextView)findViewById(R.id.tvDedGastosTrans);
		tvDedRoboPar=(TextView)findViewById(R.id.tvDedRoboParc);
		tvDedEqpEsp=(TextView)findViewById(R.id.tvDedEquipoEsp);
		checkCompPer=(CheckBox)findViewById(R.id.checkBox1);
		checkOcupantes=(CheckBox)findViewById(R.id.checkBox12);
		checkAsisVial=(CheckBox)findViewById(R.id.checkBox6);
		checkExtCob=(CheckBox)findViewById(R.id.checkBox7);
		checkGastosTrans=(CheckBox)findViewById(R.id.checkBox2);
		checkRoboPar=(CheckBox)findViewById(R.id.checkBox3);
		checkEqEsp=(CheckBox)findViewById(R.id.checkBox5);
		etSumEqEsp=(EditText)findViewById(R.id.etSumEqEsp);
		etColonia=(EditText)findViewById(R.id.etColonia);
//		btnSumEqEsp=(Button)findViewById(R.id.btnSumEqEsp);
		
		etSumEqEsp.addTextChangedListener(this);
						
		llenarSpinMarca();    
        
        spMarca.setOnItemSelectedListener(new OnItemSelectedListener() {         
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
        //Toast.makeText(parentView.getContext(), "Has seleccionado " + parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            	logoMarca.setImageResource(imagenes.get(position));
            	llenarSpinModelo(position+1);
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {            	
            }
        });
        spTipo.setOnItemSelectedListener(new OnItemSelectedListener() {         
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
            	llenarSpinVersion(position+1);
            	Random rand = new Random();
        		sumaAseg=(rand.nextInt((120000 - 60000) + 1) + 60000);
        		colocarValoresIniciales();
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {            	
            }
        }); 
        spVersion.setOnItemSelectedListener(new OnItemSelectedListener() {         
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
            	llenarSpinYear(position+1);
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {            	
            }
        });
        
        ArrayList<String> coberturas = new ArrayList<String>();
        coberturas.add("Amplia");
        coberturas.add("Amplia Integral");
        coberturas.add("Plus");
        coberturas.add("Limitada");
        coberturas.add("Basica");
        ArrayAdapter<String> adCoberturas = new ArrayAdapter<String>(this,R.layout.texto_spin, coberturas);      
        spCobertura.setAdapter(adCoberturas);
        
        spCobertura.setOnItemSelectedListener(new OnItemSelectedListener() {         
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
            	
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {            	
            }
        });
        
        ArrayList<String> pago = new ArrayList<String>();
        pago.add("Contado");
        pago.add("Semestral");
        ArrayAdapter<String> adPago = new ArrayAdapter<String>(this,R.layout.texto_spin, pago);      
        spPago.setAdapter(adPago);
        
        spPago.setOnItemSelectedListener(new OnItemSelectedListener() {         
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
            	if(position==0){
            		fraccionado=-268.344;
            		
            	}else{
            		fraccionado=268;
            	}
            	calculaPrimaNeta();
            	
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {            	
            }
        });
        
        ArrayList<String> periodo = new ArrayList<String>();
        periodo.add("3 - 7");
        periodo.add("8 - 14");
        ArrayAdapter<String> adPeriodo = new ArrayAdapter<String>(this,R.layout.texto_spin, periodo);      
        spPeriodo.setAdapter(adPeriodo);
        
        spPeriodo.setOnItemSelectedListener(new OnItemSelectedListener() {         
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
            	
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {            	
            }
        });
        ArrayList<String> pais = new ArrayList<String>();
        pais.add("México");
        ArrayAdapter<String> adPais = new ArrayAdapter<String>(this,R.layout.texto_spin, pais);      
        spPais.setAdapter(adPais);
        
        spPais.setOnItemSelectedListener(new OnItemSelectedListener() {         
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
            	MainActivity.posPais=position;
            	llenarSpinEstado(position);
            	
            	
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {            	
            }
        });
      
        spEstado.setOnItemSelectedListener(new OnItemSelectedListener() {         
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
            	llenarSpinCiudad(estado.get(position).getId_estado());
            	MainActivity.posEstado=position;
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {            	
            }
        });
               
        spCiudad.setOnItemSelectedListener(new OnItemSelectedListener() {         
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {   
            	llenarSpinCP(ciudad.get(position).getId(),ciudad.get(position).getId_estado());
            	MainActivity.posCiudad=position;
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {            	
            }
        });
               
        spCP.setOnItemSelectedListener(new OnItemSelectedListener() {         
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
           	
            	MainActivity.posCP=position;
            	tvCP.setText(spCP.getItemAtPosition(position).toString());
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {            	
            }
        });
                
        checkCompPer.setOnCheckedChangeListener(
        	    new CheckBox.OnCheckedChangeListener() {
        	        public void onCheckedChanged(CompoundButton buttonView,
        	                                            boolean isChecked) {
        	            if (isChecked) {
        	            	spCompPer.setVisibility(View.VISIBLE);
        	            	tvDedComp.setVisibility(View.VISIBLE);
        	            	tvPrimComPersonas.setVisibility(View.VISIBLE);
        	            }
        	            else {
        	            	spCompPer.setVisibility(View.INVISIBLE);
        	            	tvDedComp.setVisibility(View.INVISIBLE);
        	            	tvPrimComPersonas.setVisibility(View.INVISIBLE);
        	            }
    	            	calculaPrimaNeta();

        	           }
        	        });
        checkOcupantes.setOnCheckedChangeListener(
        	    new CheckBox.OnCheckedChangeListener() {
        	        public void onCheckedChanged(CompoundButton buttonView,
        	                                            boolean isChecked) {
        	            if (isChecked) {
        	            	tvSumOcupantes.setVisibility(View.VISIBLE);
        	            	tvDedOcupantes.setVisibility(View.VISIBLE);
        	            	tvPrimOcupantes.setVisibility(View.VISIBLE);
        	            }
        	            else {
        	            	tvSumOcupantes.setVisibility(View.INVISIBLE);
        	            	tvDedOcupantes.setVisibility(View.INVISIBLE);
        	            	tvPrimOcupantes.setVisibility(View.INVISIBLE);
        	            }
        	        	calculaPrimaNeta();

        	           }
        	        });
        checkAsisVial.setOnCheckedChangeListener(
        	    new CheckBox.OnCheckedChangeListener() {
        	        public void onCheckedChanged(CompoundButton buttonView,
        	                                            boolean isChecked) {
        	            if (isChecked) {
        	            	tvSumAsisVial.setVisibility(View.VISIBLE);
        	            	tvDedAsisVial.setVisibility(View.VISIBLE);
        	            	tvPrimAsisVial.setVisibility(View.VISIBLE);
        	            }
        	            else {
        	            	tvSumAsisVial.setVisibility(View.INVISIBLE);
        	            	tvDedAsisVial.setVisibility(View.INVISIBLE);
        	            	tvPrimAsisVial.setVisibility(View.INVISIBLE);
        	            }
        	        	calculaPrimaNeta();

        	           }
        	        });
        
        checkExtCob.setOnCheckedChangeListener(
        	    new CheckBox.OnCheckedChangeListener() {
        	        public void onCheckedChanged(CompoundButton buttonView,
        	                                            boolean isChecked) {
        	            if (isChecked) {
        	            	tvSumExtCob.setVisibility(View.VISIBLE);
        	            	tvDedExtCob.setVisibility(View.VISIBLE);
        	            	tvPrimExtCob.setVisibility(View.VISIBLE);
        	            }
        	            else {
        	            	tvSumExtCob.setVisibility(View.INVISIBLE);
        	            	tvDedExtCob.setVisibility(View.INVISIBLE);
        	            	tvPrimExtCob.setVisibility(View.INVISIBLE);
        	            }
        	        	calculaPrimaNeta();

        	           }
        	        });
        checkGastosTrans.setOnCheckedChangeListener(
        	    new CheckBox.OnCheckedChangeListener() {
        	        public void onCheckedChanged(CompoundButton buttonView,
        	                                            boolean isChecked) {
        	            if (isChecked) {
        	            	tvSumGastosTrans.setVisibility(View.VISIBLE);
        	            	tvDedGastosTrans.setVisibility(View.VISIBLE);
        	            	tvPrimGasTrans.setVisibility(View.VISIBLE);
        	            }
        	            else {
        	            	tvSumGastosTrans.setVisibility(View.INVISIBLE);
        	            	tvDedGastosTrans.setVisibility(View.INVISIBLE);
        	            	tvPrimGasTrans.setVisibility(View.INVISIBLE);
        	            }
        	        	calculaPrimaNeta();

        	           }
        	        });
        checkRoboPar.setOnCheckedChangeListener(
        	    new CheckBox.OnCheckedChangeListener() {
        	        public void onCheckedChanged(CompoundButton buttonView,
        	                                            boolean isChecked) {
        	            if (isChecked) {
        	            	tvSumRoboPar.setVisibility(View.VISIBLE);
        	            	tvDedRoboPar.setVisibility(View.VISIBLE);
        	            	tvPrimRoboParcial.setVisibility(View.VISIBLE);
        	            }
        	            else {
        	            	tvSumRoboPar.setVisibility(View.INVISIBLE);
        	            	tvDedRoboPar.setVisibility(View.INVISIBLE);
        	            	tvPrimRoboParcial.setVisibility(View.INVISIBLE);
        	            }
        	        	calculaPrimaNeta();

        	           }
        	        });
        checkEqEsp.setOnCheckedChangeListener(
        	    new CheckBox.OnCheckedChangeListener() {
        	        public void onCheckedChanged(CompoundButton buttonView,
        	                                            boolean isChecked) {
        	            if (isChecked) {
        	            	etSumEqEsp.setVisibility(View.VISIBLE);
        	            	tvDedEqpEsp.setVisibility(View.VISIBLE);
        	            	tvPrimEquipoEspecial.setVisibility(View.VISIBLE);
        	            }
        	            else {
        	            	etSumEqEsp.setVisibility(View.INVISIBLE);
        	            	tvDedEqpEsp.setVisibility(View.INVISIBLE);
        	            	tvPrimEquipoEspecial.setVisibility(View.INVISIBLE);

        	            }
        	        	calculaPrimaNeta();

        	           }
        	        });
               
	}
	
		private void colocarValoresIniciales() {
		//Formato
				final DecimalFormat formatter;
				DecimalFormatSymbols custom=new DecimalFormatSymbols();
				custom.setDecimalSeparator('.');
				
				formatter = new DecimalFormat("#.00");
				formatter.setDecimalFormatSymbols(custom);

		tvsumDanosMat.setText(String.valueOf(sumaAseg));
		tvsumRoboTot.setText(tvsumDanosMat.getText());
		ArrayList<String> val = new ArrayList<String>();
		val.add("3000000");
		 ArrayAdapter<String> adRespCiv = new ArrayAdapter<String>(this,R.layout.texto_spin, val);      
	        spSumRespCivil.setAdapter(adRespCiv);
	        spSumRespCivil.setOnItemSelectedListener(new OnItemSelectedListener() {         
	            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
	            		int position, long id) {
	           	
	            }
	                                 
	            public void onNothingSelected(AdapterView<?> parentView) {            	
	            }
	        });
	    spCompPer.setAdapter(adRespCiv);
	    
	    ArrayList<String> val2 = new ArrayList<String>();
		val2.add("300000");
		 ArrayAdapter<String> adgastosmed = new ArrayAdapter<String>(this,R.layout.texto_spin, val2);  
	    spGastosMedOcu.setAdapter(adgastosmed);
	    	    
	    ArrayList<String> val3 = new ArrayList<String>();
		val3.add("100000");
		 ArrayAdapter<String> adMuerCond = new ArrayAdapter<String>(this,R.layout.texto_spin, val3);  
	    spMuerteCond.setAdapter(adMuerCond);
	    
	    ArrayList<String> deds = new ArrayList<String>();
		deds.add("3%");
		deds.add("5%");
		deds.add("10%");
		 ArrayAdapter<String> adDeds = new ArrayAdapter<String>(this,R.layout.texto_spin, deds);  
	    spDedDanosMat.setAdapter(adDeds);
	    
	    spDedDanosMat.setOnItemSelectedListener(new OnItemSelectedListener() {         
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
            	double tempSuma=0;
            	tempSuma=Double.valueOf(tvsumDanosMat.getText().toString());
            	if(position==0){
            		tvPrimDanosMat.setText(formatter.format((tempSuma * 0.085)*relCP));
            	}else
            		if(position==1){
                		tvPrimDanosMat.setText(String.valueOf((tempSuma * 0.074)*relCP));
            		}else{
                		tvPrimDanosMat.setText(String.valueOf((tempSuma * 0.054)*relCP));
            		}
            	
            	calculaPrimaNeta();
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {            	
            }
        });
	    
	    ArrayList<String> deds2 = new ArrayList<String>();
		deds2.add("5%");
		deds2.add("10%");
		deds2.add("20%");
		 ArrayAdapter<String> adDeds2 = new ArrayAdapter<String>(this,R.layout.texto_spin, deds2);  
	    
	    spDedRoboTot.setAdapter(adDeds2);
	    
	    spDedRoboTot.setOnItemSelectedListener(new OnItemSelectedListener() {         
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
            	double tempSuma;
            	tempSuma=Double.valueOf(tvsumDanosMat.getText().toString());
            	if(position==0){
            		tvPrimRobo.setText(formatter.format((tempSuma * 0.0062)*relCP));
            	}else
            		if(position==1){
                		tvPrimRobo.setText(String.valueOf((tempSuma * 0.0056 )*relCP));
            		}else{
                		tvPrimRobo.setText(String.valueOf((tempSuma * 0.0049 )*relCP));

            		}
            	
            	calculaPrimaNeta();           	
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {            	
            }
        });	  
	    
	    tvPrimRespCivil.setText("2082.00");
	    tvPrimRespCivExt.setText("");
	    tvPrimComPersonas.setText("216.53");
	    tvPrimOcupantes.setText("124.77");
	    tvPrimGastosMedOc.setText("464.97");
	    tvPrimMuerteCond.setText("70.00");
	    tvPrimGasLeg.setText("360.00");
	    tvPrimAsisVial.setText("325.00");
	    tvPrimExtCob.setText("265.06");
	    tvPrimGasTrans.setText("391.30");
	    tvPrimRoboParcial.setText("588.55");	    
	    	    	    
	}

	private void calculaPrimaNeta() {
		primaNeta=0;
		
		primaNeta=primaNeta+Double.parseDouble(tvPrimDanosMat.getText().toString());
		primaNeta=primaNeta+Double.parseDouble(tvPrimRobo.getText().toString());
		primaNeta=primaNeta+Double.parseDouble(tvPrimRespCivil.getText().toString());
		if(tvPrimComPersonas.getVisibility()==View.VISIBLE)
			primaNeta=primaNeta+Double.parseDouble(tvPrimComPersonas.getText().toString());
		if(tvPrimOcupantes.getVisibility()==View.VISIBLE)
			primaNeta=primaNeta+Double.parseDouble(tvPrimOcupantes.getText().toString());
		primaNeta=primaNeta+Double.parseDouble(tvPrimGastosMedOc.getText().toString());
		primaNeta=primaNeta+Double.parseDouble(tvPrimMuerteCond.getText().toString());
		primaNeta=primaNeta+Double.parseDouble(tvPrimGasLeg.getText().toString());
		if(tvPrimAsisVial.getVisibility()==View.VISIBLE)
			primaNeta=primaNeta+Double.parseDouble(tvPrimAsisVial.getText().toString());
		if(tvPrimExtCob.getVisibility()==View.VISIBLE)
			primaNeta=primaNeta+Double.parseDouble(tvPrimExtCob.getText().toString());
		if(tvPrimGasTrans.getVisibility()==View.VISIBLE)
			primaNeta=primaNeta+Double.parseDouble(tvPrimGasTrans.getText().toString());
		if(tvPrimRoboParcial.getVisibility()==View.VISIBLE)
			primaNeta=primaNeta+Double.parseDouble(tvPrimRoboParcial.getText().toString());
		if(tvPrimEquipoEspecial.getVisibility()==View.VISIBLE)
			primaNeta=primaNeta+Double.parseDouble(tvPrimEquipoEspecial.getText().toString());
		
		//Formato
		DecimalFormat formatter;
		DecimalFormatSymbols custom=new DecimalFormatSymbols();
		custom.setDecimalSeparator('.');
		
		formatter = new DecimalFormat("#.00");
		formatter.setDecimalFormatSymbols(custom);

		tvPrimaNeta.setText(formatter.format(primaNeta));
		
		String frac=formatter.format(fraccionado);
		Log.e("Fraccionado", frac);
		tvPagoFracc.setText(frac);
		tvDerPoliza.setText("420.00");
		
		double subtotal=0;
		subtotal=primaNeta+(Double.valueOf(tvPagoFracc.getText().toString()));
		subtotal=subtotal+(Double.valueOf(tvDerPoliza.getText().toString()));
		tvSubtotal.setText(formatter.format(subtotal));
		
		double iva=0;
		iva=subtotal*.16;
		tvIva.setText(formatter.format(iva));		
		tvTotal.setText(formatter.format(subtotal+iva));		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case R.id.menu:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}
	
	private void llenarSpinMarca() {
		ArrayList<String> marcas = new ArrayList<String>();
		
		GenericDAO genDAO = new GenericDAO(db);
		ArrayList<Marca> marca=new ArrayList<Marca>();
		marca=genDAO.getMarcas(1);
		
		Iterator<Marca> it = marca.iterator();

		while (it.hasNext()) {
			Marca temp=it.next();
			marcas.add(temp.getMarca());
			imagenes.add(temp.getImagen());
		}

        ArrayAdapter<String> adMarcas = new ArrayAdapter<String>(this,R.layout.texto_spin, marcas);      
        spMarca.setAdapter(adMarcas);

	}
	
	private void llenarSpinModelo(int position) {
		ArrayList<String> tipos = new ArrayList<String>();
		GenericDAO genDAO = new GenericDAO(db);
		ArrayList<ModeloDTO> modelo=new ArrayList<ModeloDTO>();
		modelo=genDAO.getModelos(position);
		
		Iterator<ModeloDTO> it = modelo.iterator();

		while (it.hasNext()) {
			ModeloDTO temp=it.next();
			tipos.add(temp.getModelo());
		}
		Random rand = new Random();
		tvClave.setText(String.valueOf(rand.nextInt((89000 - 80000) + 1) + 80000));
        ArrayAdapter<String> adMarcas = new ArrayAdapter<String>(this,R.layout.texto_spin, tipos);      
        spTipo.setAdapter(adMarcas);
	}
	
	private void llenarSpinVersion(int position) {
		ArrayList<String> versiones = new ArrayList<String>();
		GenericDAO genDAO = new GenericDAO(db);
		ArrayList<VersionDTO> version=new ArrayList<VersionDTO>();
		version=genDAO.getVersiones(position);
		
		Iterator<VersionDTO> it = version.iterator();

		while (it.hasNext()) {
			VersionDTO temp=it.next();
			versiones.add(temp.getVersion());
		}
		
        ArrayAdapter<String> adMarcas = new ArrayAdapter<String>(this,R.layout.texto_spin, versiones);      
        spVersion.setAdapter(adMarcas);
	}
	
	private void llenarSpinYear(int position) {
		ArrayList<String> anos = new ArrayList<String>();
		GenericDAO genDAO = new GenericDAO(db);
		ArrayList<Year> year=new ArrayList<Year>();
		year=genDAO.getYear(position);
		
		Iterator<Year> it = year.iterator();

		while (it.hasNext()) {
			Year temp=it.next();
			anos.add(String.valueOf(temp.getYear()));
		}
		
        ArrayAdapter<String> adMarcas = new ArrayAdapter<String>(this,R.layout.texto_spin, anos);      
        spAno.setAdapter(adMarcas);
	}
	
	private void llenarSpinEstado(int position) {
		ArrayList<String> estados = new ArrayList<String>();
		GenericDAO genDAO = new GenericDAO(dbCp);
		estado=genDAO.getEstados(position);
		
		Iterator<Estado> it = estado.iterator();

		while (it.hasNext()) {
			Estado temp=it.next();
			estados.add(temp.getNombre());
		}
		
        ArrayAdapter<String> adEstado = new ArrayAdapter<String>(this,R.layout.texto_spin, estados);      
        spEstado.setAdapter(adEstado);
	}
	private void llenarSpinCiudad(String idEstado) {
		ArrayList<String> ciudades = new ArrayList<String>();
		GenericDAO genDAO = new GenericDAO(dbCp);
		ciudad=genDAO.getCiudades(idEstado);
		
		Iterator<Ciudad> it = ciudad.iterator();

		while (it.hasNext()) {
			Ciudad temp=it.next();
			ciudades.add(temp.getNombre());
		}
        ArrayAdapter<String> adCiudad = new ArrayAdapter<String>(this,R.layout.texto_spin, ciudades);      
        spCiudad.setAdapter(adCiudad);
	}
	
	private void llenarSpinCP(String idMnpio, String idEstado) {
		ArrayList<String> CPs = new ArrayList<String>();
		GenericDAO genDAO = new GenericDAO(dbCp);
		
		CPs=genDAO.getCps(idMnpio,idEstado);
		
        ArrayAdapter<String> adCPs = new ArrayAdapter<String>(this,R.layout.texto_spin, CPs);      
        spCP.setAdapter(adCPs);
	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		final DecimalFormat formatter;
		DecimalFormatSymbols custom=new DecimalFormatSymbols();
		custom.setDecimalSeparator('.');
		
		formatter = new DecimalFormat("#.00");
		formatter.setDecimalFormatSymbols(custom);
		double val,res;
		
		if(s.toString().equals("")){
			Log.e("Error", s.toString());
		}
		else{
			val=Double.valueOf(s.toString());
			if(val>999 && val <60001){
				res=val*.049;
				tvPrimEquipoEspecial.setText(formatter.format(res));			
			}else{
				tvPrimEquipoEspecial.setText("0.00");
			}
		}
		
	}
	public void buscaCP(View view){
		String cp;
		cp=etColonia.getText().toString();
		GenericDAO genDao = new GenericDAO(dbCp);
		String res=genDao.BuscaCP(cp);
		
		if(res.equals("Codigo Postal No encontrado")){
			Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
		}else{
			tvCP.setText(res);
			Toast.makeText(this,"Codigo Postal encontrado" , Toast.LENGTH_SHORT).show();
			
		}
	}
	
	public void SolicitarCotizacion(View view){
		Intent i = new Intent(this, SolicitudActivity.class);
        i.putExtra("pais", spPais.getItemAtPosition(MainActivity.posPais).toString());
        i.putExtra("estado", spEstado.getItemAtPosition(MainActivity.posEstado).toString());
        i.putExtra("ciudad", spCiudad.getItemAtPosition(MainActivity.posCiudad).toString());
        i.putExtra("cp", spCP.getItemAtPosition(MainActivity.posCP).toString());
        startActivity(i);
	}
	
	class task extends AsyncTask<Void, Void, Void>{

		ProgressDialog pd;
		MainActivity activity;
		public task(MainActivity activity) {
	        this.activity = activity;
	        pd = new ProgressDialog(activity);
	        
	    }

		@Override
		protected void onPreExecute() {
			this.pd.setMessage("Descargando BD...");
	        this.pd.show();
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... params) {
			 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
			Toast.makeText(activity, "BD descargada con éxito", Toast.LENGTH_SHORT);
			super.onPostExecute(result);
		}
		
	}
		
}