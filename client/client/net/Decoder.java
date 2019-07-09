package client.net;

import com.google.gson.Gson;
import model.Cell;
import model.cards.HeroPower;
import model.cards.Spell;
import model.cards.Warrior;
import view.fxmlControllers.ArenaController;
import view.fxmlControllers.GlobalChatController;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Decoder {
    public static void decode(Message m) {
        switch (m) {
            case HeroPowerOfPlayer:
                fillBoxAndNotifyJ(Digikala.hp, HeroPower.class);
                break;
            case WarriorOfACell:
                fillBoxAndNotifyJ(Digikala.warriorBox, Warrior.class);
                break;
            case IndexofAvatar:
                fillBoxAndNotify(Digikala.avatarIndex);
                break;
            case PlayerUsername:
                fillBoxAndNotify(Digikala.playerUsername);
                break;
            case SpecificCell:
                fillBoxAndNotifyJ(Digikala.specificCell, Cell.class);
                break;
            case NextCard:
                if (readMessage().equals(Message.itsSpell)) {
                    fillBoxAndNotifyJ(Digikala.nextCard, Spell.class);
                } else {
                    fillBoxAndNotifyJ(Digikala.nextCard, Warrior.class);
                }
                break;
            case HandCard:
                if (readMessage().equals(Message.itsSpell)) {
                    fillBoxAndNotifyJ(Digikala.handCard, Spell.class);
                } else {
                    fillBoxAndNotifyJ(Digikala.handCard, Warrior.class);
                }
                break;
            case ActivePlayerIndex:
                fillBoxAndNotify(Digikala.activePlayerIndex);
                break;
            case updateMessages: {
                GlobalChatController.updateMessages();
                break;
            }
            case isMyWarrior:
                fillBoxAndNotify(Digikala.isMyWarrior);
                break;
            case isThereWarrior:
                fillBoxAndNotify(Digikala.isThereWarrior);
                break;
            //-----------------
            case put: {
                int hero_row = (int) readObject();
                int hero_col = (int) readObject();
                String name = (String) readObject();
                ArenaController.ac.put(hero_row, hero_col, name);
                break;
            }
            case quitTheGame: {
                String winnerName = (String) readObject();
                ArenaController.ac.endGame(winnerName);
                break;
            }
            case move: {
                int original_row = (int) readObject();
                int original_col = (int) readObject();
                int target_row = (int) readObject();
                int target_col = (int) readObject();
                ArenaController.ac.move(original_row, original_col, target_row, target_col);
                break;
            }
            case attack: {
                int attackerCell_row = (int) readObject();
                int attackerCell_col = (int) readObject();
                int defenderCell_row = (int) readObject();
                int defenderCell_col = (int) readObject();
                ArenaController.ac.attack(attackerCell_row, attackerCell_col, defenderCell_row, defenderCell_col);
                break;
            }
            case useCard: {
                int handMapKey = (int) readObject();
                ArenaController.ac.useCard(handMapKey);
                break;
            }
            case cast: {
                int heroCell_row = (int) readObject();
                int heroCell_col = (int) readObject();
                ArenaController.ac.cast(heroCell_row, heroCell_col);
                break;
            }
            case setCoolDown: {
                int remainedTurnToCoolDown = (int) readObject();
                int playerNumber = (int) readObject();
                ArenaController.ac.setActiveMana(remainedTurnToCoolDown, playerNumber);
                break;
            }
            case useCollectible: {
                int indexOf = (int) readObject();
                int playerNumber = (int) readObject();
                ArenaController.ac.useCollectibleItem(indexOf, playerNumber);
                break;
            }
            //---------------
        }
    }

    public static Message readMessage() {
        try {
            return Message.values()[ClientSession.dis.readInt()];
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object readObject() {
        try (ObjectInputStream ois = new ObjectInputStream(ClientSession.dis)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> void fillBoxAndNotify(Box<T> box) {
        box.obj = (T) readObject();
        synchronized (box.waitStone) {
            box.waitStone.notify();
        }
    }

    public static <T> void fillBoxAndNotifyJ(Box<T> box, Class aClass) {
        Gson gson = new Gson();
        try {
            box.obj = (T) gson.fromJson(ClientSession.dis.readUTF(), aClass);
            synchronized (box.waitStone) {
                box.waitStone.notify();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
