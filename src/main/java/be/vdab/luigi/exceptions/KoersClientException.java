package be.vdab.luigi.exceptions;

public class KoersClientException extends RuntimeException {
    // De naam van de variabele die het versienummer voorstelt moet serialVersionUID zijn. Het type
    // moet long zijn. Je maakt de variabele final: je verhindert zo dat je de inhoud van de variabele
    // per ongeluk verder in je code wijzigt. Je maakt de variabele static: alle objecten van de class
    // delen het serienummer. Je maakt de variabele private: andere classes moeten dit serienummer
    // niet kennen.
    private static final long serialVersionUID =1L;
    // Je maakt straks met deze constructor een KoersClientException waarbij je enkel een String
    // met een omschrijving van de fout meegeeft.
    public KoersClientException(String message){
        super (message);
    }
    //Je maakt met deze constructor een KoersClientException waarbij je 2 dingen meegeeft:
    //      a. een String met een omschrijving van de fout
    //      b. de originele fout die de KoersClientException veroorzaakte.
    public KoersClientException(String message, Exception oorzaak){
        super(message, oorzaak);
    }
}
