package it.android.j940549.mybiblioteca.Activity_Utente;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.android.j940549.mybiblioteca.Model.Utente;
import it.android.j940549.mybiblioteca.R;


public class Profilo_frag extends Fragment {

    // TODO: Rename and change types of parameters

    public Profilo_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Profilo_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Profilo_frag newInstance() {
        Profilo_frag fragment = new Profilo_frag();
        Bundle args = new Bundle();
        //args.putSerializable("utente", utenteLogin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          //  utenteLogin= (Utente) getArguments().getSerializable("utente");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profilo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView user_id= view.findViewById(R.id.user_id);
        TextView nrtessera= view.findViewById(R.id.nr_tessera);
        TextView nome= view.findViewById(R.id.nome_user);
        TextView congmome= view.findViewById(R.id.cognome_user);
        TextView email= view.findViewById(R.id.email_user);

        user_id.setText("");
        nrtessera.setText("Nr Tessera: ");
        nome.setText("Nome: ");
        congmome.setText("Cognome: ");
        email.setText("Email: ");

        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profilo");
    }
}
