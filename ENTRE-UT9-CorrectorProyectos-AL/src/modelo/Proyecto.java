package modelo;

/** 
 * Representa a un proyecto realizado por un alumno
 * Todo proyecto tiene un título, y dos calificaciones
 * la de la documentación presentada y
 *  la de la exposición oral realizada por el alumno
 * 
 * Todo proyecto puede compararse por su título (es su orden natural)
 */
public class Proyecto implements Comparable<Proyecto>
{
	private String titulo;
	private double notaDocumentacion;
	private double notaExposicionOral;
	
	public Proyecto(String titulo, double notaDocumentacion,
	                double notaExposicionOral)
	{
		super();
		this.titulo = titulo.toUpperCase();
		this.notaDocumentacion = notaDocumentacion;
		this.notaExposicionOral = notaExposicionOral;
	}
	
	public String getTitulo()
	{
		return titulo;
	}
	
	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}
	
	public double getNotaDocumentacion()
	{
		return notaDocumentacion;
	}
	
	public void setNotaDocumentacion(double notaDocumentacion)
	{
		this.notaDocumentacion = notaDocumentacion;
	}
	
	public double getNotaExposicionOral()
	{
		return notaExposicionOral;
	}
	
	public void setNotaExposicionOral(double notaExposicionOral)
	{
		this.notaExposicionOral = notaExposicionOral;
	}
	
	/**
	 * la nota final se calcula:  70% nota documentación + 30% exposición oral
	 */
	public double calcularNotaFinal()
	{
		return notaDocumentacion * 0.7 + notaExposicionOral * 0.3;
	}

	@Override
	public String toString()
	{
		return String.format("%20s: %s\n", "Título proyecto",
		                this.titulo.toUpperCase()) +
		                String.format("%20s: %4.2f\n", "Nota documentación",
		                                this.notaDocumentacion)
		                +
		                String.format("%20s: %4.2f\n", "Nota exposición oral",
		                                this.notaExposicionOral)
		                +
		                String.format("%20s: %4.2f\n", "Nota final",
		                                this.calcularNotaFinal());

	}

	@Override
	public int compareTo(Proyecto p)
	{
		return this.titulo.compareToIgnoreCase(p.getTitulo());
	}
	
	public static void main(String[] args)
	{
		Proyecto p = new Proyecto("Contenedores Docker", 7, 6);
		System.out.println(p.toString());
		p = new Proyecto("Amazon Web Services", 5.4, 6.6);
		System.out.println(p.toString());
	}

}
