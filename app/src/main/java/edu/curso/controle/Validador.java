package edu.curso.controle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import javafx.beans.property.StringProperty;

// Cada método devolve "" quando o valor é válido, ou a mensagem de erro do campo
public class Validador {

    private static final DateTimeFormatter FORMATO =
        DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private Validador() {
    }

    private static String soDigitos(String v) {
        return v == null ? "" : v.replaceAll("\\D", "");
    }

    private static boolean vazio(String v) {
        return v == null || v.trim().isEmpty();
    }

    public static String texto(String v) {
        if (vazio(v)) return "Campo obrigatório";
        if (v.trim().length() > 100) return "Máximo de 100 caracteres";
        return "";
    }

    public static String nome(String v) {
        if (vazio(v)) return "Informe o nome";
        if (v.trim().length() < 3) return "O nome deve ter ao menos 3 caracteres";
        if (!v.trim().matches("[\\p{L} .'-]+")) return "O nome deve conter apenas letras";
        return "";
    }

    public static String email(String v) {
        if (vazio(v)) return "Informe o email";
        if (!v.trim().matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) return "Email inválido";
        return "";
    }

    public static String telefone(String v) {
        if (vazio(v)) return "Informe o telefone";
        int tam = soDigitos(v).length();
        if (tam != 10 && tam != 11) return "Telefone deve ter 10 ou 11 dígitos (com DDD)";
        return "";
    }

    public static String cpf(String v) {
        if (vazio(v)) return "Informe o CPF";
        String d = soDigitos(v);
        if (d.length() != 11) return "CPF deve ter 11 dígitos";
        if (d.chars().distinct().count() == 1) return "CPF inválido";
        for (int t = 9; t <= 10; t++) {
            int soma = 0;
            for (int i = 0; i < t; i++) {
                soma += (d.charAt(i) - '0') * (t + 1 - i);
            }
            int r = (soma * 10) % 11;
            if (r == 10) r = 0;
            if (r != d.charAt(t) - '0') return "CPF inválido";
        }
        return "";
    }

    public static String cnpj(String v) {
        if (vazio(v)) return "Informe o CNPJ";
        String d = soDigitos(v);
        if (d.length() != 14) return "CNPJ deve ter 14 dígitos";
        if (d.chars().distinct().count() == 1) return "CNPJ inválido";
        int[] p1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] p2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        if (digitoCnpj(d, p1) != d.charAt(12) - '0') return "CNPJ inválido";
        if (digitoCnpj(d, p2) != d.charAt(13) - '0') return "CNPJ inválido";
        return "";
    }

    private static int digitoCnpj(String d, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += (d.charAt(i) - '0') * pesos[i];
        }
        int r = soma % 11;
        return r < 2 ? 0 : 11 - r;
    }

    public static String data(String v) {
        if (vazio(v)) return "Informe a data";
        try {
            LocalDate.parse(v.trim(), FORMATO);
        } catch (DateTimeParseException e) {
            return "Data inválida (use dd/MM/aaaa)";
        }
        return "";
    }

    public static String nascimento(String v) {
        String erro = data(v);
        if (!erro.isEmpty()) return erro;
        if (parseData(v).isAfter(LocalDate.now())) return "A data não pode ser no futuro";
        return "";
    }

    public static String decimal(String v) {
        if (vazio(v)) return "Informe o valor";
        try {
            if (parseDecimal(v) < 0) return "O valor não pode ser negativo";
        } catch (NumberFormatException e) {
            return "Valor inválido (ex.: 10,50)";
        }
        return "";
    }

    public static String inteiro(String v) {
        if (vazio(v)) return "Informe a quantidade";
        try {
            if (Integer.parseInt(v.trim()) < 0) return "O valor não pode ser negativo";
        } catch (NumberFormatException e) {
            return "Informe um número inteiro";
        }
        return "";
    }

    public static String decimalPositivo(String v) {
        String erro = decimal(v);
        if (erro.isEmpty() && parseDecimal(v) <= 0) return "O valor deve ser maior que zero";
        return erro;
    }

    public static String inteiroPositivo(String v) {
        String erro = inteiro(v);
        if (erro.isEmpty() && Integer.parseInt(v.trim()) <= 0) return "A quantidade deve ser maior que zero";
        return erro;
    }

    // true quando todas as mensagens de erro estão vazias
    public static boolean semErros(StringProperty... erros) {
        for (StringProperty erro : erros) {
            if (!erro.get().isEmpty()) return false;
        }
        return true;
    }

    public static LocalDate parseData(String v) {
        return LocalDate.parse(v.trim(), FORMATO);
    }

    public static String formatarData(LocalDate d) {
        return d == null ? "" : d.format(FORMATO);
    }

    public static double parseDecimal(String v) {
        return Double.parseDouble(v.trim().replace(',', '.'));
    }
}
