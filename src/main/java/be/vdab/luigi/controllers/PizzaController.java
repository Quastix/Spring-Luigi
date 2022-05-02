package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

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
}
