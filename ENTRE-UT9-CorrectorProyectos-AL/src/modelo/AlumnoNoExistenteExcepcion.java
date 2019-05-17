
package modelo;

@SuppressWarnings("serial")
public class AlumnoNoExistenteExcepcion extends RuntimeException
{
    private String mensaje;


    public AlumnoNoExistenteExcepcion(String mensaje)
    {
        this.mensaje = mensaje;
    }


    @Override
	public String getMessage()
    {
        return mensaje;
    }
    
    

    @Override
	public String toString()
    {
        return mensaje;
    }
}
