/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

/**
 *
 * @author Jeppe
 */
public abstract class BukkitPlayerWrapper implements ZoneUser{

    private Player player;
    private final String name;

    public BukkitPlayerWrapper(Player player,String name){
        this.player = player;
        this.name = name;
    }
    
    @Override
    public Player getPlayer(){
        if(player == null){
            player = Bukkit.getPlayer(name);
        }
        return player;
    }
    
    @Override
    public void setPlayer(Player player){
        this.player = player;
    }
    
    @Override
    public String getDisplayName() {
        return player != null ? player.getDisplayName() : name;
    }

    @Override
    public void setDisplayName(String string) {
        player.setDisplayName(string);
    }

    @Override
    public String getPlayerListName() {
        return player.getPlayerListName();
    }

    @Override
    public void setPlayerListName(String string) {
        player.setPlayerListName(string);
    }

    @Override
    public void setCompassTarget(Location lctn) {
        player.setCompassTarget(lctn);
    }

    @Override
    public Location getCompassTarget() {
        return player.getCompassTarget();
    }

    @Override
    public InetSocketAddress getAddress() {
        return player.getAddress();
    }

    @Override
    public void sendRawMessage(String string) {
        player.sendRawMessage(string);
    }

    @Override
    public void kickPlayer(String string) {
        player.kickPlayer(string);
    }

    @Override
    public void chat(String string) {
        player.chat(string);
    }

    @Override
    public boolean performCommand(String string) {
        return player.performCommand(string);
    }

    @Override
    public boolean isSneaking() {
        return player.isSneaking();
    }

    @Override
    public void setSneaking(boolean bln) {
        player.setSneaking(bln);
    }

    @Override
    public boolean isSprinting() {
        return player.isSprinting();
    }

    @Override
    public void setSprinting(boolean bln) {
        player.setSprinting(bln);
    }

    @Override
    public void saveData() {
        player.saveData();
    }

    @Override
    public void loadData() {
        player.loadData();
    }

    @Override
    public void setSleepingIgnored(boolean bln) {
        player.setSleepingIgnored(bln);
    }

    @Override
    public boolean isSleepingIgnored() {
        return player.isSleepingIgnored();
    }

    @Override
    public void playNote(Location lctn, byte b, byte b1) {
        player.playNote(lctn, b1, b1);
    }

    @Override
    public void playNote(Location lctn, Instrument i, Note note) {
        player.playNote(lctn, i, note);
    }

    @Override
    public void playEffect(Location lctn, Effect effect, int i) {
        player.playEffect(lctn, effect, i);
    }

    @Override
    public void sendBlockChange(Location lctn, Material mtrl, byte b) {
        player.sendBlockChange(lctn, mtrl, b);
    }

    @Override
    public boolean sendChunkChange(Location lctn, int i, int i1, int i2, byte[] bytes) {
        return player.sendChunkChange(lctn, i, i1, i2, bytes);
    }

    @Override
    public void sendBlockChange(Location lctn, int i, byte b) {
        player.sendBlockChange(lctn, i, b);
    }

    @Override
    public void sendMap(MapView mv) {
        player.sendMap(mv);
    }

    @Override
    public void updateInventory() {
        player.updateInventory();
    }

    @Override
    public void awardAchievement(Achievement a) {
        player.awardAchievement(a);
    }

    @Override
    public void incrementStatistic(Statistic ststc) {
        player.incrementStatistic(ststc);
    }

    @Override
    public void incrementStatistic(Statistic ststc, int i) {
        player.incrementStatistic(ststc, i);
    }

    @Override
    public void incrementStatistic(Statistic ststc, Material mtrl) {
        player.incrementStatistic(ststc, mtrl);
    }

    @Override
    public void incrementStatistic(Statistic ststc, Material mtrl, int i) {
        player.incrementStatistic(ststc, mtrl, i);
    }

    @Override
    public void setPlayerTime(long l, boolean bln) {
        player.setPlayerTime(l, bln);
    }

    @Override
    public long getPlayerTime() {
        return player.getPlayerTime();
    }

    @Override
    public long getPlayerTimeOffset() {
        return player.getPlayerTimeOffset();
    }

    @Override
    public boolean isPlayerTimeRelative() {
        return player.isPlayerTimeRelative();
    }

