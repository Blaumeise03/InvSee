/*
 *     Copyright (C) 2019  Blaumeise03 - bluegame61@gmail.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
                    sender.sendMessage("§4Kann den angegebenen Spieler nicht finden!");
                    return;
                }
                Player player = (Player) sender;
                Inventory inventory = target.getInventory();
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
