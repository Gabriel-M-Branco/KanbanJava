package com.kanbanjava;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "KanbanJava", version = "1", description = "Api desenvolvida para a criação do kanban"))
public class KanbanJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanbanJavaApplication.class, args);
    }

}