package icaro.aplicaciones.informacion.gestionCitas;

public class InfoConexionUsuario {

    public InfoConexionUsuario() {
    }

    private String userName;
    private String login;
    private String host;

    public String getuserName() {
        return userName;
    }

    public void setuserName(String usName) {
        this.userName = usName;
    }

    public void setlogin(String loginU) {
        this.login = loginU;
    }

    public String getlogin() {
        return login;
    }

    public void sethost(String hostU) {
        this.host = hostU;
    }

    public String gethost() {
        return host;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ Usurio: " + this.userName);
        sb.append(", pasw: " + this.login);
        sb.append(", host: " + this.host + " ]");
        return super.toString() + ", " + sb.toString();
    }
}
