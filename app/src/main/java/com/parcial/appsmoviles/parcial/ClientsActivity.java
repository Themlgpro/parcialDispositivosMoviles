package com.parcial.appsmoviles.parcial;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import static android.widget.Toast.*;

public class ClientsActivity extends AppCompatActivity implements CrearCliente.OnFragmentInteractionListener, ListarClientes.OnFragmentInteractionListener {

    CrearCliente obj;
    Toolbar toolbar;
    TabLayout menu;
    TabItem listado;
    ListarClientes lc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);

        menu = (TabLayout) findViewById(R.id.menu);
        irListado();

        menu.setTabMode(TabLayout.MODE_SCROLLABLE);
        listado = (TabItem)findViewById(R.id.Listado);
        menu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.equals(menu.getTabAt(1))){
                    creaCliente();
                }
                else if(tab.equals(menu.getTabAt(0))){
                    irListado();
                }
                else if(tab.equals(menu.getTabAt(2))){
                    Intent goToProductos = new Intent(getApplicationContext(),Producto.class);
                    goToProductos.addFlags(goToProductos.FLAG_ACTIVITY_CLEAR_TOP | goToProductos.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(goToProductos);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void irListado() {
        lc = new ListarClientes();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.clientesLayout,lc);
        transaction.commit();
    }


    public void creaCliente(){
        obj = new CrearCliente();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.clientesLayout,obj);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
