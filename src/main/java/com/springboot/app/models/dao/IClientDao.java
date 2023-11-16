package com.springboot.app.models.dao;

import com.springboot.app.models.entity.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IClientDao extends CrudRepository<Client, Long> {


}
