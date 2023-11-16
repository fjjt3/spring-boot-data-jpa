package com.springboot.app.models.service;

import com.springboot.app.models.dao.IClientDao;
import com.springboot.app.models.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClientService{

    @Autowired
    private IClientDao  clientDao;
    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    @Transactional
    public void save(Client client) {
       clientDao.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Client findOne(Long id) {
        return clientDao.findOne(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clientDao.delete(id);
    }
}
