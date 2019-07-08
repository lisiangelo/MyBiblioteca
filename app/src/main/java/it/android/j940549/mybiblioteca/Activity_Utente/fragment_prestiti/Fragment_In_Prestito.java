package it.android.j940549.mybiblioteca.Activity_Utente.fragment_prestiti;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import it.android.j940549.mybiblioteca.Model.Libri_In_Prestito;
import it.android.j940549.mybiblioteca.Model.Libro_catalogo;
import it.android.j940549.mybiblioteca.Model.Utente;
import it.android.j940549.mybiblioteca.R;
import it.android.j940549.mybiblioteca.SQLite.DBLayer;


public class Fragment_In_Prestito extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private Utente utenteLogin;
    ArrayList mDataset;


    public Fragment_In_Prestito() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters

    public static Fragment_In_Prestito newInstance(Utente utenteLogin){//},ArrayList<Libri_In_Prestito> libri_in_prestito) {
        Fragment_In_Prestito fragment = new Fragment_In_Prestito();
        Bundle args = new Bundle();
        args.putSerializable("utente", utenteLogin);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataset = new ArrayList<Libri_In_Prestito>();

        if (getArguments() != null) {
            this.utenteLogin = (Utente) getArguments().getSerializable("utente");
        }
        Log.i("log_tag_arg","argumets "+utenteLogin.getNrtessera());




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;

        rootView = inflater.inflate(R.layout.fragment_in_prestito, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_incarico);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        caricaDati(utenteLogin.getNrtessera());

        mAdapter = new MyRecyclerViewAdapter_in_prestito(getDataSet(),getActivity(),utenteLogin);
        mRecyclerView.setAdapter(mAdapter);


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

  }

    @Override
    public void onDetach() {
        super.onDetach();
      //  mListener = null;
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


    private ArrayList<Libri_In_Prestito> getDataSet() {
        return mDataset;
    }


    public void caricaDati(String nrtessera) {
        Log.i("log_tag_argcar","arumets "+nrtessera);


        DBLayer dbLayer = null;

        try {
            dbLayer = new DBLayer(getContext());
            dbLayer.open();


//                Cursor cursor = dbLayer.getLibro(isbn, titolo, autore, genere, fulltext);
            Cursor cursor = dbLayer.getAllPrestiti();

            if (cursor.getCount() > 0) {
                cursor.moveToPosition(0);
                do {
                    Libri_In_Prestito libroIn_prestito = new Libri_In_Prestito();

                    libroIn_prestito.setIsbn(cursor.getString(1));
                    libroIn_prestito.setTitolo(cursor.getString(2));
                    libroIn_prestito.setData_prestito(cursor.getString(3));
                    libroIn_prestito.setPatch_img(cursor.getString(4));

                    mDataset.add(libroIn_prestito);
                } while (cursor.moveToNext());
            }
        } catch (SQLException ex) {
            Toast.makeText(getContext(), "" + ex.toString(), Toast.LENGTH_SHORT).show();
        }
        dbLayer.close();



    }


    }
