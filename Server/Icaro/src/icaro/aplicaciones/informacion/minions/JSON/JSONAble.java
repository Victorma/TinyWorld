package icaro.aplicaciones.informacion.minions.JSON;

public interface JSONAble {

    public String getCorrespondingClassName();

    public Object toJSONObject();

    public void fromJSONObject(Object jsonObject);

}
