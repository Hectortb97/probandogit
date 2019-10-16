package res;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DateTimeStringConverter;
import logica.Logica;
import models.Partido;
import java.text.SimpleDateFormat;


public class PantallaAlta extends Stage {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private TextField local;
    private TextField visitante;
    private ComboBox division;
    private TextField resLocal;
    private TextField resVisitante;
    private TextField fecha;
    private Button botonAceptar;

    private ObservableList<String> divisiones = FXCollections.observableArrayList("Primera","Segunda","Tercera");

    public PantallaAlta() {
        inicializaVista();
        botonAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addPartido();
            }
        });
    }

    public PantallaAlta(Partido partido, int posicion) {

        inicializaVista();

        local.setText(partido.getLocal());

        visitante.setText(partido.getVisitante());

        if(partido.getDivision().equalsIgnoreCase("Primera"))
            division.getSelectionModel().select(0);
        else
            if(partido.getDivision().equalsIgnoreCase("Segunda"))
                division.getSelectionModel().select(1);
            else
                division.getSelectionModel().select(2);

        resLocal.setText(String.valueOf(partido.getGolesLocal()));
        resVisitante.setText(String.valueOf(partido.getGolesVisitante()));

        fecha.setText(sdf.format(partido.getFecha()));

        botonAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Partido partidoM = new Partido(local.getText(), visitante.getText(),division.getSelectionModel().getSelectedItem().toString(),Integer.parseInt(resLocal.getText()),Integer.parseInt(resVisitante.getText()),fecha.getText());
                Logica.getInstance().modificarPartido(partidoM,posicion);
                close();
            }
        });
    }

    private void inicializaVista()
    {
        initModality(Modality.APPLICATION_MODAL);

        setTitle("Alta partido");

        HBox hboxPrin = new HBox();

        VBox vboxLabels = new VBox(30);

        VBox vboxDatos = new VBox(20);

        vboxLabels.getChildren().add(new Label("Equipo local:"));
        local = new TextField();
        local.setMaxWidth(100);
        vboxDatos.getChildren().add(local);

        vboxLabels.getChildren().add(new Label("Equipo visitante:"));
        visitante = new TextField();
        visitante.setMaxWidth(100);
        vboxDatos.getChildren().add(visitante);

        vboxLabels.getChildren().add(new Label("Division:"));
        division = new ComboBox<String>();
        division.setPromptText("Selecciona una division");
        division.setItems(divisiones);
        division.setMaxWidth(100);
        vboxDatos.getChildren().add(division);

        Label r = new Label("Resultado:");
        resLocal = new TextField();
        resLocal.setMaxWidth(30);
        resLocal.setStyle("-fx-alignment: CENTER;");
        resLocal.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    resLocal.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Label guion = new Label("-");

        resVisitante = new TextField();
        resVisitante.setMaxWidth(30);
        resVisitante.setStyle("-fx-alignment: CENTER;");
        resVisitante.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    resVisitante.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        vboxLabels.getChildren().add(r);

        HBox hboxGoles = new HBox();
        hboxGoles.getChildren().addAll(resLocal,guion,resVisitante);

        vboxDatos.getChildren().add(hboxGoles);

        vboxLabels.getChildren().add(new Label("Fecha:"));
        fecha = new TextField();

        fecha.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(sdf)));

        fecha.setPromptText("dd-MM-yyyy");
        fecha.setMaxWidth(100);
        vboxDatos.getChildren().add(fecha);

        vboxDatos.setPadding(new Insets(10));

        botonAceptar = new Button("Aceptar");

        botonAceptar.setPadding(new Insets(10));
        vboxLabels.getChildren().add(botonAceptar);

        hboxPrin.getChildren().addAll(vboxLabels,vboxDatos);

        Scene scenaAlta = new Scene(hboxPrin, 400, 300);
        setScene(scenaAlta);
        setResizable(true);
    }

    private void addPartido()
    {
        Partido partido = new Partido(local.getText(), visitante.getText(),division.getSelectionModel().getSelectedItem().toString(),Integer.parseInt(resLocal.getText()),Integer.parseInt(resVisitante.getText()),fecha.getText());
        Logica.getInstance().addPartido(partido);
        close();
    }

}
