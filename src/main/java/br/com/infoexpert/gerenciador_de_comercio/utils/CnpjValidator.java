package br.com.infoexpert.gerenciador_de_comercio.utils;

public class CnpjValidator {

    public static boolean isValid(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}")) return false;

        // Rejeita CNPJs com todos os dígitos iguais
        if (cnpj.chars().distinct().count() == 1) return false;

        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        // 1º dígito verificador
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += (cnpj.charAt(i) - '0') * weights1[i];
        }
        int digit1 = 11 - (sum % 11);
        digit1 = (digit1 > 9) ? 0 : digit1;

        // 2º dígito verificador
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += (cnpj.charAt(i) - '0') * weights2[i];
        }
        int digit2 = 11 - (sum % 11);
        digit2 = (digit2 > 9) ? 0 : digit2;

        return cnpj.charAt(12) - '0' == digit1 &&
                cnpj.charAt(13) - '0' == digit2;
    }
}
