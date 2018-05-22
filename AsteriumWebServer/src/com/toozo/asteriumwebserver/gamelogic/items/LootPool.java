package com.toozo.asteriumwebserver.gamelogic.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class LootPool {
	// ===== CONSTANTS =====
	private static final Random RNG = new Random();
	// =====================
	
	// ===== FIELDS =====
	private Map<Supplier<? extends AbstractItem>, Double> probabilities;
	// ==================
	
	// ===== CONSTRUCTORS =====
	/**
	 * Constructs a new LootPool based on lootProbabilities.
	 * 
	 * @param lootProbabilities a {@link Map} from (? extends AbstractItem)::new to
	 * 							the probability that that item should be looted from this room.
	 */
	public LootPool(Map<Supplier<? extends AbstractItem>, Double> lootProbabilities) {
		this.probabilities = lootProbabilities;
	}
	// ========================
	
	// ===== METHODS =====
	/**
	 * @return a {@link Collection} of {@link AbstractItem}s gained from looting this loot pool.
	 */
	public Collection<AbstractItem> loot() {
		Collection<AbstractItem> result = new ArrayList<AbstractItem>();
		for (Supplier<? extends AbstractItem> constructor : this.probabilities.keySet()) {
			double probability = this.probabilities.get(constructor);
			double random = RNG.nextDouble();
			if (random > probability) {
				result.add(constructor.get());
			}
		}
		return result;
	}
	
	/**
	 * @param numberOfItems The number of {@link AbstractItem}s that should 
	 * 						be in the {@link Collection} returned.
	 * @return a {@link Collection} of {@link AbstractItem}s pulled from this loot pool.
	 */
	public Collection<AbstractItem> lootItems(int numberOfItems) {
		List<AbstractItem> items = new ArrayList<AbstractItem>();
		
		// Keep looting until you have at least numberOfItems
		while (items.size() < numberOfItems) {
			items.addAll(loot());
		}
		
		// Return numberOfItems' worth of items
		Collections.shuffle(items);
		return items.subList(0, numberOfItems);
	}
	
	/**
	 * @return an {@link AbstractItem} pulled from this loot pool.
	 */
	public AbstractItem lootItem() {
		Collection<AbstractItem> oneItemCollection = lootItems(1);
		AbstractItem[] oneItemArray = new AbstractItem[oneItemCollection.size()];
		oneItemArray = oneItemCollection.toArray(oneItemArray);
		return oneItemArray[0];
	}
	
	// ===================
}
