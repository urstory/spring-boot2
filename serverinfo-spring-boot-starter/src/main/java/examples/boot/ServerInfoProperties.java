package examples.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

// serverinfo.address
// serverinfo.port
@ConfigurationProperties("serverinfo")
public class ServerInfoProperties {
    private String address;
    private int port;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
