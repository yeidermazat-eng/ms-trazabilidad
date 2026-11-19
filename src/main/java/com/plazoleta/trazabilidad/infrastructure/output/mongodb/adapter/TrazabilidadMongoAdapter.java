package com.plazoleta.trazabilidad.infrastructure.output.mongodb.adapter;

import com.plazoleta.trazabilidad.domain.model.Trazabilidad;
import com.plazoleta.trazabilidad.domain.spi.ITrazabilidadPersistencePort;
import com.plazoleta.trazabilidad.infrastructure.output.mongodb.document.TrazabilidadDocument;
import com.plazoleta.trazabilidad.infrastructure.output.mongodb.repository.ITrazabilidadRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrazabilidadMongoAdapter implements ITrazabilidadPersistencePort {

    private final ITrazabilidadRepository repository;

    public TrazabilidadMongoAdapter(ITrazabilidadRepository repository) {
        this.repository = repository;
    }

    @Override
    public Trazabilidad guardar(Trazabilidad t) {
        TrazabilidadDocument doc = new TrazabilidadDocument();
        doc.setId(t.getId());
        doc.setIdPedido(t.getIdPedido());
        doc.setIdCliente(t.getIdCliente());
        doc.setCorreoCliente(t.getCorreoCliente());
        doc.setIdRestaurante(t.getIdRestaurante());
        doc.setIdEmpleado(t.getIdEmpleado());
        doc.setCorreoEmpleado(t.getCorreoEmpleado());
        doc.setEstadoAnterior(t.getEstadoAnterior());
        doc.setEstadoNuevo(t.getEstadoNuevo());
        doc.setFecha(t.getFecha());
        TrazabilidadDocument saved = repository.save(doc);
        t.setId(saved.getId());
        return t;
    }

    @Override
    public List<Trazabilidad> buscarPorIdPedido(Long idPedido) {
        return repository.findByIdPedido(idPedido).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Trazabilidad> buscarPorIdRestaurante(Long idRestaurante) {
        return repository.findByIdRestaurante(idRestaurante).stream().map(this::toDomain).collect(Collectors.toList());
    }

    private Trazabilidad toDomain(TrazabilidadDocument doc) {
        Trazabilidad t = new Trazabilidad();
        t.setId(doc.getId());
        t.setIdPedido(doc.getIdPedido());
        t.setIdCliente(doc.getIdCliente());
        t.setCorreoCliente(doc.getCorreoCliente());
        t.setIdRestaurante(doc.getIdRestaurante());
        t.setIdEmpleado(doc.getIdEmpleado());
        t.setCorreoEmpleado(doc.getCorreoEmpleado());
        t.setEstadoAnterior(doc.getEstadoAnterior());
        t.setEstadoNuevo(doc.getEstadoNuevo());
        t.setFecha(doc.getFecha());
        return t;
    }
}
