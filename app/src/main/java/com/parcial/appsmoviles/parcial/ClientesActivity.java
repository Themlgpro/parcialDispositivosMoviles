package com.parcial.appsmoviles.parcial;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ClientesActivity extends AppCompatActivity implements CrearCliente.OnFragmentInteractionListener {

    CrearCliente obj;
    TabLayout options = (TabLayout) findViewById(R.id.tabOptions);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
    }


    public void creaCliente(View g){
        obj = new CrearCliente();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.clientesLayout,obj);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
