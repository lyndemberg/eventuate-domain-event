package br.edu.ifpb.producer.eventsconsumer;

import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

    @Bean
    public EventConsumerHandler reservaEspacoEventHandler() {
        return new EventConsumerHandler("ReservaEspaco");
    }

    @Bean
    public DomainEventDispatcher domainEventDispatcher(EventConsumerHandler eventConsumerHandler, MessageConsumer messageConsumer) {
        return new DomainEventDispatcher("commandproducer",
                eventConsumerHandler.domainEventHandlers(), messageConsumer);
    }


}
