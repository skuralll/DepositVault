package com.skuralll.depositvault.ui;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

abstract public class GUI {

    public static HolderMenuGUI getHolderMenuGUI(Player player, Location location){
        return new HolderMenuGUI(player, location);
    }

    protected Player player;

    public GUI(Player player) {
        this.player = player;
    }

    public String getTitle(){
        return "title";
    }

    abstract public Gui getGui();

    public void open(){
        Window window = window = Window.single()
                .setViewer(player)
                .setTitle(getTitle())
                .setGui(getGui())
                .build();
        window.open();
    }
}
