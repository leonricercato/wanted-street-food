package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.model.Tappa;
import it.uniroma3.siw.service.TappaService;

@Controller
public class TappaController {

    @Autowired
    private TappaService tappaService;

    // ----- PARTE PUBBLICA -----
    @GetMapping("/tappe")
    public String getTappe(Model model) {
        model.addAttribute("tappe", this.tappaService.findAll());
        return "tappe.html";
    }

    // ----- PARTE ADMIN: CREATE -----
    @GetMapping("/admin/tappa/new")
    public String formNewTappa(Model model) {
        model.addAttribute("tappa", new Tappa());
        return "admin/formNewTappa.html";
    }

    @PostMapping("/admin/tappa")
    public String newTappa(@ModelAttribute("tappa") Tappa tappa, Model model) {
        this.tappaService.save(tappa);
        return "redirect:/tappe";
    }

    // ----- PARTE ADMIN: UPDATE -----
    @GetMapping("/admin/tappa/edit/{id}")
    public String formEditTappa(@PathVariable Long id, Model model) {
        model.addAttribute("tappa", this.tappaService.findById(id));
        return "admin/formEditTappa.html";
    }

    @PostMapping("/admin/tappa/edit/{id}")
    public String editTappa(@PathVariable Long id, @ModelAttribute("tappa") Tappa tappa) {
        tappa.setId(id);
        this.tappaService.save(tappa);
        return "redirect:/tappe";
    }

    // ----- PARTE ADMIN: DELETE -----
    @PostMapping("/admin/tappa/delete/{id}")
    public String deleteTappa(@PathVariable Long id) {
        this.tappaService.deleteById(id);
        return "redirect:/tappe";
    }
}