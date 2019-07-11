package model.actions;

import model.Game;
import model.QualityHaver;
import model.cards.Warrior;
import model.gamestate.DeathState;
import model.player.Player;

public class Killer implements AutoAction {
    @Override
    public boolean execute(QualityHaver source, QualityHaver target) { // MOEINI معینی، برای اینکه این رو هم وصلش کنیم به کلاینت، نیاز  به سرورسشن داریم. الان من نمیدونم سرورسشنِ این کدومه...
        assert target instanceof Warrior;
        kill((Warrior) target);
        return true;//todo: it always gets done right?
    }

    public static void kill(Warrior warrior) {
        Game game = warrior.getCell().getBoard().getGame();
        Player player = game.getWarriorsPlayer(warrior);
        DeathState death = new DeathState(warrior);
        game.iterateAllTriggersCheck(death);
        warrior.getCell().setWarrior(null);
        player.getWarriors().remove(warrior);

        ArenaController.ac.kill(warrior.getCell().getRow(), warrior.getCell().getColumn());
    }
}
