package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.exceptions.KoersClientException;
import be.vdab.luigi.services.EuroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

// @Controller zorgt ervoor dat Spring een bean van de class maakt.
// Een bean is een Java object waar Spring handelingen op kan uitvoeren.
@Controller
// Met @RequestMapping voer je de domeinnaam in waar @GetMapping op verder gaat.
@RequestMapping("pizzas")
class PizzaController {
// variabelen
    // aanmaken van een array van pizzas
    private final Pizza[] allePizzas = {
            new Pizza(1, "Prosciutto", BigDecimal.valueOf(4), true),
            new Pizza(2 ,"Margherita", BigDecimal.valueOf(5), false),
            new Pizza(3, "Calzone", BigDecimal.valueOf(4), false)};

    //
    private final EuroService euroService;

    // Bij de start van de website roept maakt Spring een bean van de class PizzaController.
    // Spring roept daarbij de constructor op. Spring geeft de EuroService bean mee als
    // constructor parameter. IntelliJ toont dit met een symbool in de marge. Als je daarop klikt, opent IntelliJ
    // de class van deze bean: EuroService.
    PizzaController(EuroService euroService) {
        this.euroService = euroService;
    }

    // De method op de volgende regel (findAll) verwerkt GET request naar de URL /pizzas
    @GetMapping
    public ModelAndView findAll(){
        // viewName: de naam van de Thymeleaf pagina (pizzas.html)
        // modelName: de naam waaronder je data doorgeeft aan de Thymeleaf pagina.
        // modelObject: de data die je doorgeeft aan de Thymeleaf pagina: een array van pizza's
        return new ModelAndView("pizzas", "allePizzas", allePizzas);
    }
    // Deze method zoekt de pizza waarvan je de id meegeeft als parameter. De method geeft
    // een Optional terug. Die bevat de gevonden pizza of is leeg als de pizza niet bestaat.
    // Bij een Optional kan de returnwaarde niets bevatten. Bij andere return waarden wordt er
    // dan een NullPointerExceptions gethrowed
    private Optional<Pizza> findByIdHelper(long id){
        return Arrays.stream(allePizzas).filter(pizza->pizza.getId()==id).findFirst();
    }
    //Onderstaande method verwerkt GET request naar URL's die passen bij de URL-template pizzas/{id}
    // get request vraagt data op
    @GetMapping("{id}")
    // @PathVariable = Spring vult de parameter id met de waarde van de path variabele met dezelfde
    // naam(id). Als de URl pizzas/1 is, vult Spring id met 1.
    public ModelAndView findById(@PathVariable long id) {
        // samenwerken met de pagina pizza.html
        var modelAndView = new ModelAndView("pizza");
        // Je roept de findByIdHelper method op en je gebruikt id als parameter. Als je de pizza vindt,
        // geef je die door aan de Thymeleaf pagina onder de naam pizza.
        findByIdHelper(id).ifPresent(gevondenPizza -> {
                modelAndView.addObject("pizza", gevondenPizza);
                /*modelAndView.addObject(gevondenPizza)); is ook mogelijk, maar vind het momenteel
                nog te verwarrend ik hou geen rekening ermee dat ik de attribuut-naam niet vind.
                Spring kijkt naar het type van gevondenPizza. Dit is de class Pizza.
                Spring wijzigt de 1e letter daarvan naar kleine letter: pizza.
                Spring geeft de data onder die naam (pizza) aan de Thymeleaf pagina.
                 */
        try {
            // Je roept de method naarDollar van de class EuroService op.
            //Je geeft de pizza prijs in euro mee als parameter.
            //Je krijgt de prijs in dollar terug.
            //Je geeft die door aan de Thymeleaf pagina onder de naam inDollar.
            modelAndView.addObject(
                    "inDollar", euroService.naarDollar(gevondenPizza.getPrijs()));
        } catch (KoersClientException ex) {
            // Hier komt later code die de exception verwerkt
        }
    });
        return modelAndView;
    }
    // de method findPrijzenHelper heeft als return waarde een datastroom van BigDecimals
    // de method stream geeft een datastroom van de array allePizzas
    // de method map transformeert de array naar de prijzen van de pizzas
    // de method distinct geeft enkel de unieke waarden weer. Dubbele waarden worden, verwijdert
    // uit de stream.
    // De method sorted rangschikt de getallen BigDecimals van groot naar klein via de compareTo method.
    private Stream<BigDecimal> findPrijzenHelper() {
        return Arrays.stream(allePizzas).map(Pizza::getPrijs).distinct().sorted();
    }

    //Onderstaande method verwerkt GET request naar URL's die passen bij de URL-template pizzas/prijzen
    @GetMapping("prijzen")
    public ModelAndView prijzen(){
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
        // OPMERKING!   Na een stream moet een .iterator geplaatst worden.
        //              Als dat niet gebeurt dan zal de website een foute pagina weergeven.
        return new ModelAndView("pizzasperprijs", "prijzen",
                findPrijzenHelper().iterator());
    }
    // De method findPrijsHelper heeft als return waarde een datastroom van BigDecimals
    // de method stream geeft een datastroom van de array allePizzas
    // de method filter alle compareTo waarden die gelijk zijn aan 0.
    // de method compareTo De method geeft een integer terug: 0 bij gelijkheid, een negatief getal als
    // de prijs van het eerste object ‘kleiner’ is dan de prijs van de tweede en een positief getal als
    // de prijs van het eerste object ‘groter’ is dan de prijs van het tweede object.
        private Stream<Pizza> findByPrijsHelper(BigDecimal prijs){
        return Arrays.stream(allePizzas)
                .filter(pizza ->pizza.getPrijs().compareTo(prijs)==0);
    }
    // Geeft aan dat de method hieronder een GET request
    // verwerkt naar de pagina pizzas bij @RequestMapping en de prijs van de pizza bij @GetMapping.
    // Het URL-adres is /pizzas/de prijs van de pizza
    // Get request vraag enkel data.
    @GetMapping("prijzen/{prijs}")
    // De method findByNummer geeft een ModelAndView als return waarde.
    // @Pathvariable zorgt ervoor dat Spring de parameter prijs met de waarde van de path variable
    // met dezelfde waarde van (prijs) invult.
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
    // met de methode addObject kunnen we een object toevoegen aan de ModelAndView
    public ModelAndView findByPrijs(@PathVariable BigDecimal prijs){
        var modelAndView = new ModelAndView("pizzasperprijs",
                "pizzas", findByPrijsHelper(prijs).iterator())
                .addObject("prijzen", findPrijzenHelper().iterator());
        return modelAndView;
    }
}
