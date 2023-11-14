package com.springboot.app.controllers;

import com.springboot.app.models.dao.IClientDao;
import com.springboot.app.models.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String save(Client client){
        clientDao.save(client);
        return "redirect:list";
    }


}
