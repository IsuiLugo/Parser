package gramaticasequipoa;

public class Producciones {
    
    //Producción E -> E+E
    public String ProduccionPlus(String simbolo){
      String Produccion = "";
      if(simbolo.equals("E")){
          Produccion = "E+E";
      }
      return Produccion;
    }
    
}
