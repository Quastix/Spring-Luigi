package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class PizzaController {
// aanmaken van een array van pizzas
    private final Pizza[] allePizzas = {
            new Pizza(1, "Prosciutto", BigDecimal.valueOf(4), true),
            new Pizza(2 ,"Margherita", BigDecimal.valueOf(5), false),
            new Pizza(3, "Calzone", BigDecimal.valueOf(4), false)};
    // De method op de volgende regel (findAll) verwerkt GET request naar de URL /pizzas
    @GetMapping("/pizzas")
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
    //Onderstaande method verwerkt GET request naar URL's die passen bij de URL template pizzas/{id}
    @GetMapping("/pizzas/{id}")
    // @PathVariable = Spring vult de parameter id met de waarde van de path variabele met dezelfde
    // naam(id). Als de URl pizzas/1 is, vult Spring id met 1.
    public ModelAndView findById(@PathVariable long id){
        // samenwerken met de pagina pizza.html
        var modelAndView = new ModelAndView("pizza");
        // Je roept de findByIdHelper method op en je gebruikt id als parameter. Als je de pizza vindt,
        // geef je die door aan de Thymelead pagina onder de naam pizza.
        findByIdHelper(id).ifPresent(gevondenPizza ->
                modelAndView.addObject("pizza", gevondenPizza));
        return modelAndView;
    }
}
