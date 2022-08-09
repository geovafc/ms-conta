package br.com.coderbank.conta.events.consumer;

import br.com.coderbank.conta.dto.ClienteDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClienteConsumer {

    @KafkaListener(topics = "cliente_cadastrado", groupId="cliente-group")
//    public void consume(User user)
    public void consume(ConsumerRecord<String, String> consumerRecord)
    {
//        log.info(String.format("User created -> %s", record.value()));
        log.info("Evento para criar conta para o cliente: -> {}", consumerRecord.value());
    }
}
