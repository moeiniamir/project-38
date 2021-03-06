package model.triggers;

import model.QualityHaver;
import model.actions.Attack;
import model.conditions.HasBeenAttacked;
import model.effects.Dispelablity;
import model.exceptions.NotEnoughConditions;
import model.gamestate.AttackState;
import model.gamestate.GameState;
import server.net.Message;

//special because it's so common. also the action attack doesn't implement AutoAction.
//this triggers should be added to all minions, with -1 duration.

public class CounterAttack extends Trigger {
    {
        conditions.add(new HasBeenAttacked());
    }

    public CounterAttack(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        AttackState attackState = (AttackState) gameState;
        try {
            Attack.doIt(attackState.getAttacked().getCell(), attackState.getAttacker().getCell(), true);
            attackState.getAttacker().getCell().getBoard().getGame().cm.sendToBothPlayers(Message.attack,
                    attackState.getAttacked().getCell().getRow(), attackState.getAttacked().getCell().getColumn(),
                    attackState.getAttacker().getCell().getRow(), attackState.getAttacker().getCell().getColumn());
        } catch (NotEnoughConditions notEnoughConditions) {
            //no problem
        }
    }
}
