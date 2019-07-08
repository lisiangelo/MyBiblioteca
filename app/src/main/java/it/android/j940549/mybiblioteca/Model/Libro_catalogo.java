package it.android.j940549.mybiblioteca.Model;

public class Libro_catalogo {
    private String isbn;
    private String titolo;
    private String autore;
    private String genere;
    private String thumbnail;
    private String descrizione;
    private String recensione;
    private String stelle;
    private String dove;


    public Libro_catalogo(){

    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getRecensione() {
        return recensione;
    }

    public void setRecensione(String recensione) {
        this.recensione = recensione;
    }

    public String getStelle() {
        return stelle;
    }

    public void setStelle(String stelle) {
        this.stelle = stelle;
    }

    public String getDove() {
        return dove;
    }

    public void setDove(String dove) {
        this.dove = dove;
    }
}
