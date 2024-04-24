package com.inventorymanagementsystem;

import com.inventorymanagementsystem.entity.*;
import com.inventorymanagementsystem.config.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.burningwave.core.assembler.StaticComponentContainer.Modules;

public class DashboardController implements Initializable {
    private double x;
    private double y;


    /////////::client //////:
    @FXML
    private TextField nom_client;
    @FXML
    private TextField prenom_client;
    @FXML
    private TextField mail_client;
    @FXML
    private TextField cin_client;
    @FXML
    private TextField genre_client;
    @FXML
    private TextField pays_client;
    @FXML
    private TextField piece_client;
    @FXML
    private TextField photo_client;

    @FXML
    private TextField tel_client;
    @FXML
    private TextField poste_client;
    @FXML
    private TextField dob_client;
    @FXML
    private TextField adresse_client;
    @FXML
    private TextField signature_client;
    @FXML
    private TextField pass_client;
    //////table client//////
    @FXML
    private TableColumn<?, ?> table_client_nom;
    @FXML
    private TableColumn<?, ?> table_client_id;
    @FXML
    private TableColumn<?, ?> table_client_cin;

    @FXML
    private TableColumn<?, ?> table_client_tel;

    @FXML
    private TableColumn<?, ?> table_client_mail;
    @FXML
    private TableView<Client> client_table;
    /////////////////////////////////////////credit////////////////////////////////////////////////
    @FXML
    private Button add_credit_btn;
    @FXML
    public TextField apport_credit;
    @FXML
    private Button btn_delete_credit;
    @FXML
    private TextField credit_search;
    @FXML
    private Button cust_btn_editcredit;
    @FXML
    private Button cust_btn_printcredit;
    @FXML
    public DatePicker date_credit;
    @FXML
    public TextField ribtf;
    @FXML
    private TextField idcredit;
    @FXML
    private TextField rechtf;
    @FXML
    private Label decheance_credit;
    @FXML
    public ComboBox duree_credit;
    @FXML
    public ComboBox dateEch;
    @FXML
    public TextField mecheance_credit;
    @FXML
    public TextField montant_credit;
    @FXML
    public TextField revenu_credit;
    @FXML
    private TableView<Client> credit_credit;
    @FXML
    private TableColumn<Client, String> table_credit_nom;
    @FXML
    private TableColumn<Client, String> table_credit_prenom;
    @FXML
    private TableColumn<Client, String> table_credit_RIB;
    @FXML
    public TextField taux_credit;
    @FXML
    public ComboBox type_credit;
    @FXML
    private GridPane CredContainer;
    @FXML
    private GridPane EchContainer;
    private ObservableList<Credit> addCreditList;
    /*                  GESTION CREDIT                     */

    ObservableList<String> DureeCred = FXCollections.observableArrayList("12 mois (1 année)", "24 mois (2 années)", "36 mois (3 années)", "48 mois (4 années)", "60 mois (5 années)", "84 mois (7 années)");
    ObservableList<String> Types = FXCollections.observableArrayList("Crédit commercial", "Crédit agricole", "Crédit automobile", "Crédit à la consommation", "Crédit hypothécaire", "Crédit étudiant");
    ObservableList<String> dateEche = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25");

