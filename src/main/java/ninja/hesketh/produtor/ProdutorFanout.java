package ninja.hesketh.produtor;

import ninja.hesketh.configuracao.Configuracao;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ProdutorFanout {
    public static void main(String args[]){
        //Set up queue, exchanges and bindings
        RabbitAdmin admin = new RabbitAdmin(Configuracao.getConnection());
        Queue queueElevador = new Queue("elevador");
        Queue queueCafeteira = new Queue("cafeteira");

        admin.declareQueue(queueElevador);
        admin.declareQueue(queueCafeteira);

        FanoutExchange exchange = new FanoutExchange("exchange.automacao");
        admin.declareExchange(exchange);

        admin.declareBinding(BindingBuilder.bind(queueElevador).to(exchange));
        admin.declareBinding(BindingBuilder.bind(queueCafeteira).to(exchange));

        RabbitTemplate template = new RabbitTemplate(Configuracao.getConnection());

        template.convertAndSend("exchange.automacao", "qualquer coisa!!!", "Carlos para Andar 28º");

    }
}
