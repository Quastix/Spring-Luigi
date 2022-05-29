package be.vdab.luigi.restclients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FixerKoersClientTest {
    private FixerKoersClient client;

    // We voeren @beforeEach uit bij elke test die de variabele client aanspreekt.
    @BeforeEach
    void beforeEach(){
        // De variabele client is een variabele van de class FixerKoersClient.
        client = new FixerKoersClient();
    }
    @Test
    void deKoersIsPositief(){
        // FixerKoersClient werkt correct als de gelezen dollar koers een positief getal is.
        assertThat(client.getDollarKoers()).isPositive();
    }
}
