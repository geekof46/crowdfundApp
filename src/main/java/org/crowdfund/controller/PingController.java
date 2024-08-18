package org.crowdfund.controller;


import org.crowdfund.models.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


@RestController
@EnableWebMvc
public class PingController {

    @Autowired
    DynamoDbTable<User> userDynamoDbTable;


    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public Map<String, String> ping() {
        Map<String, String> pong = new HashMap<>();
        try{
            User customer = User.builder()
                     .userId("2")
                     .name("Customer Name")
                     .createDate(Instant.parse("2025-07-03T10:15:30.00Z"))
                    .build();
            userDynamoDbTable.putItem(customer);
        }catch(Exception e){
            pong.put("error", e.getMessage());
        }

        pong.put("pong", "Hello, World!");
        return pong;
    }
}
