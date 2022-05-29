package be.vdab.luigi.restclients;

import be.vdab.luigi.exceptions.KoersClientException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

// @Component zorgt ervoor dat Spring een bean maakt van deze class.
@Component
public class FixerKoersClient {

    // final = waarde is een constante. (in drukletters geschreven)
    // private = eigen aan deze class
    // static = variabele wordt opgeslagen in de class en niet in een object.
    // variabele is van de class Pattern
    /*  Uitleg patroon
        ^           Begin van een regel
        .           Een karakter mag overeenkomen met '\n' of '\r'
        *           Er mogen 0 of meerdere karakters voor (het onderdeel dat we willen vinden in het patroon) komen
        \"          Het " character
        USD         De letter combinatie USD
        \"          Het " character
        :           Het: character
        *           Er mogen 0 of meerdere karakters voor (het onderdeel dat we willen vinden in het patroon) komen
        (           alles wat volgt, hoort bij elkaar
        \           niets, maar citeert het volgende karakters
        \d          een digitaal getal tussen 0 en 9
        +           Het teken voor de plus mag 1 of meerdere keren voorkomen
        \           niets, maar citeert het volgende karakters
        \.          Het . character
        \           niets, maar citeert het volgende karakters
        \d          een digitaal getal tussen 0 en 9
        *           Het getal mag 0 en meerdere keren voorkomen
        )           einde van het patroon
        .           Een karakter mag overeenkomen met '\n' of '\r'
        *           Er mogen 0 of meerdere karakters na (het onderdeel dat we willen vinden in het patroon) komen
        $           Het einde van de regel
        Voorbeeld pattern: "USD":2.02188414 */
    private static final Pattern PATTERN =
            // De regular expression stelt de tekst "USD": voor, gevolgd door een getal (met de koers)
            Pattern.compile("^.*\"USD\": *(\\d+\\.?\\d*).*$");

    // final = waarde is een constante. (in drukletters geschreven)
    // private = eigen aan deze class
    // variabele is van de class URL
    private final URL url; //uit package java.net
    public FixerKoersClient(){
        try{
            // We maken een URL-object van een String.
            // Er wordt altijd een MalformedURLException gethrowed.

            // Dit geeft een response in JSON-formaat. (JSON = JavaScript Object Notation)
            // De JSON:
            // {"success":true,"timestamp":1653396556,"base":"EUR","date":"2022-05-24","rates":{"USD":1.071111}}
            // het geeft de koers van de dollar t.o.v. de euro op de dag en tijd die meegegeven is.
            url = new URL(
                    "http://data.fixer.io/api/latest?symbols=USD&access_key=35a3ad0f2f253d37131b68cd1b5953fc");
        // MalformedURlException: Thrown om aan te geven dat er een onjuiste URL is opgemaakt.
        // Of er kon geen wettelijk protocol gevonden worden in de String. Of de String kon niet opgedeeld worden.
        } catch (MalformedURLException ex) {
            throw new KoersClientException("Fixer URL is verkeerd.");
        }
    }
    public BigDecimal getDollarKoers(){
        // openStream opent een verbinding tot de URL en returned een InputStream van het lezen van de verbinding.
        // De inputSteam wordt de response van de website in bytes, dit is de JSON:
        // {"success":true,"timestamp":1653396556,"base":"EUR","date":"2022-05-24","rates":{"USD":1.071111}}
        try (var stream = url.openStream()){
            // De json wordt terug ingelezen als string en we zoeken of het PATTERN overeenkomt in de string die
            // doorgegeven wordt. Als dit niet zo is wordt er een KoersClientException gethrowed.
            var matcher = PATTERN.matcher(new String(stream.readAllBytes()));
            if(!matcher.matches()){
                throw new KoersClientException("Fixer data ongeldig");
            }
            // Je maakt een BigDecimal op basis van het getal met de koers na "USD":
            // (Het deel in de regular expression tussen ronde haken).
            return new BigDecimal(matcher.group(1));
        } catch (IOException ex){
            throw new KoersClientException("Kan koers niet lezen via Fixer.", ex);
        }
    }
}
