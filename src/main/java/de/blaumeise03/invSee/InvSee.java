/*
 * Copyright (c) 2019 Blaumeise03
 */

package de.blaumeise03.invSee;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public class InvSee extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Enabling...");

        getLogger().info("Adding Commands...");
        new Command("invSee", "Zeigt dir das Inventar eines Spielers.", new Permission("invSee.invSee"), true) {
            @Override
            public void onCommand(String[] args, CommandSender sender) {
                if (args.length == 0) {
                    sender.sendMessage("§4Bitte geb einen Spieler an: /invSee <Spieler>");
                    return;
                }

                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    if (args.length == 2) {
                        /*if(args[1].equalsIgnoreCase("offline")){
                            sender.sendMessage("§4Suche nach offline-Spieler...");
                            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                                if(offlinePlayer.hasPlayedBefore()){
                                    if(offlinePlayer.getName() != null)
                                        if(offlinePlayer.getName().equalsIgnoreCase(args[0])){
                                            target = offlinePlayer.getPlayer();
                                            break;
                                        }
                                }
                            }
                        }*/ //Does not work, can't get Player-Data of offline-Players
                    } else {
                        sender.sendMessage("§4Kann den angegebenen Spieler nicht finden!");
                        return;
                    }
                }
                if (target == null) {
                    sender.sendMessage("§4Konnte den angegebenen Spieler nicht finden!");
                    return;
                }
                Player player = (Player) sender;
                Inventory inventory = target.getInventory();
                getLogger().info("Player " + player.getName() + "opens the Inventory of " + target.getName() + ".");
                player.openInventory(inventory);
                player.sendMessage("§aDu bist nun im Inventar von §6" + target.getName());
            }
        };

        new Command("enderSee", "Zeigt dir die Enderkiste eines Spielers.", new Permission("invSee.enderSee"), true) {
            @Override
            public void onCommand(String[] args, CommandSender sender) {
                if (args.length == 0) {
                    sender.sendMessage("§4Bitte geb einen Spieler an: /enderSee <Spieler>");
                    return;
                }

                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage("§4Kann den angegebenen Spieler nicht finden!");
                    return;
                }
                Player player = (Player) sender;
                Inventory inventory = target.getEnderChest();
                getLogger().info("Player " + player.getName() + "opens the Enderchest of " + target.getName() + ".");
                player.openInventory(inventory);
                player.sendMessage("§aDu bist nun in der Enderkiste von §6" + target.getName());
            }
        };

        getLogger().info("Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling...");

        getLogger().info("Disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Command.executeCommand(args, sender, label);
        return true;
    }
}
