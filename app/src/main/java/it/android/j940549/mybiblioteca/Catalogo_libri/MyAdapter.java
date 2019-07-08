package it.android.j940549.mybiblioteca.Catalogo_libri;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


import it.android.j940549.mybiblioteca.Model.Libro_catalogo;
import it.android.j940549.mybiblioteca.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Libro_catalogo> booksList;
    private Activity myActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener{
        public TextView titolo,  isbn, autore;
        public RatingBar ratingBar;
        public ImageView tumbnail;

        public MyViewHolder(View view) {
            super(view);
            isbn= (TextView) view.findViewById(R.id.isbn_libro);
            titolo = (TextView) view.findViewById(R.id.titolo_libro);
            autore = (TextView) view.findViewById(R.id.autore_libro);
            tumbnail=(ImageView)view.findViewById(R.id.copertina_libro);
            ratingBar= view.findViewById(R.id.ratingbar_librocatalogo);

        }

      /*  @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "This is my Toast message!",
                    Toast.LENGTH_LONG).show();
        }*/
    }


    public MyAdapter(ArrayList<Libro_catalogo> booksList, Activity myActivity) {
        this.booksList = booksList;
        this.myActivity=myActivity;

        Log.i("mylibreria","dataset.size--"+getItemCount());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_libro_x_catalogo, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView isbn=view.findViewById(R.id.isbn_libro);
                String isbn_libro=isbn.getText().toString();
                Intent vaiaDettagli=new Intent(view.getContext(), Dettaglio_libro.class);
                vaiaDettagli.putExtra("isbn",isbn_libro);
                myActivity.startActivity(vaiaDettagli);
                //myActivity.finish();

            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Libro_catalogo libro=booksList.get(position);
        holder.titolo.setText(libro.getTitolo());
        holder.autore.setText(libro.getAutore());
        holder.isbn.setText(libro.getIsbn());

        URL url = null;Bitmap bitmap=null;
        try {
            url = new URL(libro.getThumbnail());

        Glide.with(myActivity)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.tumbnail);
        //bitmap= BitmapFactory.decodeStream(url.openStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String rating=libro.getStelle();
        if(!rating.equals(""))
        holder.ratingBar.setRating((float) Double.parseDouble(rating));


    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
    public void vaiaDettagli(String isbn){

    }
}
