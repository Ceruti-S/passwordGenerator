package com.passwordGenerator.backend;

import com.passwordGenerator.frontend.PasswordGeneratorGUI;

import javax.swing.*;
import java.awt.*;
import com.nulabinc.zxcvbn.Zxcvbn;
import com.nulabinc.zxcvbn.Strength;

public class Generator
{

    private static final Zxcvbn zxcvbn = new Zxcvbn();
    private static final java.security.SecureRandom rand = new java.security.SecureRandom();

    public static void generateGUI()
    {

        //applico il tema del sistema operativo
        try
        {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        }
        catch (Exception e)
        {

            System.out.println("Error with setting the OS theme. " + e.getMessage());

        }

        SwingUtilities.invokeLater(() ->
        {

            new PasswordGeneratorGUI().setVisible(true);

        });

    }

    public static String generatePassword(int lunghezza, boolean upperCase, boolean numbers, boolean symbols)
    {

        StringBuilder poolBuilder = new StringBuilder("abcdefghijklmnopqrstuvwxyz");

        if (upperCase) poolBuilder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if (numbers)   poolBuilder.append("0123456789");
        if (symbols)   poolBuilder.append("!@#$%^&*()-_=+[]{}|;:,.<>?");

        String caratteriUtilizzabili = poolBuilder.toString();
        char [] caratteriUtilizzabiliArray = caratteriUtilizzabili.toCharArray();
        int caratteriUtilizzabiliArraySize = caratteriUtilizzabiliArray.length;

        boolean valida = false;
        StringBuilder password = new StringBuilder();

        do
        {

            password.setLength(0);
            char lastChar = '\0';

            for(int i=0; i<lunghezza; i++)
            {

                char nextChar;
                //ciclo finché non trovo un carattere diverso dal precedente
                do
                {

                    nextChar = caratteriUtilizzabiliArray[rand.nextInt(caratteriUtilizzabiliArraySize)];

                } while (nextChar == lastChar);

                password.append(nextChar);
                lastChar = nextChar;

            }

            if(password.length() >=128)
                valida = true;
            else
                valida = validaRequisiti(password, upperCase, numbers, symbols);

        }while(!valida);

        return password.toString();

    }

    private static boolean validaRequisiti(CharSequence password, boolean upper, boolean numbers, boolean symbols)
    {

        boolean hasLower = false, hasUpper = false, hasNum = false, hasSym = false;

        for (int i = 0; i < password.length(); i++)
        {

            char c = password.charAt(i);
            if (c >= 'a' && c <= 'z') hasLower = true;
            else if (c >= 'A' && c <= 'Z') hasUpper = true;
            else if (c >= '0' && c <= '9') hasNum = true;
            else hasSym = true;

        }

        if (!hasLower) return false;
        if (upper && !hasUpper) return false;
        if (numbers && !hasNum) return false;
        if (symbols && !hasSym) return false;

        return true;

    }

    public static String isPasswordSafe(String password)
    {

        //test lunghezza preventivo
        if (password.length() < 6)
        {

            PasswordGeneratorGUI.resultField.setBackground(new Color(255, 77, 77));
            return "Password too short.";

        }

        //limitiamo l'analisi di Zxcvbn
        //analizzarne 128 dà lo stesso risultato di sicurezza di analizzarne 256, senza far esplodere il computer
        String stringaDaAnalizzare = (password.length() > 128) ? password.substring(0, 128) : password;

        //analisi intelligiente
        Strength strength = zxcvbn.measure(stringaDaAnalizzare);
        int score = strength.getScore(); // Base da 0 a 4

        //varietà dei caratteri
        boolean haMaiuscole = false;
        boolean haNumeri = false;
        boolean haSimboli = false;

        for (int i=0; i<password.length(); i++)
        {

            char c = password.charAt(i);

            if (c >= 'a' && c <= 'z') {
                continue;
            } else if (c >= 'A' && c <= 'Z') {
                haMaiuscole = true;
            } else if (c >= '0' && c <= '9') {
                haNumeri = true;
            } else {
                haSimboli = true;
            }

            if(haMaiuscole && haNumeri && haSimboli)
                break;

        }

        //calcolo quanti tipi di caratteri diversi ci sono
        int tipiDiCarattere = 1; //perchè le minuscole ci sono sempre
        if (haMaiuscole) tipiDiCarattere++;
        if (haNumeri) tipiDiCarattere++;
        if (haSimboli) tipiDiCarattere++;

        //se hai solo minuscole (tipi == 1), abbasso il punteggio anche se la password è lunga
        if (tipiDiCarattere == 1 && score > 1)
        {

            score = 1; //"Arancio": non è sicura se non vari i caratteri

        }
        //se invece hai un mix perfetto ma lo score è basso perché la pass è corta
        else if (tipiDiCarattere >= 3 && password.length() < 10 && score > 2)
        {

            score = 2; //"Giallo": buona varietà, ma troppo corta per il "Verde"

        }

        //se lo score è 4 ma mancano dei tipi di carattere o è troppo corta,
        //lo declassiamo a Verde Chiaro (3)
        if (score == 4 && (tipiDiCarattere < 4 || password.length() < 12))
        {

            score = 3;

        }

        Color colore = switch (score) {
            case 0 -> new Color(255, 77, 77);   // Rosso (Pessima)
            case 1 -> new Color(255, 166, 77);  // Arancio (Debole)
            case 2 -> new Color(255, 255, 128); // Giallo (Media)
            case 3 -> new Color(144, 238, 144); // Verde chiaro (Buona)
            case 4 -> new Color(3, 244, 252);   // Ciano (Eccellente)
            default -> Color.WHITE;
        };
        PasswordGeneratorGUI.resultField.setBackground(colore);

        String warning = strength.getFeedback().getWarning();
        if (warning == null || warning.isEmpty())
        {

            if (tipiDiCarattere < 4) return "Use symbols, numbers and caps.";

            return (score >= 3) ? "Fantastic password!" : "Good password.";

        }

        return warning;

    }

}
