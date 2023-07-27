package ma.emsi.db_livre.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {
    @GetMapping(path = "/user/map")
    public String map(){
        return "map";
    }

    @GetMapping(path = "/user/map2")
    public String map2(){
        return "map2";
    }

}
