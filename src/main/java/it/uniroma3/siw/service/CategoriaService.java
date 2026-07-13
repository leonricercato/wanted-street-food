package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.repository.CategoriaRepository;
import it.uniroma3.siw.model.Categoria;

@Service
public class CategoriaService {
    @Autowired private CategoriaRepository categoriaRepository;

    @Transactional
    public void save(Categoria categoria) { categoriaRepository.save(categoria); }

    public Categoria findById(Long id) { return categoriaRepository.findById(id).orElse(null); }

    public Iterable<Categoria> findAll() { return categoriaRepository.findAll(); }
}