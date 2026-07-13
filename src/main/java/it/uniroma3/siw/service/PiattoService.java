package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.repository.PiattoRepository;
import it.uniroma3.siw.model.Piatto;

@Service
public class PiattoService {
    @Autowired private PiattoRepository piattoRepository;

    @Transactional
    public void save(Piatto piatto) { piattoRepository.save(piatto); }

    public Piatto findById(Long id) { return piattoRepository.findById(id).orElse(null); }

    public Iterable<Piatto> findAll() { return piattoRepository.findAll(); }

    // Piatti non ancora assegnati a nessuna categoria
    public Iterable<Piatto> findSenzaCategoria() { return piattoRepository.findByCategoriaIsNull(); }

    @Transactional
    public void deleteById(Long id) { piattoRepository.deleteById(id); }
}