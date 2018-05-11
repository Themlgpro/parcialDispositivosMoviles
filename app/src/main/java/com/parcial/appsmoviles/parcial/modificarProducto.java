package com.parcial.appsmoviles.parcial;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.parcial.appsmoviles.parcial.Databases.Tienda;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link modificarProducto.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link modificarProducto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class modificarProducto extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View indexView;
    Spinner id,Tipo;
    EditText nombre,precio;
    Button modificar;
    Cursor c;
    String ident;

    Tienda conexion;
    private OnFragmentInteractionListener mListener;

    public modificarProducto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment modificarProducto.
     */
    // TODO: Rename and change types and number of parameters
    public static modificarProducto newInstance(String param1, String param2) {
        modificarProducto fragment = new modificarProducto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        conexion =   new Tienda(getContext(),"TiendaBD",null,1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        indexView =inflater.inflate(R.layout.fragment_modificar_producto, container, false);
        nombre = (EditText)indexView.findViewById(R.id.campoNombre);
        precio = (EditText)indexView.findViewById(R.id.campoPrecio);
        id = (Spinner)indexView.findViewById(R.id.idProducto);
        Tipo= (Spinner)indexView.findViewById(R.id.tipoProducto);
        modificar=(Button)indexView.findViewById(R.id.Modificar);



        final SQLiteDatabase db = conexion.getReadableDatabase();

        c = db.rawQuery("SELECT idProducto,nombre FROM productos;",null);
        List<String> products = new ArrayList<String>();
        products.add("<none>");


        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                products.add(c.getString(0)+". "+c.getString(1));
            } while(c.moveToNext());
        }

        id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(id.getSelectedItem().toString().trim()!="<none>") {
                    System.out.println(id.getSelectedItem().toString().trim());

                    String string= new String(id.getSelectedItem().toString().toCharArray());
                     ident = string.split(Pattern.quote("."))[0];
                    System.out.println(ident);

                    c=db.rawQuery("SELECT nombre,tipoProducto,Precio FROM productos WHERE idProducto="+ident+";",null) ;
                    c.moveToFirst();
                    nombre.setText(c.getString(0));
                    if(c.getString(1).equals("Servicio")){
                        Tipo.setSelection(1);
                    }else{
                        Tipo.setSelection(0);
                    }

                    precio.setText(c.getString(2));


                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, products);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id.setAdapter(adapter);

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombre.getText().toString().trim().length()!=0 && precio.getText().toString().trim().length()!=0) {
                    String query = "update productos set nombre='" + nombre.getText().toString().trim() + "',tipoProducto='" + Tipo.getSelectedItem().toString().trim()
                            + "',Precio=" + precio.getText().toString().trim() + " WHERE idProducto=" + ident + ";";
                    System.out.println(query);
                    db.execSQL(query);
                    Toast.makeText(getContext(), "Se ha modificado el producto ", Toast.LENGTH_SHORT).show();
                    nombre.setText("");
                    precio.setText("");
                    id.setSelection(0);
                    Tipo.setSelection(0);
                }else{
                    Toast.makeText(getContext(),"no se permiten campos vacios",Toast.LENGTH_SHORT).show();

                }
            }
        });

        return  indexView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
