package com.guigarage.demos.executor;

import io.datafx.control.cell.ServiceListCellFactory;
import io.datafx.core.concurrent.DataFxTask;
import io.datafx.core.concurrent.ObservableExecutor;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by hendrikebbers on 28.09.14.
 */
public class ObservableExecutorDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ObservableExecutor executor = new ObservableExecutor();

        ListView<Service<?>> taskList = new ListView<>();
        taskList.setItems(executor.currentServicesProperty().get());
        taskList.setCellFactory(new ServiceListCellFactory());

        Button addTask = new Button("Add Task");
        addTask.setOnAction((e) -> executor.submit((javafx.concurrent.Task) new DataFxTask<Void>() {
            @Override
            protected Void call() throws Exception {
                updateTaskTitle("My Task");
                long time = 0;
                long maxTime = (long) (Math.random() * 50000);
                while(time < maxTime) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {}
                    time = time + 200;
                    updateTaskProgress(time, maxTime);
                    updateMessage(time + " of " + maxTime);
                }
                return null;
            }
        }));

        primaryStage.setScene(new Scene(new VBox(taskList, addTask)));
        primaryStage.show();
    }

}