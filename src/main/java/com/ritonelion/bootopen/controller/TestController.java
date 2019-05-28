package com.ritonelion.bootopen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController
{
    @GetMapping("/test/tableget")
    public @ResponseBody
    Map<String,String> tableGet(HttpServletRequest request)
    {
        Map<String,String> map = new HashMap<>();
        String id = request.getParameter("id");
        String value = request.getParameter(id+"_value_input");
        map.put("id",id);
        map.put("value",value);
        return map;
    }

    @RequestMapping("/test/table")
    public ModelAndView table()
    {
        ModelAndView modelAndView = new ModelAndView("test/table");

        List<Pair> pairs = new ArrayList<>();

        pairs.add(new Pair("a","1"));
        pairs.add(new Pair("b","2"));
        pairs.add(new Pair("c","3"));
        pairs.add(new Pair("d","4"));
        pairs.add(new Pair("e","5"));

        modelAndView.addObject("pairs",pairs);

        return modelAndView;
    }

    public class Pair {
        private String id;
        private String value;

        public Pair(String id, String value)
        {
            this.id = id;
            this.value = value;
        }

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }
    }
}
