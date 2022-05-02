package be.vdab.luigi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;


@Controller
class IndexControllers {
    // Geeft aan dat de method hieronder een GET request verwerkt naar de URL /.
    // / Staat voor de welkom pagina
    @GetMapping("/")
    // De naam van de method mag vrij gekozen worden.
    // Een methode die data doorgeeft aan de Thymeleaf pagina heeft als return type ModelAndView
    // Model staat voor de data, View staat voor de Thymeleaf pagina.
    public ModelAndView index() {
        var morgenOfMiddag = LocalTime.now().getHour() < 12 ? "morgen" : "middag";
        // Er worden 3 parameters ingegeven in het return-object
        // Parameter 1: De naam van de Thymeleaf pagina terug. Je typt de extensie (.html) niet
        // Parameter 2: De naam waaronder je een stukje data doorgeeft, dit wordt ook gebruikt
        //              in de Thymeleaf pagina index.
        // Parameter 3: De inhoud van de variabele zelf.
        return new ModelAndView("index", "moment", morgenOfMiddag);
    }
}
