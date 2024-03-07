package edu.esprit.entities;

public class PanierState {
    private static PanierState instance;
    private int itemCount;
    private String buttonColor;

    private PanierState() {
        // Initialiser les valeurs par défaut du panier
        itemCount = 0;
        buttonColor = "-fx-background-color: #3333C4;";
    }

    public static PanierState getInstance() {
        if (instance == null) {
            instance = new PanierState();
        }
        return instance;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor;
    }
}
