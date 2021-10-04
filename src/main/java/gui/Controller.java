package gui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Controller {

    private Model model;
    private final FileChooser fileChooser = new FileChooser();
    private Stage primaryStage;
    private Boolean bool;
    private ArrayList<ArrayList<Integer>> knf;
    private Thread thread, thread2, thread3;
    private String knfString, anzClauses, anzLiterals, path;



    //Elemente aus der FXML-Datei:
    @FXML
    private Label ausgabeCNF, ergebnis, rekSteps, literalLabel;
    @FXML
    private Label pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, pos9, pos10, pos11, pos12;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button runButton, stopButton, importCNF;
    @FXML
    private ToggleButton pauseButton;
    @FXML
    private ToggleGroup modeGroup;
    @FXML
    private RadioButton turboToggle, explainToggle;
    @FXML
    private TextArea infoArea;
    @FXML
    private Slider timeSlider;

    @FXML
    public void initialize(Stage primaryStage){
        this.primaryStage = primaryStage;

        modeGroup.selectedToggleProperty().addListener(e -> {
            if(turboToggle.isSelected()){
                SatSolverRekursiv.delayMode = false;
                timeSlider.setDisable(true);
            } else{
                SatSolverRekursiv.delayMode = true;
                timeSlider.setDisable(false);
            }
        });

        timeSlider.valueProperty().addListener(e -> {
            SatSolverRekursiv.delayTime = (long) timeSlider.getValue() * 1000;
        });
    }

    public void setModel(Model m){
        this.model = m;
    }

    public void setInfoArea(){
        infoArea.setWrapText(true);
        knfString = model.getKnfString();
        anzClauses = model.getAnzClauses();
        anzLiterals = model.getAnzLiterals();

        infoArea.setText("Anzahl der Klauseln: " + anzClauses + "\n" + "Anzahl der Literale: " + anzLiterals + "\n" + "\n" + "Klauselnormalform: " + "\n" + knfString);
    }

    public void runStateGUI() {
        stopButton.setDisable(false);
        pauseButton.setDisable(false);
        runButton.setDisable(true);
        importCNF.setDisable(true);
        ergebnis.setText("");
        model.resetLiteralList();
        setInfoArea();
        SatSolverRekursiv.rek_counter = 0;
        SatSolverRekursiv.position = 0;
    }

    public void stopStateGUI() {
        runButton.setDisable(false);
        importCNF.setDisable(false);
        stopButton.setDisable(true);
        pauseButton.setDisable(true);
        SatSolverRekursiv.rek_counter = 0;
        SatSolverRekursiv.position = 0;
        progressBar.progressProperty().unbind();
        progressBar.setProgress(1);
    }

    public boolean checkKNF() throws Exception {
        if(model.knfSatisfiable(knf)) {
            return true;
        } else {
            return false;
        }
    }

    public void handleImportButton() throws Exception {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CNF-Dateien (*.cnf)", "*.cnf");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            infoArea.clear();
            path = file.getAbsolutePath();
            String s = file.getName();
            ausgabeCNF.setText(s);
            runButton.setDisable(false);
            knf = model.getKNF(path);
            setInfoArea();
        } else {
            ausgabeCNF.setText(null);
        }
    }

   public void handleRunButton() throws Exception {
            runStateGUI();

            Task solver = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    if (checkKNF()) {
                        bool = true;
                    } else {
                        bool = false;
                    }

                    updateProgress(1, 1);

                    // TODO Codierung bzgl. Sonderzeichen checken
                    Platform.runLater(new Runnable() {
                        public void run() {
                            if (bool) {
                                ergebnis.setText("Erf\u00fcllbar");
                            } else {
                                ergebnis.setText("Nicht Erf\u00fcllbar");
                            }

                            runButton.setDisable(false);
                            importCNF.setDisable(false);
                            stopButton.setDisable(true);

                            infoArea.setText(infoArea.getText() + "\n" + "\n" + "Gew\u00e4hlte Literale in richtiger Reihenfolge: " + "\n" + model.getLiteralList());

                        }
                    });

                    return null;
                }

            };

            Task rekCount = new Task<Void>() {
                @Override
                protected Void call() throws Exception {


                    while (thread.isAlive()) {

                        updateMessage(SatSolverRekursiv.rek_counter + "");

                    }
                    updateMessage(SatSolverRekursiv.rek_counter + "");
                    return null;
                }

            };

            Task chosenLiterals = new Task() {
                @Override
                protected Object call() throws Exception {

                    while(thread.isAlive()){
                        if(SatSolverRekursiv.position == 6 || SatSolverRekursiv.position == 9) {
                            updateMessage("Folgendes Literal wurde jetzt gew\u00e4hlt: " + SatSolverRekursiv.literal);
                        }
                    }
                    return null;
                }
            };


            Task label1 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    while (thread.isAlive()) {
                        if (SatSolverRekursiv.position == 1) {
                            updateMessage("o");
                          } else{
                            updateMessage("");
                        }
                    }
                    return null;
                }

            };


            Task label2 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    while (thread.isAlive()) {
                        if (SatSolverRekursiv.position == 2) {
                            updateMessage("o");
                        } else{
                            updateMessage("");
                        }
                    }
                    return null;
                }

            };

            Task label3 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    while (thread.isAlive()) {
                        if (SatSolverRekursiv.position == 3) {
                            updateMessage("o");
                        }else{
                            updateMessage("");
                        }
                    }

                    return null;
                }

            };

            Task label4 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    while (thread.isAlive()) {
                        if (SatSolverRekursiv.position == 4) {
                            updateMessage("o");
                        }
                        if (SatSolverRekursiv.positionEnd == 4) {
                            updateMessage("");
                        }
                    }
                    return null;
                }

            };

            Task label5 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    while (thread.isAlive()) {
                        if (SatSolverRekursiv.position == 5) {
                            updateMessage("o");
                        } else{
                            updateMessage("");
                        }
                    }
                    return null;
                }

            };

            Task label6 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    while (thread.isAlive()) {
                        if (SatSolverRekursiv.position == 6) {
                            updateMessage("o");
                        } else{
                            updateMessage("");
                        }
                    }
                    return null;
                }

            };

            Task label7 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    while (thread.isAlive()) {
                        if (SatSolverRekursiv.position == 7) {
                            updateMessage("o");
                        }
                        if (SatSolverRekursiv.positionEnd == 7) {
                            updateMessage("");
                        }
                    }
                    return null;
                }
            };

            Task label8 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    while (thread.isAlive()) {
                        if (SatSolverRekursiv.position == 8) {
                            updateMessage("o");
                        } else{
                            updateMessage("");
                        }
                    }
                    return null;
                }
            };

            Task label9 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    while (thread.isAlive()) {
                        if (SatSolverRekursiv.position == 9) {
                            updateMessage("o");
                        } else {
                            updateMessage("");
                        }
                    }
                    return null;
                }
            };

            Task label10 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    while (thread.isAlive()) {
                        if (SatSolverRekursiv.position == 10) {
                            updateMessage("o");
                        } else {
                            updateMessage("");
                        }
                    }
                    return null;
                }
            };

            Task label11 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    while (thread.isAlive()) {
                        if (SatSolverRekursiv.position == 11) {
                            updateMessage("o");
                        } else {
                            updateMessage("");
                        }
                    }
                    return null;
                }
            };

            Task label12 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    while (thread.isAlive()) {
                        if (SatSolverRekursiv.position == 12) {
                            updateMessage("o");
                        } else {
                            updateMessage("");
                        }
                    }
                    return null;
                }
            };


            thread = new Thread(solver);
            thread2 = new Thread(rekCount);

            thread3 = new Thread(chosenLiterals);



            Thread labelThread1 = new Thread(label1);
            Thread labelThread2 = new Thread(label2);
            Thread labelThread3 = new Thread(label3);
            Thread labelThread4 = new Thread(label4);
            Thread labelThread5 = new Thread(label5);
            Thread labelThread6 = new Thread(label6);
            Thread labelThread7 = new Thread(label7);
            Thread labelThread8 = new Thread(label8);
            Thread labelThread9 = new Thread(label9);
            Thread labelThread10 = new Thread(label10);
            Thread labelThread11 = new Thread(label11);
            Thread labelThread12 = new Thread(label12);


            thread.start();
            thread2.start();


            if (explainToggle.isSelected()) {
                labelThread1.start();
                labelThread2.start();
                labelThread3.start();
                labelThread4.start();
                labelThread5.start();
                labelThread6.start();
                labelThread7.start();
                labelThread8.start();
                labelThread9.start();
                labelThread10.start();
                labelThread11.start();
                labelThread12.start();
                thread3.start();
            }

            rekSteps.textProperty().bind(rekCount.messageProperty());
            literalLabel.textProperty().bind(chosenLiterals.messageProperty());
            progressBar.progressProperty().bind(solver.progressProperty());

            pos1.textProperty().bind(label1.messageProperty());
            pos2.textProperty().bind(label2.messageProperty());
            pos3.textProperty().bind(label3.messageProperty());
            pos4.textProperty().bind(label4.messageProperty());
            pos5.textProperty().bind(label5.messageProperty());
            pos6.textProperty().bind(label6.messageProperty());
            pos7.textProperty().bind(label7.messageProperty());
            pos8.textProperty().bind(label8.messageProperty());
            pos9.textProperty().bind(label9.messageProperty());
            pos10.textProperty().bind(label10.messageProperty());
            pos11.textProperty().bind(label11.messageProperty());
            pos12.textProperty().bind(label12.messageProperty());

        }


    public void handleStopButton(){
        if(thread.isAlive()){
            thread.stop();
            stopStateGUI();
        }
    }


}
