package it.uniroma3.siw.model;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Piatto> piatti;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Piatto> getPiatti() { return piatti; }
    public void setPiatti(List<Piatto> piatti) { this.piatti = piatti; }
}