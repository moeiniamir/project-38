package model.player;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Constant;
import model.Deck;
import model.Game;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;


public abstract class Player implements Serializable {
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private int mana;
    protected Deck mainDeck;
    protected HashMap<Integer, Card> hand = new HashMap<>();
    protected ArrayList<Warrior> warriors = new ArrayList<>();
    protected ArrayList<Card> usedCards = new ArrayList<>();
    private ArrayList<Spell> collectibleItems = new ArrayList<>();
    public boolean ableToReplaceCard;
    private Card nextCard;
    public Image avatar;
    public String username;

    public Player(Deck deck) {
        this.mainDeck = deck;
        for (int i = 0; i < Constant.GameConstants.handSize; i++) {
            hand.put(i, null);
        }
        initializeNextCard();
    }

    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int amount) {
        mana = amount;
        pcs.firePropertyChange("mana", -1, mana);
    }

    public void addMana(int amount) {
        mana += amount;
        pcs.firePropertyChange("mana", -1, mana);
    }

    public void initializeNextCard() {
        int randomIndex = (new Random(System.currentTimeMillis()).nextInt(this.getMainDeck().getCardIDs().size()));
        nextCard = Card.getAllCards().get(this.getMainDeck().getCardIDs().get(randomIndex)).deepCopy();
    }

    public ArrayList<Card> getUsedCards() {
        return usedCards;
    }

    public ArrayList<Warrior> getWarriors() {
        return warriors;
    }

    public Hero getPlayerHero() {
        Optional<Warrior> optional = warriors.stream().filter(warrior -> warrior instanceof Hero).findFirst();
        return (Hero) optional.orElse(null);
    }

    public HashMap<Integer, Card> getHand() {
        return hand;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public ArrayList<Spell> getCollectibleItems() {
        return collectibleItems;
    }

    public Card getNextCard() {
        return nextCard;
    }

    public Game getGame() {
        return warriors.get(0).getCell().getBoard().getGame();
    }
}