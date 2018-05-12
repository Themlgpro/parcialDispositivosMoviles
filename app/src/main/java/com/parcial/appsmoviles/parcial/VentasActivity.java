package com.parcial.appsmoviles.parcial;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.parcial.appsmoviles.parcial.Databases.Tienda;

import java.util.ArrayList;

public class VentasActivity extends AppCompatActivity {

    private Spinner spinner1;
    private EditText cantidad1;
    private EditText cantidad2;
    private CheckBox product1, product2;
    ArrayList<Cliente> listClientes;
    ArrayList<String>  listaMostrar;
    Tienda adminbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
        adminbd = new Tienda(this, "administracion",  null, 1 );
        spinner1 = (Spinner)findViewById(R.id.spinner);
        cantidad1 = (EditText)findViewById(R.id.Cantidad1);
        cantidad2 = (EditText)findViewById(R.id.Cantidad2);
        product1 = (CheckBox)findViewById(R.id.producto1);
        product2 = (CheckBox)findViewById(R.id.producto2);
        Toast.makeText(this, "Hola ventas", Toast.LENGTH_LONG).show();
        Listar();
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaMostrar);
        spinner1.setAdapter(adaptador);
    }

    public void registrar(View view){
        //Tienda adminbd = new Tienda(this, "administracion",  null, 1 );
        SQLiteDatabase baseDeDatos = adminbd.getReadableDatabase();

        String cantidadd1 = cantidad1.getText().toString();
        String cantidadd2 = cantidad2.getText().toString();

        int cant1 = Integer.parseInt(cantidadd1);
        int cant2 = Integer.parseInt(cantidadd2);
        ContentValues registr = new ContentValues();
        ContentValues registr2 = new ContentValues();
        String seleccion = spinner1.getSelectedItem().toString();
        if(product1.isChecked() == true && !cantidadd1.isEmpty()){

            int total = cant1 * 200;
            registr2.put("Cliente_Cedula", seleccion);
            registr2.put("totalVenta", total );
            registr.put("Producto_idProducto", 1);
            registr.put("Venta_idVenta", 1);
            registr.put("cantidad", cant1);
            baseDeDatos.insert("Venta", null, registr2);
            baseDeDatos.insert("ventaDetalle", null, registr);
            Toast.makeText(this, "venta registrada", Toast.LENGTH_LONG).show();
        }else if(product2.isChecked() == true && !cantidadd2.isEmpty()) {
            int total = cant2 * 2000;
            registr2.put("Cliente_Cedula", seleccion);
            registr2.put("totalVenta", total);
            registr.put("Producto_idProducto", 2);
            registr.put("Venta_idVenta", 1);
            registr.put("cantidad", cant2);

            baseDeDatos.insert("Venta", null, registr2);
            baseDeDatos.insert("ventaDetalle", null, registr);
            Toast.makeText(this, "venta registrada", Toast.LENGTH_LONG).show();
        }else  if(product1.isChecked() == true && !cantidadd1.isEmpty() && product2.isChecked() == true && !cantidadd2.isEmpty()){
            int total = cant1 * 200 + cant2 * 2000;

            registr2.put("Cliente_Cedula", seleccion);
            registr2.put("totalVenta", total);
            registr.put("Producto_idProducto", 1);
            registr.put("Venta_idVenta", 1);
            registr.put("cantidad", cant1);
            registr.put("Producto_idProducto", 2);
            registr.put("Venta_idVenta", 1);
            registr.put("cantidad", cant2);

            baseDeDatos.insert("Venta", null, registr2);
            baseDeDatos.insert("ventaDetalle", null, registr);
            Toast.makeText(this, "venta registrada", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(this, "Debes llenar los campos de cantidad", Toast.LENGTH_LONG).show();
        }
    }

    public  void Listar(){
        //Tienda adminbd = new Tienda(this, "administracion",  null, 1 );
        Cliente cliente = null;
        SQLiteDatabase baseDeDatos = adminbd.getWritableDatabase();

        Cursor fila = baseDeDatos.rawQuery("select * from clientes", null);

        while (fila.moveToNext()){
            cliente = new Cliente();
            cliente.setCedula(fila.getInt(0));
            cliente.setNombre(fila.getString(0));
            cliente.setDireccion(fila.getString(2));
            cliente.setTelefono(fila.getString(3));

            listClientes.add(cliente);
        }
       //Toast.makeText(this, "cliente " + fila, Toast.LENGTH_LONG).show();
        obtenerLista();

    }

    public void obtenerLista(){
        listaMostrar = new ArrayList<String>();
        listaMostrar.add("Seleccione el Cliente ...");



            for(int i=0; i<listClientes.size(); i++){

                //listaMostrar.add(listClientes.get(i).getCedula().toString() + " - " + listClientes.get(i).getNombre());
                //listaMostrar.add(listClientes.get(i).getNombre());
            }


        //Toast.makeText(this, "Tamaño de lista clientes" + listClientes.size(), Toast.LENGTH_LONG).show();


    }


}
