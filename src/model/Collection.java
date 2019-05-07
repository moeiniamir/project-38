package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import view.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Collection implements Serializable {
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private ArrayList<String> decks = new ArrayList<>();
    private HashMap<String, Deck> allDecks = new HashMap<>();
    private HashMap<String, Integer> howManyCard = new HashMap<>();
    private Deck mainDeck = null;
    {//todo danger for test
        mainDeck = Deck.getAllDecks().get("level3");
        decks.add("level3");
        allDecks.put("level3",Deck.getAllDecks().get("level3"));
        decks.add("level2");
        allDecks.put("level2",Deck.getAllDecks().get("level2"));
    }

    //***
    public static Collection getCollection() {
        return Account.getActiveAccount().getCollection();
    }

    public int searchInCollectionCards(String cardName) {
        int numberOf = 0;
        Card card = null;
        for (int ID : getCardIDs()) {
            card = Card.getAllCards().get(ID);
            if (card.getName().equals(cardName)) {
                numberOf++;
            }
        }
        return numberOf;
    }

    public void createDeck(String deckName) {
        for (String template : getDecks()) {
            if (template.toLowerCase().equals(deckName.toLowerCase())) {
                Message.thereIsADeckWhitThisName();
                return;
            }
        }
        Deck deck = new Deck();
        deck.setName(deckName);
        getDecks().add(deckName);
        getAllDecks().put(deckName, deck);
        Deck.getLowerCaseNamesToOriginalName().put(deckName.toLowerCase(), deckName);
        Message.deckCreated();
    }

    public void deleteDeck(String deckName) {
        boolean isDeckNameValid = false;
        for (String template : getDecks()) {
            if (template.toLowerCase().equals(deckName.toLowerCase())) {
                isDeckNameValid = true;
            }
        }
        if (!isDeckNameValid) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        getDecks().remove(deckName);
        getAllDecks().remove(deckName);
        Deck.getLowerCaseNamesToOriginalName().remove(deckName.toLowerCase(), deckName);
        Message.deckDeleted();
    }

    public void addCardToDeck(String cardName, String deckName) {
        if (!Deck.getLowerCaseNamesToOriginalName().containsKey(deckName.toLowerCase())) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(Deck.getLowerCaseNamesToOriginalName().get(deckName.toLowerCase()));
        int cardID = getIDFromName(cardName);
        if (!getCardIDs().contains(cardID)) {
            Message.thereIsNoCardWithThisNameInCollection();
            return;
        }
        Card card = Card.getAllCards().get(cardID);
        if (deck.getCardIDs().contains(cardID)) {
            Message.thereIsACardWithThisNameInThisDeck();
            return;
        }
        if (card instanceof Warrior) {
            Warrior warrior = (Warrior) card;
            if (warrior instanceof Hero) {
                if (deck.getHero() != null) {
                    Message.thereIsAHeroInThisDeck();
                    return;
                } else {
                    deck.setHero((Hero) card);
                    return;
                }
            }
        }
        if (Spell.checkIsItem(card)) {
            if (deck.getItem() != null) {
                Message.thereIsAnItemInThisDeck();
                return;
            } else {
                deck.setItem((Spell) card);
                return;
            }
        }
        if (deck.getCardIDs().size() == 20) {
            Message.have20CardsInThisDeck();
            return;
        }
        int numberOf = 0;
        for (int ID : deck.getCardIDs()) {
            if (ID == cardID) {
                numberOf++;
            }
        }
        if (numberOf > Collection.getCollection().howManyCard.get(cardName)) {
            Message.notEnoughCardNumber();
            return;
        }
        deck.getCardIDs().add(cardID);
        Message.cardAddedToDeckSuccessfully();
    }

    public void removeCardFromDeck(String cardName, String deckName) {
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        int cardID = getIDFromName(cardName);
        if (!deck.getCardIDs().contains(cardID)) {
            Message.thereIsNoCardWithThisIDInThisDeck();
            return;
        }
        deck.getCardIDs().remove((Integer) cardID);
        Message.cardRemovedFromDeckSuccessfully();
    }

    public boolean validateDeck(String deckName, boolean showResultsOrNot) {
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            if (showResultsOrNot) Message.thereIsNoDeckWithThisName();
            return false;
        }
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        if (deck.getCardIDs().size() != 20 || deck.getHero() == null) {
            if (showResultsOrNot) Message.deckIsNotValid();
            return false;
        }
        if (showResultsOrNot) Message.deckIsValid();
        return true;
    }

    public void selectMainDeck(String deckName) {
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        if (validateDeck(deckName, false)) {
            setMainDeck(Account.getActiveAccount().getCollection().getAllDecks().get(deckName));
            Message.deckSelectedAsMain();
        } else {
            Message.deckIsNotValid();
        }
    }

    private int getIDFromName(String cardName) {
        for (int ID : Shop.getShop().getCardIDs()) {
            if (Card.getAllCards().get(ID).getName().equals(cardName)) {
                return ID;
            }
        }
        return -1;
    }

    //***

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public ArrayList<Integer> getCardIDs() {
        return cardIDs;
    }

    public ArrayList<String> getDecks() {
        return decks;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public HashMap<String, Integer> getHowManyCard() {
        return howManyCard;
    }

    public HashMap<String, Deck> getAllDecks() {
        return allDecks;
    }
}
