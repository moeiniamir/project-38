package model.cards;

import model.Cell;
import model.QualityHaver;
import model.actions.SpellAction;
import model.player.Player;
import model.targets.SpellTarget;

import java.util.HashMap;
import java.util.Map;

public class Spell extends Card {
    private boolean isItem;
    HashMap<SpellAction, SpellTarget> actions = new HashMap<>();

    public Spell(int ID, String name, int requiredMana, int price, boolean isItem) {
        super(ID, name, price, requiredMana);
        this.isItem = isItem;
    }

    public static boolean checkIsItem(Card card) {
        if (card instanceof Spell) {
            Spell spell = (Spell) card;
            return spell.isItem;
        }
        return false;
    }

    public boolean isItem() {
        return isItem;
    }

    @Override
    public void apply(Cell cell) {
        //todo
        Player user = cell.getBoard().getGame().getActivePlayer();
        for (Map.Entry<SpellAction, SpellTarget> entry : actions.entrySet()) {
            for (QualityHaver target : entry.getValue().getTarget(user, cell)) {
                entry.getKey().execute(user,target);
            }
        }
    }

    @Override
    public Spell deepCopy() {
        //todo
        return null;
    }
}
