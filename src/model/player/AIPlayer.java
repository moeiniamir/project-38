package model.player;


import model.Cell;
import model.Constant;
import model.Deck;
import model.Game;
import model.actions.EndTurn;
import model.actions.Move;
import model.actions.UseCard;
import model.cards.Card;
import model.cards.Warrior;

import java.util.ArrayList;
import java.util.Map;

public class AIPlayer extends Player {
    public AIPlayer(Deck deck) {
        super(deck);
    }

    public void doSomething() {
        outer1:
        for (Warrior warrior : warriors) {
            for (Cell cell : getBoardCells()) {
                if(Move.doIt(warrior.getCell(),cell)){
                    continue outer1;
                }
            }
        }

        outer2:
        for (Map.Entry<Integer, Card> cardEntry : hand.entrySet()) {
            if(cardEntry.getValue()!=null){
                for (Cell cell : getBoardCells()) {
                    if(UseCard.doIt(cardEntry.getKey(),cell)){
                        continue outer2;
                    }
                }
            }
        }

        EndTurn.doIt(getGame());
    }

    private ArrayList<Cell> getBoardCells(){
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < Constant.GameConstants.boardRow; i++) {
            for (int j = 0; j < Constant.GameConstants.boardColumn; j++) {
                cells.add(getGame().getBoard().getCell(i,j));
            }
        }
        return cells;
    }

    private Game getGame(){
        return warriors.get(0).getCell().getBoard().getGame();
    }
}
