package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import model.Stock;

public class MainController {
    private Stock currentStock;

    public MainController(){
        currentStock = null;
    }

    public void searchStock(Event event) {

    }

    public void showDefaultStock() {

    }

    private void graphData() {

    }

    private String getRecommendations() {

        return "Sell";
    }

    public boolean logout(){

        return true;
    }

    private void displayError() {

    }
}