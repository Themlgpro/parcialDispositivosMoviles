package com.parcial.appsmoviles.parcial;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parcial.appsmoviles.parcial.Databases.Tienda;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VentasActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Tienda conexion;
    private SQLiteDatabase bd;
    Spinner cliente,producto;
    Cursor c,c1;
    EditText cantidad;
    Button add,venta;
    ArrayList<String> carrito;
    String clienteVendido = ""; String productoVendido = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cliente = (Spinner) findViewById(R.id.selectClientes);
        producto = (Spinner) findViewById(R.id.selectProductos);

        cantidad = (EditText) findViewById(R.id.cantidad);
        carrito = new ArrayList<>();
        add = (Button) findViewById(R.id.añadir);
        venta = (Button) findViewById(R.id.vender);

        toolbar= (Toolbar)findViewById(R.id.Menu);
        conexion=new Tienda(this,"TiendaBD",null,1);
        bd = conexion.getWritableDatabase();

        //Creacion de instancia para lectura

        final SQLiteDatabase lectura = conexion.getReadableDatabase();

        c = lectura.rawQuery("Select cedula, nombre from clientes;",null);
        List<String> clientes = new ArrayList<>();
        clientes.add("Seleccione un cliente");

        if(c.moveToFirst()){
            do {
                clientes.add(c.getString(0) + ". " + c.getString(1));
            }while (c.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clientes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cliente.setAdapter(adapter);



        c1 = lectura.rawQuery("Select idProducto, nombre from productos;",null);
        List<String> productos = new ArrayList<>();
        productos.add("Seleccione un producto");

        if(c1.moveToFirst()){
            do {
                productos.add(c1.getString(0) + ". " + c1.getString(1));
            }while (c1.moveToNext());
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, productos);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        producto.setAdapter(adapter1);



        cliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String string = new String(cliente.getSelectedItem().toString().toCharArray());
                clienteVendido = string.split(Pattern.quote("."))[0];
                System.out.println(clienteVendido);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Listener para el producto
        producto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String string = new String(producto.getSelectedItem().toString().toCharArray());
                productoVendido = string.split(Pattern.quote("."))[0];
                System.out.println(productoVendido);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addToCar(View view){
        if(cantidad.getText().toString().trim().length()!=0){
            carrito.add(productoVendido + "-" + cantidad.getText().toString().trim());
            cliente.setEnabled(false);
            Toast.makeText(getApplicationContext(),"Agregado al carro.",Toast.LENGTH_LONG).show();


        }else{
            Toast.makeText(getApplicationContext(),"Ingrese una cantidad",Toast.LENGTH_LONG).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void intoClientes(){
        Intent goToClientes = new Intent(this,ClientsActivity.class);
        goToClientes.addFlags(goToClientes.FLAG_ACTIVITY_CLEAR_TOP | goToClientes.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(goToClientes);
    }
    public void intoVentas(){
        Intent goToVentas = new Intent(this,VentasActivity.class);
        goToVentas.addFlags(goToVentas.FLAG_ACTIVITY_CLEAR_TOP | goToVentas.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(goToVentas);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cliente:
                intoClientes();
                return  true;
            case R.id.producto:
                Toast.makeText(this, "producto", Toast.LENGTH_SHORT).show();
                irProducto();
                return  true;
            case R.id.venta:
                intoVentas();
                return  true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }

    public void irProducto(){
        Intent ir = new Intent(VentasActivity.this,Producto.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);

    }

    public void cerrarVenta(View view) {
        if(carrito.size() == 0)
        {
            Toast.makeText(getApplicationContext(),"Carro vacio",Toast.LENGTH_LONG).show();
        }
        else{
            int total = 0;
            final SQLiteDatabase lector = conexion.getReadableDatabase();
            for(int i=0;i<carrito.size();i++) {
                String producto = new String(carrito.get(i)).split(Pattern.quote("-"))[0];
                int cantidad = Integer.parseInt(new String(carrito.get(i)).split(Pattern.quote("-"))[1]);
                System.out.println(cantidad + "-" +producto);
                c = lector.rawQuery("Select Precio from productos Where idProducto = " + Integer.parseInt(producto) + ";", null);
                c.moveToFirst();
                System.out.println(c.getInt(0));
                total+= c.getInt(0) * cantidad;
                System.out.println(total);
            }

            String query = "Insert into Venta(Cliente_Cedula, totalVenta) VALUES ('"+clienteVendido+"','"+total+"');";

            bd.execSQL(query);


            //Traer el id de la venta
            c = lector.rawQuery("Select idVenta from Venta ;", null);
            c.moveToLast();
            int idVenta = c.getInt(0);

            for(int i=0;i<carrito.size();i++){
                int cantidad = Integer.parseInt(new String(carrito.get(i)).split(Pattern.quote("-"))[1]);
                int  codProducto = Integer.parseInt(new String(carrito.get(i)).split(Pattern.quote("-"))[0]);
                System.out.println("Cantidad: "+ cantidad +" " + "Producto: "+codProducto);
                String vDetalle = "Insert into ventaDetalle(Producto_idProducto, Venta_idVenta, Cantidad) VALUES ('"+codProducto+"','"+idVenta+"','"+cantidad+"');";
                bd.execSQL(vDetalle);
                Toast.makeText(getApplicationContext(),"Venta realizada. Total: " + total,Toast.LENGTH_LONG).show();
            }

            cliente.setEnabled(true);
            carrito = new ArrayList<>();
            clienteVendido = "";
            productoVendido = "";

        }


    }
}
