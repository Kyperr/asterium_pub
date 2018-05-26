package com.toozo.asteriumwebserver.gamelogic.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;

public class LootPool {
	/*
	ItemSupplier(Supplier<Item> item, float basePercentChange, float luckWeight, float intuitionWeight)
    -getItem
    -getBaseChance
    -getLuckWeight
    -getIntuitionWeight
    -getEffectiveChance(Stats stats)//Idk about this.
    
Note: The above ItemSupplier class could also take a Function<Game, Boolean> that could be used
to make the item conditional. An example of this would be an item that is only available if 
nobody already has the item, such as a victory condition item. Essentially, if the function 
supplier returns false, the getEffectiveChance() will return 0.0

    
LootPool(List<ItemSupplier> itemSuppliers)
    -List<ItemSupplier>
    -getRandomItem()//This would simply use the ItemSupplier.baseChance
    -getRandomItemFor(Player p)//This would weight probabilities on the p's stats.
    
	// Here is a rough example of how this could work.
    public Item getRandomItemFor(Player p){
    
        int totalSumChance = 0f;
        
        for(ItemSupplier is : this.itemSuppliers){
            totalSumChance += is.getEffectiveChance(p.getStats());
        }
        
        int index = this.(secure rand).nextFloat(totalSumChance);
        int sum = 0;
        int i = 0;
        while(sum < index){
            sum += this.itemSuppliers.get(i++).getEffectiveChance(p.getStats());
        }
        
        ItemSupplier randIS = this.itemSuppliers.get(Math.max(0, i-1));
        return randIS.getItem();
    
    }
    */
	
	// ===== CONSTANTS =====
	// index = number of items, array[index] = chance of getting that many
	public static final double[] NUMBER_ITEMS_PROBABILITIES = {0.00, 0.50, 0.25, 0.15, 0.07, 0.03};

	private static final Random RNG = new Random();
	
	// =====================
	
	// ===== FIELDS =====
	private List<ItemLoot> items;
	// ==================
	
	// ===== CONSTRUCTORS =====
	/**
	 * Constructs a new LootPool based on lootProbabilities.
	 * 
	 * @param lootProbabilities a {@link Map} from (? extends AbstractItem)::new to
	 * 							the probability that that item should be looted from this room.
	 */
	public LootPool(List<ItemLoot> lootItems) {
		this.items = lootItems;
	}
	// ========================
	
	// ===== METHODS =====
	/**
	 * @return a {@link Collection} of {@link AbstractItem}s gained from looting this loot pool.
	 */
	public List<AbstractItem> loot(PlayerCharacter looter) {
		List<AbstractItem> result = new ArrayList<AbstractItem>();
		
		// Cascade NUMBER_ITEMS_PROBABILITIES
		// e.g. [0.0, 0.4, 0.2, 0.1, 0.05, 0.05] -> [0.0, 0.4, 0.6, 0.7, 0.75,
		double[] cascade = new double[NUMBER_ITEMS_PROBABILITIES.length];
		if (cascade.length > 0) {
			cascade[0] = NUMBER_ITEMS_PROBABILITIES[0];
		
			for (int i = 1; i < cascade.length; i++) {
				cascade[i] = cascade[i - 1] + NUMBER_ITEMS_PROBABILITIES[i];
			}
		}
		
		// Determine the number of items that should be returned
		double random = RNG.nextDouble();
		int itemsToLoot;
		for (itemsToLoot = 0; random < NUMBER_ITEMS_PROBABILITIES[itemsToLoot]; itemsToLoot++);
		
		// Maybe get extra item for luck
		random = RNG.nextDouble();
		double threshold = looter.getEffectiveStats().getStat(Stat.LUCK) / 10;
		if (random < threshold) {
			itemsToLoot++;
		}
		
		// Get that many loot items
		for (int i = 0; i < itemsToLoot; i++) {
			result.add(this.lootItem(looter));
		}
		
		return result;
	}
	
	/**
	 * @param numberOfItems The number of {@link AbstractItem}s that should 
	 * 						be in the {@link Collection} returned.
	 * @return a {@link Collection} of {@link AbstractItem}s pulled from this loot pool.
	 */
	public Collection<AbstractItem> lootItems(int numberOfItems, PlayerCharacter looter) {
		List<AbstractItem> items = new ArrayList<AbstractItem>();
		
		while (items.size() < numberOfItems) {
			items.add(this.lootItem(looter));
		}
		
		return items;
	}
	
	/**
	 * @return an {@link AbstractItem} pulled from this loot pool based on looter's stats.
	 */
	public AbstractItem lootItem(PlayerCharacter looter) {
        int totalSumChance = 0;
        
        for(ItemLoot is : this.items){
            totalSumChance += is.getEffectiveChance(looter.getEffectiveStats());
        }
        
        int index = RNG.nextInt(totalSumChance);
        int sum = 0;
        int i = 0;
        while(sum < index){
            sum += this.items.get(i++).getEffectiveChance(looter.getEffectiveStats());
        }
        
        ItemLoot randIS = this.items.get(Math.max(0, i-1));
        
        return randIS.getItem();
	}
	
	// ===================
}
