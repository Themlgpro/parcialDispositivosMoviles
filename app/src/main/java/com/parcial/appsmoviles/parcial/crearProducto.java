package com.parcial.appsmoviles.parcial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parcial.appsmoviles.parcial.Databases.Tienda;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link crearProducto.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link crearProducto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class crearProducto extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText nombre,precio;
    private  Spinner tipo;
    private  Button regis;
    private  Tienda conexion;
    private  SQLiteDatabase db;
    private View indexView;

    public crearProducto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment crearProducto.
     */
    // TODO: Rename and change types and number of parameters
    public static crearProducto newInstance(String param1, String param2) {
        crearProducto fragment = new crearProducto();
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
        conexion=new Tienda(getContext(),"TiendaBD",null,1);
        db = conexion.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        indexView= inflater.inflate(R.layout.fragment_crear_producto, container, false);
        nombre = (EditText)indexView.findViewById(R.id.campoNombre);
        precio = (EditText)indexView.findViewById(R.id.campoPrecio);
        tipo = (Spinner)indexView.findViewById(R.id.tipoProducto);
        regis = (Button)indexView.findViewById(R.id.Registrar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.tipoProdutcto, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adapter);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombre.getText().toString().trim().length()!=0 && precio.getText().toString().trim().length()!=0 ){
                    String query = "insert into productos (nombre,tipoProducto,Precio) values ('" + nombre.getText().toString().trim() + "','" +
                            tipo.getSelectedItem().toString().trim() + "','" +
                            precio.getText().toString().trim()+ "');";
                    db.execSQL(query);
                    Toast.makeText(getContext(),"Productto registrado",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(),"no se permiten  campos vacios",Toast.LENGTH_SHORT).show();
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
