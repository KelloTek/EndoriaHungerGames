package fr.kellotek.endoriaHungerGames.runnables;

import fr.kellotek.endoriaHungerGames.EndoriaHungerGames;
import fr.kellotek.endoriaHungerGames.GameSettings;
import fr.kellotek.endoriaHungerGames.GameStatus;
import fr.kellotek.endoriaHungerGames.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Lobby extends BukkitRunnable {

    private int timer = 31;
    public static boolean forceStart = false;

    @Override
    public void run() {
        if(GameStatus.isStatus(GameStatus.LOBBY) && (EndoriaHungerGames.playersStandby.size() >= GameSettings.minPlayers || forceStart)){
            if((timer == 30) || (timer == 20) || (timer == 10) || (timer <= 5)){
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                    player.sendTitle(timer + "", ChatColor.GREEN + "Début de la partie dans", 2, 10, 10);
                }
                Bukkit.broadcastMessage(EndoriaHungerGames.prefix + "Début de la partie dans " + ChatColor.GREEN + timer + "s");
            }

            if(timer == 0){
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(TeamManager.getTeam(player).equals("no_team")){
                        TeamManager.setTeam(player, TeamManager.setTeamPlay());
                    }

                    EndoriaHungerGames.playersAlive.put(player.getUniqueId(), player.getUniqueId());

                    player.getInventory().clear();
                    player.setGameMode(GameMode.ADVENTURE);
                }

                timer = 31;
                GameStatus.setStatus(GameStatus.GAME);
                new Game().runTaskTimer(EndoriaHungerGames.instance, 0L, 20L);
                this.cancel();
            }
            timer--;
        } else {
            if(timer < 31){
                timer = 31;
            }
        }
    }
}
