package main.httpconnection;

import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * Created by alankrit on 26/11/17.
 */
public class ConnectionManager {
    public static CloseableHttpClient httpClient = null;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(200);
        httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }
}
