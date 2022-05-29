package be.vdab.luigi.services;

import be.vdab.luigi.restclients.FixerKoersClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

// @Service maakt een bean van de class waar Spring bewerkingen mee kan uitvoeren.
@Service
// Deze method krijgt een bedrag in euro als parameter binnen en geeft het geconverteerde bedrag in
// dollar terug als return waarde.
public class EuroService {
    // Je maakt een FixerKoersClient reference variabele, maar je initialiseert ze nog niet.
    private final FixerKoersClient koersClient;
    // Je maakt een constructor. Die krijgt een FixerKoersClient object als parameter binnen.
    public EuroService(FixerKoersClient koersClient){
        // Je onthoudt dit object in de variabele koersClient
        this.koersClient = koersClient;
    }
    // Je maakt een object van je class waarop je een dependency hebt: FixerKoersClient.
    public BigDecimal naarDollar(BigDecimal euro){
        // Je roept daarop de method getDollarKoers op tijdens je conversie van euro naar dollar.
        return euro.multiply(koersClient.getDollarKoers())
                .setScale(2, RoundingMode.HALF_UP);
    }
}
