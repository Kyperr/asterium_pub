package com.toozo.asteriumwebserver.gamelogic.items.location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Location.LocationType;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;
import com.toozo.asteriumwebserver.gamelogic.statuseffects.AffectStats;

/**
 * Book item which can be used to permanently boost a stat or stats.
 * 
 * @author Greg Schmitt
 */
public class Book extends AbstractLocationItem {
	// ===== CONSTANTS =====
	// Map from Stat to a collection of book flavor texts associated with that stat
	public static final Map<Stat, Collection<String>> BOOK_FLAVOR_TEXTS;
	static {
		Map<Stat, Collection<String>> namesMap = new HashMap<Stat, Collection<String>>();

		// Stamina Book Flavor Texts
		namesMap.put(Stat.STAMINA,
				Arrays.asList("Workouts for the Lazy", "Bunches of Punches and Crunches", "Hernia-Free Deadlifts",
						"Wrastlin': A Review", "Push-ups and Pull-ups", "Weightlifting in Zero-G", "Creatinism",
						"The Peccing Order"));

		// Luck Book Flavor Texts
		namesMap.put(Stat.LUCK,
				Arrays.asList("The Ace Up My Sleeve", "History of Space Poker", "Turns, Rivers, and Flops, Oh My!",
						"How to Win at Games of Chance", "Living With Paraskevidekatriaphobia", "On Horseshoes",
						"Quadrifolium", "21: Triple Seven", "Space Albatross: More Than A Myth?",
						"Outsmarting Statistics"));

		// Intuition Book Flavor Texts
		namesMap.put(Stat.INTUITION,
				Arrays.asList("Elementary Crystallography", "Cellular Automata for Dummies",
						"Chicken Soup for the Neurobiologist's Soul", "Defibrillation and You",
						"20 Ways To Milk Your Pineal Gland", "Spectacular Spectacled Specialists",
						"Spectacular Spectacled Specialists: Swimsuit Edition", "The Hypocritical Oath",
						"Forceps and Foresight", "Preventing the Helvetica Scenario",
						"Everything You Need To Know About Exsanguination"));

		BOOK_FLAVOR_TEXTS = Collections.unmodifiableMap(namesMap);
	};

	// Single Stat Collections
	public static final Collection<Stat> STAMINA = Arrays.asList(Stat.STAMINA);
	public static final Collection<Stat> LUCK = Arrays.asList(Stat.LUCK);
	public static final Collection<Stat> INTUITION = Arrays.asList(Stat.INTUITION);
	// Dual Stat Collections
	public static final Collection<Stat> STAMINA_LUCK = Arrays.asList(Stat.STAMINA, Stat.LUCK);
	public static final Collection<Stat> STAMINA_INTUITION = Arrays.asList(Stat.STAMINA, Stat.INTUITION);
	public static final Collection<Stat> LUCK_INTUITION = Arrays.asList(Stat.LUCK, Stat.INTUITION);
	// Triple Stat Collections
	public static final Collection<Stat> STAMINA_LUCK_INTUITION = Arrays.asList(Stat.STAMINA, Stat.LUCK,
			Stat.INTUITION);
	// Don't judge me.

	// Other
	public static final String ERROR_NAME = "Squashing Bugs, 1st Edition";
	public static final String IMG = "book.png";
	public static final int DEFAULT_STAT_BOOST = 1;
	public static final double SINGLE_PROBABILITY = 0.9;
	public static final double DOUBLE_PROBABILITY = 0.09;
	public static final double TRIPLE_PROBABILITY = 0.01;
	private static final Collection<LocationType> BOOK_LOCATIONS = Arrays.asList(LocationType.CONTROL_ROOM);
	private static final Random RNG = new Random();
	// =====================

	// ===== INSTANCE FIELDS =====
	private Collection<Stat> stats;
	private Function<Integer, Integer> effect;
	// ===========================

	// ===== STATIC METHODS =====
	/**
	 * Returns a Collection of {@link Stat}s.
	 * 
	 * Probabilities of the number of {@link Stat}s it contains are given by
	 * SINGLE_PROBABILITY, DOUBLE_PROBABILITY, and TRIPLE_PROBABILITY.
	 * 
	 * Which {@link Stat}s are included are a subset of that size of all
	 * non-variable {@link Stat}s.
	 * 
	 * @return a "random" {@link Collection} of {@link Stat}s, as defined above.
	 */
	private static Collection<Stat> generateStats() {
		int numberOfStats = 0;
		ArrayList<Stat> result = new ArrayList<Stat>();

		// Initialize result with all non-variable Stats
		for (Stat stat : Stat.values()) {
			if (!stat.isVariable()) {
				result.add(stat);
			}
		}

		// Generate weights distribution (e.g. {0.8, 0.15, 0.05} -> {0.8, 0.95, 1.0})
		double[] weights = { SINGLE_PROBABILITY, DOUBLE_PROBABILITY, TRIPLE_PROBABILITY };
		for (int i = 1; i < weights.length; i++) {
			weights[i] += weights[i - 1];
		}

		// Determine number of Stats which should be in result based on weights
		// distribution.
		double random = RNG.nextDouble();
		for (numberOfStats = 1; numberOfStats - 1 < weights.length
				&& random > weights[numberOfStats - 1]; numberOfStats++)
			;

		// Randomly return that many Stats
		Collections.shuffle(result, RNG);
		return result.subList(0, numberOfStats);
	}

