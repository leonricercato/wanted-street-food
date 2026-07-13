package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.CategoriaService;
import it.uniroma3.siw.service.PiattoService;

@Controller
public class PiattoController {

    @Autowired
    private PiattoService piattoService;

    @Autowired
    private CategoriaService categoriaService;

    // ----- LETTURA PUBBLICA: menù raggruppato per categoria -----
    @GetMapping("/piatti")
    public String getPiatti(Model model) {
        model.addAttribute("categorie", this.categoriaService.findAll());
        model.addAttribute("piattiSenzaCategoria", this.piattoService.findSenzaCategoria());
        return "piatti.html";
    }

    // ----- CREATE -----
    @GetMapping("/admin/piatto/new")
    public String formNewPiatto(Model model) {
        model.addAttribute("piatto", new Piatto());
        model.addAttribute("categorie", this.categoriaService.findAll());
        return "admin/formNewPiatto.html";
    }

    @PostMapping("/admin/piatto")
    public String newPiatto(@ModelAttribute("piatto") Piatto piatto,
                             @RequestParam(value = "categoriaId", required = false) Long categoriaId) {
        if (categoriaId != null) {
            piatto.setCategoria(this.categoriaService.findById(categoriaId));
        }
        this.piattoService.save(piatto);
        return "redirect:/piatti";
    }

    // ----- UPDATE -----
    @GetMapping("/admin/piatto/edit/{id}")
    public String formEditPiatto(@PathVariable Long id, Model model) {
        model.addAttribute("piatto", this.piattoService.findById(id));
        model.addAttribute("categorie", this.categoriaService.findAll());
        return "admin/formEditPiatto.html";
    }

    @PostMapping("/admin/piatto/edit/{id}")
    public String editPiatto(@PathVariable Long id, @ModelAttribute("piatto") Piatto piatto,
                              @RequestParam(value = "categoriaId", required = false) Long categoriaId) {
        piatto.setId(id);
        if (categoriaId != null) {
            piatto.setCategoria(this.categoriaService.findById(categoriaId));
        }
        this.piattoService.save(piatto);
        return "redirect:/piatti";
    }

    // ----- DELETE -----
    @PostMapping("/admin/piatto/delete/{id}")
    public String deletePiatto(@PathVariable Long id) {
        this.piattoService.deleteById(id);
        return "redirect:/piatti";
    }

    // ----- TOGGLE DISPONIBILITA': admin nasconde/mostra un piatto senza cancellarlo -----
    @PostMapping("/admin/piatto/toggle/{id}")
    public String toggleDisponibilita(@PathVariable Long id) {
        Piatto piatto = this.piattoService.findById(id);
        piatto.setDisponibile(!piatto.isDisponibile());
        this.piattoService.save(piatto);
        return "redirect:/piatti";
    }
}