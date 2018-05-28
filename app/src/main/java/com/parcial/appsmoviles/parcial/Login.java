package com.parcial.appsmoviles.parcial;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parcial.appsmoviles.parcial.Databases.Tienda;


public class Login extends AppCompatActivity  implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, Registrarse.OnFragmentInteractionListener   {

    boolean isMobile,IsWifi =false;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private Tienda conexion;
    private SQLiteDatabase bd;
    private EditText usuario, pass;

    boolean estado = false;

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient googleApiClient;
    Registrarse obj;
    AccountManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario = (EditText)findViewById(R.id.usuario);
        pass = (EditText)findViewById(R.id.contraseña);

        conexion= new Tienda(this,"TiendaBD",null,1);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        manager = AccountManager.get(this);
        findViewById(R.id.sign_in).setOnClickListener(this);
    }

    public  boolean checkConnection(){
        boolean isConnet=false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!=null){
            isConnet = activeNetwork.isConnectedOrConnecting();
        }
        if(isConnet && activeNetwork!=null){
            isMobile= activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
            IsWifi = activeNetwork.getType()== ConnectivityManager.TYPE_WIFI;
            return  true;
        }else{
            return  false;
        }
    }

    public void inicioG( View v)
    {
        intoClientes();
    }
    public void intoClientes(){
        Intent goToClientes = new Intent(this,ClientsActivity.class);
        goToClientes.addFlags(goToClientes.FLAG_ACTIVITY_CLEAR_TOP | goToClientes.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(goToClientes);
    }



    private  void signIn(){
        if(checkConnection()){
            if(isMobile){
                Toast.makeText(this,"hola3",Toast.LENGTH_SHORT).show();
                AlertDialog dialogo = new AlertDialog.Builder(this).create();
                dialogo.setTitle("Validar red");
                dialogo.setMessage("Desea consumir datos");
                dialogo.setCancelable(false);
                dialogo.setButton(DialogInterface.BUTTON_POSITIVE,"Aceptar",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                        startActivityForResult(signIntent,RC_SIGN_IN);
                    }
                });
                dialogo.setButton(DialogInterface.BUTTON_NEGATIVE, "La proxima", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogo.show();
            }else{
                estado = true;
                Toast.makeText(this,"hola4",Toast.LENGTH_SHORT).show();
                Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signIntent,RC_SIGN_IN);
            }
        }else{
            Toast.makeText(this,"55",Toast.LENGTH_SHORT).show();
            AlertDialog dialogo = new AlertDialog.Builder(this).create();
            dialogo.setTitle("Sin conexion");
            dialogo.setMessage("No puede ingresar sin estar conectado");
            dialogo.setButton(DialogInterface.BUTTON_NEUTRAL, "Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogo.show();
        }
    }

    private void handleSignInResult(GoogleSignInResult result ) {
        if(result.isSuccess()){
            intoClientes();
        }else{
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RC_SIGN_IN==requestCode){
            GoogleSignInResult resul = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(resul);
        }
    }

    public void iniciarSesion(View g) {
        String u,p;
        u = usuario.getText().toString(); p = pass.getText().toString();
        System.out.println("Valor de u: " + u+", valor de p: "+p);
        if(usuario.getText().toString().trim().equals("") || pass.getText().toString().trim().equals(""))
        {
            System.out.println("jaja"+usuario.getText().toString() + pass.getText().toString());
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage("Hay campos vacios")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create();
            builder.show();
        }
        else {
            SQLiteDatabase db = conexion.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT cedula,password FROM usuarios WHERE cedula='"+usuario.getText().toString().trim()
                    +"' AND password='"+MD5.getMD5(pass.getText().toString().trim())+"';",null);
            if(c.moveToFirst() || estado){
                usuario.setText("");
                pass.setText("");
                intoProductos();;
            }else{
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Login.this);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                android.app.AlertDialog dialog = builder.create();
                dialog.setMessage("El usuario no existe");
                dialog.show();
            }
        }
    }

    private void intoProductos() {
        Intent goToProducto = new Intent(this,Producto.class);
        goToProducto.addFlags(goToProducto.FLAG_ACTIVITY_CLEAR_TOP | goToProducto.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(goToProducto);
    }


    public void regist(View g){
        obj = new Registrarse();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLogin,obj);
        transaction.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in:
                signIn();
                break;

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
