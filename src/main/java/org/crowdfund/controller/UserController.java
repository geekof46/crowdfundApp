package org.crowdfund.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class UserController {

    @Autowired
    private DynamoDbClient dynamoDbClient;
}
