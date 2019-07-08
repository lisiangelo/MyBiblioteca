package it.android.j940549.mybiblioteca.Catalogo_libri;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import it.android.j940549.mybiblioteca.Activity_Utente.InserisciBook;
import it.android.j940549.mybiblioteca.Activity_Utente.Ricerca_frag;
import it.android.j940549.mybiblioteca.Model.Libro_catalogo;
import it.android.j940549.mybiblioteca.R;
import it.android.j940549.mybiblioteca.SQLite.DBLayer;


public class Catalogo extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLinearLayoutManager;
    private ArrayList<Libro_catalogo> myDataset = new ArrayList<Libro_catalogo>();


    public Catalogo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment Gestisci_Catalogo_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Catalogo newInstance() {
        Catalogo fragment = new Catalogo();
        Bundle args = new Bundle();
        //args.putSerializable("utente", utenteLogin);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          //  utenteLogin = (Utente) getArguments().getSerializable("utente");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_catalogo, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_catalogo);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a Linear grid layout manager
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

            // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset, getActivity());

        mRecyclerView.setAdapter(mAdapter);// Inflate the layout for this fragment

        creaLibreria();


        FloatingActionButton fab= view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment=InserisciBook.newInstance("");

                fragmentManager.beginTransaction().replace(R.id.flContent_utente, fragment).commit();

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Catalogo");

    }

    private void creaLibreria() {
        myDataset.clear();
        DBLayer dbLayer = null;

        try {
            dbLayer = new DBLayer(getContext());
            dbLayer.open();
            Cursor cursor = dbLayer.getCatalogo();

            aggiornaDataSet(cursor);
        } catch (SQLException ex) {
            Toast.makeText(getActivity(), "" + ex.toString(), Toast.LENGTH_SHORT).show();
        }
        dbLayer.close();

        mAdapter = new MyAdapter(myDataset, getActivity());

        mRecyclerView.setAdapter(mAdapter);
    }

    private void cercaLibro(String fulltext) {
        myDataset.clear();
        DBLayer dbLayer = null;

        try {
            dbLayer = new DBLayer(getContext());
            dbLayer.open();
            Cursor cursor = dbLayer.getLibro("","","","",fulltext);

            aggiornaDataSet(cursor);

        } catch (SQLException ex) {
            Toast.makeText(getActivity(), "" + ex.toString(), Toast.LENGTH_SHORT).show();
        }
        dbLayer.close();
        mAdapter = new MyAdapter(myDataset, getActivity());

        mRecyclerView.setAdapter(mAdapter);

    }

    private void aggiornaDataSet(Cursor cursor){
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            do {
                Libro_catalogo libro = new Libro_catalogo();

                libro.setIsbn(cursor.getString(1));
                libro.setTitolo(cursor.getString(2));
                libro.setAutore(cursor.getString(3));
                libro.setGenere(cursor.getString(4));
                libro.setThumbnail(cursor.getString(5));
                libro.setDescrizione(cursor.getString(6));
                libro.setRecensione(cursor.getString(7));
                libro.setStelle(cursor.getString(8));
                libro.setDove(cursor.getString(9));

                myDataset.add(libro);
            } while (cursor.moveToNext());
        }

    }

}
