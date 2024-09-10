package opn.dev.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import opn.dev.authservice.entity.User;
import opn.dev.authservice.service.imp.UserServiceImp;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserServiceImp userService;

    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public User getMethodName(@PathVariable String username) {
        return userService.findByUsername(username);
    }

}

