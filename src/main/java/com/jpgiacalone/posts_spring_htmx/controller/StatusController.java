package com.jpgiacalone.posts_spring_htmx.controller;

import static com.jpgiacalone.posts_spring_htmx.data.StatusData.STATUSES;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import reactor.core.publisher.Mono;

import org.springframework.ui.Model;





@Controller
public class StatusController {


    @GetMapping("/")
    public Mono<String> handleMain() {
        return Mono.just("feed");
    }

    private static final int PAGE_LENTH = 10;
        @GetMapping("/feed")
        public String getFeed(Model model) {
            model.addAttribute("statuses", STATUSES.subList(0, PAGE_LENTH));
        return "feed";
    }
    @GetMapping("/status")
    public String getMoreStatus(@RequestParam("page") Integer page, Model model) {
        var to = page * PAGE_LENTH;
        var from = to - PAGE_LENTH;
        model.addAttribute("statuses", STATUSES.subList(from, to));
        model.addAttribute("link", "/status?page=" + (page + 1));
        return "status";
    }
    

}
