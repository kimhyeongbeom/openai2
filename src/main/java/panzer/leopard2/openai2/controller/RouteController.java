package panzer.leopard2.openai2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {
    @GetMapping("/askview")
    public String askview() {
        return "ask";
    }

    @GetMapping("/imageview")
    public String imageview() {
        return "image";
    }
}
