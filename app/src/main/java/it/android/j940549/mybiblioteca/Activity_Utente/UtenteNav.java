package it.android.j940549.mybiblioteca.Activity_Utente;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import it.android.j940549.mybiblioteca.Catalogo_libri.Catalogo;
import it.android.j940549.mybiblioteca.Catalogo_libri.FragmentSearch;
import it.android.j940549.mybiblioteca.Model.Utente;
import it.android.j940549.mybiblioteca.R;

public class UtenteNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawer;
    private String qualeFragment;
    String isbn_daRicercaGoogle="";
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente_nav);
        qualeFragment="";

        if(savedInstanceState!=null){

        }else {

            try {
                qualeFragment = getIntent().getExtras().getString("qualeFragment");
                isbn_daRicercaGoogle= getIntent().getExtras().getString("isbn");
            }catch (Exception e){

            }

        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        caricaFragment(qualeFragment);

    }

    private void caricaFragment(String qualeFragment){
        Fragment fragment;

        switch(qualeFragment){
            case "Prestiti":
                fragment=Prestiti_frag.newInstance();
                break;
            case "Ricerca":
                fragment=Ricerca_frag.newInstance();
                break;
            case "Profilo":
                fragment=Profilo_frag.newInstance();
                break;
            case "Catalogo":
                fragment=Catalogo.newInstance();
                break;

            case "InserisciBook":
                fragment=InserisciBook.newInstance(isbn_daRicercaGoogle);
                break;

            default:
                fragment=Catalogo.newInstance();

        }

        //inserisci il fragment rimpiazzando i frgment esitente
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent_utente, fragment).commit();

    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });

    }

    public void selectDrawerItem(MenuItem item) {

        Fragment fragment=null;



            switch (item.getItemId()) {

                case R.id.nav_catalogo:
                    fragment = Catalogo.newInstance();

                    break;

                case R.id.nav_inserisci:
                    fragment = InserisciBook.newInstance(isbn_daRicercaGoogle);

                    break;

                case R.id.nav_ricerca:
                    fragment = Ricerca_frag.newInstance();

                    break;


                case R.id.nav_prestiti:

                    fragment = Prestiti_frag.newInstance();
                    break;

                case R.id.nav_profilo:

                    fragment = Profilo_frag.newInstance();
                    break;




                default:
                    fragment =   Ricerca_frag.newInstance();

                    break;
            }

            //inserisci il fragment rimpiazzando i frgment esitente
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent_utente, fragment).commit();

        //evidenzio  l'item che è stato selezionato nel Navigationview
        item.setChecked(true);

        //imposto il titolo dellìaction bar
        setTitle(item.getTitle());
        //chiudo il navigationdrawer
        mDrawer.closeDrawers();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                Log.i("SearchOnQueryTxtSubmit:", query);
                Fragment fragmentSearch = FragmentSearch.newInstance( query);
                //inserisci il fragment rimpiazzando i frgment esitente
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent_utente, fragmentSearch).commit();

                // searchItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                Log.i( "SearchOnQueryTxtChan:" , s);
                if (s.equals("")) {
                    Log.i("qualefragment:", qualeFragment);

                    caricaFragment(qualeFragment);
                } else {

                    Fragment fragmentSearch = FragmentSearch.newInstance( s);
                    //inserisci il fragment rimpiazzando i frgment esitente
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.flContent_utente, fragmentSearch).commit();
                }
                return false;
            }
        });
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

//            super.onBackPressed();
            AlertDialog.Builder builder=new AlertDialog.Builder(this)
                    .setMessage("sei sicuro di voler uscire?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            UtenteNav.this.onSuperBackPressed();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alert= builder.create();
            alert.show();

        }
    }

    public  void onSuperBackPressed(){
        super.onBackPressed();
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        selectDrawerItem(item);

        return true;
    }
}
