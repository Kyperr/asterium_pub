package com.toozo.asteriumwebserver.gamelogic;

import java.util.ArrayList;
import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.items.Item;

public class Inventory {
	// ===== FIELDS =====
	private Collection<Item> inventory;
	// ==================
	
	// ===== CONSTRUCTORS =====
	public Inventory() {
		this.inventory = new ArrayList<Item>();
	}
	// ========================

	// ===== METHODS =====
	/**
	 * @return The number of {@link Item}s in this Inventory.
	 */
	public int size() {
		return this.inventory.size();
	}

	/**
	 * @return True if there are no {@link Item}s in this Inventory, false otherwise.
	 */
	public boolean isEmpty() {
		return this.inventory.isEmpty();
	}

	/**
	 * @param item The item you want to check for.
	 * @return True if item is in this Inventory, false otherwise.
	 */
	public boolean contains(Item item) {
		return this.inventory.contains(item);
	}

	/**
	 * Adds item to this Inventory and returns whether it was added.
	 * 
	 * @param item The {@link Item} which should be added to this Inventory.
	 * @return True if item was added successfully, false otherwise.
	 */
	public boolean add(Item item) {
		return this.inventory.add(item);
	}

	/**
	 * Removes an instance of item from the Inventory, if Inventory contains item.
	 * 
	 * @param item The {@link Item} which should be removed.
	 * @return Whether an {@link Item} was removed.
	 */
	public boolean remove(Item item) {
		return this.inventory.remove(item);
	}

	/**
	 * Returns whether this Inventory contains all {@link Item}s in items.
	 * 
	 * @param items The items whose presence should be checked.
	 * @return True if all {@link Item}s in items are present in this Inventory.
	 */
	public boolean containsAll(Collection<Item> items) {
		return this.inventory.containsAll(items);
	}

	/**
	 * Adds all {@link Item}s in items to this Inventory.
	 * 
	 * @param items The {@link Item}s to be added to this Inventory.
	 * @return True if this Inventory changed as a result of the call, false otherwise.
	 */
	public boolean addAll(Collection<? extends Item> items) {
		return this.inventory.addAll(items);
	}
	
	/**
	 * Adds the contents of inventory to this Inventory.
	 * WARNING: The contents are not copied, just referenced.
	 * 
	 * @param inventory The Inventory whose contents should be added to this.
	 */
	public void addAll(Inventory inventory) {
		this.addAll(inventory.inventory);
	}
	
	/**
	 * Replaces the contents in this Inventory with the contents in newInventory.
	 * WARNING: The contents are not copied, just referenced.
	 * 
	 * @param newInventory The Inventory whose contents should be in this Inventory.
	 */
	public void replaceContents(final Inventory newInventory) {
		this.clear();
		this.addAll(newInventory);
	}

	/**
	 * Removes all {@link Item}s in items from this Inventory, if they are present.
	 * 
	 * @param items The {@link Item}s which should be removed from this Inventory.
	 * @return True if this Inventory changed as a result of the call, false otherwise.
	 */
	public boolean removeAll(Collection<? extends Item> items) {
		return this.inventory.removeAll(items);
	}

	/**
	 * Returns the intersection between this Inventory and items.
	 * In other words, removes all {@link Item}s that are in this Inventory but not in items.
	 * 
	 * @param items The {@link Item}s which should not be removed from this Inventory.
	 * @return True if this Inventory changed as a result of the call, false otherwise.
	 */
	public boolean retainAll(Collection<? extends Item> items) {
		return this.inventory.retainAll(items);
	}

	/**
	 * Empties this Inventory.
	 */
	public void clear() {
		this.inventory.clear();
	}
	// ===================
}