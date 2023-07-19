package com.jamieleen.movieticketingsystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

public class MovieTicketingSystem extends Application {
    //declare variables
    private TextField movieName, seatsChosen;
    private ComboBox<String> experienceCategory;
    private Button btnSubmit;
    private String[] experienceName ={"Beanie", "Classic", "Deluxe", "Family-Friendly", "Flexound", "IMAX", "Indulge", "Infinity", "Junior", "Onyx"}, seat;
    private double[] experiencePrice = {19.90, 15.90, 23.90, 23.90, 25.90, 25.90, 120.00, 120.00, 15.90, 89.90};
    String errorMessage;

    @Override
    public void start(Stage stage) throws IOException {
        GridPane layout = new GridPane();

        //Movie
        Label movie = new Label("Movie");
        movieName = new TextField("");
        movieName.setPromptText("Enter a movie...");
        layout.add(movieName, 1,0,3,1);

        //Experience
        Label experience = new Label("Experience");
        experienceCategory = new ComboBox<>();
        experienceCategory.setPrefWidth(300);
        experienceCategory.setPromptText("Choose an experience");
        experienceCategory.getItems().addAll("Beanie", "Classic", "Deluxe", "Family-Friendly", "Flexound", "IMAX", "Indulge", "Infinity", "Junior", "Onyx");
        layout.add(experienceCategory, 1,1,1,1);

        //Session
        Label session = new Label("Session");
        RadioButton rbShowTime1 = new RadioButton("11:00 AM");
        rbShowTime1.setUserData("11:00 AM");
        RadioButton rbShowTime2 = new RadioButton("01:30 PM");
        rbShowTime2.setUserData("01:30 PM");
        RadioButton rbShowTime3 = new RadioButton("04:00 PM");
        rbShowTime3.setUserData("04:00 PM");
        RadioButton rbShowTime4 = new RadioButton("06:30 PM");
        rbShowTime4.setUserData("06:30 PM");
        RadioButton rbShowTime5 = new RadioButton("09:00 PM");
        rbShowTime5.setUserData("09:00 PM");

        ToggleGroup tgTime = new ToggleGroup();
        rbShowTime1.setToggleGroup(tgTime);
        rbShowTime2.setToggleGroup(tgTime);
        rbShowTime3.setToggleGroup(tgTime);
        rbShowTime4.setToggleGroup(tgTime);
        rbShowTime5.setToggleGroup(tgTime);

        VBox rdVBox = new VBox(rbShowTime1,rbShowTime2,rbShowTime3,rbShowTime4,rbShowTime5);
        layout.add(rdVBox, 1,2,1,1);

        //Seats
        Label seats = new Label("Seats");
        seatsChosen = new TextField();
        seatsChosen.setPromptText("Enter the seats you want... the format should be \"A7, A8\" (e.g., Row A, Seat 7 and Seat 8)");
        layout.add(seatsChosen, 1,3,3,1);

        //Food & Beverages
        int col = 1;
        String[] popcornName= {"Royal Popcorn Combo - Member Special", "Royal Popcorn", "Royal Popcorn Combo"};

        Label fnB = new Label("Food & Beverages");
        for(int i=1; i<4; i++){
            Image popcorn = new Image(MovieTicketingSystem.class.getResource("popcorn"+i+".png").toString());
            ImageView ivpopcorn = new ImageView(popcorn);
            ivpopcorn.setFitWidth(300);
            ivpopcorn.setFitHeight(200);
            layout.add(ivpopcorn, col, 4, 1,1);
            Text txt = new Text(popcornName[i-1]);
            layout.add(txt, col, 5, 1,1);
            if(col<4){
                col++;
            }
        }

        RadioButton price1 = new RadioButton("RM 19.90");
        price1.setUserData("Royal Popcorn Combo - Member Special");
        layout.add(price1, 1,6,1,1);
        RadioButton price2 = new RadioButton("RM 17.90");
        price2.setUserData("Royal Popcorn");
        layout.add(price2, 2,6,1,1);
        RadioButton price3 = new RadioButton("RM 21.90");
        price3.setUserData("Royal Popcorn Combo");
        layout.add(price3, 3,6,1,1);
        ToggleGroup tgPrice = new ToggleGroup();
        price1.setToggleGroup(tgPrice);
        price2.setToggleGroup(tgPrice);
        price3.setToggleGroup(tgPrice);

        //Submit Button
        btnSubmit = new Button("Submit");
        btnSubmit.setPrefWidth(120);

        btnSubmit.setOnAction(e -> {
            try{
                double total = 0;
                String temp = "You selected ";
                errorMessage = "";
                boolean validate = true;


                String name = movieName.getText();
                if(name.trim().isEmpty()) {
                    errorMessage += "Please enter a movie name!\n";
                    validate = false;
                }
                else
                    temp += name + " with ";

                String userChoseExperience = experienceCategory.getValue();
                if(userChoseExperience == null) {
                    errorMessage += "Please choose an experience!\n";
                    validate = false;
                }
                else
                    temp += userChoseExperience + " experience at ";

                if(tgTime.getSelectedToggle() == null) {
                    errorMessage += "Please choose a time!\n";
                    validate = false;
                }
                else {
                    temp += tgTime.getSelectedToggle().getUserData().toString() + " with ";
                }

                String regex = "[A-G][0-9]";
                String seatInput = seatsChosen.getText();
                if (seatInput.isEmpty()) {
                    errorMessage += "Please enter seats!\n";
                    validate = false;
                } else{
                    seat = seatInput.split(",");
                    for(String sea:seat) {
                        if (!sea.trim().matches(regex)) {
                            errorMessage += "Incorrect format! Please re-enter seats!\n";
                            validate = false;
                            break;
                        }
                    }
                    temp += seat.length + " seat(s) and a ";
                }




                if(tgPrice.getSelectedToggle() == null) {
                    errorMessage += "Please choose a set!\n";
                    validate = false;
                }
                else
                    temp += tgPrice.getSelectedToggle().getUserData().toString();

                if(price1.isSelected()){
                    total += 19.90;
                } else if (price2.isSelected()) {
                    total += 17.90;
                } else if (price3.isSelected()) {
                    total += 21.90;
                }

                for(int i=0; i<10; i++){
                    if(userChoseExperience.equals(experienceName[i])) {
                        total += experiencePrice[i] * seat.length;
                    }
                }

                String totalPrice = String.format("%.2f", total);
                temp += ".\n The total is RM " + totalPrice;

                if(validate == false){
                    throw new Exception();
                } else{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Thank You!");
                    alert.setContentText(temp);
                    alert.showAndWait();
                }
            }catch (Exception ex){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText(errorMessage);
                alert.showAndWait();
            }
        });
        HBox hbBtn = new HBox(btnSubmit);
        hbBtn.setAlignment(Pos.BASELINE_RIGHT);
        layout.add(hbBtn, 3,7,1,1);


        layout.addColumn(0, movie, experience,session,seats,fnB);
        layout.setPadding(new Insets(10));
        layout.setHgap(10);
        layout.setVgap(10);

        Scene scene = new Scene(layout);
        stage.setTitle("Movie Ticketing System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}