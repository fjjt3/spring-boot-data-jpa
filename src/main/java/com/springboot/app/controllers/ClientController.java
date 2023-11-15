package com.springboot.app.controllers;

import com.springboot.app.models.dao.IClientDao;
import com.springboot.app.models.entity.Client;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;



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

    @RequestMapping(value="/form") //GET
    public String create(Map<String, Object> model){

        Client client = new Client();
        model.put("client", client);
        model.put("title", "Client Form");
        return "form";
    }

    @RequestMapping(value="/form", method=RequestMethod.POST)
    public String save(@Valid Client client, BindingResult result, Model model){
        if(result.hasErrors()) {
            model.addAttribute("Title", "List Client");
            return "form";
        }
        clientDao.save(client);
        return "redirect:list";
    }


}
