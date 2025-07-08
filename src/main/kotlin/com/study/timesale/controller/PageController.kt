package com.study.timesale.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PageController {
    @GetMapping("/ui/product")
    fun productPage(): String {
        return "product"
    }

    @GetMapping("/ui/time-sale")
    fun timeSale(): String {
        return "timesale"
    }

    @GetMapping("/ui/async-time-sale")
    fun asyncTimeSale(): String {
        return "async-timesale"
    }
}
