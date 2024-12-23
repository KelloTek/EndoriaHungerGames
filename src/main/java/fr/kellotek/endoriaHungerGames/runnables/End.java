package fr.kellotek.endoriaHungerGames.runnables;

import fr.kellotek.endoriaHungerGames.EndoriaHungerGames;
import fr.kellotek.endoriaHungerGames.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class End extends BukkitRunnable {

    private int timer = 6;

    @Override
    public void run() {
        if(GameStatus.isStatus(GameStatus.END)){
            if((timer == 30) || (timer == 20) || (timer == 10) || (timer <= 5)){
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                    player.sendTitle(timer + "", ChatColor.RED + "Redirection vers le hub dans", 2, 10, 10);
                }
                Bukkit.broadcastMessage(EndoriaHungerGames.prefix + "Redirection vers le hub dans " + ChatColor.GREEN + timer + "s");
            }

            if(timer == 0){
                this.cancel();
            }
            timer--;
        }
    }
}
