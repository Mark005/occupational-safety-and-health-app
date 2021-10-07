package com.nncompany.rest.config;

import com.nncompany.api.interfaces.services.task.TaskForRoleGetterService;
import com.nncompany.api.model.enums.Role;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class CommonConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Map<Role, TaskForRoleGetterService> getRoleTaskMap(List<TaskForRoleGetterService> services) {
        return services.stream()
                .collect(Collectors.toMap(
                        TaskForRoleGetterService::getRole,
                        service -> service));
    }
}
