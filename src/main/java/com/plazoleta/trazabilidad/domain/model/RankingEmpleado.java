package com.plazoleta.trazabilidad.domain.model;

public class RankingEmpleado {
    private Long idEmpleado;
    private String correoEmpleado;
    private long pedidosCompletados;
    private double tiempoPromedioSegundos;

    public RankingEmpleado(Long idEmpleado, String correoEmpleado, long pedidosCompletados, double tiempoPromedioSegundos) {
        this.idEmpleado = idEmpleado;
        this.correoEmpleado = correoEmpleado;
        this.pedidosCompletados = pedidosCompletados;
        this.tiempoPromedioSegundos = tiempoPromedioSegundos;
    }

    public Long getIdEmpleado() { return idEmpleado; }
    public String getCorreoEmpleado() { return correoEmpleado; }
    public long getPedidosCompletados() { return pedidosCompletados; }
    public double getTiempoPromedioSegundos() { return tiempoPromedioSegundos; }
}
