package com.example.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import com.example.user.App;
import com.example.user.MyListener;
import models.Voiture;

public class ItemController {
    @FXML
    private ImageView img;

    @FXML
    private Label marqueLable;

    @FXML
    private Label matriculeLabel;

    @FXML
    private Label priceLable;

    @FXML
    private Label puissanceLable;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(voiture);
    }

    private Voiture voiture;
    private MyListener myListener;

    public void setData(Voiture voiture, MyListener myListener) {
        this.voiture = voiture;
        this.myListener = myListener;
        matriculeLabel.setText(voiture.getMatricule());
        marqueLable.setText(voiture.getMarque());
        //priceLable.setText(String.valueOf(voiture.getPrix_voiture()));
       // puissanceLable.setText(String.valueOf(voiture.getPuissance()));

        Image image = new Image(getClass().getResource("/images/car.png").toString());
        img.setImage(image);
    }


}