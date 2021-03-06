package model.triggers;

import model.QualityHaver;
import model.conditions.Condition;
import model.effects.Dispelablity;
import model.gamestate.EffTriggApplyState;
import model.gamestate.GameState;

import java.io.Serializable;

//special because of unique action and condition.
public class EffTriggImmune extends Trigger {
    private Class aClass;

    {
        conditions.add((Condition & Serializable) (gameState, trigger, triggerOwner) -> {
            if(!(gameState instanceof EffTriggApplyState)){
                return false;
            }
            EffTriggApplyState state = (EffTriggApplyState)gameState;
            return state.getTarget().equals(triggerOwner) && state.getEffOrTrigg().getClass().equals(aClass);
        });
    }

    public EffTriggImmune(int duration, Dispelablity dispelablity,Class aClass) {
        super(duration, dispelablity);
        this.aClass = aClass;
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        ((EffTriggApplyState)gameState).canceled=true;
    }
}
