package grd.pfc.pojo;

public class IVA {
    private int id;
    private String iva;
    private double tipo;

    public IVA(int id, String iva, double tipo) {
        this.id = id;
        this.iva = iva;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIVA() {
        return iva;
    }

    public void setIVA(String iva) {
        this.iva = iva;
    }

    public double getTipo() {
        return tipo;
    }

    public void setTipo(double tipo) {
        this.tipo = tipo;
    }
}
