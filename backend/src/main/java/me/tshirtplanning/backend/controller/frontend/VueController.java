package me.tshirtplanning.backend.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VueController {
  @RequestMapping(value = {"/{path:[^\\.]*}", "/**/{path:^(?!ws).*}/{path:[^\\.]*}"}, method = RequestMethod.GET)
  public String redirect() {
    return "forward:/";
  }
}