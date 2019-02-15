package tnc.at.brpl.apis;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SuppressWarnings("unused")
public class Home {

    @GetMapping("/")
    public String home() {

        return "home";
    }

}
