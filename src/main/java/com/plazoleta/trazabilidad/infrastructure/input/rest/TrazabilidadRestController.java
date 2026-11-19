package com.plazoleta.trazabilidad.infrastructure.input.rest;

import com.plazoleta.trazabilidad.application.dto.ReporteEficienciaResponseDto;
import com.plazoleta.trazabilidad.application.dto.TrazabilidadRequestDto;
import com.plazoleta.trazabilidad.domain.api.ITrazabilidadServicePort;
import com.plazoleta.trazabilidad.domain.model.Trazabilidad;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trazabilidad")
@Tag(name = "Trazabilidad y Eficiencia", description = "Historial de estados (HU 17) y métricas de eficiencia (HU 18)")
public class TrazabilidadRestController {

    private final ITrazabilidadServicePort trazabilidadServicePort;

    public TrazabilidadRestController(ITrazabilidadServicePort trazabilidadServicePort) {
        this.trazabilidadServicePort = trazabilidadServicePort;
    }

    @Operation(summary = "Registrar cambio de estado de pedido")
    @PostMapping
    public ResponseEntity<Trazabilidad> registrar(@RequestBody TrazabilidadRequestDto dto) {
        Trazabilidad t = new Trazabilidad();
        t.setIdPedido(dto.getIdPedido());
        t.setIdCliente(dto.getIdCliente());
        t.setCorreoCliente(dto.getCorreoCliente());
        t.setIdRestaurante(dto.getIdRestaurante());
        t.setIdEmpleado(dto.getIdEmpleado());
        t.setCorreoEmpleado(dto.getCorreoEmpleado());
        t.setEstadoAnterior(dto.getEstadoAnterior());
        t.setEstadoNuevo(dto.getEstadoNuevo());
        t.setFecha(dto.getFecha());
        return ResponseEntity.status(HttpStatus.CREATED).body(trazabilidadServicePort.registrarTrazabilidad(t));
    }

    @Operation(summary = "Consultar trazabilidad de un pedido (HU 17)", description = "Solo el cliente dueño del pedido puede consultarlo")
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<Trazabilidad>> obtenerPorPedido(@PathVariable Long idPedido, Authentication authentication) {
        Claims claims = (Claims) authentication.getCredentials();
        Long idCliente = claims.get("id", Number.class).longValue();
        return ResponseEntity.ok(trazabilidadServicePort.obtenerTrazabilidadPorPedido(idPedido, idCliente));
    }

    @Operation(summary = "Consultar eficiencia de pedidos y ranking de empleados (HU 18)", description = "Solo ejecutable por PROPIETARIO")
    @PreAuthorize("hasRole('PROPIETARIO')")
    @GetMapping("/eficiencia/restaurante/{idRestaurante}")
    public ResponseEntity<ReporteEficienciaResponseDto> obtenerEficiencia(@PathVariable Long idRestaurante) {
        return ResponseEntity.ok(new ReporteEficienciaResponseDto(
                trazabilidadServicePort.obtenerEficienciaPorRestaurante(idRestaurante),
                trazabilidadServicePort.obtenerRankingEmpleadosPorRestaurante(idRestaurante)
        ));
    }
}
