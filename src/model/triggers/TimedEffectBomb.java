package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.TurnEnded;
import model.effects.Dispelablity;
import model.gamestate.GameState;

public class TimedEffectBomb extends Trigger {
    {
        conditions.add(new TurnEnded());
        conditions.add(((gameState, trigger) -> duration == 1));
    }
    public TimedEffectBomb(Warrior warrior, int duration, Dispelablity dispelablity) {
        super(warrior, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        getWarrior().getEffects().addAll(effects);
        addTriggersToWarriorFromTrigger(getWarrior(),triggers);
    }
}
