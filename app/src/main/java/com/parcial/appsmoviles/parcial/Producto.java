package com.parcial.appsmoviles.parcial;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parcial.appsmoviles.parcial.Databases.Tienda;

public class Producto extends AppCompatActivity implements crearProducto.OnFragmentInteractionListener,listadoProducto.OnFragmentInteractionListener,modificarProducto.OnFragmentInteractionListener {
    Toolbar toolbar;
    TabLayout menu;
    TabItem listado;
    crearProducto obj;
    listadoProducto lp;
    modificarProducto mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        toolbar= (Toolbar)findViewById(R.id.Menu);
//Esta linea da error si la descomenta y no permite el launch de la actividad para ingresar  setSupportActionBar(toolbar);
        menu = (TabLayout) findViewById(R.id.menu);
        irListado();

        menu.setTabMode(TabLayout.MODE_SCROLLABLE);
        listado = (TabItem)findViewById(R.id.Listado);
        menu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.equals(menu.getTabAt(1))){
                    irCrear();
                }
                else if(tab.equals(menu.getTabAt(0))){
                    irListado();
                }else if (tab.equals(menu.getTabAt(2))){
                    irModificar();
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void irCrear(){
        obj = new crearProducto();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frameProducto,obj);
        transaction.commit();

    }


    public void irListado(){
        lp = new listadoProducto();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frameProducto,lp);
        transaction.commit();

    }
    public void irModificar(){
        mp = new modificarProducto();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frameProducto,mp);
        transaction.commit();

    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
