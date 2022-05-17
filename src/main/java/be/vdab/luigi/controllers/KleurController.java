package be.vdab.luigi.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller @RequestMapping("kleuren")
class KleurController {
    private static final int EEN_JAAR_IN_SECONDEN = 31_536_000;
    @GetMapping
    // Spring vult de parameter kleur met de inhoud van de cookie met dezelfde naam (kleur).
    // Als de cookie ontbreekt, is de Optional leeg.
    public ModelAndView toonPagina(@CookieValue Optional<String>kleur){
        var modelAndView = new ModelAndView("kleuren");
        // Als de cookie er is, geef je de inhoud aan de Thymeleaf pagina als de variabele kleur
        kleur.ifPresent(hetKleur -> modelAndView.addObject("kleur", hetKleur));
        return modelAndView;
    }
    @GetMapping("{kleur}")
    // De Thymeleaf pagina zal 2 hyperlinks bevatten.
    // Bij een klik op de 1e hyperlink stuurt de browser een GET request naar /kleuren/roze
    // Bij een klik op de 2e hyperlink stuurt de browser een GET request naar /kleuren/blauw
    public String kiesKleur (@PathVariable String kleur,
                             // Je stuurt een cookie naar de browser met een HttpsServletResponse object.
                             HttpServletResponse response) {
        // Je maakt een cookie. De naam is kleur. De inhoud is de kleur die de gebruiker selecteerde.
        var cookie = new Cookie("kleur", kleur); //javax.servlet.http.Cookie
        // Je vult een maxAge in. Je maakt zo een permanente cookie. De browser onthoudt die
        // op de harde schijf. De browser verwijdert de cookie na maxAge seconden.
        // Als je magAge niet invult, maak je een tijdelijke cookie.
        // De browser onthoud die in het RAM-geheugen,tot je de browser sluit.
        cookie.setMaxAge(EEN_JAAR_IN_SECONDEN);
        // path bevat het begin van een URL in je website.
        // Enkele requests naar URL's die hiermee beginnen, kunnen de cookie lezen. Voorbeelden:
        //      a. path bevat /. Alle requests van je website kunnen de cookie lezen.
        //      b. path bevat /mandje. Enkel requests naar URL's die beginnen met /mandje
        //         kunnen de cookie lezen.
        cookie.setPath("/");
        // Je voegt de cookie toe aan de response
        response.addCookie(cookie);
        // Je werkt samen met kleuren.html. Je moet de kleur niet zelf doorgeven
        // aan de Thymeleaf pagina. Spring doet dit zelf met elke path variabele.
        return "kleuren";
    }
}
