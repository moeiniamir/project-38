package model;


import java.util.Date;


public interface Constant {
    interface GameConstants {
        int boardRow = 3;
        int boardColumn = 5;
        long turnTime = 20000;
        Date gameDateObject = new Date();
    }

    interface EffectsTriggersConstants{
        interface CellPoison{
            int poisonBuffDuration = 3;
        }

        interface PoisonBuff{
            int poisonBuffDamage = 1;
        }

        interface HolyBuff{
            int holyBuffReducedDamage = 1;
        }

        interface FiredCell{
            int firedCellDamage = 2;
        }

        interface WoundDeepener{
            int additionalDamage = 5;
        }
    }
}
