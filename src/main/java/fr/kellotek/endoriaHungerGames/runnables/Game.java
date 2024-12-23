package fr.kellotek.endoriaHungerGames.runnables;

import fr.kellotek.endoriaHungerGames.EndoriaHungerGames;
import fr.kellotek.endoriaHungerGames.GameSettings;
import fr.kellotek.endoriaHungerGames.GameStatus;
import fr.kellotek.endoriaHungerGames.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class Game extends BukkitRunnable {

    public static boolean pvp = false;
    public static int pvpTimer = 60;

    @Override
    public void run() {
        if(GameStatus.isStatus(GameStatus.GAME)){
            Bukkit.setMaxPlayers(GameSettings.maxPlayers * 2);

            if(pvpTimer == 0 && !pvp){
                for(World world : Bukkit.getWorlds()){
                    world.setPVP(true);
                }
                pvp = true;
                Bukkit.broadcastMessage(EndoriaHungerGames.prefix + "Le combat entre les joueurs est activé !");
            }

            if(TeamManager.getTeamNumber() <= 1 && EndoriaHungerGames.playersAlive.size() <= TeamManager.teamSize){
                GameStatus.setStatus(GameStatus.END);
                Bukkit.broadcastMessage(EndoriaHungerGames.prefix + "L'équipe gagnante est " + TeamManager.getTeamAlive());
                new End().runTaskTimer(EndoriaHungerGames.instance, 0L, 20L);
                this.cancel();
            }

            pvpTimer--;
        }
    }
}
