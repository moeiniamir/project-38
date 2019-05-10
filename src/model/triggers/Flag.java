package model.triggers;

import model.Cell;
import model.Game;
import model.QualityHaver;
import model.cards.Warrior;
import model.conditions.HasDied;
import model.conditions.HasWarriorOnIt;
import model.effects.Dispelablity;
import model.gamestate.GameState;
import model.targets.OnCellGetter;

public class Flag extends Trigger {
    {
        conditions.add(new HasDied().or(new HasWarriorOnIt()));
    }
    public Flag() {
        super(-1, Dispelablity.UNDISPELLABLE);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        Game game =getGameFromQualityHaver(owner);
        game.triggRemoveBuffer.put(this,owner);
        if(owner instanceof Cell){
            game.triggAddBuffer.put(this,((Cell)owner).getWarrior());
//            new OnCellGetter().getTarget(owner,gameState).get(0).getTriggers().add(this);
        }else {
            game.triggAddBuffer.put(this,((Warrior)owner).getCell());
//            ((Warrior)owner).getCell().getTriggers().add(this);
        }
    }
}
