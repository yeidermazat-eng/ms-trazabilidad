package com.plazoleta.trazabilidad.domain.spi;

import com.plazoleta.trazabilidad.domain.model.Trazabilidad;
import java.util.List;

public interface ITrazabilidadPersistencePort {
    Trazabilidad guardar(Trazabilidad trazabilidad);
    List<Trazabilidad> buscarPorIdPedido(Long idPedido);
    List<Trazabilidad> buscarPorIdRestaurante(Long idRestaurante);
}
