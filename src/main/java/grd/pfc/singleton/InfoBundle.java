package grd.pfc.singleton;
public class InfoBundle {
 
private int idEmpleado;
private static InfoBundle infoBundle;
 
public  static InfoBundle getInfoBundle() {
 
    if (infoBundle==null) {infoBundle=new InfoBundle();}
    return infoBundle;
}

    private InfoBundle(){}

    public int getIdEmpleado() {return idEmpleado;}

    public void setIdEmpleado(int idEmpleado) {this.idEmpleado = idEmpleado;}

}