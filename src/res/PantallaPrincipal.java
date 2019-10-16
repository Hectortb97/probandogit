package res;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import logica.Logica;
import models.Partido;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class PantallaPrincipal extends Application {

    //meter imagen

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Gestor de partidos");

        TableView tableView = new TableView(Logica.getInstance().getListaPartidos());

        AnchorPane.setTopAnchor(tableView,10d);
        AnchorPane.setLeftAnchor(tableView,10d);
        AnchorPane.setRightAnchor(tableView,10d);
        AnchorPane.setBottomAnchor(tableView,50d);

        TableColumn<Partido,String> column1 = new TableColumn<>("Equipo Local");
        column1.setCellValueFactory(new PropertyValueFactory<>("local"));

        TableColumn<Partido,String> column2 = new TableColumn<>("Equipo Visitante");
        column2.setCellValueFactory(new PropertyValueFactory<>("visitante"));

        TableColumn<Partido,String> column3 = new TableColumn<>("Division");
        column3.setCellValueFactory(new PropertyValueFactory<>("division"));

        TableColumn<Partido,Integer> column4 = new TableColumn<>("Resultado");
        column4.setCellValueFactory(new PropertyValueFactory<>("resultado"));
        column4.setStyle( "-fx-alignment: CENTER;");//Centra el texto

        TableColumn<Partido,Date> column5 = new TableColumn<>("Fecha");

        column5.setCellFactory(column -> {
            TableCell<Partido,Date> cell = new TableCell<Partido,Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item , empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        this.setText(format.format(item));

                    }
                }
            };

            return cell;
        });

        column5.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        column5.setStyle( "-fx-alignment: CENTER;");//Centra el texto

        tableView.getColumns().addAll(column1,column2,column3,column4,column5);

        Button alta = new Button("Alta");

        AnchorPane.setBottomAnchor(alta, 10d);
        AnchorPane.setLeftAnchor(alta, 20d);

        alta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PantallaAlta pantallaAlta = new PantallaAlta();
                pantallaAlta.show();
            }
        });

        Button modificar = new Button("Modificar");

        AnchorPane.setBottomAnchor(modificar,10d);
        AnchorPane.setLeftAnchor(modificar,70d);

        modificar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int indice = tableView.getSelectionModel().getSelectedIndex();
                Partido partidoSeleccionado = Logica.getInstance().getListaPartidos().get(indice);
                PantallaAlta dialogoPersona = new PantallaAlta(partidoSeleccionado,indice);
                dialogoPersona.show();
            }
        });

        RadioButton radioButton1 = new RadioButton("Primera");
        RadioButton radioButton2 = new RadioButton("Segunda");
        RadioButton radioButton3 = new RadioButton("Tercera");
        ToggleGroup radioGroup = new ToggleGroup();
        radioButton1.setToggleGroup(radioGroup);
        radioButton2.setToggleGroup(radioGroup);
        radioButton3.setToggleGroup(radioGroup);

        radioButton1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    ObservableList<Partido> primera = FXCollections.observableArrayList();
                    for (Partido p : Logica.getInstance().getListaPartidos()) {
                        if(p.getDivision().equalsIgnoreCase("Primera"))
                            primera.add(p);
                    }
                    tableView.getItems().clear();
                    tableView.getItems().addAll(primera);

                }
            }
        });

        radioButton2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    ObservableList<Partido> segunda = FXCollections.observableArrayList();
                    for (Partido p : Logica.getInstance().getListaPartidos()) {
                        if(p.getDivision().equalsIgnoreCase("Segunda"))
                            segunda.add(p);
                    }
                    tableView.getItems().clear();
                    tableView.getItems().addAll(segunda);

                } else {
                    // ...
                }
            }
        });

        radioButton3.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    ObservableList<Partido> tercera = FXCollections.observableArrayList();
                    for (Partido p : Logica.getInstance().getListaPartidos()) {
                        if(p.getDivision().equalsIgnoreCase("Tercera"))
                            tercera.add(p);
                    }
                    tableView.getItems().clear();
                    tableView.getItems().addAll(tercera);

                } else {
                    // ...
                }
            }
        });

        AnchorPane.setBottomAnchor(radioButton1,15d);
        AnchorPane.setLeftAnchor(radioButton1,150d);
        AnchorPane.setBottomAnchor(radioButton2,15d);
        AnchorPane.setLeftAnchor(radioButton2,220d);
        AnchorPane.setBottomAnchor(radioButton3,15d);
        AnchorPane.setLeftAnchor(radioButton3,290d);

        Button borrar = new Button("Borrar");

        AnchorPane.setBottomAnchor(borrar,10d);
        AnchorPane.setRightAnchor(borrar,10d);

        borrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int indiceBorrar = tableView.getSelectionModel().getSelectedIndex();
                if (indiceBorrar>=0)
                    Logica.getInstance().borrarPartido(indiceBorrar);
                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmacion");
                    alert.setHeaderText("Al aceptar usted borrara todos los datos");
                    alert.setContentText("¿Desea borrar la tabla?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        tableView.getItems().clear();
                    } else {
                        alert.close();
                    }

                }
            }
        });

        AnchorPane anchorPane = new AnchorPane(tableView,alta,modificar,radioButton1,radioButton2,radioButton3,borrar);

        stage.getIcons().add(new Image("pelota.png"));
        Scene scene = new Scene(anchorPane, 430, 300);
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }

}
