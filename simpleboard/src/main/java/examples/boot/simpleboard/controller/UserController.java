package examples.boot.simpleboard.controller;

import examples.boot.simpleboard.domain.User;
import examples.boot.simpleboard.domain.UserRole;
import examples.boot.simpleboard.dto.UserJoinForm;
import examples.boot.simpleboard.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/info")
    public String info(Principal principal, ModelMap modelMap){
        User user = userService.getUserByEmail(principal.getName());

        modelMap.addAttribute("user", user);

        return "users/info";
    }

//    prefix: classpath:/templates/
//    suffix: .html

    @GetMapping(path = "/login")
    public String login(HttpSession session){
        return "users/login";
    }

    @GetMapping(path = "/joinform")
    public String joinform(UserJoinForm userJoinForm, ModelMap modelMap){
        User user = new User();
        modelMap.addAttribute("user", user);
        return "users/joinform"; // clasjoinformspath:/templates/users/joinform.html
    }

    @PostMapping(path = "join")
    public String join(@Valid UserJoinForm userJoinForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "users/joinform";
        }

        if(!userJoinForm.getPassword().equals(userJoinForm.getRePassword())){
            FieldError error =new FieldError("userJoinForm","rePassword", "암호가 일치하지 않습니다.");
            bindingResult.addError(error);
            return "users/joinform";
        }

        User userByEmail = userService.getUserByEmail(userJoinForm.getEmail());
        if(userByEmail != null){
            FieldError error =new FieldError("userJoinForm","email", "이미 존재하는 email입니다.");
            bindingResult.addError(error);
            return "users/joinform";
        }

        User user = new User();
        BeanUtils.copyProperties(userJoinForm, user);

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserRole userRole = new UserRole();
        userRole.setRoleName("USER");
        user.addUserRole(userRole); // 헬퍼 메소드를 이용해서 양방향을 맺어줘야 한다.

        User saveUser = userService.addUser(user);

        return "redirect:/users/welcome";
    }

    @GetMapping(path = "/welcome")
    public String welcome(){
        return "users/welcome";
    }

    @GetMapping(path = "/already-join")
    public String alreadyJoin(){
        return "users/alreadyJoin";
    }

    @GetMapping(path = "/already-social-join")
    public String alreadySocialJoin(){
        return "users/alreadySocialJoin";
    }

    @GetMapping(path = "already-social-join-and-login")
    public String alreadySocialJoinAndLogin(){
        return "users/alreadySocialJoinAndLogin";
    }
}
