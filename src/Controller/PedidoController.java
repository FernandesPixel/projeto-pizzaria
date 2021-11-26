package Controller;

import Model.Pedido;
import DAO.PedidoDAO;
import static DAO.PedidoDAO.inserePedido;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author juann
 */
public class PedidoController implements Initializable {

    @FXML
    private Button BtPesquisar;
    @FXML
    private Button BtPDF;
    @FXML
    private TextField TxPesquisa;
    @FXML
    private Button BtD20;
    // ComboBox<TipoDeDado>
    @FXML
    private ComboBox<String> Cb1Sabor;
    @FXML
    private ComboBox<String> Cb2Sabor;
    @FXML
    private ComboBox<String> Cb3Sabor;
    @FXML
    private ComboBox<String> CbTamanho;
    @FXML
    private Button BtPedir;
    @FXML
    private Tab PEDIDO;
    @FXML
    private Button Btdeleta;
    @FXML
    TableView<Pedido> TabelaID;
    @FXML
    TableColumn<Pedido, String> colunaSabor3;
    @FXML
    TableColumn<Pedido, String> colunaSabor2;
    @FXML
    TableColumn<Pedido, String> colunaSabor1;
    @FXML
    TableColumn<Pedido, String> colunaTamanho;
    @FXML
    TableColumn<Pedido, Integer> colunaID;
    
    
    //lista para receber pedidos do DAO e passar para a tabela(preencheTabela)
    private ObservableList<Pedido> pedido;
     
    private static Pedido selecionado;

    private ObservableList<String> saborPizza = FXCollections.observableArrayList("Calabresa", "Quatro Queijos", "Milho com Bacon","Americana","Bacon com Cheddar","Mussarela");
    
    /*String[] sabores = new String[3];
    sabores[0] = "Crocante";
    sabores[1] = "Quatro Queijos";
    sabores[2] = "Calabresa";*/

    
    private ObservableList<String> tamanhoPizza = FXCollections.observableArrayList("Broto", "Média", "Grande");

