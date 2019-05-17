package vistacontrolador;

/**
 * @author Elena Urra
 */

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.AlumnoNoExistenteExcepcion;
import modelo.CorrectorProyectos;

public class GuiCorrectorProyectos extends Application
{
	private MenuItem itemLeer;
	private MenuItem itemGuardar;
	private MenuItem itemSalir;

	private TextField txtAlumno;
	private Button btnVerProyecto;

	private RadioButton rbtAprobados;
	private RadioButton rbtOrdenados;
	private Button btnMostrar;

	private TextArea areaTexto;

	private Button btnClear;
	private Button btnSalir;

	private CorrectorProyectos corrector; // el modelo

	@Override
	public void start(Stage stage)
	{
		corrector = new CorrectorProyectos();
		BorderPane root = crearGui();

		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.setTitle("- Corrector de proyectos -");
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		stage.show();
	}

	private BorderPane crearGui()
	{
		BorderPane panel = new BorderPane();
		MenuBar barraMenu = crearBarraMenu();
		panel.setTop(barraMenu);

		VBox panelPrincipal = crearPanelPrincipal();
		panel.setCenter(panelPrincipal);

		HBox panelBotones = crearPanelBotones();
		panel.setBottom(panelBotones);

		return panel;
	}

	private MenuBar crearBarraMenu()
	{
		MenuBar barraMenu = new MenuBar();
		Menu menu = new Menu("Archivo");

		itemLeer = new MenuItem("_Leer de fichero");
		itemLeer.setAccelerator(KeyCombination.keyCombination("CTRL+L"));
		// a completar
		itemLeer.setOnAction(evet -> leerDeFichero());

		itemGuardar = new MenuItem("Guardar de fichero");
		itemGuardar.setAccelerator(KeyCombination.keyCombination("CTRL+G"));
		itemGuardar.setOnAction(event -> salvarEnFichero());

		itemSalir = new MenuItem("Salir de fichero");
		itemSalir.setAccelerator(KeyCombination.keyCombination("CTRL+S"));
		itemSalir.setOnAction(event -> salir());

		itemLeer.setDisable(false);// habilitar itemLeer
		itemGuardar.setDisable(true);// deshabilitar itemGuardar

		menu.getItems().addAll(itemLeer, itemGuardar, itemSalir);
		barraMenu.getMenus().add(menu);
		barraMenu.getStyleClass().add("menu-bar");

		return barraMenu;
	}

	private VBox crearPanelPrincipal()
	{
		VBox panel = new VBox();
		panel.setPadding(new Insets(5));
		panel.setSpacing(10);

		Label lblEntrada = new Label("Panel de entrada");
		// a completar
		lblEntrada.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		lblEntrada.getStyleClass().add("titulo-panel");

		HBox cajaEntrada = crearPanelEntrada();
		cajaEntrada.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

		Label lblopciones = new Label("Panel de opciones");
		lblopciones.getStyleClass().add("titulo-panel");
		lblopciones.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

		HBox cajaOpciones = crearPanelOpciones();
		cajaOpciones.setMaxWidth(Integer.MAX_VALUE);

		areaTexto = new TextArea();
		areaTexto.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

		panel.getChildren().addAll(lblEntrada, cajaEntrada, lblopciones, cajaOpciones, areaTexto);
		return panel;
	}

	private HBox crearPanelEntrada()
	{
		HBox panel = new HBox();
		panel.setPadding(new Insets(5));
		// a completar
		panel.setSpacing(10);
		Label lblalumno = new Label("Alumno");

		txtAlumno = new TextField();
		txtAlumno.setPrefColumnCount(30);

		btnVerProyecto = new Button("Ver proyecto");
		btnVerProyecto.setPrefWidth(120);
		btnVerProyecto.setOnAction(event -> verProyecto());

		panel.getChildren().addAll(lblalumno, txtAlumno, btnVerProyecto);
		return panel;
	}

	private HBox crearPanelOpciones()
	{
		HBox panel = new HBox();
		panel.setPadding(new Insets(5));
		// a completar
		panel.setSpacing(50);
		panel.setAlignment(Pos.CENTER);

		rbtAprobados = new RadioButton("Mostrar aprobados");
		rbtAprobados.setSelected(true);// Seleccionado

		rbtOrdenados = new RadioButton("Mostrar ordenados");

		btnMostrar = new Button("Mostrar");
		btnMostrar.setOnAction(event -> mostrar());
		// btnMostrar.getStyleClass().add("button");

		panel.getChildren().addAll(rbtAprobados, rbtOrdenados, btnMostrar);
		return panel;
	}

	private HBox crearPanelBotones()
	{
		HBox panel = new HBox();
		panel.setPadding(new Insets(5));
		// a completar
		panel.setSpacing(10);
		panel.setAlignment(Pos.CENTER_RIGHT);

		btnClear = new Button("Clear");
		btnClear.setPrefWidth(90);
		btnClear.setOnAction(event -> clear());

		btnSalir = new Button("Salir");
		btnSalir.setPrefWidth(90);
		btnSalir.setOnAction(event -> salir());

		panel.getChildren().addAll(btnClear, btnSalir);
		return panel;
	}

	private void salvarEnFichero()
	{//
		try
		{
			corrector.guardarOrdenadosPorNota();
			areaTexto.setText("Guardados en fichero de texto los proyectos ordenados\r\n");
		}
		catch (IOException e)
		{
			areaTexto.setText("Error al guardar el fichero");
		}
	}

	private void leerDeFichero()
	{//
		corrector.leerDatosProyectos();
		areaTexto.setText(corrector.toString());

		itemLeer.setDisable(true);
		itemGuardar.setDisable(false);
	}

	private void verProyecto()
	{//
		if (!itemLeer.isDisable())
		{
			areaTexto.setText("No se han leído todavía los datos del fichero\r\n" + "Vaya a la opción leer del menú");
		}
		else
		{
			String str = txtAlumno.getText();
			if (str.isEmpty())
			{
				areaTexto.setText("Teclee nombre de alumno");
			}
			else
			{
				try
				{
					String alumno = String.valueOf(corrector.proyectoDe(txtAlumno.getText()));
					areaTexto.setText(alumno);
				}
				catch (AlumnoNoExistenteExcepcion e)
				{
					areaTexto.setText("Alumno/a no existente");
				}
			}
		}
	}

	private void mostrar()
	{
		clear();
		// a completar
		if (rbtAprobados.isSelected())
		{
			String str = String.valueOf(corrector.aprobados());
			areaTexto.setText("Han aprobado el proyecto " + str);
		}
		else if (rbtOrdenados.isSelected())
		{
			areaTexto.setText(corrector.ordenadosPorNota().toString());
		}
	}

	private void cogerFoco()
	{
		txtAlumno.requestFocus();
		txtAlumno.selectAll();
	}

	private void salir()
	{
		System.exit(0);
	}

	private void clear()
	{
		areaTexto.clear();
		cogerFoco();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}