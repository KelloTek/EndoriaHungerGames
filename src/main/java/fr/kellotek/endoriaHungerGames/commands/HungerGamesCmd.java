package fr.kellotek.endoriaHungerGames.commands;

import fr.kellotek.endoriaHungerGames.EndoriaHungerGames;
import fr.kellotek.endoriaHungerGames.GameStatus;
import fr.kellotek.endoriaHungerGames.runnables.Lobby;
import fr.kellotek.endoriaHungerGames.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HungerGamesCmd implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
        Player player = (Player) sender;

        if(args.length == 0){
            return false;
        }

        switch (args[0]){
            case "start":
                if(player.hasPermission("endoriahungergames.admin") && GameStatus.isStatus(GameStatus.LOBBY)){
                    Lobby.forceStart = true;
                }
                break;

            case "set":
                if(player.hasPermission("endoriahungergames.admin") && args[1].equals("team")){
                    Player target = Bukkit.getPlayer(args[2]);
                    assert target != null;
                    TeamManager.removeTeam(target);
                    TeamManager.setTeam(target, args[3]);

                    target.sendMessage(EndoriaHungerGames.prefix + "Vous êtes maintenant dans l'équipe " + TeamManager.getTeamDisplay(target));
                    player.sendMessage(EndoriaHungerGames.prefix + ChatColor.YELLOW + target.getDisplayName() + ChatColor.WHITE
                            + " est maintenant dans l'équipe " + TeamManager.getTeamDisplay(target));
                }
                break;

            case "get":
                if(player.hasPermission("endoriahungergames.admin") && args[1].equals("team")){
                    Player target = Bukkit.getPlayer(args[2]);
                    assert target != null;
                    player.sendMessage(EndoriaHungerGames.prefix + ChatColor.YELLOW + target.getDisplayName() + ChatColor.WHITE
                            + " est dans l'équipe " + TeamManager.getTeamDisplay(target));
                }
                break;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
        List<String> tr = new ArrayList<>();

        if(args.length == 1) {
            tr.add("start");
            tr.add("set");
            tr.add("get");
        }

        if(args.length == 2 && (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("get"))) {
            tr.add("team");
        }

        if(args.length == 3 && (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("get"))){
            for(Player player : Bukkit.getOnlinePlayers()){
                tr.add(player.getName().toLowerCase());
            }
        }

        if(args.length == 4 && args[0].equalsIgnoreCase("set")) {
            tr.add("red");
            tr.add("gold");
            tr.add("yellow");
            tr.add("green");
            tr.add("dark_green");
            tr.add("aqua");
            tr.add("dark_aqua");
            tr.add("blue");
            tr.add("pink");
            tr.add("purple");
            tr.add("white");
            tr.add("gray");
        }

        return tr;
    }
}
