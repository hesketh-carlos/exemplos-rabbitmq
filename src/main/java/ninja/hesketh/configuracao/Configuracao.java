package ninja.hesketh.configuracao;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

public class Configuracao {
    private static CachingConnectionFactory connectionFactory;

    public static CachingConnectionFactory getConnection(){

        if(connectionFactory == null){
            connectionFactory = new CachingConnectionFactory("spider.rmq.cloudamqp.com");
            connectionFactory.setUsername("vdchtboi");  //SET TEU USUARIO    !!!!!
            connectionFactory.setPassword("jOvyU_kOnG-2AgDQ_Uz-PXhYMZnPV-ts"); //SENHA !!!!!
            connectionFactory.setVirtualHost("vdchtboi");

            //Recommended settings
            connectionFactory.setRequestedHeartBeat(30);
            connectionFactory.setConnectionTimeout(30000);
        }

        return connectionFactory;
    }
}
