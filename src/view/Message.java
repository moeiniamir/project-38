package view;

public class Message {
    public static void thereIsNoCardWithThisName() {
        System.out.println("There is no card with this name in shop cards");
    }
    public static void haveNotEnoughMoney() {
        System.out.println("You haven't enough money");
    }
    public static void buyWasSuccessful(){
        System.out.println("You bought the cart successfully");
    }
    public static void have3Items(){
        System.out.println("You have 3 items. You couldn't buy any other item");
    }
    public static void hava20Cards(){
        System.out.println("You have 20 cards. You couldn't buy any other card");
    }
    public static void haveNotThisCard(){
        System.out.println("You haven't this card. You can't sell it");
    }
    public static void sellWasSuccessful(){
        System.out.println("You sell the card successfully");
    }
}

