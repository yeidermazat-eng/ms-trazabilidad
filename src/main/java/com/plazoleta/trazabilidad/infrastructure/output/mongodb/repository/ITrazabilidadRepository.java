package com.plazoleta.trazabilidad.infrastructure.output.mongodb.repository;

import com.plazoleta.trazabilidad.infrastructure.output.mongodb.document.TrazabilidadDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ITrazabilidadRepository extends MongoRepository<TrazabilidadDocument, String> {
    List<TrazabilidadDocument> findByIdPedido(Long idPedido);
    List<TrazabilidadDocument> findByIdRestaurante(Long idRestaurante);
}
