package it.uniroma3.siw.model;

import jakarta.persistence.*;

@Entity
public class Recensione {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Integer stelle; // Voto da 1 a 5
    
    @Column(length = 500)
    private String commento; // Testo della recensione

    
    @ManyToOne
    private User autore;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getStelle() { return stelle; }
    public void setStelle(Integer stelle) { this.stelle = stelle; }
    
    public String getCommento() { return commento; }
    public void setCommento(String commento) { this.commento = commento; }
    
    public User getAutore() { return autore; }
    public void setAutore(User autore) { this.autore = autore; }
    
}