package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import modelo.Correntista;
import modelo.Conta;
import regras_negocio.Fachada;

public class TelaCorrentistas {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JLabel label;
    private JLabel label_6;
    private JLabel label_2;
    private JTextField textField_1;
    private JButton button;
    private JButton button_1;
    private JLabel label_3;
    private JTextField textField_2;
    private JTextField textField_3;
    private JButton button_5;
    private JLabel label_4;
    private JButton button_2;

    /**
     * Create the application.
     */
    public TelaCorrentistas() {
        initialize();
        frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Correntistas");
        frame.setBounds(100, 100, 729, 385);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                // Initial state with an empty table
                limparTabela();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 10, 674, 148);
        frame.getContentPane().add(scrollPane);
        
                table = new JTable();
                scrollPane.setViewportView(table);
                table.setGridColor(Color.BLACK);
                table.setRequestFocusEnabled(false);
                table.setFocusable(false);
                table.setBackground(Color.WHITE);
                table.setFillsViewportHeight(true);
                table.setRowSelectionAllowed(true);
                table.setFont(new Font("Tahoma", Font.PLAIN, 14));
                table.setBorder(new LineBorder(new Color(0, 0, 0)));
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                table.setShowGrid(true);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        label = new JLabel("");
        label.setForeground(Color.BLUE);
        label.setBounds(21, 321, 688, 14);
        frame.getContentPane().add(label);

        label_6 = new JLabel("selecione");
        label_6.setBounds(21, 166, 431, 14);
        frame.getContentPane().add(label_6);

        label_2 = new JLabel("CPF:");
        label_2.setHorizontalAlignment(SwingConstants.LEFT);
        label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label_2.setBounds(21, 206, 71, 14);
        frame.getContentPane().add(label_2);

        textField_1 = new JTextField();
        textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
        textField_1.setColumns(10);
        textField_1.setBounds(67, 203, 195, 20);
        frame.getContentPane().add(textField_1);

        button_1 = new JButton("Criar Correntista");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textField_1.getText().isEmpty() || textField_2.getText().isEmpty()
                            || textField_3.getText().isEmpty()) {
                        label.setText("campo vazio");
                        return;
                    }
                    String CPF = textField_1.getText();
                    String NOME = textField_2.getText();
                    String SENHA = textField_3.getText();
                    
                    Fachada.criarCorrentista(CPF, NOME, SENHA);
                    
                    label.setText("CORRENTISTA CRIADO: " + NOME);

                    // Limpar os campos após a criação
                    textField_1.setText("");
                    textField_2.setText("");
                    textField_3.setText("");

                    // Atualiza a lista de correntistas
                    listagem();
                } catch (Exception ex) {
                    label.setText(ex.getMessage());
                }
            }
        });

        button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_1.setBounds(320, 236, 133, 46);
        frame.getContentPane().add(button_1);

        button = new JButton("Listar");
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listagem();
            }
        });
        button.setBounds(444, 168, 89, 23);
        frame.getContentPane().add(button);

        label_3 = new JLabel("NOME:");
        label_3.setHorizontalAlignment(SwingConstants.LEFT);
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label_3.setBounds(272, 206, 63, 14);
        frame.getContentPane().add(label_3);

        textField_2 = new JTextField();
        textField_2.setFont(new Font("Dialog", Font.PLAIN, 12));
        textField_2.setColumns(10);
        textField_2.setBounds(320, 203, 168, 20);
        frame.getContentPane().add(textField_2);

        textField_3 = new JTextField();
        textField_3.setFont(new Font("Dialog", Font.PLAIN, 12));
        textField_3.setColumns(10);
        textField_3.setBounds(67, 249, 195, 20);
        frame.getContentPane().add(textField_3);

        button_5 = new JButton("Limpar");
        button_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparTabela();
            }
        });
        button_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_5.setBounds(554, 168, 89, 23);
        frame.getContentPane().add(button_5);
        
        label_4 = new JLabel("SENHA:");
        label_4.setHorizontalAlignment(SwingConstants.LEFT);
        label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label_4.setBounds(21, 252, 71, 14);
        frame.getContentPane().add(label_4);
        
        button_2 = new JButton("SaldoTotal");
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        String cpfString = (String) table.getValueAt(table.getSelectedRow(), 0);
                        ArrayList<Correntista> listCorrentistas = Fachada.listarCorrentistas();
                        double saldo = 0;
                        for(Correntista co : listCorrentistas) {
							if(co.getCpf().equals(cpfString))
								saldo = co.getSaldoTotal();
							}
                        
                        label.setText("Saldo total do correntista com cpf " + cpfString + " é =" + saldo);
                    } else {
                        label.setText("Selecione uma linha");
                    }
                } catch (NumberFormatException ex) {
                    label.setText("CPF inválido");
                } catch (Exception ex) {
                    label.setText("Erro: " + ex.getMessage());
                }
            }
        });
        button_2.setBounds(558, 211, 105, 38);
        frame.getContentPane().add(button_2);
        
        
    }

    // Método que lista todos os correntistas
    public void listagem() {
        try {
            List<Correntista> lista = Fachada.listarCorrentistas();
            atualizarTabela(lista);
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }

    // Método que limpa a tabela
    public void limparTabela() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Remove todas as linhas da tabela
        label_6.setText("Tabela limpa.");
    }

    // Método que atualiza a tabela com os dados fornecidos
    private void atualizarTabela(List<Correntista> lista) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("CPF");
        model.addColumn("NOME");
        model.addColumn("SENHA");
        model.addColumn("CONTAS");

        for (Correntista c : lista) {
        	ArrayList<Conta> listaContas = c.getContas();
        	String STRlistaContas = "";
        	for (Conta co : listaContas) {
        		STRlistaContas = STRlistaContas + co.getId()  + ",";
        	}
        	
            model.addRow(new Object[]{c.getCpf(), c.getNome(), c.getSenha(), STRlistaContas});
        }

        table.setModel(model);
        label_6.setText("Resultados: " + lista.size() + " correntistas encontrados.");
    }
}