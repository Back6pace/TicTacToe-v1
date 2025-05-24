package com.tictactoe.tictactoe.di;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.tictactoe.tictactoe.domain.service",
        "com.tictactoe.tictactoe.datasource.repository",
        "com.tictactoe.tictactoe.di.aspects" })
public class ProjectConfig {
}