    int ID;
    String nom;
    private String typecreditvar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Exports all modules to other modules
        Modules.exportAllToAll();
        fetchAllClients();
        LoadCredits();
        LoadEchs();
        setUsername();
        activateDashboard();
        //client pane
        showTableClient();
        duree_credit.setItems(DureeCred);
        type_credit.setItems(Types);
        dateEch.setItems(dateEche);

    }

    public void Refresh2() {
        EchContainer.getChildren().clear();
        LoadEchs();
    }

    public List<Echeance> afficherEch() {
        List<Echeance> echs = new ArrayList<>();
        Connection cnx = Database.getInstance().connectDB();
        String sql = "SELECT `id`, `id_credit_id`, `mode_paiement`, `etat`, `date` FROM `echeance` WHERE `etat` LIKE 'en cours'";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Echeance ech = new Echeance();
                ech.setId(rs.getInt("id"));
                ech.setIdCredit(rs.getInt("id_credit_id"));
                ech.setModePyament(rs.getString("mode_paiement"));
                ech.setEtat(rs.getString("etat"));
                ech.setDate(rs.getDate("date"));
                echs.add(ech);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return echs;
    }

    public List<Credit> afficher() {
        List<Credit> credits = new ArrayList<>();
        Connection cnx = Database.getInstance().connectDB();
        String sql = "SELECT `id`, `id_compte_id`, `montant`, `taux`, `duree`, `date_debut`, `type`, `apport_propre`, `revenu_propre`, `montant_echeance`, `date_echeance`, `etat`, `nb_echeances_restants` FROM `credit`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Credit credit = new Credit();
                credit.setIdcredit(rs.getInt("id"));
                credit.setId(rs.getInt("id_compte_id"));
                credit.setMontant(rs.getFloat("montant"));
                credit.setTaux(rs.getFloat("taux"));
                credit.setDure(rs.getInt("duree"));
                credit.setDate_debut(rs.getDate("date_debut"));
                credit.setType(rs.getString("type"));
                credit.setApport_propre(rs.getFloat("apport_propre"));
                credit.setRevenu_propre(rs.getFloat("revenu_propre"));
                credit.setMontant_echeance(rs.getFloat("montant_echeance"));
                credit.setDate_echeance(rs.getInt("date_echeance"));
                credit.setEtat(rs.getString("etat"));
                credit.setNb_echeances_restants(rs.getInt("nb_echeances_restants"));
                credits.add(credit);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return credits;
    }

    public List<Credit> Rechreche(String recherche) {
        Connection cnx = Database.getInstance().connectDB();
        List<Credit> credits = new ArrayList<>();
        String sql = "SELECT * FROM `credit` WHERE `montant` LIKE '%" + recherche + "%' OR `etat` LIKE '%" + recherche + "%'";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Credit credit = new Credit();
                credit.setIdcredit(rs.getInt("id"));
                credit.setId(rs.getInt("id_compte_id"));
                credit.setMontant(rs.getFloat("montant"));
                credit.setTaux(rs.getFloat("taux"));
                credit.setDure(rs.getInt("duree"));
                credit.setDate_debut(rs.getDate("date_debut"));
                credit.setType(rs.getString("type"));
                credit.setApport_propre(rs.getFloat("apport_propre"));
                credit.setRevenu_propre(rs.getFloat("revenu_propre"));
                credit.setMontant_echeance(rs.getFloat("montant_echeance"));
                credit.setDate_echeance(rs.getInt("date_echeance"));
                credit.setEtat(rs.getString("etat"));
                credit.setNb_echeances_restants(rs.getInt("nb_echeances_restants"));
                credits.add(credit);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return credits;
    }

    public void LoadEchs() {
        int column = 0;
        int row = 1;
        try {
            for (Echeance ech : afficherEch()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("EchCredit.fxml"));
                Pane userBox = fxmlLoader.load();
                EchCardController ECC = fxmlLoader.getController();
                ECC.setData(ech);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                EchContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadCredits() {
        int column = 0;
        int row = 1;
        try {
            for (Credit credit : afficher()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("CardCredit.fxml"));
                Pane userBox = fxmlLoader.load();
                CardCreditController CCC = fxmlLoader.getController();
                CCC.setData(credit);
                if (column == 2) {
                    column = 0;
                    ++row;
                }
                CredContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchAllClients() {
        ObservableList<Client> clientsList = FXCollections.observableArrayList();
        Connection cnx = Database.getInstance().connectDB();
        try {
            String sql1 = "SELECT `id`, `nom`, `prenom` FROM `client`";
            Statement stm1 = cnx.createStatement();
            ResultSet rs = stm1.executeQuery(sql1);
            while (rs.next()) {
                int IDCLIENT = rs.getInt("id");
                String NOMCLIENT = rs.getString("nom");
                String PRENOMCLIENT = rs.getString("prenom");
                String sql2 = "SELECT * FROM `compte` WHERE `id_user_id` LIKE '%" + IDCLIENT + "%'";
                Statement stm2 = cnx.createStatement();
                ResultSet rs2 = stm2.executeQuery(sql2);
                while (rs2.next()) {
                    String RIB = rs2.getString("rib");
                    clientsList.add(new Client(NOMCLIENT, PRENOMCLIENT, RIB));
                    table_credit_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                    table_credit_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                    table_credit_RIB.setCellValueFactory(new PropertyValueFactory<>("rib"));
                    credit_credit.setItems(clientsList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectClientrib() {
        Client SelectedClient = credit_credit.getSelectionModel().getSelectedItem();
        if (SelectedClient != null) {
            ribtf.setText(String.valueOf(SelectedClient.getRib()));
        }
    }

    public void Refresh() {
        CredContainer.getChildren().clear();
        LoadCredits();
    }

    public void AjouterCredit() {
        if (ribtf.getText().isBlank() || DureeCred.get(duree_credit.getSelectionModel().getSelectedIndex()).isBlank() || type_credit.equals(null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Contorle de saisir");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir les données obligatoires");
            alert.showAndWait();
            return;
        }
        // el ! kbal l condition bch tkoul ken MECH .match kharajli l eeruer
        if (!revenu_credit.getText().matches("\\d+") || !apport_credit.getText().matches("\\d+") || !montant_credit.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Contorle de saisir");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir les données Int or Float");
            alert.showAndWait();
            return;
        }
        String RIB = ribtf.getText();
        int selectedIndex = duree_credit.getSelectionModel().getSelectedIndex(); // Get the index of the selected item
        String selectedDuration = DureeCred.get(selectedIndex); // Get the selected duration string
        int DUREE = Integer.parseInt(selectedDuration.split(" ")[0]); // Extract the numerical value
        String TYPE = typecreditvar; //(String) type_credit.getValue();
        Float APPORT = Float.parseFloat(apport_credit.getText());
        Float MONTANT = Float.parseFloat(montant_credit.getText());
        Date DATE = Date.valueOf(date_credit.getValue());
        Float TAUX = Float.parseFloat(taux_credit.getText());
        Float REVENU = Float.parseFloat(revenu_credit.getText());
        int DATE_ECH = Integer.parseInt((String) dateEch.getValue());
        Float MontantTotal = (MONTANT - APPORT) * (1 + TAUX);
        Float MONT_ECH = MontantTotal / DUREE;
        System.out.println(MONT_ECH);
        mecheance_credit.setText(String.valueOf(MONT_ECH)); //Afficher le montant calculer fel textfield

        Connection connection = Database.getInstance().connectDB();
        PreparedStatement preparedStatement = null;

            try {
                String sql0 = "SELECT * FROM `compte` WHERE `rib` LIKE '%" + RIB + "%'";
                Statement stm0 = connection.createStatement();
                ResultSet rs0 = stm0.executeQuery(sql0);
                while (rs0.next()) {
                    String IDCOMPTE = rs0.getString("id");
                    String sql = "INSERT INTO `credit`(`id_compte_id`, `montant`, `taux`, `duree`, `date_debut`, `type`, `apport_propre`, `revenu_propre`, `montant_echeance`, `date_echeance`, `etat`, `nb_echeances_restants`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, IDCOMPTE);
                    preparedStatement.setFloat(2, MONTANT);
                    preparedStatement.setFloat(3, TAUX);
                    preparedStatement.setInt(4, DUREE);
                    preparedStatement.setDate(5, DATE);
                    preparedStatement.setString(6, TYPE);
                    preparedStatement.setFloat(7, APPORT);
                    preparedStatement.setFloat(8, REVENU);
                    preparedStatement.setFloat(9, MONT_ECH);
                    preparedStatement.setInt(10, DATE_ECH);
                    preparedStatement.setString(11, "en cours");
                    preparedStatement.setInt(12, DUREE);
                    preparedStatement.executeUpdate();
                    LoadCredits();
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        long idCredit = generatedKeys.getLong(1);
                        for (int i = 0; i < DUREE; i++) {
                            LocalDate echeanceDate = DATE.toLocalDate().minusMonths(i);
                            String echeanceQuery = "INSERT INTO echeance(id_credit_id, mode_paiement, etat, date) VALUES (?, ?, ?, ?)";
                            PreparedStatement echeanceStmt = connection.prepareStatement(echeanceQuery);
                            echeanceStmt.setLong(1, idCredit);
                            echeanceStmt.setString(2, "Your mode of payment"); // Example payment mode
                            echeanceStmt.setString(3, "en cours");
                            echeanceStmt.setDate(4, Date.valueOf(echeanceDate));
                            echeanceStmt.executeUpdate();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Ajout avec success");
                            alert.setHeaderText(null);
                            alert.setContentText("L'ajout du credit sur le RIB :"+RIB+" avec succes");
                            alert.showAndWait();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    public void updateCredit() {
        int IDCREDIT = Integer.parseInt(idcredit.getText());
        String RIB = ribtf.getText();
        int selectedIndex = duree_credit.getSelectionModel().getSelectedIndex(); // Get the index of the selected item
        String selectedDuration = DureeCred.get(selectedIndex); // Get the selected duration string
        int DUREE = Integer.parseInt(selectedDuration.split(" ")[0]); // Extract the numerical value
        String TYPE = typecreditvar; //(String) type_credit.getValue();
        Float APPORT = Float.parseFloat(apport_credit.getText());
        Float MONTANT = Float.parseFloat(montant_credit.getText());
        Date DATE = Date.valueOf(date_credit.getValue());
        Float TAUX = Float.parseFloat(taux_credit.getText());
        Float REVENU = Float.parseFloat(revenu_credit.getText());
        int DATE_ECH = Integer.parseInt((String) dateEch.getValue());
        Float MontantTotal = (MONTANT - APPORT) * (1 + TAUX);
        Float MONT_ECH = MontantTotal / DUREE;
        System.out.println(MONT_ECH);
        mecheance_credit.setText(String.valueOf(MONT_ECH)); //Afficher le montant calculer fel textfield
        Connection connection = Database.getInstance().connectDB();
        PreparedStatement preparedStatement = null;
        try {
            String sql0 = "SELECT * FROM `compte` WHERE `rib` LIKE '%" + RIB + "%'";
            Statement stm0 = connection.createStatement();
            ResultSet rs0 = stm0.executeQuery(sql0);
            while (rs0.next()) {
                String IDCOMPTE = rs0.getString("id");
                String sql = "UPDATE `credit` SET `id_compte_id`=?,`montant`=?,`taux`=?,`duree`=?,`date_debut`=?,`type`=?,`apport_propre`=?,`revenu_propre`=?,`montant_echeance`=?,`date_echeance`=?,`etat`=?,`nb_echeances_restants`=? WHERE `id`=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, IDCOMPTE);
                preparedStatement.setFloat(2, MONTANT);
                preparedStatement.setFloat(3, TAUX);
                preparedStatement.setInt(4, DUREE);
                preparedStatement.setDate(5, DATE);
                preparedStatement.setString(6, TYPE);
                preparedStatement.setFloat(7, APPORT);
                preparedStatement.setFloat(8, REVENU);
                preparedStatement.setFloat(9, MONT_ECH);
                preparedStatement.setInt(10, DATE_ECH);
                preparedStatement.setString(11, "en cours");
                preparedStatement.setInt(12, DUREE);
                preparedStatement.setInt(13, IDCREDIT);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Recherche() {
        int column = 0;
        int row = 1;
        String recherche = rechtf.getText();
        try {
            CredContainer.getChildren().clear();
            for (Credit credit : Rechreche(recherche)) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("CardCredit.fxml"));
                Pane userBox = fxmlLoader.load();
                CardCreditController CCC = fxmlLoader.getController();
                CCC.setData(credit);
                if (column == 2) {
                    column = 0;
                    ++row;
                }
                CredContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void TypeTaux(ActionEvent event) {
        String TYPE = (String) type_credit.getValue();
        if (TYPE.equals("Crédit commercial")) {
            typecreditvar = "commercial";
            taux_credit.setText("0.09");
        } else if (TYPE.equals("Crédit agricole")) {
            typecreditvar = "agricole";
            taux_credit.setText("0.07");
        } else if (TYPE.equals("Crédit automobile")) {
            typecreditvar = "automobile";
            taux_credit.setText("0.1");
        } else if (TYPE.equals("Crédit à la consommation")) {
            typecreditvar = "consommation";
            taux_credit.setText("0.12");
        } else if (TYPE.equals("Crédit hypothécaire")) {
            typecreditvar = "hypothecaire";
            taux_credit.setText("0.05");
        } else if (TYPE.equals("Crédit étudiant")) {
            typecreditvar = "etudiant";
            taux_credit.setText("0.11");
        }
    }

    ////////////      fin credit                 ////////////////
    public void addClient() {
        if (nom_client.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir les données obligatoires.");
            alert.showAndWait();
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        long cinLong = Long.parseLong(cin_client.getText());
        long telLong = Long.parseLong(tel_client.getText());
        try {
            connection = Database.getInstance().connectDB();
            String sql = "INSERT INTO client (dob, nom, prenom, cin, num_tel, genre, pays, adresse, email, document, signature, profession, date_creation, username, password, last_login, etat, photo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, java.sql.Date.valueOf("2023-11-22"));
            preparedStatement.setString(2, nom_client.getText());
            preparedStatement.setString(3, prenom_client.getText());
            preparedStatement.setLong(4, cinLong);
            preparedStatement.setLong(5, telLong);
            preparedStatement.setString(6, genre_client.getText());
            preparedStatement.setString(7, pays_client.getText());
            preparedStatement.setString(8, adresse_client.getText());
            preparedStatement.setString(9, mail_client.getText());
            preparedStatement.setString(10, "cin");
            preparedStatement.setString(11, signature_client.getText());
            preparedStatement.setString(12, poste_client.getText());
            preparedStatement.setDate(13, java.sql.Date.valueOf("2023-11-22"));
            preparedStatement.setString(14, "username");
            preparedStatement.setString(15, "password");
            preparedStatement.setDate(16, java.sql.Date.valueOf("2023-11-22"));
            preparedStatement.setString(17, "activer");
            preparedStatement.setString(18, "ss");

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Client ajouté avec succès.");
                successAlert.showAndWait();

                ClearClientData();
                showTableClient();

            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Erreur lors de l'ajout du client.");
                errorAlert.showAndWait();
            }
        } catch (Exception e) {
            Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
            exceptionAlert.setTitle("Exception");
            exceptionAlert.setHeaderText(null);
            exceptionAlert.setContentText("Une exception s'est produite lors de l'ajout du client. Veuillez vérifier les données.");
            exceptionAlert.showAndWait();
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<Client> listClients() {
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM CLIENT";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Client clientData = new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("profession"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("photo"),
                        resultSet.getDate("dob"),
                        resultSet.getLong("cin"),
                        resultSet.getString("adresse"),
                        resultSet.getString("etat"),
                        resultSet.getString("username"),
                        resultSet.getLong("num_tel"),
                        resultSet.getString("pays"),
                        resultSet.getString("document")
                );
                clientList.add(clientData); // Ajouter chaque client à la liste
            }

        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientList;
    }


    public void showTableClient() {
        ObservableList<Client> clientList = listClients();
        FXCollections.reverse(clientList); //pour inverser
        table_client_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_client_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        table_client_cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        table_client_tel.setCellValueFactory(new PropertyValueFactory<>("num"));
        table_client_mail.setCellValueFactory(new PropertyValueFactory<>("email"));

        client_table.setItems(clientList);


    }


    public void deleteClient() {
        if (client_table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select client for deletion.");
            alert.showAndWait();
            return;
        }
        connection = Database.getInstance().connectDB();
        String sql = "DELETE FROM CLIENT WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, client_table.getSelectionModel().getSelectedItem().getId());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Client deleted successfully.");
                alert.showAndWait();
                ClearClientData();
                showTableClient();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("No data present in the client table.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }

    public void ClearClientData() {
        nom_client.setText("");
        prenom_client.setText("");
        cin_client.setText("");
        tel_client.setText("");
        mail_client.setText("");
        adresse_client.setText("");
        dob_client.setText("");
        signature_client.setText("");
        photo_client.setText("");
        piece_client.setText("");
        pays_client.setText("");
        poste_client.setText("");
        genre_client.setText("");
        pass_client.setText("");
    }

    public void selectClient() {
        Client selectedClient = client_table.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            // Aucun client sélectionné, rien à faire
            return;
        }

        // Remplir les champs de texte avec les données du client sélectionné
        nom_client.setText(selectedClient.getNom());
        prenom_client.setText(selectedClient.getPrenom());
        mail_client.setText(selectedClient.getEmail());
        cin_client.setText(String.valueOf(selectedClient.getCin()));
        genre_client.setText("homme");
        pays_client.setText(selectedClient.getPays());
        piece_client.setText(selectedClient.getDocument());
        photo_client.setText(selectedClient.getPhoto());
        tel_client.setText(String.valueOf(selectedClient.getNum()));
        poste_client.setText(selectedClient.getPoste());
        dob_client.setText(selectedClient.getDob().toString()); // Modifier en fonction de votre format de date
        adresse_client.setText(selectedClient.getAdresse());
        signature_client.setText(selectedClient.getDocument());
        pass_client.setText(selectedClient.getPassword());
    }

    public void updateClient() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Database.getInstance().connectDB();
            String sql = "UPDATE CLIENT SET nom=?, prenom=?, profession=?, email=?, password=?, photo=?, dob=?, cin=?, adresse=?, num_tel=?, pays=?, document=? WHERE id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nom_client.getText());
            preparedStatement.setString(2, prenom_client.getText());
            preparedStatement.setString(3, poste_client.getText());
            preparedStatement.setString(4, mail_client.getText());
            preparedStatement.setString(5, pass_client.getText());
            preparedStatement.setString(6, photo_client.getText());
            preparedStatement.setDate(7, Date.valueOf(dob_client.getText()));
            preparedStatement.setLong(8, Long.parseLong(cin_client.getText()));
            preparedStatement.setString(9, adresse_client.getText());

            preparedStatement.setLong(10, Long.parseLong(tel_client.getText()));
            preparedStatement.setString(11, pays_client.getText());
            preparedStatement.setString(12, "document/cin");
            preparedStatement.setInt(13, client_table.getSelectionModel().getSelectedItem().getId());

            //  preparedStatement.setInt(12, Integer.parseInt(client_id.getText())); // Supposons que client_id est le champ ID du client à modifier

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Client updated successfully.");
                successAlert.showAndWait();
                ClearClientData();
                showTableClient();
                // Vous pouvez également actualiser ou recharger les données du tableau après la mise à jour
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Message");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Failed to update client. Please check the provided data.");
                errorAlert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void printPdfClient() {
        connection = Database.getInstance().connectDB();
        String sql = "SELECT id, nom, num_tel FROM client";
        ;
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/customers.jrxml"));
            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(sql);
            jasperDesign.setQuery(updateQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @FXML
    private void searchClient() {
        String searchText = client_search.getText().trim();

        // Vérifier si le champ de recherche n'est pas vide
        if (!searchText.isEmpty()) {
            // Créer un filtre pour rechercher dans la table
            FilteredList<Client> filteredData = new FilteredList<>(listClients(), p -> true);

            // Appliquer le filtre en fonction du texte de recherche
            filteredData.setPredicate(client -> {
                // Convertir l'ID en String pour la comparaison
                String idString = Integer.toString(client.getId());

                // Convertir le numéro de téléphone en String pour la comparaison
                String numTelString = Long.toString(client.getNum());

                // Convertir le texte de recherche en minuscules pour une comparaison insensible à la casse
                String lowerCaseFilter = searchText.toLowerCase();

                if (idString.contains(lowerCaseFilter)) {
                    return true; // Correspondance de l'ID
                } else if (client.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Correspondance du nom
                } else if (numTelString.contains(lowerCaseFilter)) {
                    return true; // Correspondance du numéro de téléphone
                }
                return false; // Aucune correspondance
            });

            // Mettre à jour la TableView avec la liste filtrée
            client_table.setItems(filteredData);
        } else {
            // Si le champ de recherche est vide, afficher tous les clients
            client_table.setItems(listClients());
        }
    }


    ///////fin client ////////:

    @FXML
    private Button billing_btn;

    @FXML
    private AnchorPane billing_pane;

    @FXML
    private Button customer_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane customer_pane;

    @FXML
    private AnchorPane dasboard_pane;

    @FXML
    private Button purchase_btn;

    @FXML
    private AnchorPane purchase_pane;

    @FXML
    private Button sales_btn;

    @FXML
    private AnchorPane sales_pane;

    @FXML
    private AnchorPane clients_pane;
    @FXML
    private Button clients_btn;
    @FXML
    private Button credits_btn;
    @FXML
    private AnchorPane credits_pane;
    @FXML
    private Label user;

    @FXML
    private Label inv_num;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;


    @FXML
    private TextField bill_item;

    @FXML
    private TextField bill_name;

    @FXML
    private TextField bill_phone;

    @FXML
    private TextField bill_price;

    @FXML
    private Button bill_print;

    @FXML
    private ComboBox<?> bill_quantity;

    @FXML
    private Button bill_save;

    @FXML
    private TextField bill_total_amount;

    @FXML
    private TableView<Billing> billing_table;

    @FXML
    private TextField billing_table_search;

    @FXML
    private Label final_amount;

    private String invoiceList[] = {"BX123456", "ZX123456", "AX123456"};

    private String quantityList[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @FXML
    private TextField cust_field_name;

    @FXML
    private TextField cust_field_phone;

    @FXML
    private TextField client_search;

    @FXML
    private Button signout_btn;

    List<Product> productsList;

    public void onExit() {
        System.exit(0);
    }

    public void activateAnchorPane() {
        clients_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(false);
            clients_pane.setVisible(true);
            credits_pane.setVisible(false);
            credits_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

            clients_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");


        });
        credits_btn.setOnMouseClicked(mouseEvent -> {
            credits_pane.setVisible(true);
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(false);
            clients_pane.setVisible(false);

            credits_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");

            clients_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

        });

        dashboard_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(true);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(false);
            clients_pane.setVisible(false);
            credits_pane.setVisible(false);
            credits_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

            clients_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

        });
        billing_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(true);
            customer_pane.setVisible(false);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(false);
            clients_pane.setVisible(false);
            credits_pane.setVisible(false);
            credits_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

            clients_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

        });
        customer_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(true);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(false);
            clients_pane.setVisible(false);
            credits_pane.setVisible(false);
            credits_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

            clients_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");


        });
        sales_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            sales_pane.setVisible(true);
            purchase_pane.setVisible(false);
            clients_pane.setVisible(false);
            credits_pane.setVisible(false);
            credits_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

            clients_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

        });
        purchase_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(true);
            clients_pane.setVisible(false);
            credits_pane.setVisible(false);
            credits_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

            clients_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");

        });


    }

    public void setUsername() {
        user.setText(User.name.toUpperCase());
    }

    public void activateDashboard() {
        dasboard_pane.setVisible(true);
        billing_pane.setVisible(false);
        customer_pane.setVisible(false);
        sales_pane.setVisible(false);
        purchase_pane.setVisible(false);
        clients_pane.setVisible(false);
        credits_pane.setVisible(false);
    }

    public void signOut() {
        signout_btn.getScene().getWindow().hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            root.setOnMousePressed((event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged((event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }

    }


}
