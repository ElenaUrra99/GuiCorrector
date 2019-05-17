package modelo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *   Esta clase guarda en un map la relación de alumnos
 *   que han hecho el proyecto de 2º
 *   
 *   La clave es el nombre del alumno (siempre en mayúsculas) y el valor asociado un 
 *   objeto Proyecto con la información de su proyecto
 */
public class CorrectorProyectos
{

	private static final String FIC_PROYECTOS = "datos/proyectos.txt";
	private static final String FIC_ORDENADOS = "datos/ordenados.txt";
	private Map<String, Proyecto> mapProyectos;
	private List<String> errores; // líneas con error

	public CorrectorProyectos()
	{
		mapProyectos = new TreeMap<String, Proyecto>();
		errores = new ArrayList<>();
	}

	/**
	*  Añade al map un alumno (siempre en mayúsculas) junto con su proyecto
	*  
	*/
	public void add(String alumno, Proyecto proyecto)
	{
		mapProyectos.put(alumno.toUpperCase(), proyecto);
	}

	public List<String> getErrores()
	{
		return errores;
	}

	/**
	 * Lee del fichero FIC_PROYECTOS la información de cada proyecto y el alumno 
	 * que lo ha realizado 
	 * 
	 * Cada línea del fichero contiene los datos de un alumno y tiene la siguiente estructura
	 * 
	 * 		nombrealumno:tituloProyecto:notaDocumentación:notaOral1:notaOral2:notaOral3
	 * 
	 * 	 Por ejemplo,        pedro:Contenedores Docker:7.6 : 5 :5.5: 6.2
	 * 
	 * Cada proyecto tiene una nota en la documentación y tres notas dadas en la exposición oral
	 * por tres profesores distintos.
	 * 
	 *  A partir de la información de cada línea se parsea la línea y se añade el alumno junto con su
	 *  proyecto al map. Hay que utilizar parsearDatosProyecto()
	 * 
	 * Cuando se detecta una línea errónea se ignora la línea y continúa la ejecución
	 * normalmente. El método genera una lista de String con los nº de líneas 
	 * incorrectas que había en el fichero.
	 * 
	 * 	 
	 * Hay que usar BufferedReader y se capturarán todas las excepciones
	 *
	 */
	public void leerDatosProyectos()
	{
		File f = new File(FIC_PROYECTOS);
		int numLinea = 0;
		BufferedReader entrada = null;
		try {
			entrada = new BufferedReader(new FileReader(f));
			String linea = entrada.readLine();
			while (linea != null) {
				numLinea++;
				try {
					int pos = linea.indexOf(":");
					String alumno = linea.substring(0, pos)
					                .trim();
					Proyecto proyecto = parsearDatosProyecto(
					                linea.substring(pos + 1));
					this.add(alumno.toUpperCase(), proyecto);
					
				}
				catch (NumberFormatException e) {
					errores.add("Error de formato en línea " + numLinea);
					//System.out.println("error" + numLinea);
				}
				linea = entrada.readLine();
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if (entrada != null) {
				try {
					entrada.close();
				}
				catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	/**
	 * Recibe un String con los datos de un proyecto, parsea la información 
	 * y devuelve un objeto Proyecto
	 * 
	 * Por ejemplo, se recibe   "Contenedores Docker:7.6:5:5.5:6.2"
	 * 
	 * La nota de la exposición oral es la media aritmética de las tres notas dadas por
	 * los profesores
	 * 
	 * Eliminar posibles espacios existentes
	 * 
	 * Se propaga cualquier excepción producida al convertir los datos
	 * 
	 * 
	 */
	private Proyecto parsearDatosProyecto(String datosProyecto)
	                throws NumberFormatException
	{
		 
		String[] datos = datosProyecto.split(":");
		String titulo = datos[0].trim();
		double notaDocumentacion = Double.parseDouble(datos[1].trim());
		double nota1 = Double.parseDouble(datos[2].trim());
		double nota2 = Double.parseDouble(datos[3].trim());
		double nota3 = Double.parseDouble(datos[4].trim());
		
		double notaExposicionOral = (nota1 + nota2 + nota3) / 3;
		return new Proyecto(titulo, notaDocumentacion,
		                notaExposicionOral);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Proyecto> entrada : mapProyectos
		                .entrySet()) {
			sb.append(String.format("%20s: %s\n", "Alumno/a",
			                entrada.getKey()));
			sb.append(entrada.getValue());
			sb.append("-------------------------------------------\n");
		}

		return sb.toString();
	}

	/**
	 * Calcula y devuelve cuántos alumnos han aprobado el proyecto
	 */
	public int aprobados()
	{
		int total = 0;
		for (String alumno : mapProyectos.keySet()) {
			if (mapProyectos.get(alumno).calcularNotaFinal() >= 5) {
				total++;
			}
		}
		return total;

	}

	/**
	 * Dado un alumno devuelve su proyecto. Si no existe el alumno se lanza la
	 * excepción personalizada AlumnoNoExistenteException y se avisa
	 */
	public Proyecto proyectoDe(String alumno) throws AlumnoNoExistenteExcepcion
	{
		alumno = alumno.toUpperCase();
		if (!mapProyectos.containsKey(alumno)) {
			throw new AlumnoNoExistenteExcepcion(
			                "No existe ese alumno/a: " + alumno);
		}
		return mapProyectos.get(alumno);
	}


	/**
	 * Devuelve una colección List con las entradas del map ordenadas
	 * de mayor a menor nota de proyecto
	 * 
	 * Habrá que ordenar la colección de entradas (ver
	 * enunciado)  
	 */
	public List<Map.Entry<String, Proyecto>> ordenadosPorNota()
	{

		List<Map.Entry<String, Proyecto>> lista =
		                new ArrayList<Map.Entry<String, Proyecto>>(
		                                mapProyectos.entrySet());

		Collections.sort(lista, (e1, e2) -> (int) (Math
		                .signum(e2.getValue().calcularNotaFinal()
		                                - e1.getValue().calcularNotaFinal())));

		return lista;
	}

	/**
	 * Guarda en el fichero de texto FIC_ORDENADOS 
	 * el nombre de cada alumno y su proyecto en orden
	 * decreciente de nota
	 *  
	 * Se propagan las excepciones
	 */
	public void guardarOrdenadosPorNota() throws IOException
	{
		PrintWriter salida = null;
		try {
			File f = new File(FIC_ORDENADOS);
			salida = new PrintWriter(new BufferedWriter(new FileWriter(f)));
			List<Map.Entry<String, Proyecto>> lista = ordenadosPorNota();
			for (Entry<String, Proyecto> entrada : lista) {
				salida.printf("%20s\n%s\n", entrada.getKey(), entrada.getValue());
			}
		}
		finally {
			salida.close();
		}


	}



}
