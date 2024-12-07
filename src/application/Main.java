package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {

    private Rule[] ruleBase;
    private ArrayList<String> initialFacts;
    private TextArea proofStepsTextArea;
    private TextField inputTextField;

    @Override
    public void start(Stage primaryStage) {
        // Initialize the rule base and initial facts
        initializeRuleBase();
        initialFacts = new ArrayList<>(Arrays.asList("D", "O", "G"));

        Label titleLabel = new Label("Expert System Interface");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label inputLabel = new Label("Enter a fact to prove (type 'exit' to quit):");
        inputLabel.setStyle("-fx-font-size: 18px;");
        
        inputTextField = new TextField();
        inputTextField.setPrefWidth(300); // Set the preferred width to 300 pixels

        Button proveButton = new Button("Prove");
        proveButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        proveButton.setOnAction(e -> proveFact());

        proofStepsTextArea = new TextArea();
        proofStepsTextArea.setEditable(false);
        proofStepsTextArea.setPrefHeight(150);

        // Layout for input section
        VBox inputVBox = new VBox(5); // Adjust the spacing here
        inputVBox.getChildren().addAll(inputLabel, inputTextField, proveButton);
        inputVBox.setAlignment(Pos.CENTER);

        // Layout for the entire scene
        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(10));
        root.setCenter(inputVBox);
        root.setBottom(proofStepsTextArea);
        BorderPane.setMargin(proofStepsTextArea, new Insets(10));

        // Create the scene
        Scene scene = new Scene(root, 400, 400);

        // Load the CSS file for styling
        String cssFile = getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(cssFile);

        // Set up the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Forward Chaining Expert System");
        primaryStage.show();
    }



    private void initializeRuleBase() {
        // Define rules and add them to the rule base
        ruleBase = new Rule[9];
        ArrayList<String> P, C;

        P = new ArrayList<>(Arrays.asList("A", "B"));
        C = new ArrayList<>(Arrays.asList("F"));
        ruleBase[0] = new Rule(0, P, C);

        P = new ArrayList<>(Arrays.asList("F", "H"));
        C = new ArrayList<>(Arrays.asList("I"));
        ruleBase[1] = new Rule(1, P, C);

        P = new ArrayList<>(Arrays.asList("D", "H", "G"));
        C = new ArrayList<>(Arrays.asList("A"));
        ruleBase[2] = new Rule(2, P, C);

        P = new ArrayList<>(Arrays.asList("O", "G"));
        C = new ArrayList<>(Arrays.asList("H"));
        ruleBase[3] = new Rule(3, P, C);

        P = new ArrayList<>(Arrays.asList("E", "H"));
        C = new ArrayList<>(Arrays.asList("B"));
        ruleBase[4] = new Rule(4, P, C);

        P = new ArrayList<>(Arrays.asList("G", "A"));
        C = new ArrayList<>(Arrays.asList("B"));
        ruleBase[5] = new Rule(5, P, C);

        P = new ArrayList<>(Arrays.asList("G", "H"));
        C = new ArrayList<>(Arrays.asList("P"));
        ruleBase[6] = new Rule(6, P, C);

        P = new ArrayList<>(Arrays.asList("G", "H"));
        C = new ArrayList<>(Arrays.asList("O"));
        ruleBase[7] = new Rule(7, P, C);

        P = new ArrayList<>(Arrays.asList("D", "O", "G"));
        C = new ArrayList<>(Arrays.asList("J"));
        ruleBase[8] = new Rule(8, P, C);
    }

    private void proveFact() {
        String input = inputTextField.getText().trim();
        if (input.equalsIgnoreCase("exit")) {
            proofStepsTextArea.appendText("Exiting Expert System Interface. Goodbye!\n");
            return;
        }

        ArrayList<String> facts = new ArrayList<>(initialFacts);
        String result = ForwardChain.forwardChain(ruleBase, facts, new ArrayList<>(Arrays.asList(input)));

        proofStepsTextArea.appendText(result + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
