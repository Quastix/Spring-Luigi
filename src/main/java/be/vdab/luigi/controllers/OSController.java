package be.vdab.luigi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
// De controller verwerkt request naar de URL-os.
@RequestMapping("os")
class OSController {
    private static final String[] OSS={"Windows", "Macintosh", "Android", "Linux"};
    @GetMapping
    // Spring vult de method parameter userAgent met de inhdoud vna de request header User-agent
    // vermeld in @RequestHeader.
    public ModelAndView os(@RequestHeader("User-Agent") String userAgent){
        // Je werkt samen met os.html
        var modelAndView = new ModelAndView("os");
        Arrays.stream(OSS)
                // Als in de header User-Agent bvb. de tekst Windows voorkomt, draait de
                // browser op Windows
                .filter(os-> userAgent.contains(os))
                .findFirst()
                // Je geeft de naam van het besturingssysteem onder de naam os door aan de
                // Thymeleaf pagina.
                .ifPresent(gevondenOS -> modelAndView.addObject("os", gevondenOS));
        return modelAndView;
    }
}
