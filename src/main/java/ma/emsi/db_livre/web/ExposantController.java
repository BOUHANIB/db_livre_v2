package ma.emsi.db_livre.web;

import jakarta.validation.Valid;
import ma.emsi.db_livre.entities.Exposant;
import ma.emsi.db_livre.repositories.ExposantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ExposantController {

    @Autowired
    ExposantRepository exposantRepository;

    @GetMapping(path = "/user/listExposants")
    public String exposants(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "5") int size,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Exposant> pageExposants = exposantRepository.findByNomContains(keyword, PageRequest.of(page, size));
        List<Exposant> exposants = pageExposants.getContent();
        for (Exposant exposant : exposants) {
            if (exposant.getNom() == null || exposant.getNom().isEmpty()) {
                exposant.setNom("Indisponible");
            }
            if (exposant.getPays() == null || exposant.getPays().isEmpty()) {
                exposant.setPays("Indisponible");
            }
            if (exposant.getMail() == null || exposant.getMail().isEmpty()) {
                exposant.setMail("Indisponible");
            }
            if (exposant.getSpecialite() == null || exposant.getSpecialite().isEmpty()) {
                exposant.setSpecialite("Indisponible");
            }

        }

        model.addAttribute("listExposants", exposants);
        model.addAttribute("pages", new int[pageExposants.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "exposants";
    }

    @GetMapping("/admin/deleteExposant")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteExposant(@RequestParam(name = "id") Long id, String keyword, int page){
        exposantRepository.deleteById(id);
        return "redirect:/user/listExposants?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/listExposants";
    }

    @GetMapping("/user/exposantdetails")
    public String exposantDetails(@RequestParam(name = "id") Long id, Model model) {
        // Récupérer l'exposant à partir de l'id
        Exposant exposant = exposantRepository.findById(id).orElse(null);

        if (exposant == null) {
            return "redirect:/listExposants";
        }
        model.addAttribute("exposant", exposant);
        return "exposantdetails";
    }

    @GetMapping("/admin/formExposants")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formExposant(Model model ){
        model.addAttribute("exposant",new Exposant());
        return "formExposants";
    }

    @PostMapping("/exposant/saveExposant")
    @PreAuthorize("hasRole('ROLE_EXPOSANT')")
    public String saveExposant(Model model, @Valid Exposant exposant, BindingResult bindingResult,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "") String keyword){
        if (bindingResult.hasErrors()) return "formExposants";
        exposantRepository.save(exposant);
        return "redirect:/user/listExposants?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/exposant/editExposant")
    @PreAuthorize("hasRole('ROLE_EXPOSANT')")
    public String editExposant(Long id, Model model,String keyword,int page){
        Exposant exposant=exposantRepository.findById(id).orElse(null);
        if(exposant==null) throw new RuntimeException("Exposant introuvable");
        model.addAttribute("exposant",exposant);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editExposant";
    }


}
