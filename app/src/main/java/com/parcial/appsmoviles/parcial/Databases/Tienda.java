package com.parcial.appsmoviles.parcial.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by carlo on 7/05/2018.
 */

public class Tienda extends SQLiteOpenHelper {

    String query="create table usuarios " + "(cedula TEXT PRIMARY KEY,password TEXT);";
    String query2="create table productos" + "(idProducto INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,tipoProducto TEXT,Precio INTEGER);";
    String query3="create table clientes" + "(cedula TEXT PRIMARY KEY,nombre TEXT,telefono TEXT,direccion TEXT);";
    String query4="CREATE TABLE Venta (idVenta INTEGER  PRIMARY KEY AUTOINCREMENT,Cliente_Cedula VARCHAR  NOT NULL  ,totalVenta DOUBLE ,FOREIGN KEY(Cliente_Cedula)REFERENCES Cliente(Cedula)ON DELETE CASCADE ON UPDATE CASCADE);";
    String query5="CREATE INDEX Venta_FKIndex1 ON Venta (Cliente_Cedula);";
    String query6="CREATE TABLE ventaDetalle(Producto_idProducto INTEGER  NOT NULL,Venta_idVenta INTEGER  NOT NULL ,Cantidad DOUBLE ,PRIMARY KEY(Producto_idProducto, Venta_idVenta),FOREIGN KEY(Producto_idProducto)REFERENCES Producto(idProducto)ON DELETE CASCADE ON UPDATE CASCADE,FOREIGN KEY(Venta_idVenta)REFERENCES Venta(idVenta)ON DELETE CASCADE ON UPDATE CASCADE);";
    String query7="CREATE INDEX Producto_has_Venta_FKIndex1 ON ventaDetalle (Producto_idProducto);";
    String query8="CREATE INDEX Producto_has_Venta_FKIndex2 ON ventaDetalle (Venta_idVenta);";

    public Tienda(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query2);
        sqLiteDatabase.execSQL(query3);
        sqLiteDatabase.execSQL(query4);
        sqLiteDatabase.execSQL(query5);
        sqLiteDatabase.execSQL(query6);
        sqLiteDatabase.execSQL(query7);
        sqLiteDatabase.execSQL(query8);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
