package br.edu.ifpb.producer.eventsconsumer;

import br.edu.ifpb.producer.domain.Conteudo;
import br.edu.ifpb.producer.events.ConteudoCriado;
import br.edu.ifpb.producer.events.ReservaEspacoEfetuada;
import br.edu.ifpb.producer.events.ReservaEspacoExpirada;
import br.edu.ifpb.producer.events.ReservaEspacoNegada;
import br.edu.ifpb.producer.service.ConteudoService;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class EventConsumerHandler {

    @Autowired
    public ConteudoService conteudoService;
    private String agregateType;

    public EventConsumerHandler(String agregateType) {
        this.agregateType = agregateType;
    }

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(agregateType)
                .onEvent(ReservaEspacoEfetuada.class, this::tratarReservaEfetuada)
                .onEvent(ReservaEspacoNegada.class, this::tratarReservaNegada)
                .onEvent(ReservaEspacoExpirada.class, this::tratarReservaExpirada)
                .build();
    }

    public void tratarReservaEfetuada(DomainEventEnvelope<ReservaEspacoEfetuada> event) {
        conteudoService.recuperarConteudo(event.getEvent().getConteudoId()).ifPresent( (conteudo) -> {
            conteudo.setCodigoReserva(event.getEvent().getCodigoReserva());
            conteudo.setStatus(Conteudo.ConteudoStatus.APROVADO);
            conteudoService.atualizarConteudo(conteudo);
        });
    }

    public void tratarReservaNegada(DomainEventEnvelope<ReservaEspacoNegada> event) {
        conteudoService.recuperarConteudo(event.getEvent().getConteudoId()).ifPresent((conteudo) -> {
            conteudo.setStatus(Conteudo.ConteudoStatus.NEGADO);
            conteudoService.atualizarConteudo(conteudo);
        });
    }

    public void tratarReservaExpirada(DomainEventEnvelope<ReservaEspacoExpirada> event){
        conteudoService.recuperarPorCodigoReserva(event.getEvent().getCodigoReserva()).
                ifPresent((conteudo)-> {
                    conteudo.setStatus(Conteudo.ConteudoStatus.NEGADO);
                    conteudoService.atualizarConteudo(conteudo);
                });
    }

}
