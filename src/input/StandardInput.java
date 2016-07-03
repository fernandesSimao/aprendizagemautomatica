package input;

import java.io.*;

/**
 * Esta classe fornece m�todos est�ticos que permitem ler
 * tipos primitivos directamente a partir do standard input.
 * @version 1.0 - 16 Mar�o 1999
 * @author Vitor Carreira
 */
public class StandardInput {

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


    // N�o permite construir nenhum objecto
    private StandardInput() {
    }

    /**
     * L� uma string do standard input. Devolve a string em caso de sucesso. Caso
     * contr�rio, devolve null
     * @return devolve a string lida */
    public static String readString() {
        String s = null;
        try {
            s = in.readLine();
        } catch (IOException e) {
            System.err.println("Erro de leitura");
        }
        return s;
    }

    /**
     * L� o tipo primitivo int do standard input. Este m�todo entra em ciclo at�
     * que o valor lido seja um inteiro v�lido
     * @return o inteiro lido */
    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(readString());
            } catch (Exception e) {
                System.err.println("Erro: Inteiro inválido!");
            }
        }
    }

    /**
     * L� o tipo primitivo long do standard input. Este m�todo entra em ciclo at�
     * que o valor lido seja um long v�lido
     * @return o long lido */
    public static long readLong() {
        while (true) {
            try {
                return Long.parseLong(readString());
            } catch (Exception e) {
                System.err.println("Erro: Long inválido!");
            }
        }
    }

    /**
     * L� o tipo primitivo float do standard input. Este m�todo entra em ciclo at�
     * que o valor lido seja um float v�lido
     * @return o float lido */
    public static float readFloat() {
        while (true) {
            try {
                return Float.parseFloat(readString());
            } catch (Exception e) {
                System.err.println("Erro: Inteiro inv�lido!");
            }
        }
    }

    /**
     * L� o tipo primitivo double do standard input. Este m�todo entra em ciclo at�
     * que o valor lido seja um double v�lido
     * @return o double lido */
    public static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(readString());
            } catch (Exception e) {
                System.err.println("Erro: Double inv�lido!");
            }
        }
    }

    /**
     * L� o tipo primitivo boolean do standard input. Este m�todo entra em ciclo
     * ate que o valor lido seja um boolean v�lido
     * @return o boolean lido */
    public static boolean readBoolean() {
        while (true) {
            String s = readString();
            if (s != null) {
                if (s.equalsIgnoreCase("true")) {
                    return true;
                } else if (s.equalsIgnoreCase("false")) {
                    return false;
                }
            }
            System.err.println("Erro: Boolean inv�lido!");
        }
    }
}