	/**
	 * Generates a book name based on the stats given. Ex: A book which boosts luck
	 * will be called "Luck Book".
	 * 
	 * @param stats
	 *            The {@link Stat}s which the returned name should be related to.
	 * @return a generated name containing the stats the book improves and "Book".
	 */
	private static String generateName(Collection<Stat> stats) {
		StringBuilder name = new StringBuilder();

		for (Stat stat : stats) {
			name.append(stat.toString());
			name.append(" ");
		}
		name.append("Book");

		return name.toString();
	}

	/**
	 * Generates a book description based on the stats given. Ex: A book which
	 * boosts luck will have the description "Permanently increases your Luck by 1."
	 * Books which boost more than one stat will look like this: "Permanently
	 * increases your Luck, Intuition, and Stamina by 1."
	 * 
	 * @param stats
	 *            The {@link Stat}s which the returned description should be related
	 *            to.
	 * @return a generated description containing the stats the book improves.
	 */
	private static String generateDescription(Collection<Stat> stats) {
		String desc = "Permanently increases your %s by 1.";
		String lastStat = null;
		String statName = null;
		
		for (Stat stat : stats) {
			if (lastStat != null) {
				lastStat += lastStat + ", ";
			}
			statName = stat.toString();
			lastStat = statName;
		}
		if (!lastStat.equals(statName)) {
			lastStat += "and ";
		}
		
		return String.format(desc, lastStat);
	}

	/**
	 * @param stats
	 *            The {@link Stat}s which the returned flavor text should be related
	 *            to.
	 * @return a random flavor text related to a {@link Stat} in stats, or
	 *         ERROR_NAME if stats is empty or an error has occurred.
	 */
	private static String getRandomFlavorText(Collection<Stat> stats) {
		List<String> possibleTexts = new ArrayList<String>();

		// Assemble list of possible flavor texts
		for (Stat stat : stats) {
			if (BOOK_FLAVOR_TEXTS.get(stat) != null) {
				possibleTexts.addAll(BOOK_FLAVOR_TEXTS.get(stat));
			}
		}

		if (possibleTexts.isEmpty()) {
			return ERROR_NAME;
		} else {
			// Pick a random flavor texts
			return "It's titled \"" + possibleTexts.get(RNG.nextInt(possibleTexts.size() - 1)) + "\".";
		}
	}
	// ==========================

	// ===== CONSTRUCTORS =====
	/**
	 * Construct a book with a random name and stats which boosts stats by
	 * DEFAULT_STAT_BOOST.
	 */
	public Book() {
		this(generateStats());
	}

	/**
	 * Construct a book with a random name which boosts defined stats by
	 * DEFAULT_STAT_BOOST.
	 * 
	 * @param stats
	 *            The {@link Stat}s which this book will boost.
	 */
	public Book(Collection<Stat> stats) {
		this(stats, DEFAULT_STAT_BOOST);
	}

	/**
	 * Construct a book with a random name and random stats which boosts stats by a
	 * defined amount.
	 * 
	 * @param amount
	 *            The amount by which this book will boost stats.
	 */
	public Book(final int amount) {
		this(generateStats(), amount);
	}

	/**
	 * Construct a book with a name, description, and random flavor text which
	 * boosts defined stats by a defined amount.
	 * 
	 * @param stats
	 *            The {@link Stat}s which this book will boost.
	 * @param amount
	 *            The amount by which this book will boost stats.
	 */
	public Book(Collection<Stat> stats, final int amount) {
		this(generateName(stats), generateDescription(stats), getRandomFlavorText(stats), stats, amount);
	}

	/**
	 * Construct a book with defined name, stats, and amount to boost by.
	 * 
	 * @param name
	 *            The name of the book.
	 * @param stats
	 *            The {@link Stat}s which this book will boost.
	 * @param amount
	 *            The amount by which this book will boost stats.
	 */
	public Book(final String name, final String description, final String flavor, final Collection<Stat> stats,
			final int amount) {
		super(name, description, flavor, IMG, BOOK_LOCATIONS);
		this.stats = stats;
		this.effect = (oldStat) -> (oldStat + amount);
	}
	// ========================

	// ===== METHODS =====
	@Override
	/**
	 * Give user a permanent AffectStats status effect that boosts their stats by
	 * amount.
	 */
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		Map<Stat, Function<Integer, Integer>> statModifiers = new HashMap<Stat, Function<Integer, Integer>>();
		for (Stat stat : this.stats) {
			statModifiers.put(stat, this.effect);
		}

		user.addStatusEffect(new AffectStats(user, "Read " + this.getName(), statModifiers));
	}
	// ===================

}
