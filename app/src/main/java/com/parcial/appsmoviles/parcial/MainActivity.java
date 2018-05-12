package com.parcial.appsmoviles.parcial;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parcial.appsmoviles.parcial.Databases.Tienda;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Tienda conexion;
    private SQLiteDatabase bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= (Toolbar)findViewById(R.id.Menu);
        setSupportActionBar(toolbar);
        conexion=new Tienda(this,"TiendaBD",null,1);
        bd = conexion.getWritableDatabase();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cliente:
                Toast.makeText(this, "Cliente", Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.producto:
                Toast.makeText(this, "producto", Toast.LENGTH_SHORT).show();
                irProducto();
                return  true;
            case R.id.venta:
                Toast.makeText(this, "Venta", Toast.LENGTH_SHORT).show();
                return  true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }

    public void irProducto(){
        Intent ir = new Intent(MainActivity.this,Producto.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);

    }
}
