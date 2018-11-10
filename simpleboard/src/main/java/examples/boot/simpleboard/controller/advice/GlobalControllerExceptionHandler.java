package examples.boot.simpleboard.controller.advice;


import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = Controller.class)
@Order(2)
@Controller
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = CookieTheftException.class)
    public String handleCookieTheftException(CookieTheftException e){

        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/users/login";
    }

    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e) {
        return "exceptions/exception";
    }

}
