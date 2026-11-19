package com.plazoleta.trazabilidad.domain.api;

import com.plazoleta.trazabilidad.domain.model.EficienciaPedido;
import com.plazoleta.trazabilidad.domain.model.RankingEmpleado;
import com.plazoleta.trazabilidad.domain.model.Trazabilidad;
import java.util.List;

public interface ITrazabilidadServicePort {
    Trazabilidad registrarTrazabilidad(Trazabilidad trazabilidad);
    List<Trazabilidad> obtenerTrazabilidadPorPedido(Long idPedido, Long idClienteAutenticado);
    List<EficienciaPedido> obtenerEficienciaPorRestaurante(Long idRestaurante);
    List<RankingEmpleado> obtenerRankingEmpleadosPorRestaurante(Long idRestaurante);
}
