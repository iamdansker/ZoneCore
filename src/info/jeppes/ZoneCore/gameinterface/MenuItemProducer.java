/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.jeppes.ZoneCore.gameinterface;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jeppe
 */
public class MenuItemProducer extends ItemStack{
    public MenuItemProducer(int type) {
        super(type);
    }
    public MenuItemProducer(Material type) {
        super(type);
    }
    public MenuItemProducer(int type, int amount) {
        super(type, amount);
    }
    public MenuItemProducer(Material type, int amount) {
        super(type, amount);
    }
    public MenuItemProducer(int type, int amount, short damage) {
        super(type, amount, damage);
    }
    public MenuItemProducer(Material type, int amount, short damage) {
        super(type, amount, damage);
    }
    public MenuItemProducer(int type, int amount, short damage, Byte data) {
        super(type, amount, damage, data);
    }
    public MenuItemProducer(Material type, int amount, short damage, Byte data) {
        super(type, amount, damage, data);
    }
    public MenuItemProducer(ItemStack stack) throws IllegalArgumentException {
        super(stack);
    }
    
    public MenuItemProducer setLore(String displayName){
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(displayName);
        this.setItemMeta(itemMeta);
        return this;
    }
    
    public MenuItemProducer setLore(String[] lore){
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(Arrays.asList(lore));
        this.setItemMeta(itemMeta);
        return this;
    }
    public MenuItemProducer setLore(List<String> lore){
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
        return this;
    }
}
