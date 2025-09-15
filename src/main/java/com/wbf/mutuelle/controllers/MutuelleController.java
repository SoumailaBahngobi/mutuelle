package com.wbf.mutuelle.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutuelle")
public class MutuelleController {
    @RequestMapping("/member")
    public String member() {
        return "member";
    }

    @RequestMapping("/president")
    public String president() {
        return "president";
    }

    @RequestMapping("/secretary")
    public String secretary  () {
        return "secretary";
    }
    @RequestMapping("/treasurer")
    public String treasurer() {
        return "treasurer";
    }
}
