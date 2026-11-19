package com.plazoleta.trazabilidad.domain.usecase;

import com.plazoleta.trazabilidad.domain.exception.AccesoDenegadoException;
import com.plazoleta.trazabilidad.domain.model.EficienciaPedido;
import com.plazoleta.trazabilidad.domain.model.RankingEmpleado;
import com.plazoleta.trazabilidad.domain.model.Trazabilidad;
import com.plazoleta.trazabilidad.domain.spi.ITrazabilidadPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrazabilidadUseCaseTest {

    @Mock
    private ITrazabilidadPersistencePort persistencePort;

    @InjectMocks
    private TrazabilidadUseCase useCase;

    @Test
    void obtenerTrazabilidadPorPedido_ClientePropietario_Exitoso() {
        Trazabilidad t1 = new Trazabilidad();
        t1.setIdPedido(1L);
        t1.setIdCliente(50L);
        t1.setEstadoNuevo("PENDIENTE");
        t1.setFecha(LocalDateTime.now().minusMinutes(10));

        when(persistencePort.buscarPorIdPedido(1L)).thenReturn(new ArrayList<>(List.of(t1)));

        List<Trazabilidad> res = useCase.obtenerTrazabilidadPorPedido(1L, 50L);
        assertEquals(1, res.size());
    }

    @Test
    void obtenerTrazabilidadPorPedido_OtroCliente_LanzaExcepcion() {
        Trazabilidad t1 = new Trazabilidad();
        t1.setIdPedido(1L);
        t1.setIdCliente(50L);

        when(persistencePort.buscarPorIdPedido(1L)).thenReturn(List.of(t1));

        assertThrows(AccesoDenegadoException.class, () -> useCase.obtenerTrazabilidadPorPedido(1L, 99L));
    }

    @Test
    void obtenerRankingEmpleados_CalculaPromedioCorrectamente() {
        LocalDateTime base = LocalDateTime.of(2026, 11, 18, 12, 0);
        Trazabilidad e1 = new Trazabilidad();
        e1.setIdPedido(1L);
        e1.setIdRestaurante(5L);
        e1.setEstadoNuevo("PENDIENTE");
        e1.setFecha(base);

        Trazabilidad e2 = new Trazabilidad();
        e2.setIdPedido(1L);
        e2.setIdRestaurante(5L);
        e2.setIdEmpleado(7L);
        e2.setCorreoEmpleado("chef@rest.com");
        e2.setEstadoNuevo("ENTREGADO");
        e2.setFecha(base.plusMinutes(15));

        when(persistencePort.buscarPorIdRestaurante(5L)).thenReturn(List.of(e1, e2));

        List<EficienciaPedido> eficiencias = useCase.obtenerEficienciaPorRestaurante(5L);
        List<RankingEmpleado> ranking = useCase.obtenerRankingEmpleadosPorRestaurante(5L);

        assertEquals(900L, eficiencias.get(0).getDuracionSegundos());
        assertEquals(1, ranking.size());
        assertEquals(900.0, ranking.get(0).getTiempoPromedioSegundos());
    }
}
