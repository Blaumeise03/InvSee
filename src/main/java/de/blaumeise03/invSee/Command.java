/*
 * Copyright (c) 2019 Blaumeise03
 */

package de.blaumeise03.invSee;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;


/**
 * This Class adds the possibility to easily create Spigot-Commands.
 *
 * @author Blaumeise03
 * @version 1.2
 */
abstract public class Command {
    private static List<Command> commands = new ArrayList<>();
    private String label;
    private List<String> alias = new ArrayList<>();
    private Permission permission;
    private String help;
    private boolean onlyPlayer = false;

    /**
     * Constructor for creating a new command.
     *
     * @param label      The prefix of the command, e.g. /prefix arg0 arg1 arg2...
     * @param help       The help-text for the command
     * @param permission The Bukkit-permission for the command.
     */
    public Command(String label, String help, Permission permission) {
        this.label = label;
        this.help = help;
        this.permission = permission;
        commands.add(this);
    }

    /**
     * Constructor for creating a new command.
     *
     * @param label      The prefix of the command, e.g. /prefix arg0 arg1 arg2...
     * @param help       The help-text for the command
     * @param permission The Bukkit-permission for the command.
     * @param alias      A list with aliases for the Command
     * @deprecated The alias-feature does not work!
     */
    @Deprecated
    public Command(String label, String help, Permission permission, List<String> alias) {
        this.label = label;
        this.help = help;
        this.permission = permission;
        this.alias = alias;
        commands.add(this);
    }

    /**
     * Constructor for creating a new command.
     *
     * @param label The prefix of the command, e.g. /prefix arg0 arg1 arg2...
     * @param help The help-text for the command
     * @param permission The Bukkit-permission for the command.
     * @param alias A list with aliases for the Command
     * @deprecated The alias-feature does not work!
     * @param onlyPlayer If true only players may execute the command.
     */
    @Deprecated
    public Command(String label, String help, Permission permission, List<String> alias, boolean onlyPlayer) {
        this.label = label;
        this.help = help;
        this.permission = permission;
        this.alias = alias;
        this.onlyPlayer = onlyPlayer;
        commands.add(this);
    }

    /**
     * Constructor for creating a new command.
     *
     * @param label The prefix of the command, e.g. /prefix arg0 arg1 arg2...
     * @param help The help-text for the command
     * @param permission The Bukkit-permission for the command.
     * @param onlyPlayer If true only players may execute the command.
     */
    public Command(String label, String help, Permission permission, boolean onlyPlayer) {
        this.label = label;
        this.help = help;
        this.permission = permission;
        this.onlyPlayer = onlyPlayer;
        commands.add(this);
    }

    /**
     * This method must be executed wenn a player execute a command. Just fill in the parameters of the onCommand() method from the main-class of the plugin.
     *
     * @param args The arguments of the command (/label args[]).
     * @param sender The CommandSender (should be self-explained).
     * @param label The command wich should be executed.
     * @return Returns a boolean if the command was found (NOT if the command was executed successfully).
     */
    public static boolean executeCommand(String[] args, CommandSender sender, String label) {
        for (Command command : commands) {
            if (command.isCommand(label)) {
                command.run(sender, label, args);
                return true;
            }
        }
        return false;
    }

    /**
     * This method will be executed if a player execute the command.
     *
     * @param args The arguments from the command (if the player has entered some).
     * @param sender The CommandSender of the command. If 'onlyPlayer' form the constructor is true this will be a player.
     */
    public abstract void onCommand(String[] args, CommandSender sender);

    /**
     * Defines what should happen if the player has no permissions.
     *
     * @param sender The CommandSender who tried to execute the command.
     */
    public void onNoPermission(CommandSender sender) {
        sender.sendMessage("§4Dazu hast du keine Rechte!");
    }

    /**
     * Checks if the command wich was executed by the player equals. WARING: Aliases doesn't work!
     *
     * @param label The label of the command the player entered.
     * @return Returns true if the label equals the command.
     */
    private boolean isCommand(final String label) {
        final boolean[] al = {false};
        alias.forEach(s -> al[0] = (s.equalsIgnoreCase(label) || al[0]));
        return (label.equalsIgnoreCase(this.label) || al[0]);
    }

    /**
     * Checks if the player has permission for executing the command.
     *
     * @param player The player who tries to execute the command.
     * @return Returns true if the player has the permission.
     */
    private boolean hasPermission(Player player) {
        return player.hasPermission(permission);
    }

    /**
     * Method for checking if the command equals the command from the player. It also checks if the executor must be a player.
     *
     * @param sender Equals the CommandSender of the onCommand method.
     * @param label  Equals the label of the onCommand method.
     * @param args   Equals the args of the onCommand method.
     */
    private void run(CommandSender sender, String label, String[] args) {
        if (isCommand(label)) {
            if (sender instanceof Player) {
                if (hasPermission((Player) sender)) {
                    onCommand(args, sender);
                } else onNoPermission(sender);
            } else if (!onlyPlayer) {
                onCommand(args, sender);
            } else sender.sendMessage("You must be a Player to execute this Command!");
        }
    }

    public String getLabel() {
        return label;
    }

    public Permission getPermission() {
        return permission;
    }

    public String getHelp() {
        return help;
    }

    public void addAlias(String alias) {
        this.alias.add(alias);
    }
}