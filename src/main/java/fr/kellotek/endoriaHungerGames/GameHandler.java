package fr.kellotek.endoriaHungerGames;

import fr.kellotek.endoriaHungerGames.teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameHandler implements Listener {

    public static Map<UUID, Location> playerDeathLocation = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        e.setJoinMessage(EndoriaHungerGames.prefix + ChatColor.GREEN + "+ " + ChatColor.WHITE + player.getDisplayName()
                + ChatColor.DARK_GRAY + " (" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")");

        if(GameStatus.isStatus(GameStatus.LOBBY)){
            EndoriaHungerGames.playersStandby.put(player.getUniqueId(), player.getUniqueId());

            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setLevel(0);
            player.setExp(0);

            World world = Bukkit.getWorld("world");
            Location loc = new Location(world, 0, 0, 0);


            assert world != null;
            loc.setY(world.getHighestBlockYAt(loc) + 5);
            player.teleport(loc);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        int playerSize = Bukkit.getOnlinePlayers().size();
        playerSize--;

        e.setQuitMessage(EndoriaHungerGames.prefix + ChatColor.RED + "- " + ChatColor.WHITE + player.getDisplayName()
                + ChatColor.DARK_GRAY + " (" + playerSize + "/" + Bukkit.getMaxPlayers() + ")");

        if(GameStatus.isStatus(GameStatus.LOBBY)){
            EndoriaHungerGames.playersStandby.remove(player.getUniqueId(), player.getUniqueId());
            TeamManager.removeTeam(player);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();

        if(!EndoriaHungerGames.playersAlive.containsKey(player.getUniqueId()) && !EndoriaHungerGames.playersAlive.containsValue(player.getUniqueId())
                && !GameStatus.isStatus(GameStatus.LOBBY)){
            e.setFormat(ChatColor.GRAY + "☠ " + player.getDisplayName() + " » " + e.getMessage());
            e.setCancelled(true);

            for(Player players : Bukkit.getOnlinePlayers()){
                players.sendMessage(e.getFormat());
            }
        } else {
            e.setFormat(player.getDisplayName() + ChatColor.GRAY + " » " + ChatColor.WHITE + e.getMessage());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player player = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();

        assert player != null;
        if(GameStatus.isStatus(GameStatus.GAME)){
            for(Player players : Bukkit.getOnlinePlayers()){
                players.playSound(players.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1f, 1f);
            }

            if(killer != null){
                e.setDeathMessage(EndoriaHungerGames.prefix + player.getDisplayName() + ChatColor.WHITE + " à été tué par " + killer.getDisplayName());
            } else {
                e.setDeathMessage(EndoriaHungerGames.prefix + player.getDisplayName() + ChatColor.WHITE + " est mort(e).");
            }

            Location deathLocation = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

            playerDeathLocation.put(player.getUniqueId(), deathLocation);

            TeamManager.removeTeam(player);
            EndoriaHungerGames.playersAlive.remove(player.getUniqueId(), player.getUniqueId());

            player.teleport(playerDeathLocation.get(player.getUniqueId()));
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(GameStatus.isStatus(GameStatus.LOBBY)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e){
        if(GameStatus.isStatus(GameStatus.LOBBY)){
            e.setCancelled(true);
        }
    }
}
