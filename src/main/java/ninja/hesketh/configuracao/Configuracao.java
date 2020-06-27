package ninja.hesketh.configuracao;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

public class Configuracao {
    private static CachingConnectionFactory connectionFactory;

    public static CachingConnectionFactory getConnection(){

        if(connectionFactory == null){
            connectionFactory = new CachingConnectionFactory("spider.rmq.cloudamqp.com");
            connectionFactory.setUsername("vdchtboi");
            connectionFactory.setPassword("JLkDUowPEwxXYO5aROVRMxXHSMvSz_gI");
            connectionFactory.setVirtualHost("vdchtboi");

            //Recommended settings
            connectionFactory.setRequestedHeartBeat(30);
            connectionFactory.setConnectionTimeout(30000);
        }

        return connectionFactory;
    }
}
