package com.common.camel.controller

import org.apache.logging.log4j.LogManager
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kotlin")
class DemoKotlinController {

    val log = LogManager.getLogger(DemoKotlinController::class)

    @GetMapping("DemoKotlin")
    fun demoKotlin() : ResponseEntity<String>{

        return ResponseEntity.ok().body("DemoKotlin")
    }
}
