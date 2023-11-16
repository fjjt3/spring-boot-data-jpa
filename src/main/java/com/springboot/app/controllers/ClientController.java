package com.springboot.app.controllers;

import com.springboot.app.models.dao.IClientDao;
import com.springboot.app.models.entity.Client;
import com.springboot.app.models.service.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Map;



@Controller
@SessionAttributes("client")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @RequestMapping(value="/list") //GET
    public String list(Model model){
        model.addAttribute("title", "List Client");
        model.addAttribute("clients", clientService.findAll());
        return "list";
    }

    @RequestMapping(value="/form") //GET
    public String create(Map<String, Object> model){

        Client client = new Client();
        model.put("client", client);
        model.put("title", "Client Form");
        return "form";
    }

    @RequestMapping(value="/form/{id}")
    public String update(@PathVariable(value="id") Long id, Map<String, Object> model){
        Client client = null;
        if(id>0){
            client = clientService.findOne(id);
        } else {
            return "redirect:/list";
        }
        model.put("client", client);
        model.put("title", "Edit Client");

        return "form";
    }

    @RequestMapping(value="/form", method=RequestMethod.POST)
    public String save(@Valid Client client, BindingResult result, Model model, SessionStatus status){
        if(result.hasErrors()) {
            model.addAttribute("Title", "List Client");
            return "form";
        }
        clientService.save(client);
        status.setComplete();
        return "redirect:list";
    }

    @RequestMapping(value="/delete")
    public String delete(@PathVariable(value="id") Long id){
        if(id>0){
            clientService.delete(id);
        }
        return "redirect:list";
    }

}
