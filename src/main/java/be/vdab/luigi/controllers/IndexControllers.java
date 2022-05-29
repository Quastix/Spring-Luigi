package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Adres;
import be.vdab.luigi.domain.Persoon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

// @Controller zorgt ervoor dat Spring een bean van de class maakt.
// een bean is een Java object waar Spring handelingen op kan uitvoeren.
@Controller
class IndexControllers {
    // Uitleg MultiThreading:
    // Als er bij het verwerken vna een request, een private variabele in een controller wijzigt,
    // moet die thread safe zijn. Dwz: de variabele wijzigen door gelijktijdige threads verloopt correct.
    // Primitive variabelen (int, long, double, ...) zijn niet thread safe.
    // Volgende thread safe classes zijn dan alternatieven:
    //      AtomicBoolean       een thread safe boolean
    //      AtomicInteger       een thread safe integer
    //      AtomicLong          een thread safe long
    // Veel classes uit de standaard Java library zijn niet thread safe:
    //  JBDC-objecten (Connection, Statement, ResultSet,...)
    //  Collection objecten (ArrayList, HashSet, HashMap, ...)
    //      CopyOnWriteArrayList    een thread safe List
    //      CopyWriteArraySet       een thread safe Set
    //      ConcurrentHashMap       een thread safe Map
    // Lokale variabelen (die je in een mehtod delcareert), moeten niet thread safe zijn.

    //De constructor van AtomicInteger initialiseert het getal in hde AtomicInteger op 0.
    // De AtomicInteger variabele aantalBezoken gaat verloren als de webiste stopt.
    // Als je op lange termijn het aantal bezoekers van een pagina wil onthouden,
    // doe je dit beter in een database.
    private final AtomicInteger aantalBezoeken = new AtomicInteger();
    // Geeft aan dat de method hieronder een GET request verwerkt naar de URL ("/").
    // Get request vraagt enkel data.
    // ("/") Staat voor de welkom pagina
    @GetMapping("/")
    // De naam van de method mag vrij gekozen worden.
    // Een methode die data doorgeeft aan de Thymeleaf pagina heeft als return type ModelAndView
    // Model staat voor de data, View staat voor de Thymeleaf pagina.
    public ModelAndView index() {
        var morgenOfMiddag = LocalTime.now().getHour() < 12 ? "morgen" : "middag";
        // De variabele van een object ModelAndView.
        // Model = data die we gebruiken (java bestanden). Het Model is een Map,
        // hierdoor kunnen er meerderen objecten gebruikt worden die een sleutel hebben bij naam
        // view = weergaven van die data (in een HTML-bestand)
        // Dit object van de class ModelAndView heeft 3 parameters. Er bestaan ook andere constructors.
        // Er worden 3 parameters ingegeven in het return-object
        // Parameter 1: De naam van de Thymeleaf pagina terug. Je typt de extensie (.html) niet
        // Parameter 2: De naam waaronder je een stukje data doorgeeft, dit wordt ook gebruikt
        //              in de Thymeleaf pagina index.
        // Parameter 3: De inhoud van de variabele zelf.
        var modelAndView = new ModelAndView("index", "moment", morgenOfMiddag);
        // met addObject geef je extra data door, onder andere de naam zaakvoerder
        // De data is een Persoon object
        modelAndView.addObject("zaakvoerder",
                new Persoon("Luigi", "Peperone", 7, true,
                        LocalDate.of(1996, 1 ,31),
                        new Adres("Grote markt", "3", 9700, "Oudenaarde")));
        // incrementAndGet verhoogt de teller in de AtomicInteger op een thread-safe manier.
        // De method geeft daarna een verhoogde teller terug.
        modelAndView.addObject("aantalBezoeken", aantalBezoeken.incrementAndGet());
    return modelAndView;
    }
}
