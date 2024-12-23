package fr.kellotek.endoriaHungerGames.teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Team {
    public Map<UUID, String> members = new HashMap<>();

    public String id;
    public String displayName;
    public ChatColor displayNameColor;
    public Location location;

    public Team(String id, String displayName, ChatColor displayNameColor){
        this.id = id;
        this.displayName = displayName;
        this.displayNameColor = displayNameColor;
        this.location = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    }

    public Material getMaterial(){
        return switch (id){
            case "red" ->
                    Material.RED_WOOL;
            case "gold" ->
                    Material.ORANGE_WOOL;
            case "yellow" ->
                    Material.YELLOW_WOOL;
            case "green" ->
                    Material.LIME_WOOL;
            case "dark_green" ->
                    Material.GREEN_WOOL;
            case "aqua" ->
                    Material.LIGHT_BLUE_WOOL;
            case "dark_aqua" ->
                    Material.CYAN_WOOL;
            case "blue" ->
                    Material.BLUE_WOOL;
            case "pink" ->
                    Material.PINK_WOOL;
            case "purple" ->
                    Material.PURPLE_WOOL;
            case "white" ->
                    Material.WHITE_WOOL;
            case "gray" ->
                    Material.LIGHT_GRAY_WOOL;
            default -> Material.BLACK_WOOL;
        };
    }
}
