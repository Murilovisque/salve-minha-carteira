package com.salveminhacarteira.boletas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoletaManager {

    @Autowired
    private BoletaRepository boletaRepository;

    public List<BoletasAgrupadasPelaDaAcao> getBoletasAgrupadasPorAcao() {
        return null;
    }
}