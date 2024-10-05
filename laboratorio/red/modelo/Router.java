package red.modelo;
public class Router extends Equipo {
    private String modelo;
    private String firmware;
    private int throughput;

    public Router(String id, String ipAddress, String macAddress, String modelo, String firmware, boolean status, int throughput, String ubicacion) {
        super(id, ipAddress, macAddress, status, ubicacion);
        this.modelo = modelo;
        this.firmware = firmware;
        this.throughput = throughput;
    }

    // Getters and setters
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public int getThroughput() {
        return throughput;
    }

    public void setThroughput(int throughput) {
        this.throughput = throughput;
    }

    @Override
    public String toString() {
        return "Router{" +
                "id='" + getId() + '\'' +
                ", ipAddress='" + getIpAddress() + '\'' +
                ", macAddress='" + getMacAddress() + '\'' +
                ", modelo='" + modelo + '\'' +
                ", firmware='" + firmware + '\'' +
                ", status=" + isStatus() +
                ", throughput=" + throughput +
                ", ubicacion='" + getUbicacion() + '\'' +
                '}';
    }
}