package br.edu.ifpb.producer.events;

import io.eventuate.tram.events.common.DomainEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaEspacoExpirada implements DomainEvent {
    private String codigoReserva;

    public ReservaEspacoExpirada(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public ReservaEspacoExpirada() {
    }
}
