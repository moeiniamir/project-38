package model.targets;

import model.Board;
import model.Cell;
import model.Game;
import model.QualityHaver;
import model.cards.Hero;
import model.cards.Warrior;
import model.gamestate.GameState;
import model.player.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class WithinDistanceGetter implements TriggerTarget,SpellTarget {
    private boolean friendMod;
    private boolean heroToo;
    private int distance;

    public WithinDistanceGetter(boolean friendMod, int distance, boolean heroToo) {
        this.friendMod = friendMod;
        this.distance = distance;
        this.heroToo = heroToo;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        Warrior warrior = (Warrior) triggerOwner;
        Board board = warrior.getCell().getBoard();
        Game game = board.getGame();
        return board.getCellWithinDistance(warrior.getCell(), distance).stream().map(Cell::getWarrior)
                .filter(Objects::nonNull).filter(warrior1 -> !(warrior1 instanceof Hero) || heroToo)
                .filter(warrior1 -> friendMod == game.getWarriorsPlayer(warrior).equals(game.getWarriorsPlayer(warrior1)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(Player spellOwner, Cell cell) {
        Board board = cell.getBoard();
        Game game = board.getGame();
        return board.getCellWithinDistance(cell, distance).stream().map(Cell::getWarrior)
                .filter(Objects::nonNull).filter(warrior1 -> !(warrior1 instanceof Hero) || heroToo)
                .filter(warrior1 -> friendMod == spellOwner.equals(game.getWarriorsPlayer(warrior1)))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
