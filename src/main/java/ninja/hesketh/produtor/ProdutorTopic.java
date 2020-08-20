package ninja.hesketh.produtor;

import ninja.hesketh.configuracao.Configuracao;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ProdutorTopic {

    public static void main(String[] args) {

        //Set up queue, exchanges and bindings
        RabbitAdmin admin = new RabbitAdmin(Configuracao.getConnection());
        Queue queue = new Queue("myQueue");
        admin.declareQueue(queue);
        TopicExchange exchange = new TopicExchange("exchange.topic");
        admin.declareExchange(exchange);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("app1.#.error"));
        //admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("app1.#.error")); posso usar o "*"

        RabbitTemplate template = new RabbitTemplate(Configuracao.getConnection());

        template.convertAndSend("exchange.topic", "app1.log.error", "Hello CloudAMQP! ");//atende
        template.convertAndSend("exchange.topic", "app1.logg.info", "Hello CloudAMQP! "); // não atende!!
        template.convertAndSend("exchange.topic", "app1.error", "Hello CloudAMQP! "); //atende


    }
}
