package com.ritonelion.bootopen.controller;


import com.ritonelion.bootopen.security.WebSecurityConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController
{
    @GetMapping("/logintest")
    public ModelAndView logintest(@SessionAttribute(WebSecurityConfig.SESSION_KEY) String account)
    {
        ModelAndView modelAndView = new ModelAndView("logintest");
        modelAndView.addObject("name",account);
        return modelAndView;
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @PostMapping("/loginpost")
    public @ResponseBody
    Map<String,Object> loginPost(@RequestParam("account") String account,@RequestParam("password") String password, HttpSession session)
    {
        Map<String,Object> map = new HashMap<>();

        if (!"password".equals(password))
        {
            map.put("success", false);
            map.put("message", "wrong password");
            return map;
        }

        session.setAttribute(WebSecurityConfig.SESSION_KEY,account);

        map.put("success", true);
        map.put("message", "success");
        return map;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login";
    }
}
