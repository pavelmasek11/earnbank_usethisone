package com.example.application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.sql.Connection;

public class ItemsController {

    @FXML
    private TextField incomeGoal;
    @FXML
    private TextField incomeSaved;
    @FXML
    private TextField Nazev;
    @FXML
    private Text successText;

    private MujModel model;
    private OverviewController overviewController;
    private HomeController homeController;
    private ItemsController itemsController;  // deklarace proměnné pro ItemsController


    public void setItemsController(ItemsController itemsController) {
        this.itemsController = itemsController;
    }

    @FXML
    private void initialize() {
        // Inicializace MujModel
        Connection databaseConnection = MujModel.connection();


        // Vytvoření instance MujModel pouze s homeController (overviewController bude nastaven později)
        this.model = new MujModel(databaseConnection, homeController, null, itemsController);

        // Načtení dat z databáze
        model.loadDataFromDatabase();
        System.out.println("Setting MujModel: " + model);
        System.out.println("ItemsController initialized");

        // Přesuňte volání metody pro aktualizaci přehledu na vhodné místo
        updateOverview();
    }


    @FXML
    public void setMujModel(MujModel model) {
        System.out.println("Setting MujModel: " + model);
        this.model = model;
        System.out.println("MujModel is set in ItemsController");
        // Zavolejte initialize() pro inicializaci MujModel
        initialize();
    }

    public void setOverviewController(OverviewController overviewController) {
        this.overviewController = overviewController;
    }
    @FXML
    private void addincome(MouseEvent event) {
        System.out.println("Přidávám novou položku.");

        if (model == null) {
            printMujModelInfo();
            System.out.println("Model is not set. Cannot proceed.");
            return;
        }

        if (incomeGoal == null || incomeSaved == null || incomeGoal.getText().isEmpty() || incomeSaved.getText().isEmpty()) {
            System.out.println("incomeGoal or incomeSaved is null or empty. Cannot proceed.");
            return;
        }

        try {
            double celkovaCastka = Double.parseDouble(incomeGoal.getText());
            double nasetrenaCastka = Double.parseDouble(incomeSaved.getText());
            String nazev = Nazev.getText();

            model.zadejNazev(nazev);
            model.zadejGoal(celkovaCastka);
            model.zadejSaved(nasetrenaCastka);

            // Přidání nové položky do modelu
            model.pridejPolozku(nazev, celkovaCastka, nasetrenaCastka);

            // Kontrola, zda je overviewController inicializován
            if (overviewController != null) {
                // Volání metody pro aktualizaci přehledu v OverviewController
                overviewController.updateOverview();
            } else {
                System.out.println("OverviewController is null. Cannot update overview.");
                // Zde byste mohli přidat logiku pro inicializaci nebo získání instance OverviewController
            }

            // Znovu načtěte data z databáze, aby se získaly aktualizované hodnoty
            model.loadDataFromDatabase();

            System.out.println("Data byla úspěšně přidána do databáze a přehled byl aktualizován.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers for incomeGoal and incomeSaved.");
        }
    }



    private void updateOverview() {
        if (overviewController != null) {
            overviewController.updateOverview();
        }
    }

    private void printMujModelInfo() {
        if (model != null) {
            System.out.println("MujModel instance info: " + model.toString());
        } else {
            System.out.println("MujModel is null");
        }
    }
    @FXML
    public void showOverview() {
        homeController.showOverview();
    }
}






