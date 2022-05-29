package be.vdab.luigi.services;

import be.vdab.luigi.restclients.FixerKoersClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

// @ExtendWith(MockitoExtension.class): Je vraagt JUnit de test uit te voeren met Mockito.
// Mockito is een mocking library. We maken een mock aan met deze library.
// Een Mock is een stub waarmee je iets extra kan testen: verificaties.
// Je controleert (verifieert) of de te testen class zijn dependency heeft gebruikt.
// Een stub is een crash-dummy, de class doet zich voor als de class "EuroService".

@ExtendWith(MockitoExtension.class)
public class EuroServiceTest {
    // Je maakt een reference variabele voor de class die je wil testen: EuroService.
    private EuroService euroService;
    // Voor de variabele koersClient staat @Mock. Mockito maakt dan een class. Die erft van de class
    // bij die variabele: FixerKoersClient. Mockito override in die class de method getDollarKoers:
    // de method geeft null terug. Je lost dit straks op. Mockito vult de variabele koersClient met
    // dit mock object.
    @Mock
    private FixerKoersClient koersClient;

    // De variabelen worden geïnitialiseerd in de @beforeEach method.
    // Bij het oproepen van de variabele euroService in @Test roept Junit eerste @beforeEach op
    // om de variabele te initialiseren.
    @BeforeEach
    void beforeEach(){
        /* 28.4
        // Je voegt deze opdracht even toe om te zien dat de variabele koersClient niet verwijst naar
        // een object van de class FixerKoersClient, maar naar een mock object van een class gemaakt door
        // Mockito.
        System.out.println(koersClient.getClass());
        // Je voegt deze object even toe om te zien dat de method getDollarKoers, in de class gemaakt door
        // Mockito, null teruggeeft.
        System.out.println(koersClient.getDollarKoers());
        */

        // Je geeft het mock object mee aan de constructor van EuroService.
        euroService = new EuroService(koersClient);
    }

    // Geeft aan dat het over een test method gaat.
    @Test
    // We testen de method naarDollar van de class EuroService.
    void naarDollar(){
        // Je traint het mock object: de method getDollarKoers geeft geen null, maar 1.1565 terug.
        // Je traint een mock met de static Mockito method when.
        // Je geeft als parameter de mock variabele koersClient mee,
        // gevolgd door de method van die mock getDollarKoers.
        // When geeft een object terug. Je roept hierna de method thenReturn op.
        // Je geeft de waarde mee die de method getDollarKoers van de mock moet teruggeven.
        // De method zal hier een BigDecimal met de waarde van 1.1565 teruggeven.
        //
        when(koersClient.getDollarKoers()).thenReturn(BigDecimal.valueOf(1.1565));
        // assertThat is een static method. Je voert daarin de method die je wilt uittesten.
        // We testen de method naarDollar met de waarde 3 op de euroService variabele.
        // assertThat geeft een AbstractBooleanAssert object
        assertThat(euroService.naarDollar(BigDecimal.valueOf(3)))
                // Je test de EuroService method naarDollar. Die moet een bedrag van 3 euro converteren
                // naar een bedrag van 3.47 dollar, bij een koers van 1.1565 (die de mock teruggeeft).
                .isEqualByComparingTo("3.47");
        // De code bij hierboven moet de method getDollarKoers van de mock hebben opgeroepen.
        // Je verifieert met de static Mockito method verify.
        // Je geeft als parameter de mock mee. Je Krijgt een object terug. Je geeft aan dat op de mock
        // de method getDollarKoers moet opgeroepen worden zijn. Als dit niet het geval is, mislukt de test.
        verify(koersClient).getDollarKoers();
    }
}
