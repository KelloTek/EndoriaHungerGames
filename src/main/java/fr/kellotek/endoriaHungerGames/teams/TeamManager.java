package fr.kellotek.endoriaHungerGames.teams;

import fr.kellotek.endoriaHungerGames.EndoriaHungerGames;
import fr.kellotek.endoriaHungerGames.GameSettings;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;

public class TeamManager {
    public static List<Team> teams = new ArrayList<>();

    public static int teamSize = GameSettings.teamSize;

    public static void createTeams() {
        TeamManager.teams.add(new Team("red", ChatColor.RED + "Rouge",  ChatColor.RED));
        TeamManager.teams.add(new Team("gold", ChatColor.GOLD + "Orange",  ChatColor.GOLD));
        TeamManager.teams.add(new Team("yellow", ChatColor.YELLOW + "Jaune",  ChatColor.YELLOW));
        TeamManager.teams.add(new Team("green", ChatColor.GREEN + "Vert",  ChatColor.GREEN));
        TeamManager.teams.add(new Team("dark_green", ChatColor.DARK_GREEN + "Vert Foncé",  ChatColor.DARK_GREEN));
        TeamManager.teams.add(new Team("aqua", ChatColor.AQUA + "Cyan",  ChatColor.AQUA));
        TeamManager.teams.add(new Team("dark_aqua", ChatColor.DARK_AQUA + "Cyan Foncé",  ChatColor.DARK_AQUA));
        TeamManager.teams.add(new Team("blue", ChatColor.BLUE + "Bleu",  ChatColor.BLUE));
        TeamManager.teams.add(new Team("pink", ChatColor.LIGHT_PURPLE + "Rose",  ChatColor.LIGHT_PURPLE));
        TeamManager.teams.add(new Team("purple", ChatColor.DARK_PURPLE + "Violet",  ChatColor.DARK_PURPLE));
        TeamManager.teams.add(new Team("white", ChatColor.WHITE + "Blanc",  ChatColor.WHITE));
        TeamManager.teams.add(new Team("gray", ChatColor.GRAY + "Gris",  ChatColor.GRAY));
    }

    public static void setTeam(Player player, String newTeam) {
        for (Team team : teams) {
            if (Objects.equals(team.id, newTeam)) {
                team.members.put(player.getUniqueId(), player.getName());
            }
        }
    }

    public static String setTeamPlay() {
        Team bestTeam = null;
        for (Team team : teams) {
            if (bestTeam == null && team.members.size() == teamSize) continue;
            if (bestTeam == null || (bestTeam.members.size() > team.members.size() && !team.members.isEmpty())) {
                bestTeam = team;
            }

        }

        assert bestTeam != null;
        return bestTeam.id;
    }

    public static void removeTeam(Player player) {
        for (Team team : teams) {
            if (team.members.containsKey(player.getUniqueId()) && team.members.containsValue(player.getName())) {
                team.members.remove(player.getUniqueId());
            }
        }
    }

    public static String getTeam(Player player) {
        for (Team team : teams) {
            if (team.members.containsKey(player.getUniqueId()) && team.members.containsValue(player.getName())) {
                return team.id;
            }
        }
        return "no_team";
    }

    public static String getTeamDisplay(Player player) {
        for (Team team : teams) {
            if (team.members.containsKey(player.getUniqueId())) {
                return team.displayName;
            }
        }
        return ChatColor.WHITE + "Aucune";
    }

    public static String getTeamDisplayColor(Player player) {
        for (Team team : teams) {
            if (team.members.containsKey(player.getUniqueId())) {
                return team.displayNameColor.toString();
            }
        }
        return ChatColor.WHITE.toString();
    }

    public static String getTeamAlive() {
        for (Team team : teams) {
            if (!team.members.isEmpty()) {
                return team.displayName;
            }
        }
        return ChatColor.WHITE + "Aucune";
    }

    public static int getTeamNumber() {
        int remainingTeams = 0;
        for (Team team : teams) {
            if (!team.members.isEmpty()) {
                remainingTeams++;
            }
        }
        return remainingTeams;
    }

    public static void teleportTeam() {
        World world = Bukkit.getWorld("random_uhc");

        assert world != null;
        WorldBorder worldBorder = world.getWorldBorder();
        double borderSize = worldBorder.getSize() / 2;

        Random random = new Random();

        for (Team team : teams) {
            team.location = new Location(world, borderSize * (random.nextDouble() * 2 - 1), 0, borderSize * (random.nextDouble() * 2 - 1));
            for (UUID playerUID : team.members.keySet()) {
                Player player = Bukkit.getPlayer(playerUID);
                if (player != null) {
                    teleport(player, team.location);
                }
            }
        }
    }

    public static void teleport(Player player, Location location) {
        location.setY(Objects.requireNonNull(Bukkit.getWorld("random_uhc")).getHighestBlockYAt(location) + 50);
        player.teleport(location);
        Bukkit.broadcastMessage(EndoriaHungerGames.prefix + ChatColor.WHITE + "Teleportation of " + player.getDisplayName());
    }
}
