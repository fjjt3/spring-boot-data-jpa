package com.springboot.app.controllers;

import com.springboot.app.models.dao.IClientDao;
import com.springboot.app.models.entity.Client;
import com.springboot.app.models.service.IClientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;


@Controller
@SessionAttributes("client")
public class ClientController {
    @Autowired
    private IClientService clientService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    

    @GetMapping(value = "/see/{id}")
    public String see(@PathVariable(value="id") Long id, Map<String, Object> model){
        Client client = clientService.findOne(id);
        if(client==null){
            return "redirect:/list";
        }

        model.put("client", client);
        model.put("title", "Client Detail: " + client.getName());

        return "see";
    }

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
    public String save(@Valid Client client, BindingResult result, Model model, @RequestParam("file") MultipartFile photo, SessionStatus status){
        if(result.hasErrors()) {
            model.addAttribute("Title", "List Client");
            return "form";
        }
        if (!photo.isEmpty()){

            String uniqueFilename = UUID.randomUUID().toString() + " " + photo.getOriginalFilename();
            Path rootPath = Paths.get("uploads").resolve(uniqueFilename);

            Path rootAbsoluthPath = rootPath.toAbsolutePath();
            log.info("rootPath: " + rootPath);
            log.info("rootAbsoluthPath: " + rootAbsoluthPath);

            try{
                Files.copy(photo.getInputStream(), rootAbsoluthPath);

                client.setPhoto(uniqueFilename);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        clientService.save(client);
        status.setComplete();
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{id}")
    public String delete(@PathVariable(value="id") Long id){
        if(id>0){
            clientService.delete(id);
        }
        return "redirect:/list";
    }

}
