package com.springboot.app.models.service;

import com.springboot.app.models.entity.Client;

import java.util.List;

public interface IClientService {

    public List<Client> findAll();

    public void save(Client client);

    public Client findOne(Long id);

    public void delete(Long id);
}

