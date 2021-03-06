package com.toozo.asteriumwebserver.gamelogic.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;

public class LootPool {
	// ===== CONSTANTS =====
	// index = number of items, array[index] = chance of getting that many
	public static final double[] NUMBER_ITEMS_PROBABILITIES = {0.00, 0.80, 0.10, 0.065, 0.025, 0.01};
	public static final boolean VERBOSE = false;
	
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
	 * @return a {@link List} of {@link AbstractItem}s gained from looting this loot pool.
	 */
	public List<AbstractItem> loot(GameState state, PlayerCharacter looter) {
		List<AbstractItem> result = new ArrayList<AbstractItem>();
		
		// Cascade NUMBER_ITEMS_PROBABILITIES
		// e.g. [0.0, 0.4, 0.2, 0.1, 0.05, 0.05] -> [0.0, 0.4, 0.6, 0.7, 0.75,
		int probLength = NUMBER_ITEMS_PROBABILITIES.length;
		double[] cascade = new double[probLength];
		if (probLength > 0) {
			cascade[0] = NUMBER_ITEMS_PROBABILITIES[0];
		
			for (int i = 1; i < probLength; i++) {
				cascade[i] = cascade[i - 1] + NUMBER_ITEMS_PROBABILITIES[i];
			}
		}
		
		// Determine the number of items that should be returned
		double random = RNG.nextDouble();
		int itemsToLoot;
		for (itemsToLoot = 0; itemsToLoot < probLength && random >= cascade[itemsToLoot]; itemsToLoot++);
		
		// Maybe get extra item for luck
		random = RNG.nextDouble();
		double threshold = looter.getEffectiveStats().getStat(Stat.LUCK) / 10;
		if (random < threshold) {
			itemsToLoot++;
		}
		
		// Get that many loot items
		for (int i = 0; i < itemsToLoot; i++) {
			result.add(this.lootItem(state, looter));
		}
		
		if (VERBOSE) {
			System.out.println("\tLootPool says: Looted! Items gained:");
			for (AbstractItem item : result) {
				System.out.printf("\t\t%s:%s\n", item.getName(), item.getDescription());
			}
		}
		
		return result;
	}
	
	/**
	 * @param numberOfItems The number of {@link AbstractItem}s that should 
	 * 						be in the {@link Collection} returned.
	 * @return a {@link Collection} of {@link AbstractItem}s pulled from this loot pool.
	 */
	public Collection<AbstractItem> lootItems(int numberOfItems, GameState state, PlayerCharacter looter) {
		List<AbstractItem> items = new ArrayList<AbstractItem>();
		
		while (items.size() < numberOfItems) {
			items.add(this.lootItem(state, looter));
		}
		
		return items;
	}
	
	/**
	 * @return an {@link AbstractItem} pulled from this loot pool based on looter's stats.
	 */
	public AbstractItem lootItem(GameState state, PlayerCharacter looter) {
        int totalSumChance = 0;
        
        for(ItemLoot is : this.items){
            totalSumChance += is.getEffectiveChance(looter.getEffectiveStats());
        }
        ItemLoot randIS;
        do {
	        int index = RNG.nextInt(totalSumChance);
	        int sum = 0;
	        int i = 0;
	        while(sum < index){
	            sum += this.items.get(i++).getEffectiveChance(looter.getEffectiveStats());
	        }
	        
	        randIS = this.items.get(Math.max(0, i-1));
        } while (!randIS.canBeDropped(state));
        return randIS.getItem();
	}
	
	// ===================
}
