package kz.akmarzhan.nationaltest.bus.events;

import java.util.List;

import kz.akmarzhan.nationaltest.models.Predmet;

/**
 * Created by Akmarzhan Raushanova on 6/4/17.
 */

public class PredmetListLoadedEvent {

    public List<Predmet> predmets;

    public PredmetListLoadedEvent(List<Predmet> predmets) {
        this.predmets = predmets;
    }
}
