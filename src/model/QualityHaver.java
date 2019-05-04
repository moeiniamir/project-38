package model;

import model.cards.Warrior;
import model.effects.Effect;
import model.triggers.Trigger;

import java.util.ArrayList;

public abstract class QualityHaver {
    protected ArrayList<Effect> effects = new ArrayList<>();
    protected ArrayList<Trigger> triggers = new ArrayList<>();

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public ArrayList<Trigger> getTriggers() {
        return triggers;
    }

    public static Game getGameFromQualityHaver(QualityHaver qualityHaver){
        assert ((qualityHaver instanceof Warrior)|(qualityHaver instanceof Cell));

        if(qualityHaver instanceof Warrior){
            return ((Warrior)qualityHaver).getCell().getBoard().getGame();
        }else {
            return ((Cell)qualityHaver).getBoard().getGame();
        }
    }
}
