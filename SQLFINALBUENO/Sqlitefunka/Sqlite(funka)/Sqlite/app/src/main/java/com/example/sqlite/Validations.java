package com.example.sqlite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
    /**
     * Valida rut de la forma XXXXXXXX-X
     */
    public static Boolean validarRut(String rut) {
        Pattern pattern = Pattern.compile("^[0-9]+-[0-9kK]{1}$");
        Matcher matcher = pattern.matcher(rut);
        if (!matcher.matches()) return false;
        String[] stringRut = rut.split("-");
        return stringRut[1].equalsIgnoreCase(dv(stringRut[0]));
    }

    /**
     * Valida el dígito verificador
     */
    public static String dv(String rut) {
        Integer M = 0, S = 1, T = Integer.parseInt(rut);
        for (; T != 0; T = (int) Math.floor(T /= 10))
            S = (S + T % 10 * (9 - M++ % 6)) % 11;
        return (S > 0) ? String.valueOf(S - 1) : "k";
    }

    /**
     * Valida nombre y apellido
     */
    public static boolean validarNombre(String nombre) {
        return nombre.matches("[a-zA-Z\\s]+") && nombre.length() >= 3;
    }

    /**
     * Valida descripción
     */
    public static boolean validarDescripcion(String descripcion) {
        return !descripcion.isEmpty();
    }
}
