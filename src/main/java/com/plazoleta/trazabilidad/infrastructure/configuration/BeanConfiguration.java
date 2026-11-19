package com.plazoleta.trazabilidad.infrastructure.configuration;

import com.plazoleta.trazabilidad.domain.api.ITrazabilidadServicePort;
import com.plazoleta.trazabilidad.domain.spi.ITrazabilidadPersistencePort;
import com.plazoleta.trazabilidad.domain.usecase.TrazabilidadUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public ITrazabilidadServicePort trazabilidadServicePort(ITrazabilidadPersistencePort persistencePort) {
        return new TrazabilidadUseCase(persistencePort);
    }
}
