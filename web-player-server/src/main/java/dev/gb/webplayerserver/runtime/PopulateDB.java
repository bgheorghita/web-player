package dev.gb.webplayerserver.runtime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class PopulateDB implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
    }
}
