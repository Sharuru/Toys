package me.sharuru.mattermost.sum.web;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.mattermost.sum.model.CreateUserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@Slf4j
public class WebController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("createUserForm", new CreateUserForm());
        return "page";
    }

    @PostMapping("/biz/createNewUser")
    public String createNewUser(@ModelAttribute @Valid CreateUserForm createUserForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttribute) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("operationResults", false);
            return "page";
        }
        redirectAttribute.addFlashAttribute("operationResults", true);
        return "redirect:/";
    }


}
