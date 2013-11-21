/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import java.io.File;
import java.lang.ref.WeakReference;
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

    private WeakReference<Player> player;
    private final String name;

    public BukkitPlayerWrapper(Player player,String name){
        this.player = new WeakReference(player);
        this.name = name;
    }
    
    @Override
    public Player getPlayer(){
        if(player.get() == null){
            player = new WeakReference(Bukkit.getPlayer(name));
        }
        return player.get();
    }
    
    @Override
    public void setPlayer(Player player){
        this.player = new WeakReference(player);
    }
    
    @Override
    public String getDisplayName() {
        return getPlayer() != null ? getPlayer().getDisplayName() : name;
    }

    @Override
    public void setDisplayName(String string) {
        getPlayer().setDisplayName(string);
    }

    @Override
    public String getPlayerListName() {
        return getPlayer().getPlayerListName();
    }

    @Override
    public void setPlayerListName(String string) {
        getPlayer().setPlayerListName(string);
    }

    @Override
    public void setCompassTarget(Location lctn) {
        getPlayer().setCompassTarget(lctn);
    }

    @Override
    public Location getCompassTarget() {
        return getPlayer().getCompassTarget();
    }

    @Override
    public InetSocketAddress getAddress() {
        return getPlayer().getAddress();
    }

    @Override
    public void sendRawMessage(String string) {
        getPlayer().sendRawMessage(string);
    }

    @Override
    public void kickPlayer(String string) {
        getPlayer().kickPlayer(string);
    }

    @Override
    public void chat(String string) {
        getPlayer().chat(string);
    }

    @Override
    public boolean performCommand(String string) {
        return getPlayer().performCommand(string);
    }

    @Override
    public boolean isSneaking() {
        return getPlayer().isSneaking();
    }

    @Override
    public void setSneaking(boolean bln) {
        getPlayer().setSneaking(bln);
    }

    @Override
    public boolean isSprinting() {
        return getPlayer().isSprinting();
    }

    @Override
    public void setSprinting(boolean bln) {
        getPlayer().setSprinting(bln);
    }

    @Override
    public void saveData() {
        getPlayer().saveData();
    }

    @Override
    public void loadData() {
        getPlayer().loadData();
    }

    @Override
    public void setSleepingIgnored(boolean bln) {
        getPlayer().setSleepingIgnored(bln);
    }

    @Override
    public boolean isSleepingIgnored() {
        return getPlayer().isSleepingIgnored();
    }

    @Override
    public void playNote(Location lctn, byte b, byte b1) {
        getPlayer().playNote(lctn, b1, b1);
    }

    @Override
    public void playNote(Location lctn, Instrument i, Note note) {
        getPlayer().playNote(lctn, i, note);
    }

    @Override
    public void playEffect(Location lctn, Effect effect, int i) {
        getPlayer().playEffect(lctn, effect, i);
    }

    @Override
    public void sendBlockChange(Location lctn, Material mtrl, byte b) {
        getPlayer().sendBlockChange(lctn, mtrl, b);
    }

    @Override
    public boolean sendChunkChange(Location lctn, int i, int i1, int i2, byte[] bytes) {
        return getPlayer().sendChunkChange(lctn, i, i1, i2, bytes);
    }

    @Override
    public void sendBlockChange(Location lctn, int i, byte b) {
        getPlayer().sendBlockChange(lctn, i, b);
    }

    @Override
    public void sendMap(MapView mv) {
        getPlayer().sendMap(mv);
    }

    @Override
    public void updateInventory() {
        getPlayer().updateInventory();
    }

    @Override
    public void awardAchievement(Achievement a) {
        getPlayer().awardAchievement(a);
    }

    @Override
    public void incrementStatistic(Statistic ststc) {
        getPlayer().incrementStatistic(ststc);
    }

    @Override
    public void incrementStatistic(Statistic ststc, int i) {
        getPlayer().incrementStatistic(ststc, i);
    }

    @Override
    public void incrementStatistic(Statistic ststc, Material mtrl) {
        getPlayer().incrementStatistic(ststc, mtrl);
    }

    @Override
    public void incrementStatistic(Statistic ststc, Material mtrl, int i) {
        getPlayer().incrementStatistic(ststc, mtrl, i);
    }

    @Override
    public void setPlayerTime(long l, boolean bln) {
        getPlayer().setPlayerTime(l, bln);
    }

    @Override
    public long getPlayerTime() {
        return getPlayer().getPlayerTime();
    }

    @Override
    public long getPlayerTimeOffset() {
        return getPlayer().getPlayerTimeOffset();
    }

    @Override
    public boolean isPlayerTimeRelative() {
        return getPlayer().isPlayerTimeRelative();
    }

    @Override
    public void resetPlayerTime() {
        getPlayer().resetPlayerTime();
    }

    @Override
    public void giveExp(int i) {
        getPlayer().giveExp(i);
    }

    @Override
    public float getExp() {
        return getPlayer().getExp();
    }

    @Override
    public void setExp(float f) {
        getPlayer().setExp(f);
    }

    @Override
    public int getLevel() {
        return getPlayer().getLevel();
    }

    @Override
    public void setLevel(int i) {
        getPlayer().setLevel(i);
    }

    @Override
    public int getTotalExperience() {
        return getPlayer().getTotalExperience();
    }

    @Override
    public void setTotalExperience(int i) {
        getPlayer().setTotalExperience(i);
    }

    @Override
    public float getExhaustion() {
        return getPlayer().getExhaustion();
    }

    @Override
    public void setExhaustion(float f) {
        getPlayer().setExhaustion(f);
    }

    @Override
    public float getSaturation() {
        return getPlayer().getSaturation();
    }

    @Override
    public void setSaturation(float f) {
        getPlayer().setSaturation(f);
    }

    @Override
    public int getFoodLevel() {
        return getPlayer().getFoodLevel();
    }

    @Override
    public void setFoodLevel(int i) {
        getPlayer().setFoodLevel(i);
    }

    @Override
    public Location getBedSpawnLocation() {
        return getPlayer().getBedSpawnLocation();
    }

    @Override
    public String getName() {
        return getPlayer() != null ? getPlayer().getName() : name;
    }

    @Override
    public PlayerInventory getInventory() {
        return getPlayer().getInventory();
    }

    @Override
    public ItemStack getItemInHand() {
        return getPlayer().getItemInHand();
    }

    @Override
    public void setItemInHand(ItemStack is) {
        getPlayer().setItemInHand(is);
    }

    @Override
    public boolean isSleeping() {
        return getPlayer().isSleeping();
    }

    @Override
    public int getSleepTicks() {
        return getPlayer().getSleepTicks();
    }

    @Override
    public GameMode getGameMode() {
        return getPlayer().getGameMode();
    }

    @Override
    public void setGameMode(GameMode gm) {
        getPlayer().setGameMode(gm);
    }

    @Override
    public double getHealth() {
        return getPlayer().getHealth();
    }

    @Override
    public void setHealth(double i) {
        getPlayer().setHealth(i);
    }

    @Override
    public double getMaxHealth() {
        return getPlayer().getMaxHealth();
    }

    @Override
    public double getEyeHeight() {
        return getPlayer().getEyeHeight();
    }

    @Override
    public double getEyeHeight(boolean bln) {
        return getPlayer().getEyeHeight(bln);
    }

    @Override
    public Location getEyeLocation() {
        return getPlayer().getEyeLocation();
    }

    @Override
    public List<Block> getLineOfSight(HashSet<Byte> hs, int i) {
        return getPlayer().getLineOfSight(hs, i);
    }

    @Override
    public Block getTargetBlock(HashSet<Byte> hs, int i) {
        return getPlayer().getTargetBlock(hs, i);
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> hs, int i) {
        return getPlayer().getLastTwoTargetBlocks(hs, i);
    }

    @Override
    public Egg throwEgg() {
        return getPlayer().throwEgg();
    }

    @Override
    public Snowball throwSnowball() {
        return getPlayer().throwSnowball();
    }

    @Override
    public Arrow shootArrow() {
        return getPlayer().shootArrow();
    }

    @Override
    public boolean isInsideVehicle() {
        return getPlayer().isInsideVehicle();
    }

    @Override
    public boolean leaveVehicle() {
        return getPlayer().leaveVehicle();
    }

    @Override
    public Entity getVehicle() {
        return getPlayer().getVehicle();
    }

    @Override
    public int getRemainingAir() {
        return getPlayer().getRemainingAir();
    }

    @Override
    public void setRemainingAir(int i) {
        getPlayer().setRemainingAir(i);
    }

    @Override
    public int getMaximumAir() {
        return getPlayer().getMaximumAir();
    }

    @Override
    public void setMaximumAir(int i) {
        getPlayer().setMaximumAir(i);
    }

    @Override
    public void damage(double i) {
        getPlayer().damage(i);
    }

    @Override
    public void damage(double i, Entity entity) {
        getPlayer().damage(i,entity);
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return getPlayer().getMaximumNoDamageTicks();
    }

    @Override
    public void setMaximumNoDamageTicks(int i) {
        getPlayer().setMaximumNoDamageTicks(i);
    }

    @Override
    public double getLastDamage() {
        return getPlayer().getLastDamage();
    }

    @Override
    public void setLastDamage(double i) {
        getPlayer().setLastDamage(i);
    }

    @Override
    public int getNoDamageTicks() {
        return getPlayer().getNoDamageTicks();
    }

    @Override
    public void setNoDamageTicks(int i) {
        getPlayer().setNoDamageTicks(i);
    }

    @Override
    public Location getLocation() {
        return getPlayer().getLocation();
    }

    @Override
    public void setVelocity(Vector vector) {
        getPlayer().setVelocity(vector);
    }

    @Override
    public Vector getVelocity() {
        return getPlayer().getVelocity();
    }

    @Override
    public World getWorld() {
        return getPlayer().getWorld();
    }

    @Override
    public boolean teleport(Location lctn) {
        return getPlayer().teleport(lctn);
    }

    @Override
    public boolean teleport(Location lctn, TeleportCause tc) {
        return getPlayer().teleport(lctn, tc);
    }

    @Override
    public boolean teleport(Entity entity) {
        return getPlayer().teleport(entity);
    }

    @Override
    public boolean teleport(Entity entity, TeleportCause tc) {
        return getPlayer().teleport(entity, tc);
    }

    @Override
    public List<Entity> getNearbyEntities(double d, double d1, double d2) {
        return getPlayer().getNearbyEntities(d, d1, d2);
    }

    @Override
    public int getEntityId() {
        return getPlayer().getEntityId();
    }

    @Override
    public int getFireTicks() {
        return getPlayer().getFireTicks();
    }

    @Override
    public int getMaxFireTicks() {
        return getPlayer().getMaxFireTicks();
    }

    @Override
    public void setFireTicks(int i) {
        getPlayer().setFireTicks(i);
    }

    @Override
    public void remove() {
        getPlayer().remove();
    }

    @Override
    public boolean isDead() {
        return getPlayer().isDead();
    }

    @Override
    public Server getServer() {
        return getPlayer().getServer();
    }

    @Override
    public Entity getPassenger() {
        return getPlayer().getPassenger();
    }

    @Override
    public boolean setPassenger(Entity entity) {
        return getPlayer().setPassenger(entity);
    }

    @Override
    public boolean isEmpty() {
        return getPlayer().isEmpty();
    }

    @Override
    public boolean eject() {
        return getPlayer().eject();
    }

    @Override
    public float getFallDistance() {
        return getPlayer().getFallDistance();
    }

    @Override
    public void setFallDistance(float f) {
        getPlayer().setFallDistance(f);
    }

    @Override
    public void setLastDamageCause(EntityDamageEvent ede) {
        getPlayer().setLastDamageCause(ede);
    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        return getPlayer().getLastDamageCause();
    }

    @Override
    public UUID getUniqueId() {
        return getPlayer().getUniqueId();
    }

    @Override
    public int getTicksLived() {
        return getPlayer().getTicksLived();
    }

    @Override
    public void setTicksLived(int i) {
        getPlayer().setTicksLived(i);
    }

    @Override
    public boolean isPermissionSet(String string) {
        return getPlayer().isPermissionSet(string);
    }

    @Override
    public boolean isPermissionSet(Permission prmsn) {
        return getPlayer().isPermissionSet(prmsn);
    }

    @Override
    public boolean hasPermission(String string) {
        return getPlayer().hasPermission(string);
    }

    @Override
    public boolean hasPermission(Permission prmsn) {
        return getPlayer().hasPermission(prmsn);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bln) {
        return getPlayer().addAttachment(plugin,string,bln);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return getPlayer().addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bln, int i) {
        return getPlayer().addAttachment(plugin, string, bln, i);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int i) {
        return getPlayer().addAttachment(plugin, i);
    }

    @Override
    public void removeAttachment(PermissionAttachment pa) {
        getPlayer().removeAttachment(pa);
    }

    @Override
    public void recalculatePermissions() {
        getPlayer().recalculatePermissions();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return getPlayer().getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return getPlayer().isOp();
    }

    @Override
    public void setOp(boolean bln) {
        getPlayer().setOp(bln);
    }

    @Override
    public void sendMessage(String string) {
        getPlayer().sendMessage(string);
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
        return getPlayer().isBanned();
    }

    @Override
    public void setBanned(boolean bln) {
        getPlayer().setBanned(bln);
    }

    @Override
    public boolean isWhitelisted() {
        return getPlayer().isWhitelisted();
    }

    @Override
    public void setWhitelisted(boolean bln) {
        getPlayer().setWhitelisted(bln);
    }
    
    @Override
    public Map<String, Object> serialize() {
        return getPlayer().serialize();
    }

    @Override
    public Player getKiller() {
        return getPlayer().getKiller();
    }

    @Override
    public long getFirstPlayed() {
        return getPlayer().getFirstPlayed();
    }

    @Override
    public long getLastPlayed() {
        return getPlayer().getLastPlayed();
    }

    @Override
    public boolean hasPlayedBefore() {
        return getPlayer().hasPlayedBefore();
    }

    @Override
    public void setBedSpawnLocation(Location lctn) {
        getPlayer().setBedSpawnLocation(lctn);
    }

    @Override
    public boolean getAllowFlight() {
        return getPlayer().getAllowFlight();
    }

    @Override
    public void setAllowFlight(boolean bln) {
        getPlayer().setAllowFlight(bln);
    }

    @Override
    public void playEffect(EntityEffect ee) {
        getPlayer().playEffect(ee);
    }

    @Override
    public void sendPluginMessage(Plugin plugin, String string, byte[] bytes) {
        getPlayer().sendPluginMessage(plugin, string, bytes);
    }

    @Override
    public Set<String> getListeningPluginChannels() {
        return getPlayer().getListeningPluginChannels();
    }

    @Override
    public <T> void playEffect(Location lctn, Effect effect, T t) {
        getPlayer().playEffect(lctn, effect, t);
    }

    @Override
    public void hidePlayer(Player player) {
        getPlayer().hidePlayer(player);
    }

    @Override
    public void showPlayer(Player player) {
        getPlayer().showPlayer(player);
    }

    @Override
    public boolean canSee(Player player) {
       return getPlayer().canSee(player);
    }

    @Override
    public boolean setWindowProperty(Property prprt, int i) {
        return getPlayer().setWindowProperty(prprt, i);
    }

    @Override
    public InventoryView getOpenInventory() {
        return getPlayer().getOpenInventory();
    }

    @Override
    public InventoryView openInventory(Inventory invntr) {
        return getPlayer().openInventory(invntr);
    }

    @Override
    public InventoryView openWorkbench(Location lctn, boolean bln) {
        return getPlayer().openWorkbench(lctn, bln);
    }

    @Override
    public InventoryView openEnchanting(Location lctn, boolean bln) {
        return getPlayer().openEnchanting(lctn, bln);
    }

    @Override
    public void openInventory(InventoryView iv) {
        getPlayer().openInventory(iv);
    }

    @Override
    public void closeInventory() {
        getPlayer().closeInventory();
    }

    @Override
    public ItemStack getItemOnCursor() {
        return getPlayer().getItemOnCursor();
    }

    @Override
    public void setItemOnCursor(ItemStack is) {
        getPlayer().setItemOnCursor(is);
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> type) {
        return getPlayer().launchProjectile(type);
    }

    @Override
    public boolean addPotionEffect(PotionEffect pe) {
        return getPlayer().addPotionEffect(pe);
    }

    @Override
    public boolean addPotionEffect(PotionEffect pe, boolean bln) {
        return getPlayer().addPotionEffect(pe, bln);
    }

    @Override
    public boolean addPotionEffects(Collection<PotionEffect> clctn) {
        return getPlayer().addPotionEffects(clctn);
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType pet) {
        return getPlayer().hasPotionEffect(pet);
    }

    @Override
    public void removePotionEffect(PotionEffectType pet) {
        getPlayer().removePotionEffect(pet);
    }

    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return getPlayer().getActivePotionEffects();
    }

    @Override
    public EntityType getType() {
        return getPlayer().getType();
    }

    @Override
    public void setMetadata(String string, MetadataValue mv) {
        getPlayer().setMetadata(string, mv);
    }

    @Override
    public List<MetadataValue> getMetadata(String string) {
        return getPlayer().getMetadata(string);
    }

    @Override
    public boolean hasMetadata(String string) {
        return getPlayer().hasMetadata(string);
    }

    @Override
    public void removeMetadata(String string, Plugin plugin) {
        getPlayer().removeMetadata(string, plugin);
    }

    @Override
    public boolean isConversing() {
        return getPlayer().isConversing();
    }

    @Override
    public void acceptConversationInput(String string) {
        getPlayer().acceptConversationInput(string);
    }

    @Override
    public boolean beginConversation(Conversation c) {
        return getPlayer().beginConversation(c);
    }

    @Override
    public void abandonConversation(Conversation c) {
        getPlayer().abandonConversation(c);
    }

    @Override
    public void abandonConversation(Conversation c, ConversationAbandonedEvent cae) {
        getPlayer().abandonConversation(c, cae);
    }

    @Override
    public void sendMessage(String[] strings) {
        getPlayer().sendMessage(strings);
    }
    
    @Override
    public boolean isFlying() {
        return getPlayer().isFlying();
    }

    @Override
    public void setFlying(boolean bln) {
        getPlayer().setFlying(bln);
    }

    @Override
    public boolean isBlocking() {
        return getPlayer().isBlocking();
    }
    @Override
    public int getExpToLevel() {
        return getPlayer().getExpToLevel();
    }

    @Override
    public boolean hasLineOfSight(Entity entity) {
        return getPlayer().hasLineOfSight(entity);
    }

    @Override
    public boolean isValid() {
        return getPlayer().isValid();
    }
    @Override
    public void setFlySpeed(float f) throws IllegalArgumentException {
        getPlayer().setFlySpeed(f);
    }

    @Override
    public void setWalkSpeed(float f) throws IllegalArgumentException {
        getPlayer().setWalkSpeed(f);
    }

    @Override
    public float getFlySpeed() {
        return getPlayer().getFlySpeed();
    }

    @Override
    public float getWalkSpeed() {
        return getPlayer().getWalkSpeed();
    }
    
    @Override
    public void playSound(Location lctn, Sound sound, float f, float f1) {
        getPlayer().playSound(lctn, sound, f, f1);
    }

    @Override
    public void giveExpLevels(int i) {
        getPlayer().giveExpLevels(i);
    }

    @Override
    public void setBedSpawnLocation(Location lctn, boolean bln) {
        getPlayer().setBedSpawnLocation(lctn, bln);
    }

    @Override
    public Inventory getEnderChest() {
        return getPlayer().getEnderChest();
    }
    @Override
    public void setTexturePack(String string) {
        getPlayer().setTexturePack(string);
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return getPlayer().getRemoveWhenFarAway();
    }

    @Override
    public void setRemoveWhenFarAway(boolean bln) {
        getPlayer().setRemoveWhenFarAway(bln);
    }

    @Override
    public EntityEquipment getEquipment() {
        return getPlayer().getEquipment();
    }

    @Override
    public void setCanPickupItems(boolean bln) {
        getPlayer().setCanPickupItems(bln);
    }

    @Override
    public boolean getCanPickupItems() {
        return getPlayer().getCanPickupItems();
    }

    @Override
    public Location getLocation(Location lctn) {
        return getPlayer().getLocation(lctn);
    }

    @Override
    public void setMaxHealth(double i) {
        getPlayer().setMaxHealth(i);
    }

    @Override
    public void resetMaxHealth() {
        getPlayer().resetMaxHealth();
    }
    
    @Override
    public void setPlayerWeather(WeatherType wt) {
        getPlayer().setPlayerWeather(wt);
    }

    @Override
    public WeatherType getPlayerWeather() {
        return getPlayer().getPlayerWeather();
    }

    @Override
    public void resetPlayerWeather() {
        getPlayer().resetPlayerWeather();
    }

    @Override
    public boolean isOnGround() {
        return getPlayer().isOnGround();
    }

    @Override
    public void setCustomName(String string) {
        getPlayer().setCustomName(string);
    }

    @Override
    public String getCustomName() {
        return getPlayer().getCustomName();
    }

    @Override
    public void setCustomNameVisible(boolean bln) {
        getPlayer().setCustomNameVisible(bln);
    }

    @Override
    public boolean isCustomNameVisible() {
        return getPlayer().isCustomNameVisible();
    }
    
    @Override
    public Scoreboard getScoreboard() {
        return getPlayer().getScoreboard();
    }

    @Override
    public void setScoreboard(Scoreboard scrbrd) throws IllegalArgumentException, IllegalStateException {
        getPlayer().setScoreboard(scrbrd);
    }
    
    
    @Override
    public int _INVALID_getLastDamage() {
        return (int)getPlayer().getLastDamage();
    }

    @Override
    public void _INVALID_setLastDamage(int i) {
        getPlayer().setLastDamage(i);
    }

    @Override
    public void _INVALID_damage(int i) {
        getPlayer().damage(i);
    }

    @Override
    public void _INVALID_damage(int i, Entity entity) {
        getPlayer().damage(i,entity);
    }

    @Override
    public int _INVALID_getHealth() {
        return (int)getHealth();
    }

    @Override
    public void _INVALID_setHealth(int i) {
        getPlayer().setHealth(i);
    }

    @Override
    public int _INVALID_getMaxHealth() {
        return (int)getPlayer().getMaxHealth();
    }

    @Override
    public void _INVALID_setMaxHealth(int i) {
        getPlayer().setMaxHealth(i);
    }
    
    @Override
    public boolean isHealthScaled() {
        return getPlayer().isHealthScaled();
    }

    @Override
    public void setHealthScaled(boolean bln) {
        getPlayer().setHealthScaled(bln);
    }

    @Override
    public void setHealthScale(double d) throws IllegalArgumentException {
        getPlayer().setHealthScale(d);
    }

    @Override
    public double getHealthScale() {
        return getPlayer().getHealthScale();
    }
    
    @Override
    public void playSound(Location lctn, String string, float f, float f1)
    {
        getPlayer().playSound(lctn, Sound.BURP, f, f1);
    }

    @Override
    public boolean isLeashed()
    {
        return getPlayer().isLeashed();
    }

    @Override
    public Entity getLeashHolder() throws IllegalStateException
    {
        return getPlayer().getLeashHolder();
    }

    @Override
    public boolean setLeashHolder(Entity entity)
    {
        return getPlayer().setLeashHolder(entity);
    }
}