    @Override
    public void resetPlayerTime() {
        player.resetPlayerTime();
    }

    @Override
    public void giveExp(int i) {
        player.giveExp(i);
    }

    @Override
    public float getExp() {
        return player.getExp();
    }

    @Override
    public void setExp(float f) {
        player.setExp(f);
    }

    @Override
    public int getLevel() {
        return player.getLevel();
    }

    @Override
    public void setLevel(int i) {
        player.setLevel(i);
    }

    @Override
    public int getTotalExperience() {
        return player.getTotalExperience();
    }

    @Override
    public void setTotalExperience(int i) {
        player.setTotalExperience(i);
    }

    @Override
    public float getExhaustion() {
        return player.getExhaustion();
    }

    @Override
    public void setExhaustion(float f) {
        player.setExhaustion(f);
    }

    @Override
    public float getSaturation() {
        return player.getSaturation();
    }

    @Override
    public void setSaturation(float f) {
        player.setSaturation(f);
    }

    @Override
    public int getFoodLevel() {
        return player.getFoodLevel();
    }

    @Override
    public void setFoodLevel(int i) {
        player.setFoodLevel(i);
    }

    @Override
    public Location getBedSpawnLocation() {
        return player.getBedSpawnLocation();
    }

    @Override
    public String getName() {
        return player != null ? player.getName() : name;
    }

    @Override
    public PlayerInventory getInventory() {
        return player.getInventory();
    }

    @Override
    public ItemStack getItemInHand() {
        return player.getItemInHand();
    }

    @Override
    public void setItemInHand(ItemStack is) {
        player.setItemInHand(is);
    }

    @Override
    public boolean isSleeping() {
        return player.isSleeping();
    }

    @Override
    public int getSleepTicks() {
        return player.getSleepTicks();
    }

    @Override
    public GameMode getGameMode() {
        return player.getGameMode();
    }

    @Override
    public void setGameMode(GameMode gm) {
        player.setGameMode(gm);
    }

    @Override
    public double getHealth() {
        return player.getHealth();
    }

    @Override
    public void setHealth(double i) {
        player.setHealth(i);
    }

    @Override
    public double getMaxHealth() {
        return player.getMaxHealth();
    }

    @Override
    public double getEyeHeight() {
        return player.getEyeHeight();
    }

    @Override
    public double getEyeHeight(boolean bln) {
        return player.getEyeHeight(bln);
    }

    @Override
    public Location getEyeLocation() {
        return player.getEyeLocation();
    }

    @Override
    public List<Block> getLineOfSight(HashSet<Byte> hs, int i) {
        return player.getLineOfSight(hs, i);
    }

    @Override
    public Block getTargetBlock(HashSet<Byte> hs, int i) {
        return player.getTargetBlock(hs, i);
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> hs, int i) {
        return player.getLastTwoTargetBlocks(hs, i);
    }

    @Override
    public Egg throwEgg() {
        return player.throwEgg();
    }

    @Override
    public Snowball throwSnowball() {
        return player.throwSnowball();
    }

    @Override
    public Arrow shootArrow() {
        return player.shootArrow();
    }

    @Override
    public boolean isInsideVehicle() {
        return player.isInsideVehicle();
    }

    @Override
    public boolean leaveVehicle() {
        return player.leaveVehicle();
    }

    @Override
    public Entity getVehicle() {
        return player.getVehicle();
    }

    @Override
    public int getRemainingAir() {
        return player.getRemainingAir();
    }

    @Override
    public void setRemainingAir(int i) {
        player.setRemainingAir(i);
    }

    @Override
    public int getMaximumAir() {
        return player.getMaximumAir();
    }

    @Override
    public void setMaximumAir(int i) {
        player.setMaximumAir(i);
    }

    @Override
    public void damage(double i) {
        player.damage(i);
    }

    @Override
    public void damage(double i, Entity entity) {
        player.damage(i,entity);
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return player.getMaximumNoDamageTicks();
    }

    @Override
    public void setMaximumNoDamageTicks(int i) {
        player.setMaximumNoDamageTicks(i);
    }

    @Override
    public double getLastDamage() {
        return player.getLastDamage();
    }

    @Override
    public void setLastDamage(double i) {
        player.setLastDamage(i);
    }

    @Override
    public int getNoDamageTicks() {
        return player.getNoDamageTicks();
    }

