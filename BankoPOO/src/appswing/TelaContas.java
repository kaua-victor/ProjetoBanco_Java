package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Conta;
import modelo.ContaEspecial;
import regras_negocio.Fachada;

public class TelaContas {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton button;
    private JButton button_1;
    private JButton button_4;
    private JLabel label;
    private JLabel label_8;
    private JButton button_3;
    private JButton button_5;
    private JButton button_2;
    private JButton button_6;

    public TelaContas() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                // A tabela deve iniciar vazia; a listagem é feita pelo botão
            }
        });
        frame.setTitle("Contas");
        frame.setBounds(100, 100, 912, 351);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(26, 20, 844, 120);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
        table.setFocusable(false);
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Inicializa a tabela com um modelo vazio
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"id", "data", "saldo", "limite"});
        table.setModel(model);

        button = new JButton("Criar Conta");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String CPF = JOptionPane.showInputDialog(frame, "Digite o CPF:");

                    if (CPF == null || CPF.trim().isEmpty()) {
                        label.setText("Campo CPF vazio");
                        return;
                    }

                    // Cria a conta com o CPF fornecido
                    Fachada.criarConta(CPF);
                    label.setText("Conta criada com CPF: " + CPF);

                    // Atualiza a listagem (se necessário)
                    listagem();
                } catch (Exception ex) {
                    label.setText("Erro: " + ex.getMessage());
                }
            }
        });

        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.setBounds(26, 182, 95, 23);
        frame.getContentPane().add(button);

        button_1 = new JButton("Apagar Conta");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        String idStr = (String) table.getValueAt(table.getSelectedRow(), 0);
                        int id = Integer.parseInt(idStr);

                        Object[] options = { "Confirmar", "Cancelar" };
                        int escolha = JOptionPane.showOptionDialog(null, 
                                "Confirma o cancelamento da conta com ID: " + id, 
                                "Alerta",
                                JOptionPane.DEFAULT_OPTION, 
                                JOptionPane.WARNING_MESSAGE, 
                                null, 
                                options, 
                                options[1]);

                        if (escolha == 0) {
                            Fachada.apagarConta(id);
                            label.setText("Conta com ID " + id + " apagada.");
                            listagem();
                        } else {
                            label.setText("Cancelamento da conta com ID " + id + " não realizado.");
                        }
                    } else {
                        label.setText("Selecione uma linha.");
                    }
                } catch (NumberFormatException ex) {
                    label.setText("ID da conta inválido.");
                } catch (Exception erro) {
                    label.setText("Erro: " + erro.getMessage());
                }
            }
        });

        button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_1.setBounds(568, 182, 114, 23);
        frame.getContentPane().add(button_1);

        label = new JLabel("");
        label.setForeground(Color.BLUE);
        label.setBackground(Color.RED);
        label.setBounds(26, 287, 830, 14);
        frame.getContentPane().add(label);

        label_8 = new JLabel("selecione");
        label_8.setBounds(26, 144, 561, 14);
        frame.getContentPane().add(label_8);

        button_4 = new JButton("Listar");
        button_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listagem();
            }
        });
        button_4.setBounds(669, 150, 95, 23);
        frame.getContentPane().add(button_4);

        button_3 = new JButton("Remover Cotitular");
        button_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        String idStr = (String) table.getValueAt(table.getSelectedRow(), 0);
                        int id = Integer.parseInt(idStr);

                        String cpf = JOptionPane.showInputDialog(frame, "Digite o CPF:");

                        if (cpf == null || cpf.trim().isEmpty()) {
                            label.setText("Campo CPF vazio");
                            return;
                        }

                        Fachada.removerCorrentistaConta(id, cpf);
                        label.setText("Cotitular removido com ID: " + id + " e CPF: " + cpf);
                        listagem();
                    } else {
                        label.setText("Selecione uma linha");
                    }
                } catch (Exception erro) {
                    label.setText("Erro: " + erro.getMessage());
                }
            }
        });

        button_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_3.setBounds(423, 182, 135, 23);
        frame.getContentPane().add(button_3);

        button_5 = new JButton("Adicionar Cotitular");
        button_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        String idString = (String) table.getValueAt(table.getSelectedRow(), 0);
                        int id = Integer.parseInt(idString);

                        String cpf = JOptionPane.showInputDialog(frame, "Digite o CPF:");

                        if (cpf == null || cpf.trim().isEmpty()) {
                            label.setText("Campo CPF vazio");
                            return;
                        }

                        Fachada.inserirCorrentistaConta(id, cpf);
                        label.setText("Cotitular adicionado com CPF: " + cpf);
                    } else {
                        label.setText("Selecione uma linha");
                    }
                } catch (NumberFormatException ex) {
                    label.setText("ID inválido");
                } catch (Exception ex) {
                    label.setText("Erro: " + ex.getMessage());
                }
            }
        });

        button_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_5.setBounds(278, 182, 135, 23);
        frame.getContentPane().add(button_5);

        button_2 = new JButton("Criar Conta Especial");
        button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_2.setBounds(131, 182, 137, 23);
        frame.getContentPane().add(button_2);
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String CPF = JOptionPane.showInputDialog(frame, "Digite o CPF:");

                    if (CPF == null || CPF.trim().isEmpty()) {
                        label.setText("Campo CPF vazio");
                        return;
                    }

                    String limiteString = JOptionPane.showInputDialog(frame, "Digite o limite da conta (número decimal):");

                    if (limiteString == null || limiteString.trim().isEmpty()) {
                        label.setText("Campo limite vazio");
                        return;
                    }

                    double limite;
                    try {
                        limite = Double.parseDouble(limiteString);
                    } catch (NumberFormatException ex) {
                        label.setText("Valor do limite inválido");
                        return;
                    }

                    Fachada.criarContaEspecial(CPF, limite);
                    label.setText("Conta criada com CPF: " + CPF + " e limite: " + limite);

                    listagem();
                } catch (Exception ex) {
                    label.setText("Erro: " + ex.getMessage());
                }
            }
        });
        
        button_6 = new JButton("Limpar");
        button_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_6.setBounds(775, 150, 95, 23);
        frame.getContentPane().add(button_6);
        button_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Limpa o modelo da tabela
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0); // Remove todas as linhas da tabela

                // Atualiza o rótulo de status
                label_8.setText("Tabela limpa");
            }
        });
        
    }

    public void listagem() {
        try {
            List<Conta> lista = Fachada.listarContas();

            // Cria um novo modelo de tabela
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new Object[]{"id", "data", "saldo", "limite"});

            // Adiciona as linhas ao modelo
            for (Conta c : lista) {
                if (c instanceof ContaEspecial) {
                    model.addRow(new Object[]{c.getId() + "", c.getData(), c.getSaldo(), ((ContaEspecial) c).getLimite()});
                } else {
                    model.addRow(new Object[]{c.getId() + "", c.getData(), c.getSaldo()});
                }
            }

            // Atualiza o modelo da tabela
            table.setModel(model);
            label_8.setText("Resultados: " + lista.size() + " Contas - selecione uma linha");
        } catch (Exception erro) {
            label.setText("Erro: " + erro.getMessage());
        }
    }
}