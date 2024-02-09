package com.example.application;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

import javafx.collections.ObservableList;

public class OverviewController implements MujModelAware {

    private List<Item> itemList = new ArrayList<>();

    @FXML
    private TableView<Item> tableView;
    private MujModel model;
    private HomeController homeController;



    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    public void setOverviewController(HomeController homeController) {
        this.homeController = homeController;
    }


    @FXML
    private void initialize() {
        Connection databaseConnection = MujModel.connection();
        this.model = new MujModel(databaseConnection, homeController, this, null);
        model.loadDataFromDatabase();
        System.out.println("Setting MujModel: " + model);

        // Získání seznamu položek z modelu
        itemList = model.getItemsList();

        // Nastavte data pro TableView z itemList
        tableView.getItems().setAll(itemList);

        // Nastavení šířky sloupců
        distributeColumnWidths();

        // Nastavte HomeController v OverviewController s kontrolou na null
        if (homeController != null) {
            homeController.setOverviewController(this);
        }
    }

    private void distributeColumnWidths() {
        // Získání seznamu sloupců
        ObservableList<TableColumn<Item, ?>> columns = tableView.getColumns();

        // Nastavení šířky sloupců
        for (TableColumn<Item, ?> column : columns) {
            column.prefWidthProperty().bind(tableView.widthProperty().divide(columns.size()));
        }
    }

    @FXML
    public void updateOverview() {
        itemList = model.getItemsList();
        tableView.getItems().setAll(itemList);
    }

    @Override
    public void setMujModel(MujModel model) {
        this.model = model;
    }
}