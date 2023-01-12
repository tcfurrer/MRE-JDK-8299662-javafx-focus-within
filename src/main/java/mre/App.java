package mre;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.beans.binding.Bindings.createStringBinding;

public final class App extends Application
{
    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage stage)
    {
        stage.setTitle("Demo Bug 9173050");

        var root = new TreeItem<>("Items");
        root.getChildren().setAll(
                new TreeItem<>("Item 1"),
                new TreeItem<>("Item 2"),
                new TreeItem<>("Item 3")
        );
        root.setExpanded(true);

        var column = new TreeTableColumn<String,String>("Column");
        column.setPrefWidth(150);

        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> p) ->
                                           new ReadOnlyStringWrapper(p.getValue().getValue()));

        var treeTableView = new TreeTableView<>(root);
        treeTableView.getColumns().add(column);
        treeTableView.setShowRoot(true);

        var label = new Label("Focus Within:");
        var state = new Label();
        state.textProperty().bind(createStringBinding(
                () -> treeTableView.isFocusWithin() ? "yes" : "no",
                treeTableView.focusWithinProperty()
        ));

        var hBox = new HBox(label, state);
        var vbox = new VBox(hBox, treeTableView);
        final Scene scene = new Scene(new StackPane(vbox), 300, 200);
        stage.setScene(scene);
        stage.show();
    }
}