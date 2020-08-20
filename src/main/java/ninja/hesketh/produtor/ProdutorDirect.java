package ninja.hesketh.produtor;

import ninja.hesketh.configuracao.Configuracao;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ProdutorDirect {

    public static void main(String[] args) {

        //Set up queue, exchanges and bindings
        RabbitAdmin admin = new RabbitAdmin(Configuracao.getConnection());
        Queue queueSPFC = new Queue("torcedor.spfc");
        Queue queueCorinthians = new Queue("torcedor.corinthians");
        Queue queueSantos = new Queue("torcedor.santos");
        Queue queuePalmeiras = new Queue("torcedor.palmeiras");

        final String exchange = "exchange.torcedor";

        admin.declareQueue(queueSPFC);
        admin.declareQueue(queueCorinthians);
        admin.declareQueue(queueSantos);
        admin.declareQueue(queuePalmeiras);

        DirectExchange exchangeTorcedor = new DirectExchange(exchange);
        admin.declareExchange(exchangeTorcedor);

        admin.declareBinding(BindingBuilder.bind(queueSPFC).to(exchangeTorcedor).with("spfc"));
        admin.declareBinding(BindingBuilder.bind(queueCorinthians).to(exchangeTorcedor).with("corinthians"));
        admin.declareBinding(BindingBuilder.bind(queueSantos).to(exchangeTorcedor).with("santos"));
        admin.declareBinding(BindingBuilder.bind(queuePalmeiras).to(exchangeTorcedor).with("palmeiras"));

        RabbitTemplate template = new RabbitTemplate(Configuracao.getConnection());

        template.convertAndSend(exchange, "spfc", "Julio Cesar");
        template.convertAndSend(exchange, "santos", "Marco Aurelio");
        template.convertAndSend(exchange, "corinthians", "Augusto");
        template.convertAndSend(exchange, "palmeiras", "Paulo ");
        template.convertAndSend(exchange, "remo", "Carlos Hesketh");
    }

}
