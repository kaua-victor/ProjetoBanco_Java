package appswing;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import regras_negocio.Fachada;

public class TelaCaixa {
    private JDialog frame;
    private JButton button_7; // Creditar
    private JButton button_8; // Debitar
    private JButton button_9; // Transferir
    private JLabel label;
    private JLabel label_8;

    public TelaCaixa() {
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
        
        button_7 = new JButton("Creditar");
        button_7.setBounds(72, 99, 168, 134);
        button_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String idStr = JOptionPane.showInputDialog(frame, "Digite o ID da conta:");
                    if (idStr == null || idStr.trim().isEmpty()) {
                        label.setText("Campo ID vazio");
                        return;
                    }
                    int id = Integer.parseInt(idStr);

                    String cpf = JOptionPane.showInputDialog(frame, "Digite o CPF:");
                    if (cpf == null || cpf.trim().isEmpty()) {
                        label.setText("Campo CPF vazio");
                        return;
                    }

                    String senha = JOptionPane.showInputDialog(frame, "Digite a senha:");
                    if (senha == null || senha.trim().isEmpty()) {
                        label.setText("Campo senha vazio");
                        return;
                    }

                    String valorStr = JOptionPane.showInputDialog(frame, "Digite o valor a creditar (número decimal):");
                    if (valorStr == null || valorStr.trim().isEmpty()) {
                        label.setText("Campo valor vazio");
                        return;
                    }
                    double valor = Double.parseDouble(valorStr);

                    Fachada.creditarValor(id, cpf, senha, valor);
                    label.setText("Valor creditado com sucesso.");
                } catch (NumberFormatException ex) {
                    label.setText("Valor inválido.");
                } catch (Exception ex) {
                    label.setText("Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(button_7);
        
        button_8 = new JButton("Debitar");
        button_8.setBounds(294, 99, 168, 134);
        button_8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String idStr = JOptionPane.showInputDialog(frame, "Digite o ID da conta:");
                    if (idStr == null || idStr.trim().isEmpty()) {
                        label.setText("Campo ID vazio");
                        return;
                    }
                    int id = Integer.parseInt(idStr);

                    String cpf = JOptionPane.showInputDialog(frame, "Digite o CPF:");
                    if (cpf == null || cpf.trim().isEmpty()) {
                        label.setText("Campo CPF vazio");
                        return;
                    }

                    String senha = JOptionPane.showInputDialog(frame, "Digite a senha:");
                    if (senha == null || senha.trim().isEmpty()) {
                        label.setText("Campo senha vazio");
                        return;
                    }

                    String valorStr = JOptionPane.showInputDialog(frame, "Digite o valor a debitar (número decimal):");
                    if (valorStr == null || valorStr.trim().isEmpty()) {
                        label.setText("Campo valor vazio");
                        return;
                    }
                    double valor = Double.parseDouble(valorStr);

                    Fachada.debitarValor(id, cpf, senha, valor);
                    label.setText("Valor debitado com sucesso.");
                } catch (NumberFormatException ex) {
                    label.setText("Valor inválido.");
                } catch (Exception ex) {
                    label.setText("Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(button_8);
        
        button_9 = new JButton("Transferir");
        button_9.setBounds(523, 99, 168, 134);
        button_9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String idOrigemStr = JOptionPane.showInputDialog(frame, "Digite o ID da conta de origem:");
                    if (idOrigemStr == null || idOrigemStr.trim().isEmpty()) {
                        label.setText("Campo ID de origem vazio");
                        return;
                    }
                    int idOrigem = Integer.parseInt(idOrigemStr);

                    String cpf = JOptionPane.showInputDialog(frame, "Digite o CPF:");
                    if (cpf == null || cpf.trim().isEmpty()) {
                        label.setText("Campo CPF vazio");
                        return;
                    }

                    String senha = JOptionPane.showInputDialog(frame, "Digite a senha:");
                    if (senha == null || senha.trim().isEmpty()) {
                        label.setText("Campo senha vazio");
                        return;
                    }

                    String valorStr = JOptionPane.showInputDialog(frame, "Digite o valor a transferir (número decimal):");
                    if (valorStr == null || valorStr.trim().isEmpty()) {
                        label.setText("Campo valor vazio");
                        return;
                    }
                    double valor = Double.parseDouble(valorStr);

                    String idDestinoStr = JOptionPane.showInputDialog(frame, "Digite o ID da conta destino:");
                    if (idDestinoStr == null || idDestinoStr.trim().isEmpty()) {
                        label.setText("Campo ID destino vazio");
                        return;
                    }
                    int idDestino = Integer.parseInt(idDestinoStr);

                    Fachada.transferirValor(idOrigem, cpf, senha, valor, idDestino);
                    label.setText("Valor transferido com sucesso.");
                } catch (NumberFormatException ex) {
                    label.setText("Valor ou ID inválido.");
                } catch (Exception ex) {
                    label.setText("Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(button_9);
        
        // Configurações adicionais
        label = new JLabel("");
        label.setBounds(72, 250, 600, 14);
        frame.getContentPane().add(label);

        label_8 = new JLabel("Ação:");
        label_8.setBounds(72, 74, 100, 14);
        frame.getContentPane().add(label_8);
    }
}