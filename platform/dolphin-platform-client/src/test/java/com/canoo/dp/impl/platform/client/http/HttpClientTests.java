package com.canoo.dp.impl.platform.client.http;

import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.core.http.HttpClient;
import com.canoo.platform.core.http.HttpResponse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import spark.Spark;

import java.net.ServerSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class HttpClientTests {

    private final int freePort;

    public HttpClientTests() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            freePort = socket.getLocalPort();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @BeforeClass
    public void startSpark() {
        Spark.port(freePort);
        Spark.get("/", (req, res) -> "Spark Server for HTTP client integration tests");
    }

    @AfterClass
    public void destroySpark() {
        Spark.stop();
    }

    @Test
    public void testSimpleGet() throws Exception {
        //given:
        final HttpClient client = PlatformClient.getService(HttpClient.class);

        //when:
        CompletableFuture<HttpResponse<Void>> future = client.get("http://localhost:" + freePort).
                withoutContent().
                withoutResult().
                execute();

        //then:
        future.get(1_000, TimeUnit.MILLISECONDS);
    }

}
