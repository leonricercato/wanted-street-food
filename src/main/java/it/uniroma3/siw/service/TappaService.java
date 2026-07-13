package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.repository.TappaRepository;
import it.uniroma3.siw.model.Tappa;

@Service
public class TappaService {
    @Autowired private TappaRepository tappaRepository;

    @Transactional
    public void save(Tappa tappa) { tappaRepository.save(tappa); }

    public Tappa findById(Long id) { return tappaRepository.findById(id).orElse(null); }

    public Iterable<Tappa> findAll() { return tappaRepository.findAll(); }

    @Transactional
    public void deleteById(Long id) { tappaRepository.deleteById(id); }
}