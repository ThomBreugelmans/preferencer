package com.thombreugelmans;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    private List<Profile> profileList;
    private List<Topic> topicList;

    @FXML
    private ListView topics;
    @FXML
    private ListView profiles;
    @FXML
    private TextField maxPreferences;
    @FXML
    private GridPane result;
    @FXML
    private Text score;

    public Controller() {
        this.profileList = new ArrayList<>();
        this.topicList = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        this.maxPreferences.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()) {
                char c = this.maxPreferences.getText().charAt(oldValue.intValue());
                /** Check if the new character is the number or other's */
                if (c > '9' || c < '0') {
                    /** if it's not number then just setText to previous one */
                    this.maxPreferences.setText(this.maxPreferences.getText().substring(0, this.maxPreferences.getText().length() - 1));
                }
                int maxPrefs = Integer.parseInt(this.maxPreferences.getText());
                Preferencer.getInstance().setAmountOfPreferences(maxPrefs);
            }
        });
    }

    @FXML
    private void addProfile() {
        Dialog dialog = new Dialog();
        dialog.setTitle("Add Profile");

        GridPane grid = new GridPane();
        grid.add(new Label("Name:"), 1, 1);
        TextField nameField = new TextField();
        grid.add(nameField, 2, 1);
        grid.add(new Label("Preferences:"), 1, 2);
        for (int i = 0; i < Preferencer.getInstance().getAmountOfPreferences(); i++) {
            ChoiceBox box = new ChoiceBox();
            for (Topic t : this.topicList) {
                box.getItems().add(t.getName());
            }
            grid.add(box, 2, i + 2);
        }

        dialog.getDialogPane().setContent(grid);

        ButtonType bt = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(bt);

        dialog.setResultConverter(new Callback<ButtonType, Pair<String, List<Topic>>>() {
            @Override
            public Pair<String, List<Topic>> call(ButtonType b) {
                if (b != bt) return null;
                List<String> chosenTopicStrings = grid.getChildren().stream()
                        .filter(n -> n instanceof ChoiceBox)
                        .map(cb -> (String) ((ChoiceBox) cb).getSelectionModel().getSelectedItem())
                        .collect(Collectors.toList());
                List<Topic> chosenTopics = new ArrayList<>();
                for (String t : chosenTopicStrings) {
                    for (Topic top : topicList) {
                        if (top.getName().equals(t)) {
                            chosenTopics.add(top);
                            break;
                        }
                    }
                }
                String name = nameField.getText();
                return new Pair<>(name, chosenTopics);
            }
        });

        Optional<Pair<String, List<Topic>>> profile = dialog.showAndWait();
        if (profile.isPresent()) {
            Profile p = Preferencer.getInstance().addProfile(profile.get().getKey(), profile.get().getValue());
            this.profileList.add(p);
            Text pField = new Text(p.getName());
            pField.setId("" + pField.getId());
            this.profiles.getItems().add(pField);
        }
    }
    @FXML
    private void removeProfile() {
        Text profileField = (Text) this.profiles.getSelectionModel().getSelectedItem();
        if (profileField == null) return;
        Profile p = Preferencer.getInstance().getProfileById(Integer.parseInt(profileField.getId()));
        Preferencer.getInstance().removeProfile(p);
        this.profileList.remove(p);
        this.profiles.getItems().remove(profileField);
    }

    @FXML
    private void addTopic() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add topic");
        dialog.setHeaderText("Give the new topic:");
        Optional<String> topic = dialog.showAndWait();

        if (topic.isPresent()) {
            Topic t = Preferencer.getInstance().addTopic(topic.get());
            this.topicList.add(t);

            Text topicField = new Text(t.getName());
            topicField.setId("" + t.getId());
            this.topics.getItems().add(topicField);
        }
    }
    @FXML
    private void removeTopic() {
        Text topicField = (Text) this.topics.getSelectionModel().getSelectedItem();
        if (topicField == null) return;
        Topic t = Preferencer.getInstance().getTopicById(Integer.parseInt(topicField.getId()));
        Preferencer.getInstance().removeTopic(t);
        this.topicList.remove(t);
        this.topics.getItems().remove(topicField);
    }

    @FXML
    private void startComputingPreferences() {
        this.result.getChildren().clear();
        Map<Profile, Topic> matches = Preferencer.getInstance().computeOptimalPreferences();

        int score = 0;
        int i = 1;
        for (Profile p : matches.keySet()) {
            VBox match = new VBox();
            Text name = new Text(p.getName());
            Text topic = new Text(matches.get(p).getName());
            match.getChildren().addAll(name, topic);
            this.result.add(match, i, 1);
            i++;

            score += p.getPreferences().indexOf(matches.get(p));
        }
        this.score.setText("Score: " + score);
    }

}
