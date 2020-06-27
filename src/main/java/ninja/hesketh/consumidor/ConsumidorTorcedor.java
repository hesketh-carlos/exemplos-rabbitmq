package ninja.hesketh.consumidor;

import ninja.hesketh.configuracao.Configuracao;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ConsumidorTorcedor {

    public static void main(String args[]){
        //Set up queue, exchanges and bindings
        RabbitTemplate template = new RabbitTemplate(Configuracao.getConnection());

        byte[] body = template.receive("palmeiras").getBody();

    }

}
