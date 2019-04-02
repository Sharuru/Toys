package me.sharuru.mattermost.sum.web;

import me.sharuru.mattermost.sum.model.BusinessResponse;
import me.sharuru.mattermost.sum.service.BusinessService;
import me.sharuru.mattermost.sum.model.CreateUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class WebController {

    @Autowired
    BusinessService businessService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("createUserForm", new CreateUserForm());
        return "page";
    }

    @PostMapping("/biz/createNewUser")
    public String createNewUser(@ModelAttribute @Valid CreateUserForm createUserForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttribute) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("operationResults", 10);
            return "page";
        }
        BusinessResponse response = businessService.createUser(createUserForm);
        redirectAttribute.addFlashAttribute("operationResults", response.getCode());
        redirectAttribute.addFlashAttribute("createdResponse", response);
        return "redirect:/";
    }

}
