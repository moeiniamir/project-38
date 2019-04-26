package model;


import java.util.Date;


public interface Constant {
    interface GameConstants {
        int boardRow = 3;
        int boardColumn = 5;
        int turnTime = 20000;
    }

    interface EffectsTriggersConstants{
        interface PoisonBuff{
            int poisonBuffDuration = 3;
            int poisonBuffDamage = 1;
        }
    }
}
