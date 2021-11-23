package ru.mirea.tourismapp.controller;

import com.sun.xml.bind.util.AttributesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.tourismapp.domain.*;
import ru.mirea.tourismapp.repo.MessageRepo;
import ru.mirea.tourismapp.repo.OrderRepo;
import ru.mirea.tourismapp.repo.UserRepo;

import java.util.Date;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/netpage")
    public String netpage(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("name", user.getName());
        }
        return "netpage";
    }

    @GetMapping("/posts")
    public String message(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {

            model.addAttribute("name", user.getName());
        }
        Iterable <Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);
        return "posts";
    }

    @PostMapping("/netpage")
    public String processMessage(@AuthenticationPrincipal User user, MessageForm form){
        Date date = new Date();
        messageRepo.save(form.toMessage(user, date));
        Message message = new Message();
        return "netpage";
    }

    @PostMapping("/subs")
    public String subscribing(@AuthenticationPrincipal User user, SubsesForm form){
        userRepo.save(form.toSubses(user));
        return "redirect:/netpage";
    }

    @GetMapping("/done")
    public String done() {
        return "done";
    }

    @GetMapping("/subs")
    public String subs() {
        return "subs";
    }

    @GetMapping("/login")
    public String login() {


        return "login";
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/user")
    public String forUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        Iterable<Order> orders = orderRepo.findAllByUserId(user.getId());
        model.addAttribute("orders", orders);
        return "user";
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/foradmin")
    public String forAdmin() {
        return "foradmin";
    }










    @GetMapping
    public String index(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user.getUsername());
            model.addAttribute("role", user.getRoles());
            model.addAttribute("name", user.getName());
            return "index";
        }

        model.addAttribute("user", "anonymous");
        return "index";
    }


    @GetMapping("/orders")
    public String orders(@AuthenticationPrincipal User user, Model model, @RequestParam String country) {
        Iterable<Order> orders = orderRepo.findAllByCountry(country);
        model.addAttribute("orders", orders);
        model.addAttribute("name", user.getName());
        return "orders";
    }

    @GetMapping("/reserve")
    public String reserve(@RequestParam Long id, @AuthenticationPrincipal User user, Model model) {
        Order order = orderRepo.findOrderById(id);
        order.setUserId(user.getId());
        orderRepo.save(order);
        user.setOrderId(order.getId());
        userRepo.save(user);
        return "redirect:/done";
    }

}