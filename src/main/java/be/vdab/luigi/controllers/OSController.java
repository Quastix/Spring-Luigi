package be.vdab.luigi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

// @Controller zorgt ervoor dat Spring een bean van de class maakt.
// Een bean is een Java object waar Spring handelingen op kan uitvoeren.
@Controller
// De controller verwerkt request naar de URL-os.
@RequestMapping("os")

class OSController {
    // De variabele PSS is een Array van Strings met besturingssystemen
    // final = waarde is een constante. (in drukletters geschreven)
    // private = eigen aan deze class
    // static = variabele wordt opgeslagen in de class en niet in een object.
    private static final String[] OSS={"Windows", "Macintosh", "Android", "Linux"};
    @GetMapping
    // Geeft aan dat de method hieronder een GET request verwerkt naar de URL ("/").
    // Get request vraag enkel data.
    // ("/") Staat voor de welkom pagina
    // @GetMapping
    // Spring vult de method parameter userAgent met de inhoud vna de request header User-agent
    // vermeld in @RequestHeader.

    // Locatie van request headers in de browser:
    //    //  1.  Druk F12.
    //    //  2.  Surf naar http://localhost:8080
    //    //  3.  Kies in het tabblad Network de GET request naar /.
    //    //      Scrol rechts naar beneden tot Request Headers.
    //    //      Een header heeft een naam en een waarde. Voorbeelden:
    //    //      De header Accept-Language header bevat de voorkeur taal en land van de gebruiker.
    //    //      De header User-Agent bevat het type browser en het besturingssysteem.
    public ModelAndView os(@RequestHeader("User-Agent") String userAgent){
        // Je werkt samen met os.html
        var modelAndView = new ModelAndView("os");
        Arrays.stream(OSS)
                // Als in de header User-Agent bvb. de tekst Windows voorkomt, draait de
                // browser op Windows
                .filter(os-> userAgent.contains(os))
                .findFirst()
                // Je geeft de naam van het besturingssysteem onder de naam os door aan de
                // Thymeleaf pagina.
                .ifPresent(gevondenOS -> modelAndView.addObject("os", gevondenOS));
        return modelAndView;
    }
}
