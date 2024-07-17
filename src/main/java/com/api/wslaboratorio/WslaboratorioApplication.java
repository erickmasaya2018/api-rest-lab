package com.api.wslaboratorio;

import com.api.wslaboratorio.services.IEncryptingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WslaboratorioApplication {
    public static void main(String[] args) {
        SpringApplication.run(WslaboratorioApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Autowired
    private IEncryptingService encryptingService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println(encryptingService.encriptarTexto("administrador"));
        };
    }
}

//administrador
//clave12345