package com.yousra.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(){
        String viewName = getViewName();
        return "index.html" ;
    }

    private String getViewName(){
        return "index";
    }
}
