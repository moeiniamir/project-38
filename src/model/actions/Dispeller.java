package model.actions;

import model.QualityHaver;
import model.effects.Dispelablity;
import model.gamestate.DispelState;
import model.player.Player;
import model.triggers.Trigger;

public class Dispeller implements TriggerAction,SpellAction{
    private Dispelablity dispelType;

    public Dispeller(Dispelablity dispelType) {
        this.dispelType = dispelType;
    }

    @Override
    public void execute(QualityHaver source, QualityHaver target) {
        dispel(target,dispelType);
    }

    @Override
    public void execute(Player spellOwner, QualityHaver target) {

    }

    public static void dispel(QualityHaver qualityHaver,Dispelablity dispelType){
        qualityHaver.getEffects().removeIf(effect -> effect.getDispelablity().equals(dispelType));

        for (Trigger trigger : qualityHaver.getTriggers()) {
            if(trigger.getDispelablity().equals(dispelType)) {
                DispelState state = new DispelState(trigger);
                QualityHaver.getGameFromQualityHaver(qualityHaver).iterateAllTriggersCheck(state);
                qualityHaver.getTriggers().remove(trigger);
            }
        }
    }
}
