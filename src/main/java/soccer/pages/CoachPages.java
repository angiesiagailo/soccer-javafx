package soccer.pages;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import soccer.DB;
import soccer.MainFrame;
import soccer.model.Coach;
import soccer.model.League;

public class CoachPages{

    public static void showCoachListPage (Stage stage) {       
        ArrayList<Coach> coaches = DB.loadCoaches();
        
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(20);
        gp.setVgap(10);

        Label idHeading = new Label("Id");
        idHeading.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(idHeading, 0, 0);

        Label FirstNameHeader = new Label("First Name");
        FirstNameHeader.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(FirstNameHeader, 1, 0);

        Label LastNameHeader = new Label("Last Name");
        LastNameHeader.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(LastNameHeader, 2, 0);

        Label PhoneHeader = new Label("Phone");
        PhoneHeader.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(PhoneHeader, 3, 0);

        Label EmailHeader = new Label("Email");
        EmailHeader.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(EmailHeader, 4, 0);

        for (int i=0; i<coaches.size(); i++) {
            final int coachId = coaches.get(i).getCoachId();

            Button btModify = new Button("Modify");
            btModify.setOnAction(e -> {
                showModifyCoach(stage, coachId);
            });

            Button btDelete = new Button("Delete");
            btDelete.setOnAction(e-> {
                DB.deleteCoach(coachId);
                showCoachListPage(stage);
            });

            Coach coach = coaches.get(i);

            Label idBody = new Label("" + coach.getCoachId());
            idBody.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(idBody, 0, i+1);

            Label firstName = new Label("" + coach.getFirstName());
            firstName.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(firstName, 1, i+1);

            Label lastName = new Label("" + coach.getLastName());
            lastName.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(lastName, 2, i+1);

            Label phone = new Label("" + coach.getPhone());
            phone.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(phone, 3, i+1);

            Label email = new Label("" + coach.getEmail());
            email.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(email, 4, i+1);

            gp.add(btModify, 5, i+1);
            gp.add(btDelete, 6, i+1);
        }

        Button btAddCoach = new Button("Add New Coach");
        btAddCoach.setOnAction(e -> showAddCoach(stage));

        Button btMainMenu = new Button("Main Menu");
        btMainMenu.setOnAction(e -> MainFrame.loadMenu(stage));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(30,0,0,0));
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(btAddCoach, btMainMenu);

        Label pageTitle = new Label("Coaches");
        pageTitle.setFont(MainFrame.PAGE_HEADING_FONT);
        pageTitle.setPadding(new Insets(0,0,30,0));

        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);
        vb.getChildren().addAll(pageTitle, gp, hbox);


        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.show();
    }


    public static void showAddCoach (Stage stage) {       
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(20);
        gp.setVgap(10);

        Label labelFirstName = new Label("First Name");
        labelFirstName.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(labelFirstName, 0, 0);

        TextField txtFirstName = new TextField();
        gp.add(txtFirstName, 1, 0);

        Label labelLastName = new Label("Last Name");
        labelLastName.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(labelLastName, 0, 1);

        TextField txtLastName = new TextField();
        gp.add(txtLastName, 1, 1);

        Label labelPhone = new Label("Phone");
        labelPhone.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(labelPhone, 0, 2);

        TextField txtPhone = new TextField();
        gp.add(txtPhone, 1, 2);

        Label labelEmail = new Label("Email");
        labelEmail.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(labelEmail, 0, 3);

        TextField txtEmail = new TextField();
        gp.add(txtEmail, 1, 3);

        Button btAddCoach = new Button("Add");
        btAddCoach.setOnAction(e -> {
            DB.insertCoach(txtFirstName.getText(), txtLastName.getText(), txtPhone.getText(), txtEmail.getText());
            showCoachListPage(stage);
        });
        
        Button btMainMenu = new Button("Cancel");
        btMainMenu.setOnAction(e -> showCoachListPage(stage));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(30,0,0,0));
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(btAddCoach, btMainMenu);

        Label pageTitle = new Label("Add Coach");
        pageTitle.setFont(MainFrame.PAGE_HEADING_FONT);
        pageTitle.setPadding(new Insets(0,0,30,0));

        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);
        vb.getChildren().addAll(pageTitle, gp, hbox);

        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.show();
    }


    public static void showModifyCoach (Stage stage, int coachId) {       
        Coach coach = DB.loadCoach(coachId);

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(20);
        gp.setVgap(10);

        Label labelCoachIdHeading = new Label("Id");
        labelCoachIdHeading.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(labelCoachIdHeading, 0, 0);

        Label labelCoachId = new Label("" + coachId);
        labelCoachId.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(labelCoachId, 1, 0);

        Label labelFirstName = new Label("First Name");
        labelFirstName.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(labelFirstName, 0, 1);

        TextField txtFirstName = new TextField(coach.getFirstName());
        txtFirstName.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(txtFirstName, 1, 1);

        Label labelLastName = new Label("Last Name");
        labelLastName.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(labelLastName, 0, 2);

        TextField txtLastName = new TextField(coach.getLastName());
        txtLastName.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(txtLastName, 1, 2);

        Label labelPhone = new Label("Phone");
        labelPhone.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(labelPhone, 0, 3);

        TextField txtPhone = new TextField(coach.getPhone());
        txtPhone.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(txtPhone, 1, 3);

        Label labelEmail = new Label("Email");
        labelEmail.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(labelEmail, 0, 4);

        TextField txtEmail = new TextField(coach.getEmail());
        txtEmail.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(txtEmail, 1, 4);
//===

        Button btModifyCoach = new Button("Modify");
        btModifyCoach.setOnAction(e -> {
            coach.setFirstName(txtFirstName.getText());
            coach.setLastName(txtLastName.getText());
            coach.setPhone(txtPhone.getText());
            coach.setEmail(txtEmail.getText());

            DB.updateCoach(coach);
            showCoachListPage(stage);
        });
        
        Button btMainMenu = new Button("Cancel");
        btMainMenu.setOnAction(e -> showCoachListPage(stage));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(30,0,0,0));
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(btModifyCoach, btMainMenu);

        Label pageTitle = new Label("Modify Coach");
        pageTitle.setFont(MainFrame.PAGE_HEADING_FONT);
        pageTitle.setPadding(new Insets(0,0,30,0));

        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);
        vb.getChildren().addAll(pageTitle, gp, hbox);

        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.show();
    }
}
