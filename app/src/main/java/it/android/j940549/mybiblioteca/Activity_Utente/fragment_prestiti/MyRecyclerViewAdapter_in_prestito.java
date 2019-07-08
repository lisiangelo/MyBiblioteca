package it.android.j940549.mybiblioteca.Activity_Utente.fragment_prestiti;

/**
 * Created by J940549 on 30/12/2017.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import it.android.j940549.mybiblioteca.Model.Libri_In_Prestito;
import it.android.j940549.mybiblioteca.Model.Utente;
import it.android.j940549.mybiblioteca.R;


public class MyRecyclerViewAdapter_in_prestito extends RecyclerView.Adapter<MyRecyclerViewAdapter_in_prestito.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Libri_In_Prestito> mDataset;
    private static Activity mActivity;
    private Utente utenteLogin;


    public static class DataObjectHolder extends RecyclerView.ViewHolder{
        TextView isbn;{}
        TextView titolo,dal;
        ImageView img;
        ImageButton btn;

        public DataObjectHolder(View itemView) {
            super(itemView);
            isbn = (TextView) itemView.findViewById(R.id.isbn_libro);
            titolo = (TextView) itemView.findViewById(R.id.titolo_libro);
            dal = (TextView) itemView.findViewById(R.id.data_prestito);
            img = itemView.findViewById(R.id.copertina_libro);
            if (mActivity.getTitle().equals("Dettaglio_Utente")) {
               // btn = itemView.findViewById(R.id.btn_reso_prestito);

                Log.i(LOG_TAG, "Adding Listener");
            }
        }

    }


    public MyRecyclerViewAdapter_in_prestito(ArrayList<Libri_In_Prestito> myDataset,Activity activity, Utente utenteLogin) {
        mDataset = myDataset;
        mActivity=activity;
        this.utenteLogin=utenteLogin;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("log_tag", mActivity.getTitle().toString());
        View view =null;
        if(mActivity.getTitle().equals("Prestiti")) {
            view = LayoutInflater.from(mActivity.getBaseContext())
                    .inflate(R.layout.card_libro_in_prestito, parent, false);
        }

        if(mActivity.getTitle().equals("Dettaglio_Utente")) {
           /* view = LayoutInflater.from(mActivity.getBaseContext())
                    .inflate(R.layout.card_libro_in_prestito_gestore, parent, false);*/
        }
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        final String isbn=mDataset.get(position).getIsbn();
        holder.isbn.setText(mDataset.get(position).getIsbn());
        holder.titolo.setText(mDataset.get(position).getTitolo());
        holder.dal.setText(mDataset.get(position).getData_prestito());

        URL url = null;
        Bitmap image=null;
        try {
            url = new URL(mDataset.get(position).getPatch_img());

            Glide.with(mActivity)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.img);

            //image= BitmapFactory.decodeStream(url.openStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.img.setImageBitmap(image);
    if(mActivity.getTitle().equals("Dettaglio_Utente")){
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            /*    Restituisci_Libro restituisci_libro= new Restituisci_Libro(mActivity,utenteLogin);
                restituisci_libro.execute(utenteLogin.getNrtessera(),isbn);*/
            }

        });

    }
    }

    public void addItem(Libri_In_Prestito dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
