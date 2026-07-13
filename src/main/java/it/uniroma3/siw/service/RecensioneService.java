package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.repository.RecensioneRepository;
import it.uniroma3.siw.model.Recensione;

@Service
public class RecensioneService {
    @Autowired private RecensioneRepository recensioneRepository;

    @Transactional
    public void save(Recensione recensione) { recensioneRepository.save(recensione); }

    public Recensione findById(Long id) { return recensioneRepository.findById(id).orElse(null); }

    public Iterable<Recensione> findAll() { return recensioneRepository.findAll(); }

    @Transactional
    public void deleteById(Long id) { recensioneRepository.deleteById(id); }
}