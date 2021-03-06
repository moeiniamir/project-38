package model.targets;

import model.QualityHaver;
import model.gamestate.GameState;

import java.io.Serializable;
import java.util.ArrayList;

public interface TriggerTarget extends Serializable {
    ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState);

    default TriggerTarget and (TriggerTarget other){
        return new TriggerTarget(){

            @Override
            public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
                ArrayList targets = new ArrayList<>();
                try {
                    targets.addAll(TriggerTarget.this.getTarget(triggerOwner, gameState));
                    targets.addAll(other.getTarget(triggerOwner, gameState));
                }catch (Exception e){
                    System.err.println("Trying to add different types of targets to each other.");
                }
                return targets;
            }
        };
    }
}
