package it.uniroma3.siw.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Tappa {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nomeMercato;
    private String indirizzo;
    private String giornoSettimana;
    private String fotoUrl;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeMercato() { return nomeMercato; }
    public void setNomeMercato(String nomeMercato) { this.nomeMercato = nomeMercato; }
    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    public String getGiornoSettimana() { return giornoSettimana; }
    public void setGiornoSettimana(String giornoSettimana) { this.giornoSettimana = giornoSettimana; }
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
}