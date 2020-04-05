package com.springboot.blockchain.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HomeController {

    @GetMapping("")
    fun openSwagger(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.sendRedirect(req.contextPath + "/swagger-ui.html");
    }

}
