package examples.boot;

// 1. 자동으로 설정될 클래스
public class ServerInfo {
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

    @Override
    public String toString() {
        return "ServerInfo{" +
                "address='" + address + '\'' +
                ", port=" + port +
                '}';
    }
}
