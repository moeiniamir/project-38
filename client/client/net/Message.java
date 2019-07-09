package client.net;

public enum Message {
    saveTheGame, quitTheGame, createAccount, showLeaderBoard, login, startTheGame, setAnActiveAccount, accountDeckIsValid,
    accountDeckIsNotValid, showPreviousMessages, sendMessage , IamActiveNow , IamOfflineNow,
////////////////game info
    HeroPowerOfPlayer,WarriorOfACell,IndexofAvatar,PlayerUsername,SpecificCell,NextCard,HandCard,ActivePlayerIndex,isMyWarrior,isThereWarrior,itsWarrior,itsSpell,
//////////////game actions
    useCard,useSpecialPower,useCollectible,attack,comboAttack,move,endTurn,quit
}
