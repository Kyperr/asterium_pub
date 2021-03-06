package com.toozo.asteriumwebserver.gamelogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

public class Inventory implements Iterable<AbstractItem> {
	// ===== CONSTANTS =====
	public static final int INVENTORY_SIZE = 16;
	// =====================
	
	// ===== FIELDS =====
	private Collection<AbstractItem> inventory;
	// ==================
	
	// ===== CONSTRUCTORS =====
	public Inventory() {
		this.inventory = new ArrayList<AbstractItem>();
	}
	// ========================

	// ===== METHODS =====
	/**
	 * @return The number of {@link AbstractItem}s in this Inventory.
	 */
	public int size() {
		return this.inventory.size();
	}

	/**
	 * @return True if there are no {@link AbstractItem}s in this Inventory, false otherwise.
	 */
	public boolean isEmpty() {
		return this.inventory.isEmpty();
	}

	/**
	 * @param item The item you want to check for.
	 * @return True if item is in this Inventory, false otherwise.
	 */
	public boolean contains(AbstractItem item) {
		return this.inventory.contains(item);
	}

	/**
	 * Adds item to this Inventory and returns whether it was added.
	 * Cases where it might not be added include a full inventory.
	 * 
	 * @param item The {@link AbstractItem} which should be added to this Inventory.
	 * @return True if item was added successfully, false otherwise.
	 */
	public boolean add(AbstractItem item) {
		// Check if there is room in inventory and add it if there is.
		return this.inventory.size() < INVENTORY_SIZE && this.inventory.add(item);
	}

	/**
	 * Removes an instance of item from the Inventory, if Inventory contains item.
	 * 
	 * @param item The {@link AbstractItem} which should be removed.
	 * @return Whether an {@link AbstractItem} was removed.
	 */
	public boolean remove(AbstractItem item) {
		return this.inventory.remove(item);
	}

	/**
	 * Returns whether this Inventory contains all {@link AbstractItem}s in items.
	 * 
	 * @param items The items whose presence should be checked.
	 * @return True if all {@link AbstractItem}s in items are present in this Inventory.
	 */
	public boolean containsAll(Collection<AbstractItem> items) {
		return this.inventory.containsAll(items);
	}
	
	/**
	 * Replaces the contents in this Inventory with the contents in newInventory.
	 * WARNING: The contents are not copied, just re-referenced.
	 * 
	 * @param newInventory The Inventory whose contents should be in this Inventory.
	 */
	public void replaceContents(final Inventory newInventory) {
		this.clear();
		for (AbstractItem item : newInventory.inventory) {
			this.add(item);
		}
	}

	/**
	 * Removes all {@link AbstractItem}s in items from this Inventory, if they are present.
	 * 
	 * @param items The {@link AbstractItem}s which should be removed from this Inventory.
	 * @return True if this Inventory changed as a result of the call, false otherwise.
	 */
	public boolean removeAll(Collection<? extends AbstractItem> items) {
		return this.inventory.removeAll(items);
	}

	/**
	 * Returns the intersection between this Inventory and items.
	 * In other words, removes all {@link AbstractItem}s that are in this Inventory but not in items.
	 * 
	 * @param items The {@link AbstractItem}s which should not be removed from this Inventory.
	 * @return True if this Inventory changed as a result of the call, false otherwise.
	 */
	public boolean retainAll(Collection<? extends AbstractItem> items) {
		return this.inventory.retainAll(items);
	}

	/**
	 * Empties this Inventory.
	 */
	public void clear() {
		this.inventory.clear();
	}
	
	/**
	 * @return True if this inventory is full, false otherwise.
	 */
	public boolean isFull() {
		return this.size() >= INVENTORY_SIZE;
	}
	

	@Override
	public Iterator<AbstractItem> iterator() {
		return this.inventory.iterator();
	}
	// ===================
}
