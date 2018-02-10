package com;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.ConnectionStrategies;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.client.RecoveryStrategies;
import io.atomix.copycat.client.ServerSelectionStrategies;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@Component
@RestController
public class MyCopycatClient {

    private CopycatClient client;
    private List<CopycatClient> sessions;

    public MyCopycatClient() {
        client = CopycatClient.builder()
                .withClientId(Thread.currentThread().toString())
                .withConnectionStrategy(ConnectionStrategies.ONCE)
                .withRecoveryStrategy(RecoveryStrategies.CLOSE)
                .withServerSelectionStrategy(ServerSelectionStrategies.LEADER)
                .withSessionTimeout(Duration.ofMinutes(1L))
                .withTransport(new NettyTransport())
                .build();

    }

    @RequestMapping(value = "/connect")
    public void connect(){
                client.connect(new Address("localhost", 9001))
                .whenComplete((client, error) -> {
                    sessions.add(client);
                    System.out.println("@@@@@@@@@@@@connected! " + client.session());
                    client.onStateChange(state -> {
                            System.out.println("state changed: "+state);
                    });
                });
    }

    @RequestMapping(value = "/dump")
    public void print() {
        sessions.stream().forEach(session -> {
                System.out.println(session);
        });
    }

}
