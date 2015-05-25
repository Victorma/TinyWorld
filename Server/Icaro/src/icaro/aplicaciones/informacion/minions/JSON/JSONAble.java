package icaro.aplicaciones.informacion.minions.JSON;

public interface JSONAble {
    //****************************************************************************************************
    // Methods (public):
    //****************************************************************************************************

    public Object toJSONObject();

    public void fromJSONObject(Object victim);

    public String getCorrespondingClassName();
}
