package com.passwordGenerator.frontend;

import com.passwordGenerator.backend.Generator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PasswordGeneratorGUI extends JFrame
{

    //componenti della GUI
    private JSpinner lengthSpinner;
    private JCheckBox upperCaseBox, numbersBox, symbolsBox;
    private JButton generateBtn;
    private JLabel feedbackLabel;
    private JButton copyBtn;

    public static JPasswordField resultField = new JPasswordField();
    private JButton showBtn;

    public PasswordGeneratorGUI()
    {

        //impostazioni della GUI
        setTitle("Password Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); //margini tra i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //sotto il bottone (gridy = 5)
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3; //prende due colonne
        feedbackLabel = new JLabel("Security feedback: generate a password", SwingConstants.CENTER);
        add(feedbackLabel, gbc);

        //lunghezza password
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Length (min 4, max 256):"), gbc);

        gbc.gridx = 1;
        lengthSpinner = new JSpinner(new SpinnerNumberModel(12, 4, 256, 1));
        add(lengthSpinner, gbc);

        //opzioni password
        gbc.gridx = 0; gbc.gridy = 1;
        upperCaseBox = new JCheckBox("Maiuscole (A-Z)", false);
        add(upperCaseBox, gbc);

        gbc.gridx = 1;
        numbersBox = new JCheckBox("Numeri (0-9)", false);
        add(numbersBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        symbolsBox = new JCheckBox("Simboli (!@#$)", false);
        add(symbolsBox, gbc);

        //password generata
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.8;
        resultField.setEditable(false);
        resultField.setEchoChar('•'); // Pallini di default
        resultField.setFont(new Font("Monospaced", Font.BOLD, 14));
        resultField.setHorizontalAlignment(JTextField.CENTER);
        add(resultField, gbc);
        resultField.setDragEnabled(true);
        resultField.setTransferHandler(new TransferHandler("text"));

        gbc.gridx = 1;
        gbc.weightx = 0.2;
        showBtn = new JButton("SHOW");
        add(showBtn, gbc);

        //bottone
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        generateBtn = new JButton("GENERATE PASSWORD");
        generateBtn.setBackground(Color.WHITE);
        generateBtn.setForeground(Color.BLACK);
        generateBtn.setOpaque(true);
        generateBtn.setBorderPainted(true);
        add(generateBtn, gbc);

        gbc.gridx = 1; // Colonna a fianco
        copyBtn = new JButton("COPY");
        copyBtn.setEnabled(false); //disabilitato finché non c'è una password
        add(copyBtn, gbc);

        showBtn.addActionListener(e -> {
            if (resultField.getEchoChar() == '•')
            {

                resultField.setEchoChar((char) 0); // (char) 0 mostra il testo in chiaro
                showBtn.setText("HIDE");

            }
            else
            {

                resultField.setEchoChar('•');
                showBtn.setText("SHOW");

            }

        });

        resultField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e)
            {

                //se c'è testo, inizia il trascinamento al click del mouse
                if (resultField.getPassword().length > 0)
                {

                    JComponent comp = (JComponent) e.getSource();
                    TransferHandler th = comp.getTransferHandler();
                    th.exportAsDrag(comp, e, TransferHandler.COPY);
                }

            }

        });

        //azione del bottone
        generateBtn.addActionListener(e ->
        {

            java.awt.datatransfer.StringSelection emptySelection = new java.awt.datatransfer.StringSelection("");
            java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(emptySelection, null);
            copyBtn.setEnabled(false);
            generateBtn.setEnabled(false);

            String password = Generator.generatePassword((int)lengthSpinner.getValue(), upperCaseBox.isSelected(), numbersBox.isSelected(), symbolsBox.isSelected());

            //stampo la password generata
            resultField.setText(password);
            //controllo se la password è sicura e stampo il risultato
            String feedback = Generator.isPasswordSafe(password);
            feedbackLabel.setText("Security feedback: " + feedback);

            copyBtn.setEnabled(true);
            generateBtn.setEnabled(true);

        });

        copyBtn.addActionListener(e -> {
            //leggiamo la password in modo sicuro
            char[] pswChars = resultField.getPassword();

            if (pswChars.length > 0)
            {

                String psw = new String(pswChars);

                java.awt.datatransfer.StringSelection selection = new java.awt.datatransfer.StringSelection(psw);
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

                copyBtn.setText("COPIED!");
                feedbackLabel.setText("Security: Password copied. Clipboard will be cleared in 30s.");

                //timer per ripristinare il testo del bottone dopo 2 secondi
                Timer resetBtnTimer = new Timer(2000, event -> copyBtn.setText("COPY"));
                resetBtnTimer.setRepeats(false);
                resetBtnTimer.start();

                //dopo 30 secondi, svuota gli appunti del sistema
                Timer clearClipboardTimer = new Timer(30000, event -> {
                    java.awt.datatransfer.StringSelection empty = new java.awt.datatransfer.StringSelection("");
                    java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(empty, null);
                    feedbackLabel.setText("Security: Clipboard cleared automatically.");
                });
                clearClipboardTimer.setRepeats(false);
                clearClipboardTimer.start();

                //pulizia dell'array in memoria (buona pratica di sicurezza)
                java.util.Arrays.fill(pswChars, '0');

            }

        });

    }

}