    @Override
    public void setNoDamageTicks(int i) {
        player.setNoDamageTicks(i);
    }

    @Override
    public Location getLocation() {
        return player.getLocation();
    }

    @Override
    public void setVelocity(Vector vector) {
        player.setVelocity(vector);
    }

    @Override
    public Vector getVelocity() {
        return player.getVelocity();
    }

    @Override
    public World getWorld() {
        return player.getWorld();
    }

    @Override
    public boolean teleport(Location lctn) {
        return player.teleport(lctn);
    }

    @Override
    public boolean teleport(Location lctn, TeleportCause tc) {
        return player.teleport(lctn, tc);
    }

    @Override
    public boolean teleport(Entity entity) {
        return player.teleport(entity);
    }

    @Override
    public boolean teleport(Entity entity, TeleportCause tc) {
        return player.teleport(entity, tc);
    }

    @Override
    public List<Entity> getNearbyEntities(double d, double d1, double d2) {
        return player.getNearbyEntities(d, d1, d2);
    }

    @Override
    public int getEntityId() {
        return player.getEntityId();
    }

    @Override
    public int getFireTicks() {
        return player.getFireTicks();
    }

    @Override
    public int getMaxFireTicks() {
        return player.getMaxFireTicks();
    }

    @Override
    public void setFireTicks(int i) {
        player.setFireTicks(i);
    }

    @Override
    public void remove() {
        player.remove();
    }

    @Override
    public boolean isDead() {
        return player.isDead();
    }

    @Override
    public Server getServer() {
        return player.getServer();
    }

    @Override
    public Entity getPassenger() {
        return player.getPassenger();
    }

    @Override
    public boolean setPassenger(Entity entity) {
        return player.setPassenger(entity);
    }

    @Override
    public boolean isEmpty() {
        return player.isEmpty();
    }

    @Override
    public boolean eject() {
        return player.eject();
    }

    @Override
    public float getFallDistance() {
        return player.getFallDistance();
    }

    @Override
    public void setFallDistance(float f) {
        player.setFallDistance(f);
    }

    @Override
    public void setLastDamageCause(EntityDamageEvent ede) {
        player.setLastDamageCause(ede);
    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        return player.getLastDamageCause();
    }

    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public int getTicksLived() {
        return player.getTicksLived();
    }

    @Override
    public void setTicksLived(int i) {
        player.setTicksLived(i);
    }

    @Override
    public boolean isPermissionSet(String string) {
        return player.isPermissionSet(string);
    }

    @Override
    public boolean isPermissionSet(Permission prmsn) {
        return player.isPermissionSet(prmsn);
    }

    @Override
    public boolean hasPermission(String string) {
        return player.hasPermission(string);
    }

    @Override
    public boolean hasPermission(Permission prmsn) {
        return player.hasPermission(prmsn);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bln) {
        return player.addAttachment(plugin,string,bln);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return player.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bln, int i) {
        return player.addAttachment(plugin, string, bln, i);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int i) {
        return player.addAttachment(plugin, i);
    }

    @Override
    public void removeAttachment(PermissionAttachment pa) {
        player.removeAttachment(pa);
    }

    @Override
    public void recalculatePermissions() {
        player.recalculatePermissions();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return player.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return player.isOp();
    }

    @Override
    public void setOp(boolean bln) {
        player.setOp(bln);
    }

    @Override
    public void sendMessage(String string) {
        player.sendMessage(string);
    }

    @Override
    public boolean isOnline() {
        Player playerCheck = getPlayer();
        if(playerCheck != null){
            return playerCheck.isOnline();
        }
        return false;
    }

    @Override
    public boolean isBanned() {
        return player.isBanned();
    }

    @Override
    public void setBanned(boolean bln) {
        player.setBanned(bln);
    }

    @Override
    public boolean isWhitelisted() {
        return player.isWhitelisted();
    }

    @Override
    public void setWhitelisted(boolean bln) {
        player.setWhitelisted(bln);
    }
    
    @Override
    public Map<String, Object> serialize() {
        return player.serialize();
    }

    @Override
    public Player getKiller() {
        return player.getKiller();
    }

    @Override
    public long getFirstPlayed() {
        return player.getFirstPlayed();
    }

