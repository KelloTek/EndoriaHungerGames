package fr.kellotek.endoriaHungerGames;

import fr.kellotek.endoriaHungerGames.commands.HungerGamesCmd;
import fr.kellotek.endoriaHungerGames.runnables.Lobby;
import fr.kellotek.endoriaHungerGames.teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class EndoriaHungerGames extends JavaPlugin {

    public static EndoriaHungerGames instance;
    public static String prefix = ChatColor.AQUA + "" + ChatColor.BOLD + "Hunger Games " + ChatColor.GRAY + "» " + ChatColor.WHITE;

    public static final Map<UUID, UUID> playersStandby = new HashMap<>();
    public static final Map<UUID, UUID> playersAlive = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        GameStatus.setStatus(GameStatus.LOBBY);

        TeamManager.createTeams();

        new Lobby().runTaskTimer(EndoriaHungerGames.instance, 0L, 20L);

        new BukkitRunnable(){
            @Override
            public void run(){
                if(!GameStatus.isStatus(GameStatus.LOBBY)){
                    for (Player player : Bukkit.getOnlinePlayers()){
                        if(!playersAlive.containsKey(player.getUniqueId()) && !playersAlive.containsValue(player.getUniqueId())){
                            player.setGameMode(GameMode.SPECTATOR);
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0L, 20L);

        for(World world : Bukkit.getWorlds()){
            world.setPVP(false);
            world.setTime(1000);

            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        }

        Objects.requireNonNull(getCommand("hungergames")).setExecutor(new HungerGamesCmd());
        getServer().getPluginManager().registerEvents(new GameHandler(), this);

        Bukkit.setMaxPlayers(GameSettings.maxPlayers);
    }

    @Override
    public void onLoad(){
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
    }
}
