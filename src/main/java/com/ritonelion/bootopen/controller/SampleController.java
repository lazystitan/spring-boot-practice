package com.ritonelion.bootopen.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class SampleController
{

    @GetMapping("/index")
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView("index");

        return modelAndView;
    }


    public class User {
        private Long id;
        private String username;
        private String email;
        private List<Fans> fans;
        private Boolean isAdmin;
        private Date birthady;


        public User(long id, String username, String email, List<Fans> fans, boolean isAdmin, Date birthady)
        {
            this.id = id;
            this.birthady = birthady;
            this.email = email;
            this.fans = fans;
            this.isAdmin = isAdmin;
            this.username = username;
        }
    }


    public class Fans {
        private Long id;
        private String name;

        public Fans(long id, String name)
        {
            this.id = id;
            this.name = name;
        }
    }
}
