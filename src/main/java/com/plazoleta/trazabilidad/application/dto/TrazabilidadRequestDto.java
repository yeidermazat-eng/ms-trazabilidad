package com.plazoleta.trazabilidad.application.dto;

import java.time.LocalDateTime;

public class TrazabilidadRequestDto {
    private Long idPedido;
    private Long idCliente;
    private String correoCliente;
    private Long idRestaurante;
    private Long idEmpleado;
    private String correoEmpleado;
    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDateTime fecha;

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public String getCorreoCliente() { return correoCliente; }
    public void setCorreoCliente(String correoCliente) { this.correoCliente = correoCliente; }
    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }
    public Long getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Long idEmpleado) { this.idEmpleado = idEmpleado; }
    public String getCorreoEmpleado() { return correoEmpleado; }
    public void setCorreoEmpleado(String correoEmpleado) { this.correoEmpleado = correoEmpleado; }
    public String getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(String estadoAnterior) { this.estadoAnterior = estadoAnterior; }
    public String getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(String estadoNuevo) { this.estadoNuevo = estadoNuevo; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
