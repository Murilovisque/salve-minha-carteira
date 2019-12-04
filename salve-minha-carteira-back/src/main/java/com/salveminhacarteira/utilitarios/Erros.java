package com.salveminhacarteira.utilitarios;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;

public final class Erros {

    public static final int MYSQL_DUPLICATE_ENTRY_CODE = 1062;
    public static final int MYSQL_ER_NO_REFERENCED_ROW_2 = 1452;

    public static boolean ehRegistroDuplicado(DataIntegrityViolationException ex) {
        var specEx = ex.getMostSpecificCause();
        if (specEx instanceof SQLIntegrityConstraintViolationException)
            return ((SQLIntegrityConstraintViolationException) specEx).getErrorCode() == MYSQL_DUPLICATE_ENTRY_CODE;
        return false;
    }

    public static boolean ehAssociacaoDeEntidadeNaoExistente(DataIntegrityViolationException ex) {
        var specEx = ex.getMostSpecificCause();
        if (specEx instanceof SQLIntegrityConstraintViolationException) 
            return ((SQLIntegrityConstraintViolationException) specEx).getErrorCode() == MYSQL_ER_NO_REFERENCED_ROW_2;
        return false;
    }
}