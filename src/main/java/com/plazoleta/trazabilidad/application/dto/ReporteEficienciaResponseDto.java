package com.plazoleta.trazabilidad.application.dto;

import com.plazoleta.trazabilidad.domain.model.EficienciaPedido;
import com.plazoleta.trazabilidad.domain.model.RankingEmpleado;
import java.util.List;

public class ReporteEficienciaResponseDto {
    private List<EficienciaPedido> tiemposPorPedido;
    private List<RankingEmpleado> rankingPorEmpleado;

    public ReporteEficienciaResponseDto(List<EficienciaPedido> tiemposPorPedido, List<RankingEmpleado> rankingPorEmpleado) {
        this.tiemposPorPedido = tiemposPorPedido;
        this.rankingPorEmpleado = rankingPorEmpleado;
    }

    public List<EficienciaPedido> getTiemposPorPedido() { return tiemposPorPedido; }
    public List<RankingEmpleado> getRankingPorEmpleado() { return rankingPorEmpleado; }
}
