package com.plazoleta.trazabilidad.domain.usecase;

import com.plazoleta.trazabilidad.domain.api.ITrazabilidadServicePort;
import com.plazoleta.trazabilidad.domain.exception.AccesoDenegadoException;
import com.plazoleta.trazabilidad.domain.model.EficienciaPedido;
import com.plazoleta.trazabilidad.domain.model.RankingEmpleado;
import com.plazoleta.trazabilidad.domain.model.Trazabilidad;
import com.plazoleta.trazabilidad.domain.spi.ITrazabilidadPersistencePort;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TrazabilidadUseCase implements ITrazabilidadServicePort {

    private final ITrazabilidadPersistencePort persistencePort;

    public TrazabilidadUseCase(ITrazabilidadPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Trazabilidad registrarTrazabilidad(Trazabilidad trazabilidad) {
        if (trazabilidad.getFecha() == null) {
            trazabilidad.setFecha(LocalDateTime.now());
        }
        return persistencePort.guardar(trazabilidad);
    }

    @Override
    public List<Trazabilidad> obtenerTrazabilidadPorPedido(Long idPedido, Long idClienteAutenticado) {
        List<Trazabilidad> logs = persistencePort.buscarPorIdPedido(idPedido);
        if (!logs.isEmpty() && !Objects.equals(logs.get(0).getIdCliente(), idClienteAutenticado)) {
            throw new AccesoDenegadoException("No tienes permiso para consultar la trazabilidad de este pedido.");
        }
        logs.sort(Comparator.comparing(Trazabilidad::getFecha));
        return logs;
    }

    @Override
    public List<EficienciaPedido> obtenerEficienciaPorRestaurante(Long idRestaurante) {
        List<Trazabilidad> logs = persistencePort.buscarPorIdRestaurante(idRestaurante);
        Map<Long, List<Trazabilidad>> porPedido = logs.stream()
                .collect(Collectors.groupingBy(Trazabilidad::getIdPedido));

        List<EficienciaPedido> resultado = new ArrayList<>();
        for (Map.Entry<Long, List<Trazabilidad>> entry : porPedido.entrySet()) {
            List<Trazabilidad> eventos = entry.getValue();
            eventos.sort(Comparator.comparing(Trazabilidad::getFecha));
            Trazabilidad primero = eventos.get(0);
            Trazabilidad ultimo = eventos.get(eventos.size() - 1);

            Long idEmpleado = eventos.stream()
                    .map(Trazabilidad::getIdEmpleado)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);

            String correoEmpleado = eventos.stream()
                    .map(Trazabilidad::getCorreoEmpleado)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse("Sin asignar");

            long duracionSeg = Duration.between(primero.getFecha(), ultimo.getFecha()).getSeconds();
            resultado.add(new EficienciaPedido(
                    entry.getKey(),
                    idEmpleado,
                    correoEmpleado,
                    ultimo.getEstadoNuevo(),
                    primero.getFecha(),
                    ultimo.getFecha(),
                    duracionSeg
            ));
        }
        resultado.sort(Comparator.comparing(EficienciaPedido::getIdPedido));
        return resultado;
    }

    @Override
    public List<RankingEmpleado> obtenerRankingEmpleadosPorRestaurante(Long idRestaurante) {
        List<EficienciaPedido> eficiencias = obtenerEficienciaPorRestaurante(idRestaurante);
        Map<Long, List<EficienciaPedido>> porEmpleado = eficiencias.stream()
                .filter(e -> e.getIdEmpleado() != null && "ENTREGADO".equals(e.getEstadoFinal()))
                .collect(Collectors.groupingBy(EficienciaPedido::getIdEmpleado));

        List<RankingEmpleado> ranking = new ArrayList<>();
        for (Map.Entry<Long, List<EficienciaPedido>> entry : porEmpleado.entrySet()) {
            List<EficienciaPedido> pedidosEmp = entry.getValue();
            double promedio = pedidosEmp.stream()
                    .mapToLong(EficienciaPedido::getDuracionSegundos)
                    .average()
                    .orElse(0.0);
            ranking.add(new RankingEmpleado(
                    entry.getKey(),
                    pedidosEmp.get(0).getCorreoEmpleado(),
                    pedidosEmp.size(),
                    promedio
            ));
        }
        ranking.sort(Comparator.comparingDouble(RankingEmpleado::getTiempoPromedioSegundos));
        return ranking;
    }
}
