package com.salveminhacarteira.utilitarios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.BiPredicate;

public final class Checador {

    public static <T> void validarListas(List <T> listaEsperada, List<T> listaParaValidar, BiPredicate<T, T> comparador) {
        assertEquals(listaEsperada.size(), listaParaValidar.size());
        for (var validar: listaParaValidar) {
            var iteratorEsperado = listaEsperada.iterator();
            while (iteratorEsperado.hasNext()) {
                var esperado = iteratorEsperado.next();
                if (comparador.test(esperado, validar)) {
                    iteratorEsperado.remove();
                    break;
                }
            }
        }
        assertTrue("Lista recebida não confere com a lista esperada", listaEsperada.isEmpty());
    }
}