package com.springboot.app.controllers;

import com.springboot.app.models.dao.IClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientController {
    @Autowired
    private IClientDao clientDao;
    @RequestMapping(value="/list") //GET
    public String list(Model model){
        model.addAttribute("title", "List Client");
        model.addAttribute("clients", clientDao.findAll());
        return "list";
    }
}
