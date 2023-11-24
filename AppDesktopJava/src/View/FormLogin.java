package View;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class FormLogin extends JFrame {
    private JTextField campoLogin;
    private JPasswordField campoSenha;

    public FormLogin(){

        // janela de login
        setTitle("Sistema de Login");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);

    }

    private void placeComponents(JPanel panel) {

        panel.setLayout(null);
        // Campo de login
        JLabel loginLabel = new JLabel("Login:");
        loginLabel.setBounds(10, 20, 80, 25);
        panel.add(loginLabel);

        campoLogin = new JTextField(20);
        campoLogin.setBounds(100, 20, 165, 25);
        panel.add(campoLogin);

        // Campo de senha
        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(10, 50, 80, 25);
        panel.add(senhaLabel);

        campoSenha = new JPasswordField(20);
        campoSenha.setBounds(100, 50, 165, 25);
        panel.add(campoSenha);

        // Botão de login
        JButton loginButton = new JButton("Entrar");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });
        panel.add(loginButton);

        // Botão de cadastrar novo usuário
        JButton btnCadastrar = new JButton("Cadastrar usuário");
        btnCadastrar.setBounds(100, 80, 165, 25);
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                abrirTelaCadastro();
            }
        });
        panel.add(btnCadastrar);

    }

    private void autenticarUsuario() {
        String login = campoLogin.getText();
        String senha = new String(campoSenha.getPassword());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/devthiago", "root", "")) {
            String query = "SELECT id, nome, login, senha, email FROM usuario WHERE login = ? AND senha = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, senha);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Acesso Autorizado");
                } else {
                    JOptionPane.showMessageDialog(this, "Acesso Negado");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void abrirTelaCadastro() {

        // tela de cadastro
        JFrame frameCadastro = new JFrame("Cadastro de Usuário");
        frameCadastro.setSize(300, 200);
        frameCadastro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelCadastro = new JPanel();
        frameCadastro.add(panelCadastro);
        placeComponentsCadastro(panelCadastro);

        frameCadastro.setVisible(true);
    }
    private void placeComponentsCadastro(JPanel panelCadastro) {
        panelCadastro.setLayout(null);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(10, 20, 80, 25);
        panelCadastro.add(labelNome);

        JTextField campoNome = new JTextField(20);
        campoNome.setBounds(100, 20, 165, 25);
        panelCadastro.add(campoNome);

        JLabel labelLogin = new JLabel("Login:");
        labelLogin.setBounds(10, 50, 80, 25);
        panelCadastro.add(labelLogin);

        JTextField campoNovoLogin = new JTextField(20);
        campoNovoLogin.setBounds(100, 50, 165, 25);
        panelCadastro.add(campoNovoLogin);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(10, 80, 80, 25);
        panelCadastro.add(labelSenha);

        JPasswordField campoNovaSenha = new JPasswordField(20);
        campoNovaSenha.setBounds(100, 80, 165, 25);
        panelCadastro.add(campoNovaSenha);

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setBounds(10, 110, 80, 25);
        panelCadastro.add(labelEmail);

        JTextField campoNovoEmail = new JTextField(20);
        campoNovoEmail.setBounds(100, 110, 165, 25);
        panelCadastro.add(campoNovoEmail);

        JButton btnCadastrarNovo = new JButton("Cadastrar");
        btnCadastrarNovo.setBounds(10, 140, 120, 25);


        btnCadastrarNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarNovoUsuario(campoNome.getText(), campoNovoLogin.getText(), new String(campoNovaSenha.getPassword()), campoNovoEmail.getText());
                if (campoNome.getText().isEmpty() || campoNovoLogin.getText().isEmpty() ||
                        new String(campoNovaSenha.getPassword()).isEmpty() || campoNovoEmail.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de cadastrar.");
                } else {
                    cadastrarNovoUsuario(campoNome.getText(), campoNovoLogin.getText(), new String(campoNovaSenha.getPassword()), campoNovoEmail.getText());

                    // Fecha a tela de cadastro após o cadastro ser efetuado com sucesso
                    dispose();

                    // Abre a tela de login novamente
                    new FormLogin();
                }
            }
        });

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(140, 140, 150, 25);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new FormLogin();
            }
        });
        panelCadastro.add(btnCadastrarNovo);
        panelCadastro.add(btnVoltar);
    }

    private void cadastrarNovoUsuario(String nome, String login, String senha, String email) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/devthiago", "root", "")) {
            String query = "INSERT INTO usuario(nome, login, senha, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, login);
                preparedStatement.setString(3, senha);
                preparedStatement.setString(4, email);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso");

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
