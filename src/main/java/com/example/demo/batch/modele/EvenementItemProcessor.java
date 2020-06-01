package com.example.demo.batch.modele;

import org.springframework.batch.item.ItemProcessor;

public class EvenementItemProcessor implements ItemProcessor<Evenement, Evenement> {

    @Override
    public Evenement process(Evenement user) throws Exception {
        return user;

    }
}
