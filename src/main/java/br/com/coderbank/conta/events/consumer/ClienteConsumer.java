package br.com.coderbank.conta.events.consumer;

import br.com.coderbank.conta.dto.ClienteDTO;
import br.com.coderbank.conta.service.ContaCorrenteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClienteConsumer {
    private final ContaCorrenteService service;
    @KafkaListener(topics = "cliente_cadastrado", groupId="cliente-group")
    public void consume(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        String jsonCliente = consumerRecord.value();
        log.info("Evento para criar conta para o cliente: -> {}", jsonCliente);

        service.criarConta(jsonCliente);
    }
}
