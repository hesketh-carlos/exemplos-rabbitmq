package ninja.hesketh.produtor;

import ninja.hesketh.configuracao.Configuracao;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import java.util.HashMap;
import java.util.Map;

public class ProdutorHeaders {

    public static void main(String args[]){
        //Set up queue, exchanges and bindings
        RabbitAdmin admin = new RabbitAdmin(Configuracao.getConnection());
        Queue queueArquivo = new Queue("arquivo");
        Queue queuePDF = new Queue("pdf");
        Queue queueDOC = new Queue("doc");

        admin.declareQueue(queueArquivo);
        admin.declareQueue(queueDOC);
        admin.declareQueue(queuePDF);

        HeadersExchange exchange = new HeadersExchange("exchange.arquivo");
        admin.declareExchange(exchange);

        //Map<String, Object> map = new HashMap<>(); map.put("formato", "pdf");

        admin.declareBinding(BindingBuilder.bind(queueArquivo).to(exchange).where("formato").exists());
        admin.declareBinding(BindingBuilder.bind(queuePDF).to(exchange).where("formato").matches("pdf"));
        admin.declareBinding(BindingBuilder.bind(queueDOC).to(exchange).where("formato").matches("doc"));

        RabbitTemplate template = new RabbitTemplate(Configuracao.getConnection());

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("formato", "pdf");
        MessageConverter messageConverter = new SimpleMessageConverter();
        Message message = messageConverter.toMessage("sou um .pdf 2020", messageProperties);
        template.send("exchange.arquivo", "", message);

        MessageProperties messagePropertiesDoc = new MessageProperties();
        messagePropertiesDoc.setHeader("formato", "doc");
        MessageConverter messageConverterDoc = new SimpleMessageConverter();
        Message messageDoc = messageConverterDoc.toMessage("sou um .doc 2020", messagePropertiesDoc);
        template.send("exchange.arquivo", "", messageDoc);

        MessageProperties messagePropertiesXls = new MessageProperties();
        messagePropertiesXls.setHeader("formato", "xls");
        MessageConverter messageConverterXls = new SimpleMessageConverter();
        Message messageXls = messageConverterXls.toMessage("sou um .xls 2020", messagePropertiesXls);
        template.send("exchange.arquivo", "", messageXls);

    }

}
