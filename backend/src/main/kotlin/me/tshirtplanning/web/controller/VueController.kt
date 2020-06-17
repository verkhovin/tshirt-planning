package me.tshirtplanning.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class VueController {
  @RequestMapping(value = ["/**/{[path:[^\\.]*}"])
  fun redirect() = "forward:/"
}