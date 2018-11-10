package examples.boot.simpleboard.service.impl;

import examples.boot.simpleboard.service.MyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class MyServiceImpl implements MyService {
    @Override
    public String getName() {
        return "urstory";
    }
}
