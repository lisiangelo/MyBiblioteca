package it.android.j940549.mybiblioteca.Activity_Esito_Ricerche;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;

import it.android.j940549.mybiblioteca.Controller_DB.Cerca_titolo_inGoogleBooks;
import it.android.j940549.mybiblioteca.Model.Libro_catalogo;
import it.android.j940549.mybiblioteca.R;
import it.android.j940549.mybiblioteca.SQLite.DBLayer;

public class Esito_Ricerca extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Libro_catalogo> myDataset= new ArrayList<Libro_catalogo>();
    private String isbn,titolo,autore,genere,fulltext, tipoRicerca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isbn=getIntent().getExtras().get("isbn").toString();
        titolo=getIntent().getExtras().get("titolo").toString();
        autore=getIntent().getExtras().get("autore").toString();
        genere=getIntent().getExtras().get("genere").toString();
        fulltext=getIntent().getExtras().get("fulltext").toString();
        tipoRicerca=getIntent().getExtras().get("ricerca").toString();

        setContentView(R.layout.activity_esito__ricerca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_esito_ricerca);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //crea dataset

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter_x_ricerca(myDataset,this);
        mRecyclerView.setAdapter(mAdapter);

        if(tipoRicerca.equals("google")){
            setTitle("Esito da GoogleBooks");
        }else{
            setTitle("Esito ricerca in MyBiblioteca");
        }
        eseguiRicerca(tipoRicerca);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void eseguiRicerca(String tipoRicerca){

        if(!tipoRicerca.equals("google")) {

            DBLayer dbLayer = null;

            try {
                dbLayer = new DBLayer(this);
                dbLayer.open();


                Cursor cursor = dbLayer.getLibro(isbn, titolo, autore, genere, fulltext);
                //Cursor cursor = dbLayer.getLibro(isbn);

                if (cursor.getCount() > 0) {
                    cursor.moveToPosition(0);
                    do {
                        Libro_catalogo libro = new Libro_catalogo();

                        libro.setIsbn(cursor.getString(1));
                        libro.setTitolo(cursor.getString(2));
                        libro.setAutore(cursor.getString(3));
                        libro.setGenere(cursor.getString(4));
                        libro.setThumbnail(cursor.getString(5));
                        libro.setRecensione(cursor.getString(6));
                        libro.setStelle(cursor.getString(7));
                        libro.setDove(cursor.getString(8));

                        myDataset.add(libro);
                    } while (cursor.moveToNext());
                }
            } catch (SQLException ex) {
                Toast.makeText(this, "" + ex.toString(), Toast.LENGTH_SHORT).show();
            }
            dbLayer.close();




        }else{
            Cerca_titolo_inGoogleBooks cerca_on_googleBooks = new Cerca_titolo_inGoogleBooks(this, mRecyclerView, mAdapter);
            cerca_on_googleBooks.execute(titolo);
        }
    }


}