    public void initialize(URL url, ResourceBundle rb) {
        Cb1Sabor.setItems(saborPizza);
        Cb2Sabor.setItems(saborPizza);
        Cb3Sabor.setItems(saborPizza);
        CbTamanho.setItems(tamanhoPizza);
        preencheTabela();
        
        TabelaID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    PedidoController.selecionado = (Pedido) newValue;
                } else {
                    PedidoController.selecionado = null;
                }
            }
        });
        
        TxPesquisa.setOnKeyReleased((KeyEvent e) -> {
            pesquisa();
        });
        
        BtPesquisar.setOnMouseClicked((MouseEvent e) -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                pesquisa();
            }
        });
    }
    
    @FXML
    private void ClicouPedir(ActionEvent event) {
        if (Cb1Sabor.getValue() == null || Cb2Sabor.getValue() == null
                || Cb3Sabor.getValue() == null || CbTamanho.getValue() == null) {

            Alert c = new Alert(Alert.AlertType.WARNING);
            c.setTitle("ATENÇÃO!");
            c.setHeaderText("Campos vazios");
            c.setContentText("Por favor, selecione seus sabores");
            c.showAndWait();

        } else {

            try {
                /*
               O objeto fun recebe o que o usuário digitou na tela,
               por meio do controlador, e manda para a classe FuncionarioDAO
               por meio do método insereFuncionario o objeto fun
               para ser cadastrado no banco de dados
                 */
                Pedido pizza = new Pedido();
                pizza.setSabor1(Cb1Sabor.getValue());
                pizza.setSabor2(Cb2Sabor.getValue());
                pizza.setSabor3(Cb3Sabor.getValue());
                pizza.setTamanho(CbTamanho.getValue());
                inserePedido(pizza);

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("VERIFICAÇÃO DE PEDIDO");
                a.setHeaderText("Sua pedido está sendo preparado. Aguarde.");
                a.setContentText("Pedido realizado");
                a.showAndWait();
                clearForm();
                preencheTabela();

            } catch (Exception e) {
                System.out.println("Erro ao cadastrar" + e.getMessage());
            }
        }
    }

    private void clearForm() {
        //Método responsável por limpar a tela
        Cb1Sabor.setValue(null);
        Cb2Sabor.setValue(null);
        Cb3Sabor.setValue(null);
        CbTamanho.setValue(null);
    }
    
    public void preencheTabela() {
        /*
    Associa os campos da tableview com os
    campos do banco de dados
         */
        colunaSabor1.setCellValueFactory(new PropertyValueFactory("sabor1"));
        colunaSabor2.setCellValueFactory(new PropertyValueFactory("sabor2"));
        colunaSabor3.setCellValueFactory(new PropertyValueFactory("sabor3"));
        colunaTamanho.setCellValueFactory(new PropertyValueFactory("tamanho"));
        colunaID.setCellValueFactory(new PropertyValueFactory("idPedido"));
        
        /*
    Busca os dados no banco e mostra na TableView
         */
        PedidoDAO dao = new PedidoDAO();
        pedido = dao.getPedido(); //Problema no metodo getPedido
        TabelaID.setItems(pedido);
    }
    
    @FXML
    private void deletaPedido(ActionEvent event) {
        if (PedidoController.selecionado != null) {
            /*Criando um objeto da classe DAO, para
            aciona-lo e deleta-lo do banco de dados
             */
            PedidoDAO dao = new PedidoDAO();
            /*O objeto dao vai deletar o que foi selecionado
            do banco de dados
             */
            dao.deletaPedido(PedidoController.selecionado);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            //Exibe aviso ao usuario com o método setHeaderText
            a.setHeaderText("Pedido excluido com sucesso!");
            //Mostra a janela e espera o usuário fechar-showAndWait
            a.showAndWait();
            //Responsavel por manipular a Tabela
            preencheTabela();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Favor selecionar pedido !");
            a.showAndWait();
        }
    }
    
    @FXML
    public void pesquisa(){
        ObservableList<Pedido> pizza = FXCollections.observableArrayList();
        
        for(int x=0; x<pedido.size();x++){
            if(pedido.get(x).getSabor1().toUpperCase().contains(TxPesquisa.getText().toUpperCase())){
                pizza.add(pedido.get(x));
            }
            TabelaID.setItems(pizza);
        }
    }
    
    /*
    @FXML
    public void GeraPDF() throws FileNotFoundException, DocumentException {
        PedidoDAO dao = new PedidoDAO();
        ObservableList<Pedido> pizzas = dao.getPedido();

        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream("C:/Aluno/relatorio.pdf"));
        doc.open();
        doc.add(new Paragraph("Relatorio em PDF:" + "\n" + pizzas));
        doc.close();
    }*/
    
    @FXML
    public void dado(ActionEvent event) {
        //cria objeto da classe random para gerar um numero aleatorio
        Random dado = new Random();
        
        for(int i=0; i<=3; i++){
            int z = dado.nextInt(6);
            switch (i) {
                case 1:
                    Cb1Sabor.setValue(saborPizza.get(z));
                    break;
                case 2:
                    Cb2Sabor.setValue(saborPizza.get(z));
                    break;
                case 3:
                    Cb3Sabor.setValue(saborPizza.get(z));
                    break;
                default:
                    break;
            }
        }
        //int z = dado.nextInt(2);
        
        //saborPizza.get(z); pega sabor aleatorio
        
        /*
        Cb2Sabor.setValue(saborPizza.get(z));
        Cb3Sabor.setValue(saborPizza.get(z));
        CbTamanho.setValue(tamanhoPizza.get(z));
        /*
        Cb1Sabor.setValue(saborPizza[z]);
        Cb2Sabor.setValue(null);
        Cb3Sabor.setValue(null);
        CbTamanho.setValue(null);
*/
    }

}
