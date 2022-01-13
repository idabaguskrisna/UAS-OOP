import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.css.converter.InsetsConverter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    Stage windowStage;
    TableView<game> table;
    TableView<game> tableView = new TableView<game>();
    TextField idInput, nama_gameInput, jenis_gameInput, code_gameInput, ukuran_gameInput;

    @Override
    public void start(Stage stage) {

        // Menampilkan nama window
        windowStage = stage;
        windowStage.setTitle("DataBase - List Game");
       
        //Menampilkan tabel
        TableColumn<game, String> columnID = new TableColumn<>("ID");
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<game, String> columnNama_Game = new TableColumn<>("Nama Game");
        columnNama_Game.setCellValueFactory(new PropertyValueFactory<>("nama_game"));

        TableColumn<game, String> columnJenis_Game = new TableColumn<>("Jenis Game");
        columnJenis_Game.setCellValueFactory(new PropertyValueFactory<>("jenis_game"));

        TableColumn<game, String> columnCode_Game = new TableColumn<>("Code Game");
        columnCode_Game.setCellValueFactory(new PropertyValueFactory<>("code_game"));

        TableColumn<game, String> columnUkuran_Game = new TableColumn<>("Ukuran Game");
        columnUkuran_Game.setCellValueFactory(new PropertyValueFactory<>("ukuran_game"));
        
        tableView.getColumns().add(columnID);
        tableView.getColumns().add(columnNama_Game);
        tableView.getColumns().add(columnJenis_Game);
        tableView.getColumns().add(columnCode_Game);
        tableView.getColumns().add(columnUkuran_Game);

        //Input id
        idInput = new TextField();
        idInput.setPromptText("id");
        idInput.setMinWidth(10);

        //Input nama barang
        nama_gameInput = new TextField();
        nama_gameInput.setPromptText("Nama barang");
        nama_gameInput.setMinWidth(20);

        //Input merk barang
        jenis_gameInput = new TextField();
        jenis_gameInput.setPromptText("Merk barang");
        jenis_gameInput.setMinWidth(20);

        //Input seri barang
        code_gameInput = new TextField();
        code_gameInput.setPromptText("Seri barang");
        code_gameInput.setMinWidth(20);

        //Input harga
        ukuran_gameInput = new TextField();
        ukuran_gameInput.setPromptText("Harga");
        ukuran_gameInput.setMinWidth(20);

        //Button
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editButtonClicked());

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateButtonClicked());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(idInput, nama_gameInput, jenis_gameInput, code_gameInput, ukuran_gameInput, editButton, updateButton, deleteButton);

        String url = "jdbc:mysql://localhost:3306/list_game";
        String user = "root";
        String pass = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet record = stmt.executeQuery("select*from tb_game");

            while (record.next()) {
                tableView.getItems().add(new game(record.getInt("id"), record.getString("nama_game"), record.getString("jenis_game"), record.getString("code_game"), record.getString("ukuran_game")));
            }
        }
        catch (SQLException e) {
            System.out.print("koneksi gagal");
        }
        

        VBox vbox = new VBox(tableView);
        vbox.getChildren().addAll(hBox);

        Scene scene = new Scene(vbox);

        stage.setScene(scene);
        stage.show();

    }




    //Update Button Clicked
    private void updateButtonClicked(){

        Database db = new Database();
                try {
                    Statement state = db.conn.createStatement();
                    String sql = "insert into tb_game SET nama_barang='%s', merk_barang='%s', seri_barang='%s', harga='%s'";
                    sql = String.format(sql, nama_gameInput.getText(), jenis_gameInput.getText(), code_gameInput.getText(), ukuran_gameInput.getText());
                    state.execute(sql);
                    // idInput.clear();
                    nama_gameInput.clear();
                    jenis_gameInput.clear();
                    code_gameInput.clear();
                    ukuran_gameInput.clear();
                    loadData();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            
        };


    //Edit Button Clicked
    private void editButtonClicked(){

        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "update tb_game set nama_game = '%s' , jenis_game = '%s', ukuran_game = '%s' WHERE code_game ='%s'";
            sql = String.format(sql, nama_gameInput.getText(), jenis_gameInput.getText(), code_gameInput.getText(), ukuran_gameInput.getText());
            state.execute(sql);
            nama_gameInput.clear();
            jenis_gameInput.clear();
            code_gameInput.clear();
            ukuran_gameInput.clear();
            loadData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Delete button Clicked
    private void deleteButtonClicked(){

        Database db = new Database();
        try{
            Statement state = db.conn.createStatement();
            String sql = "delete from tb_game where code_game ='%s';";
            sql = String.format(sql, code_gameInput.getText());
            state.execute(sql);
            code_gameInput.clear();
            loadData();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    

    public static void main(String[] args) {
        launch();
    }

    private void loadData() {
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from tb_game");
            tableView.getItems().clear();
            while(rs.next()){
                tableView.getItems().add(new game(rs.getInt("id"), rs.getString("nama_game"), rs.getString("jenis_game"), rs.getString("code_game"), rs.getString("ukuran_game")));
            }
            
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

}