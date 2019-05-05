package model.triggers;

import model.QualityHaver;
import model.actions.Applier;
import model.conditions.*;
import model.effects.Dispelablity;
import model.gamestate.*;
import model.targets.TriggerTarget;

/*special because of complexity*/
//add triggers and effects with "1" duration to this trigger.
// the target in constructor is meant to be all friends,nearbyEnemies,nearbyFriends
public class Aura extends Trigger {
    private TriggerTarget triggerTarget;
    {
        conditions.add(new HasSpawned().or(new HasDied()).or(new HasMoved()).or(new HasTurnStarted()).or(new IsMoving())
                .or(new IsBeingDispelled()));
    }

    public Aura(int duration, Dispelablity dispelablity, TriggerTarget triggerTarget) {
        super(duration, dispelablity);
        this.triggerTarget=triggerTarget;
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        if(gameState instanceof DeathState || gameState instanceof DispelState){
            removeEffectsAndTriggers(owner,gameState);
        }

        if(gameState instanceof MoveState){
            MoveState moveState = (MoveState) gameState;

            if(!moveState.pending) {
                addEffectsAndTriggers(owner,gameState);
            }else {
                removeEffectsAndTriggers(owner,gameState);
            }
        }

        if(gameState instanceof PutMinionState || gameState instanceof TurnStartState){
            addEffectsAndTriggers(owner,gameState);
        }
    }

    private void addEffectsAndTriggers(QualityHaver owner,GameState gameState){
        triggerTarget.getTarget(owner,gameState).forEach(warrior -> {
            new Applier().execute(this,warrior);
        });
    }

    private void removeEffectsAndTriggers(QualityHaver owner,GameState gameState){
        triggerTarget.getTarget(owner,gameState).forEach(warrior -> {
            warrior.getEffects().removeAll(effects);
            warrior.getTriggers().removeAll(triggers);
        });
    }
}