package fr.kellotek.endoriaHungerGames;

public class GameSettings {

    private static String getConfigString(String path){
        return EndoriaHungerGames.instance.getConfig().getString(path);
    }

    private static int getConfigInt(String path){
        return EndoriaHungerGames.instance.getConfig().getInt(path);
    }

    public static int maxPlayers = getConfigInt("maxPlayers");
    public static int minPlayers = getConfigInt("minPlayers");
    public static int teamSize = getConfigInt("teamSize");
}