    @Override
    public long getLastPlayed() {
        return player.getLastPlayed();
    }

    @Override
    public boolean hasPlayedBefore() {
        return player.hasPlayedBefore();
    }

    @Override
    public void setBedSpawnLocation(Location lctn) {
        player.setBedSpawnLocation(lctn);
    }

    @Override
    public boolean getAllowFlight() {
        return player.getAllowFlight();
    }

    @Override
    public void setAllowFlight(boolean bln) {
        player.setAllowFlight(bln);
    }

    @Override
    public void playEffect(EntityEffect ee) {
        player.playEffect(ee);
    }

    @Override
    public void sendPluginMessage(Plugin plugin, String string, byte[] bytes) {
        player.sendPluginMessage(plugin, string, bytes);
    }

    @Override
    public Set<String> getListeningPluginChannels() {
        return player.getListeningPluginChannels();
    }

    @Override
    public <T> void playEffect(Location lctn, Effect effect, T t) {
        player.playEffect(lctn, effect, t);
    }

    @Override
    public void hidePlayer(Player player) {
        player.hidePlayer(player);
    }

    @Override
    public void showPlayer(Player player) {
        player.showPlayer(player);
    }

    @Override
    public boolean canSee(Player player) {
       return player.canSee(player);
    }

    @Override
    public boolean setWindowProperty(Property prprt, int i) {
        return player.setWindowProperty(prprt, i);
    }

    @Override
    public InventoryView getOpenInventory() {
        return player.getOpenInventory();
    }

    @Override
    public InventoryView openInventory(Inventory invntr) {
        return player.openInventory(invntr);
    }

    @Override
    public InventoryView openWorkbench(Location lctn, boolean bln) {
        return player.openWorkbench(lctn, bln);
    }

    @Override
    public InventoryView openEnchanting(Location lctn, boolean bln) {
        return player.openEnchanting(lctn, bln);
    }

    @Override
    public void openInventory(InventoryView iv) {
        player.openInventory(iv);
    }

    @Override
    public void closeInventory() {
        player.closeInventory();
    }

    @Override
    public ItemStack getItemOnCursor() {
        return player.getItemOnCursor();
    }

    @Override
    public void setItemOnCursor(ItemStack is) {
        player.setItemOnCursor(is);
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> type) {
        return player.launchProjectile(type);
    }

    @Override
    public boolean addPotionEffect(PotionEffect pe) {
        return player.addPotionEffect(pe);
    }

    @Override
    public boolean addPotionEffect(PotionEffect pe, boolean bln) {
        return player.addPotionEffect(pe, bln);
    }

    @Override
    public boolean addPotionEffects(Collection<PotionEffect> clctn) {
        return player.addPotionEffects(clctn);
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType pet) {
        return player.hasPotionEffect(pet);
    }

