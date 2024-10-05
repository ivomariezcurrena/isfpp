package red.modelo;

public class Conexion {
    private Equipo source;
    private Equipo target;
    private String tipoConexion;
    private int bandwidth;
    private int latencia;
    private boolean status;
    private double errorRate;

    public Conexion(Equipo source, Equipo target, String tipoConexion, int bandwidth, int latencia, boolean status, double errorRate) {
        this.source = source;
        this.target = target;
        this.tipoConexion = tipoConexion;
        this.bandwidth = bandwidth;
        this.latencia = latencia;
        this.status = status;
        this.errorRate = errorRate;
    }

    // Getters and setters
    public Equipo getSource() {
        return source;
    }

    public void setSource(Equipo source) {
        this.source = source;
    }

    public Equipo getTarget() {
        return target;
    }

    public void setTarget(Equipo target) {
        this.target = target;
    }

    public String getTipoConexion() {
        return tipoConexion;
    }

    public void setTipoConexion(String tipoConexion) {
        this.tipoConexion = tipoConexion;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getLatencia() {
        return latencia;
    }

    public void setLatencia(int latencia) {
        this.latencia = latencia;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(double errorRate) {
        this.errorRate = errorRate;
    }

    @Override
    public String toString() {
        return "Conexion{" +
                "source=" + source +
                ", target=" + target +
                ", tipoConexion='" + tipoConexion + '\'' +
                ", bandwidth=" + bandwidth +
                ", latencia=" + latencia +
                ", status=" + status +
                ", errorRate=" + errorRate +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Conexion other = (Conexion) obj;
        if (source == null) {
            if (other.source != null)
                return false;
        } else if (!source.equals(other.source))
            return false;
        if (target == null) {
            if (other.target != null)
                return false;
        } else if (!target.equals(other.target))
            return false;
        return true;
    }
    
}