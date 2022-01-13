import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;

public class game {
    private int id;
    private String nama_game = null;
    private String jenis_game = null;
    private String code_game = null;
    private String ukuran_game = null;
    public Object conn;

    public game(int inputId, String inputNama_Game, String inputJenis_Gam, String inputCode_Game, String inputUkuran_Game) {
        this.id = inputId;
        this.nama_game = inputNama_Game;
        this.jenis_game = inputJenis_Gam;
        this.code_game = inputCode_Game;
        this.ukuran_game = inputUkuran_Game;
    }



    public int getId(){
        return id;
    }

    public String getNama_game(){
        return nama_game;
    }

    public String getJenis_game(){
        return jenis_game;
    }

    public String getCode_game(){
        return code_game;
    }

    public String getUkuran_game(){
        return ukuran_game;
    }


    public void setId(String text) {
    }


    public void setUkuran_game() {
    }


    public void setUkuran_game(String text) {
    }


    public void setUkuran_game(double parseDouble) {
    }


    public void setNama_game(String text) {
    }


    public void setJenis_game(String text) {
    }


    public void setCode_game(String text) {
    }



}