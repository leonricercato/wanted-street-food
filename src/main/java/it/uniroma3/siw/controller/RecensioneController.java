package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.RecensioneService;

@Controller
public class RecensioneController {

    @Autowired
    private RecensioneService recensioneService;

    @Autowired
    private CredentialsService credentialsService;

    // ----- PARTE PUBBLICA: pagina con lista (letta via React) + form -----
    // Vedere le recensioni e' possibile a TUTTI, loggati o no.
    @GetMapping("/recensioni")
    public String getRecensioni(Model model, Authentication authentication) {
        model.addAttribute("recensione", new Recensione());

        Long currentUserId = null;
        boolean isAdmin = false;

        // Se l'utente e' loggato (non anonimo), recuperiamo il suo id e il suo ruolo
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Credentials credentials = this.credentialsService.getCredentials(username);
            if (credentials != null) {
                currentUserId = credentials.getUser().getId();
                isAdmin = Credentials.ADMIN_ROLE.equals(credentials.getRole());
            }
        }

        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("isAdmin", isAdmin);
        return "recensioni.html";
    }

    // Endpoint JSON usato dal componente React per popolare la lista (READ) - pubblico
    @GetMapping("/api/recensioni")
    @ResponseBody
    public Iterable<Recensione> getRecensioniJson() {
        return this.recensioneService.findAll();
    }

    // ----- CREATE: solo utenti loggati (richiesto da AuthConfiguration) -----
    @PostMapping("/recensioni")
    public String newRecensione(@ModelAttribute("recensione") Recensione recensione, Authentication authentication) {
        String username = authentication.getName();
        User autore = this.credentialsService.getCredentials(username).getUser();
        recensione.setAutore(autore);
        this.recensioneService.save(recensione);
        return "redirect:/recensioni";
    }

    // ----- UPDATE: solo l'autore puo' modificare la PROPRIA recensione -----
    // Chiamata dal componente React via fetch (PUT con corpo JSON)
    @PutMapping("/api/recensioni/{id}")
    @ResponseBody
    public ResponseEntity<Void> updateRecensione(@PathVariable Long id,
                                                  @RequestBody Recensione recensioneAggiornata,
                                                  Authentication authentication) {
        Recensione esistente = this.recensioneService.findById(id);
        if (esistente == null) {
            return ResponseEntity.notFound().build();
        }

        User utenteLoggato = this.credentialsService.getCredentials(authentication.getName()).getUser();
        boolean isAutore = esistente.getAutore().getId().equals(utenteLoggato.getId());

        if (!isAutore) {
            // Controllo di sicurezza lato server: non basta nascondere il bottone in React!
            return ResponseEntity.status(403).build();
        }

        esistente.setStelle(recensioneAggiornata.getStelle());
        esistente.setCommento(recensioneAggiornata.getCommento());
        this.recensioneService.save(esistente);
        return ResponseEntity.ok().build();
    }

    // ----- DELETE: l'autore puo' cancellare la PROPRIA recensione, l'ADMIN puo' cancellarne qualsiasi -----
    @DeleteMapping("/api/recensioni/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteRecensione(@PathVariable Long id, Authentication authentication) {
        Recensione esistente = this.recensioneService.findById(id);
        if (esistente == null) {
            return ResponseEntity.notFound().build();
        }

        Credentials credentials = this.credentialsService.getCredentials(authentication.getName());
        boolean isAutore = esistente.getAutore().getId().equals(credentials.getUser().getId());
        boolean isAdmin = Credentials.ADMIN_ROLE.equals(credentials.getRole());

        if (!isAutore && !isAdmin) {
            return ResponseEntity.status(403).build();
        }

        this.recensioneService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}