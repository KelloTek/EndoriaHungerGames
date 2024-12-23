package fr.kellotek.endoriaHungerGames;

public enum GameStatus {

    LOBBY, GAME, END;

    public static GameStatus gameStatus;

    public static void setStatus(GameStatus status){
        gameStatus = status;
    }

    public static boolean isStatus(GameStatus status){
        return gameStatus == status;
    }

    public static GameStatus getStatus(){
        return gameStatus;
    }
}
