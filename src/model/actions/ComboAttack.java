package model.actions;

import model.Cell;
import model.Game;
import model.cards.Warrior;
import model.effects.*;
import model.gamestate.AttackState;
import model.triggers.HolyBuff;

import java.util.ArrayList;
import java.util.Arrays;

import static model.actions.Attack.checkWarriorsEffectsForAttack;

public class ComboAttack {
    public static boolean doIt(ArrayList<Cell> attackersCell, Cell defenderCell) {
        Game game = attackersCell.get(0).getBoard().getGame();
        Warrior defender = defenderCell.getWarrior();
        AttackState attackState = new AttackState(null, defender, 0);
        for (Cell attackerCell : attackersCell) {
            Warrior attacker = attackerCell.getWarrior();
            int jumperManhattanDistance = game.getBoard().getJumperManhattanDistance(attackerCell, defenderCell);
            if (checkWarriorsEffectsForAttack(game, attackerCell, defenderCell, jumperManhattanDistance) &&
                    checkWarriorHasComboEffect(attacker) && !attackState.canceled) {
                attackState.ap += attacker.getAp();
                attackState.setAttacker(attacker);
                AntiHolyBuff antiHolyBuff = new AntiHolyBuff(-1, Dispelablity.UNDISPELLABLE);
                if (attacker != attackersCell.get(0).getWarrior()) {
                    attacker.addEffect(antiHolyBuff);
                }
                game.iterateAllTriggersCheck(attackState);
                if (attacker != attackersCell.get(0).getWarrior()) {
                    attacker.removeEffect(antiHolyBuff);
                }
            }
            else {
                return false;
            }
        }
        if (!attackState.canceled) {
            attackersCell.forEach(cell -> cell.getWarrior().getEffects().add(new Attacked()));
            defender.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -1 * attackState.ap));
            attackState.pending = false;
            attackState.setAttacker(attackersCell.get(0).getWarrior());
            game.iterateAllTriggersCheck(attackState);
        }
        return !attackState.canceled;
    }

    private static boolean checkWarriorHasComboEffect(Warrior warrior) {
        return warrior.getEffects().stream().anyMatch(effect -> effect instanceof Combo);
    }
}