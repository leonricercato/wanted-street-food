package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class AuthenticationController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UserService userService;

    // Mostra la pagina di registrazione
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "register.html";
    }

    // Riceve i dati del form di registrazione e li salva nel database
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               @ModelAttribute("credentials") Credentials credentials,
                               Model model) {
        
        // Salviamo l'utente (dati anagrafici)
        this.userService.saveUser(user);
        
        // Colleghiamo le credenziali all'utente e le salviamo
        credentials.setUser(user);
        this.credentialsService.saveCredentials(credentials);
        
        // Dopo la registrazione, mandiamo l'utente a fare il login
        return "redirect:/login";
    }

    // Mostra la pagina di login
    @GetMapping("/login")
    public String showLoginForm() {
        return "login.html";
    }
}