    @Override
    public void removePotionEffect(PotionEffectType pet) {
        player.removePotionEffect(pet);
    }

    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return player.getActivePotionEffects();
    }

    @Override
    public EntityType getType() {
        return player.getType();
    }

    @Override
    public void setMetadata(String string, MetadataValue mv) {
        player.setMetadata(string, mv);
    }

    @Override
    public List<MetadataValue> getMetadata(String string) {
        return player.getMetadata(string);
    }

    @Override
    public boolean hasMetadata(String string) {
        return player.hasMetadata(string);
    }

    @Override
    public void removeMetadata(String string, Plugin plugin) {
        player.removeMetadata(string, plugin);
    }

    @Override
    public boolean isConversing() {
        return player.isConversing();
    }

    @Override
    public void acceptConversationInput(String string) {
        player.acceptConversationInput(string);
    }

    @Override
    public boolean beginConversation(Conversation c) {
        return player.beginConversation(c);
    }

    @Override
    public void abandonConversation(Conversation c) {
        player.abandonConversation(c);
    }

    @Override
    public void abandonConversation(Conversation c, ConversationAbandonedEvent cae) {
        player.abandonConversation(c, cae);
    }

    @Override
    public void sendMessage(String[] strings) {
        player.sendMessage(strings);
    }
    
    @Override
    public boolean isFlying() {
        return player.isFlying();
    }

    @Override
    public void setFlying(boolean bln) {
        player.setFlying(bln);
    }

    @Override
    public boolean isBlocking() {
        return player.isBlocking();
    }
    @Override
    public int getExpToLevel() {
        return player.getExpToLevel();
    }

    @Override
    public boolean hasLineOfSight(Entity entity) {
        return player.hasLineOfSight(entity);
    }

    @Override
    public boolean isValid() {
        return player.isValid();
    }
    @Override
    public void setFlySpeed(float f) throws IllegalArgumentException {
        player.setFlySpeed(f);
    }

    @Override
    public void setWalkSpeed(float f) throws IllegalArgumentException {
        player.setWalkSpeed(f);
    }

    @Override
    public float getFlySpeed() {
        return player.getFlySpeed();
    }

    @Override
    public float getWalkSpeed() {
        return player.getWalkSpeed();
    }
    
    @Override
    public void playSound(Location lctn, Sound sound, float f, float f1) {
        player.playSound(lctn, sound, f, f1);
    }

    @Override
    public void giveExpLevels(int i) {
        player.giveExpLevels(i);
    }

    @Override
    public void setBedSpawnLocation(Location lctn, boolean bln) {
        player.setBedSpawnLocation(lctn, bln);
    }

    @Override
    public Inventory getEnderChest() {
        return player.getEnderChest();
    }
    @Override
    public void setTexturePack(String string) {
        player.setTexturePack(string);
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return player.getRemoveWhenFarAway();
    }

    @Override
    public void setRemoveWhenFarAway(boolean bln) {
        player.setRemoveWhenFarAway(bln);
    }

    @Override
    public EntityEquipment getEquipment() {
        return player.getEquipment();
    }

    @Override
    public void setCanPickupItems(boolean bln) {
        player.setCanPickupItems(bln);
    }

    @Override
    public boolean getCanPickupItems() {
        return player.getCanPickupItems();
    }

    @Override
    public Location getLocation(Location lctn) {
        return player.getLocation(lctn);
    }

    @Override
    public void setMaxHealth(double i) {
        player.setMaxHealth(i);
    }

    @Override
    public void resetMaxHealth() {
        player.resetMaxHealth();
    }
    
    @Override
    public void setPlayerWeather(WeatherType wt) {
        player.setPlayerWeather(wt);
    }

    @Override
    public WeatherType getPlayerWeather() {
        return player.getPlayerWeather();
    }

    @Override
    public void resetPlayerWeather() {
        player.resetPlayerWeather();
    }

    @Override
    public boolean isOnGround() {
        return player.isOnGround();
    }

    @Override
    public void setCustomName(String string) {
        player.setCustomName(string);
    }

    @Override
    public String getCustomName() {
        return player.getCustomName();
    }

    @Override
    public void setCustomNameVisible(boolean bln) {
        player.setCustomNameVisible(bln);
    }

    @Override
    public boolean isCustomNameVisible() {
        return player.isCustomNameVisible();
    }
    
    @Override
    public Scoreboard getScoreboard() {
        return player.getScoreboard();
    }

    @Override
    public void setScoreboard(Scoreboard scrbrd) throws IllegalArgumentException, IllegalStateException {
        player.setScoreboard(scrbrd);
    }
    
    
    @Override
    public int _INVALID_getLastDamage() {
        return (int)player.getLastDamage();
    }

    @Override
    public void _INVALID_setLastDamage(int i) {
        player.setLastDamage(i);
    }

    @Override
    public void _INVALID_damage(int i) {
        player.damage(i);
    }

    @Override
    public void _INVALID_damage(int i, Entity entity) {
        player.damage(i,entity);
    }

    @Override
    public int _INVALID_getHealth() {
        return (int)getHealth();
    }

    @Override
    public void _INVALID_setHealth(int i) {
        player.setHealth(i);
    }

    @Override
    public int _INVALID_getMaxHealth() {
        return (int)player.getMaxHealth();
    }

    @Override
    public void _INVALID_setMaxHealth(int i) {
        player.setMaxHealth(i);
    }
    
    @Override
    public boolean isHealthScaled() {
        return player.isHealthScaled();
    }

    @Override
    public void setHealthScaled(boolean bln) {
        player.setHealthScaled(bln);
    }

    @Override
    public void setHealthScale(double d) throws IllegalArgumentException {
        player.setHealthScale(d);
    }

    @Override
    public double getHealthScale() {
        return player.getHealthScale();
    }
}
