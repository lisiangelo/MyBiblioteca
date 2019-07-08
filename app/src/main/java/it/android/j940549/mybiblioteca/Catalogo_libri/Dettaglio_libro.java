package it.android.j940549.mybiblioteca.Catalogo_libri;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import it.android.j940549.mybiblioteca.R;
import it.android.j940549.mybiblioteca.SQLite.DBLayer;

public class Dettaglio_libro extends AppCompatActivity {
    ImageView copertina;
    TextView isbn_txt;
    TextView titolo_txt;
    TextView autore_txt, genere_txt;
    TextView descrizione_txt, posizione;
    RatingBar ratingBar;
    Button btn_prestalibro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_libro);
        final String isbn = getIntent().getExtras().get("isbn").toString();
        copertina = findViewById(R.id.copertina_libro);
        isbn_txt = findViewById(R.id.isbn_libro);
        titolo_txt = findViewById(R.id.titolo_libro);
        genere_txt = findViewById(R.id.genere_libro);
        autore_txt = findViewById(R.id.autore_libro);
        descrizione_txt = findViewById(R.id.descrizione_libro);
        posizione= findViewById(R.id.posizione_libro);
        btn_prestalibro =findViewById(R.id.presta_libro);
        btn_prestalibro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        ratingBar=findViewById(R.id.ratingbar_dettagliolibri);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                float f=ratingBar.getRating();
                updateRating(isbn_txt.getText().toString(),f);
            }
        });

        setTitle("Dettaglio Libro");
        caricadato(isbn);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_modifica, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_modifica:
                Intent modificaLibro = new Intent(this,Modifica_Book.class);
                modificaLibro.putExtra("isbndaModificare", isbn_txt.getText().toString());
                startActivity(modificaLibro);

                Toast.makeText(this, "click modifica", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void caricadato(String isbn){
        DBLayer dbLayer = null;

        try {
            dbLayer = new DBLayer(this);
            dbLayer.open();
            Cursor cursor = dbLayer.getLibro(isbn,"","","","");

            if (cursor.getCount() > 0) {
                cursor.moveToPosition(0);
                do {

                    isbn_txt.setText(cursor.getString(1));
                    titolo_txt.setText(cursor.getString(2));
                    autore_txt.setText(cursor.getString(3));
                    genere_txt.setText(cursor.getString(4));
                    descrizione_txt.setText(cursor.getString(6));
                    URL url = null;Bitmap bitmap=null;
                    try {
                        url = new URL(cursor.getString(5));

                        Glide.with(getBaseContext())
                                .load(url)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(copertina);
                        //bitmap= BitmapFactory.decodeStream(url.openStream());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String rating=cursor.getString(8);
                    Log.i("rating",rating);
                    if(!rating.equals("")){
                    ratingBar.setRating((float) Double.parseDouble(rating));}
                    posizione.setText(cursor.getString(9));

                } while (cursor.moveToNext());
            }
        } catch (SQLException ex) {
            Toast.makeText(this, "" + ex.toString(), Toast.LENGTH_SHORT).show();
        }
        dbLayer.close();


    }

    private void updateRating(String isbn,float rating){
        DBLayer dbLayer = null;

        try {
            dbLayer = new DBLayer(this);
            dbLayer.open();

            int result= dbLayer.updateRating(isbn,rating);
            if(result!=-1){
                Toast.makeText(this, "rating aggiornato", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException ex) {
            Toast.makeText(this, "" + ex.toString(), Toast.LENGTH_SHORT).show();
        }
        dbLayer.close();


    }



}

