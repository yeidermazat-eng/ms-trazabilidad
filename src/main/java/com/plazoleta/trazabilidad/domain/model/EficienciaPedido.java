package com.plazoleta.trazabilidad.domain.model;

import java.time.LocalDateTime;

public class EficienciaPedido {
    private Long idPedido;
    private Long idEmpleado;
    private String correoEmpleado;
    private String estadoFinal;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private long duracionSegundos;

    public EficienciaPedido(Long idPedido, Long idEmpleado, String correoEmpleado, String estadoFinal,
                            LocalDateTime fechaInicio, LocalDateTime fechaFin, long duracionSegundos) {
        this.idPedido = idPedido;
        this.idEmpleado = idEmpleado;
        this.correoEmpleado = correoEmpleado;
        this.estadoFinal = estadoFinal;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.duracionSegundos = duracionSegundos;
    }

    public Long getIdPedido() { return idPedido; }
    public Long getIdEmpleado() { return idEmpleado; }
    public String getCorreoEmpleado() { return correoEmpleado; }
    public String getEstadoFinal() { return estadoFinal; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public long getDuracionSegundos() { return duracionSegundos; }
}
