package com.example.yxy.controller;

import com.example.yxy.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/csv")
public class CsvController {

    @Autowired
    private DefaultService csvService;

    @RequestMapping("/queryByCsv")
    private String queryByCsv() throws Exception {
        csvService.queryByCsv();
        return "ok";
    }

    @RequestMapping("/queryByMysql")
    private String queryByMysql() throws Exception {
        csvService.queryByMysql();
        return "ok";
    }


    @RequestMapping("/queryByMc")
    private String queryByMc() throws Exception {
        csvService.queryByMc();
        return "ok";
    }

    @RequestMapping("/queryByMoreDataMc")
    private String queryByMoreDataMc() throws Exception {
        csvService.queryByMoreDataMc();
        return "ok";
    }


    @RequestMapping("/queryByWhite")
    private String queryByWhite() throws Exception {
        csvService.queryByWhite();
        return "ok";
    }
}
