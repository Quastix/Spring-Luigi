package be.vdab.luigi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
//Nadeel van deze aanpak is dat we JAVA en HTML code door elkaar gebruiken
// @RestController zorgt ervoor dat Spring van die class een object maakt (Spring bean)
@RestController
class IndexControllers {
    // Geeft aan dat de method hieronder een GET request verwerkt naar de URL /.
    // / staat voor de welkom pagina
    @GetMapping("/")
    // De naam van de method mag vrij gekozen worden. De method geeft een String terug.
    // Spring stuurt die response naar de browser
    public String index() {
        var morgenOfMiddag = LocalTime.now().getHour() < 12 ? "morgen" : "middag";
        // De String bevat HTML met daarin de tekst morgen of middag.
        return "<!doctype html><title>Hallo</title><body>Goede " + morgenOfMiddag + "</body></html>";
    }
}
