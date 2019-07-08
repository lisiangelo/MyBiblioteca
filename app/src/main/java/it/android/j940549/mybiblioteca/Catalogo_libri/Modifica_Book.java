package it.android.j940549.mybiblioteca.Catalogo_libri;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import it.android.j940549.mybiblioteca.Activity_Utente.UtenteNav;
import it.android.j940549.mybiblioteca.R;
import it.android.j940549.mybiblioteca.SQLite.DBLayer;

public class Modifica_Book extends AppCompatActivity {
    private EditText txtview_isbn,edit_txtview_descrizione,edit_txtview_titolo,edit_txtview_autore,edit_txtview_genere,
            edit_txtview_posizione;
    private String image_link, resultInsert,isbn_da_Modificare;
    private ImageView imageView;   // private OnFragmentInteractionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica__book);
        final String isbn = getIntent().getExtras().get("isbndaModificare").toString();

        txtview_isbn=(EditText) findViewById(R.id.isbn_libro);
        edit_txtview_descrizione=(EditText) findViewById(R.id.descrizione_libro);
        edit_txtview_titolo=(EditText) findViewById(R.id.titolo_libro);
        edit_txtview_autore=(EditText) findViewById(R.id.autore_libro);
        edit_txtview_genere=(EditText) findViewById(R.id.genere_libro);
        edit_txtview_posizione=(EditText) findViewById(R.id.posizione_libro_inserisci);
        imageView= findViewById(R.id.copertina_libro);

        setTitle("Modifica Dati Libro");
        caricaDatidaDB(isbn);


        //istanzia e gestisci onclic di btn_inserisc
        Button btn_Modifica=(Button) findViewById(R.id.btn_modifica);
        btn_Modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = txtview_isbn.getText().toString();
                String descrizione = edit_txtview_descrizione.getText().toString();
                descrizione = descrizione.replaceAll("'","\'");

                String titolo = edit_txtview_titolo.getText().toString();
                String autore = edit_txtview_autore.getText().toString();
                String subject = edit_txtview_genere.getText().toString();
                String posizione= edit_txtview_posizione.getText().toString();

                SalvaInDB salvaInDB = new SalvaInDB();
                salvaInDB.execute(isbn,titolo, autore, subject, image_link, descrizione,posizione );
//                if (resultInsert.contains("successfully")) {
                    /*Fragment fragment =   Catalogo.newInstance();

                    //inserisci il fragment rimpiazzando i frgment esitente
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.flContent_utente, fragment).commit();*/

  //              }
            }
        });
    }
    private void caricaDatidaDB(String isbn){
        DBLayer dbLayer = null;

        try {
            dbLayer = new DBLayer(this);
            dbLayer.open();
            Cursor cursor = dbLayer.getLibro(isbn,"","","","");

            if (cursor.getCount() > 0) {
                cursor.moveToPosition(0);
                do {

                    txtview_isbn.setText(cursor.getString(1));
                    edit_txtview_titolo.setText(cursor.getString(2));
                    edit_txtview_autore.setText(cursor.getString(3));
                    edit_txtview_genere.setText(cursor.getString(4));
                    edit_txtview_descrizione.setText(cursor.getString(6));
                    URL url = null;Bitmap bitmap=null;
                    try {
                        url = new URL(cursor.getString(5));

                        Glide.with(getBaseContext())
                                .load(url)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(imageView);
                        //bitmap= BitmapFactory.decodeStream(url.openStream());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } while (cursor.moveToNext());
            }
        } catch (SQLException ex) {
            Toast.makeText(this, "" + ex.toString(), Toast.LENGTH_SHORT).show();
        }
        dbLayer.close();


    }

    private class SalvaInDB {

        SalvaInDB () {
        }

        void execute(String... params) {


            DBLayer dbLayer = null;
            int result = -1;
            try {
                dbLayer = new DBLayer(Modifica_Book.this);
                dbLayer.open();

                result = dbLayer.modificaLibro( params[0], params[1], params[2], params[3], params[4],params[5], "","",params[6]);

            } catch (SQLException ex) {
                Toast.makeText(Modifica_Book.this, "" + ex.toString(), Toast.LENGTH_SHORT).show();
            }
            dbLayer.close();
            if (result !=-1) {

                Intent vaiaCatalogo=new Intent(Modifica_Book.this, UtenteNav.class);
                vaiaCatalogo.putExtra("qualeFragment","Catalogo");
                startActivity(vaiaCatalogo);

            }
            Log.i("modificaBook", "risultato " + result);
            //System.out.println(result);


        }
    }


}


