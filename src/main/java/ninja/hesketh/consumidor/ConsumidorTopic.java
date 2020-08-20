package ninja.hesketh.consumidor;

import ninja.hesketh.configuracao.Configuracao;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ConsumidorTopic {

    public static void main(String[] args) {
        //Set up queue, exchanges and bindings
        RabbitTemplate template = new RabbitTemplate(Configuracao.getConnection());

        while(true){
            try {
                byte[] body = template.receive("myQueue").getBody();
                System.out.println(new String(body));
            } catch (NullPointerException ex){
                System.out.println("fila vazia!");
                continue;
            }
        }

    }

}
