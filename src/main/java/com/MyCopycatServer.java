package com;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Component
public class MyCopycatServer {

    public MyCopycatServer(){
        Address address = new Address("localhost", Integer.valueOf(9001));
        io.atomix.copycat.server.CopycatServer server = io.atomix.copycat.server.CopycatServer.builder(address)
                .withStateMachine(MapStateMachine::new)
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build())
                .withStorage(Storage.builder()
                        .withDirectory(new File("logs"))
                        .withStorageLevel(StorageLevel.MEMORY)
                        .build())
                .build();

        server.serializer().register(PutCommand.class);
        server.serializer().register(GetQuery.class);

        CompletableFuture<io.atomix.copycat.server.CopycatServer> future = server.bootstrap();

        future.join();

    }

    public static void main(String[] a) {
        new MyCopycatServer();
    }
}
