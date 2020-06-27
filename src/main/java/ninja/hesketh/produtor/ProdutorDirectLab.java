package ninja.hesketh.produtor;

import ninja.hesketh.configuracao.Configuracao;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ProdutorDirectLab {
    public static void main(String[] args) {

        //Set up queue, exchanges and bindings
        RabbitAdmin admin = new RabbitAdmin(Configuracao.getConnection());
        Queue queueSPFC = new Queue("spfc");
        Queue queueSantos = new Queue("santos");
        Queue queuePalmeiras = new Queue("palmeiras");
        Queue queueCorinthians = new Queue("corinthians");

        admin.declareQueue(queueSPFC);
        admin.declareQueue(queueSantos);
        admin.declareQueue(queuePalmeiras);
        admin.declareQueue(queueCorinthians);

        DirectExchange exchange = new DirectExchange("exchange.para.times");
        admin.declareExchange(exchange);

        admin.declareBinding(BindingBuilder.bind(queueSPFC).to(exchange).with("spfc"));
        admin.declareBinding(BindingBuilder.bind(queueSantos).to(exchange).with("santos"));
        admin.declareBinding(BindingBuilder.bind(queuePalmeiras).to(exchange).with("palmeiras"));
        admin.declareBinding(BindingBuilder.bind(queueCorinthians).to(exchange).with("corinthians"));


        RabbitTemplate template = new RabbitTemplate(Configuracao.getConnection());
        template.convertAndSend("exchange.para.times", "spfc", "Ana spfc");
        template.convertAndSend("exchange.para.times", "santos", "Ana Santos");
        template.convertAndSend("exchange.para.times", "palmeiras", "Ana palmeias");
        template.convertAndSend("exchange.para.times", "corinthians", "Ana corinthians");
        template.convertAndSend("exchange.para.times", "remo", "Carlos Hesketh");

    }
}
