package onion.lifeproducts.rms.presentation;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import onion.lifeproducts.rms.application.ApplicationService;

/** Static class that holds the dynamic data that is fed into other classes */
public final class Data {

	static public ConsoleUIANSIOptions defaultSubmenuConsoleUIAnsiOptions =
		new ConsoleUIANSIOptions(
			ANSI.FG_GREEN,
			ANSI.FG_GREEN,
			ANSI.FG_YELLOW
		);
	static Util defaultSubmenuUtil = new Util(defaultSubmenuConsoleUIAnsiOptions);
	static final String defaultSubmenuPromptFormat = "(%s)> ";
	static final String defaultValuePrompt = ">> ";
	static final String defaultTopLevelCategoryPrefix = "Category: ";

	// graphics of the value from user input
	static final String defaultValueAnsi = ANSI.FG_CYAN + ANSI.BOLD;

	// graphics of the parent key from which the user came in
	static public String defaultSubmenuParentKeyAnsi = ANSI.FG_CYAN;
	// graphics of the submenu category
	static public String defaultSubmenuCategoryAnsi = ANSI.FG_MAGENTA;

	// graphics of the key of parent menus
	static public String defaultSubmenuKeyAnsi = ANSI.FG_CYAN;
	static public ConsoleUIEntry[] defaultSubmenuEntriesAppends = new ConsoleUIEntry[]{
		new ConsoleUIEntry("q", "Exit to previous menu", "exit")
	};

	static final public String regexNumber = "[-+]?[0-9]*(\\.[0-9]*)?";
	static final public String regexDigits = "[0-9]+";
	static final public String regexDigitsFloat = "[0-9]*(\\.[0-9]*)?";
	static final public String regexInt = "[-+]?[0-9]*";
	static final public String regexQuitOpt = "q|quit";
	static final public String regexFloat = regexNumber;
	static final public Predicate<String> fnIsInt = in -> in.matches(regexInt);
	static final public Predicate<String> fnIsNumber = in -> in.matches(regexNumber);

	static final private Runnable showListOfMaterials = () -> {
		List<String> materialsList = ApplicationService.getAllMaterialDescriptions();
		int materialsLength = materialsList.size();
		String materialsString = IntStream
			.range(0, materialsLength)
			.mapToObj(i -> 
				ANSI.formatString(
					String.valueOf(i + 1),
					defaultSubmenuKeyAnsi
				) +
				") " + materialsList.get(i)
			)
			.collect(Collectors.joining("\n"));
		Util.println(materialsString);
	};

	static final private Runnable showListOfProducts = () -> {
		List<String> productList = ApplicationService.getAllProductDescriptions();
		int productsLength = productList.size();
		String productsString = IntStream
			.range(0, productsLength)
			.mapToObj(i ->
				ANSI.formatString(
					String.valueOf(i + 1),
					defaultSubmenuKeyAnsi
				) +
				") " + productList.get(i)
			)
			.collect(Collectors.joining("\n"));
		Util.println(productsString);
	};

	/**
	 * List of all main option entries that the main ConsoleUI application will have.
	 */
	static public final ConsoleUIEntry[] mainMenuEntries = {

		createTopLevelEntry("1", "Materials", option -> {

			// menu options
			ConsoleUIEntry[] categoryMaterialsEntries = new ConsoleUIEntry[]{
				new ConsoleUIEntry("create", "Create a new material", () -> {

					List<String> recyclingCategoriesList = ApplicationService.getAllRecyclingCategoryDescriptions();

					if (recyclingCategoriesList == null || recyclingCategoriesList.size() == 0) {
						// will occur only if ApplicationService sends
						// an empty list of recycling categories,
						// which should not be the case in production
						System.out.println("[Debug] No recycling categories found. Check application service.");
						return;
					}

					List<String> recyclingGuidancesList = ApplicationService.getAllRecyclingGuidanceDescriptions();

					// early check whether there are any recycling guidances.
					// If no recycling guidances found,
					// then user will be force prompted to create one
					boolean needRecyclingGuidance = recyclingGuidancesList.size() == 0;

					// String name
					System.out.println("Material name:");
					String materialName = Util.getLineAnswer(defaultValuePrompt, defaultValueAnsi);

					// float recycleRate
					System.out.println("Recycle rate (float ∈ [0, ∞)):");
					String recycleRateRaw = Util.getLineAnswer(
						defaultValuePrompt,
						defaultValueAnsi,
						fnIsNumber
					);
					float recycleRate = Float.valueOf(recycleRateRaw);
					
					// float emissionFactor
					System.out.println("Emission factor (float ∈ [0, ∞)):");
					String emissionFactorRaw = Util.getLineAnswer(
						defaultValuePrompt,
						defaultValueAnsi,
						fnIsNumber
					);
					float emissionFactor = Float.valueOf(emissionFactorRaw);

					// int recyclingCategoryId
					int recyclingCategoriesLength = recyclingCategoriesList.size();
					System.out.printf("Recycling category:\n");
					String recyclingCategoriesString = IntStream
							.range(0, recyclingCategoriesLength)
							.mapToObj(i ->
								ANSI.formatString(
									String.valueOf(i + 1),
									defaultSubmenuKeyAnsi
								) + ") " + recyclingCategoriesList.get(i)
							)
							.collect(Collectors.joining("\n"));

					Util.println(recyclingCategoriesString);

					String recyclingCategoryRaw = Util.getLineAnswer(
						defaultValuePrompt,
						defaultValueAnsi,
						in -> {
							if (!fnIsInt.test(in)) return false;
							int value = Integer.parseInt(in);
							return (value > 0 && value <= recyclingCategoriesLength);
						}
					);
					int recyclingCategoryId = Integer.parseInt(recyclingCategoryRaw);

					// int recyclingGuidanceId
					int recyclingGuidancesLength = recyclingGuidancesList.size();
					String recyclingGuidancesString = needRecyclingGuidance
						? ""
						: IntStream
							.range(0, recyclingGuidancesLength)
							.mapToObj(i ->
								ANSI.formatString(
									String.valueOf(i + 1),
									defaultSubmenuKeyAnsi
								) +
								") " + recyclingGuidancesList.get(i)
							)
							.collect(Collectors.joining("\n")) + "\n";

					System.out.printf(
						"Recycling guidance%s:\n",
						needRecyclingGuidance
							? " (new: plain text)"
							: String.format(": [1, %d] / custom", recyclingGuidancesLength)
					);

					Util.print(recyclingGuidancesString);
					String recyclingGuidanceRaw;
					List<Integer> recyclingGuidanceIDsList =
						ApplicationService.getAllRecyclingGuidanceIds();

					int recyclingGuidanceId = -1;

					if (needRecyclingGuidance) {
						recyclingGuidanceRaw = Util.getLineAnswer(
							defaultValuePrompt,
							defaultValueAnsi,
							in -> {
								if (fnIsInt.test(in)) {
									int value = Integer.parseInt(in);
									return (value > 0 && recyclingGuidanceIDsList.contains(value));
								}
								return true;
							}
						);
					} else {
						recyclingGuidanceRaw = Util.getLineAnswer(
							defaultValuePrompt,
							defaultValueAnsi
						);
					}

					if (!needRecyclingGuidance && fnIsInt.test(recyclingGuidanceRaw)) {
						recyclingGuidanceId = Integer.parseInt(recyclingGuidanceRaw);
					} else {
						if (ApplicationService.addRecyclingGuidance(recyclingGuidanceRaw)) {
							List<String> recyclingGuidancesListInner =
								ApplicationService.getAllRecyclingGuidanceDescriptions();
							List<Integer> recyclingGuidanceIDsListInner =
								ApplicationService.getAllRecyclingGuidanceIds();
							int recyclingGuidancesLengthInner = recyclingGuidancesListInner.size();
							recyclingGuidancesString = IntStream
								.range(0, recyclingGuidancesListInner.size())
								.mapToObj(i ->
									ANSI.formatString(
										String.valueOf(i + 1),
										defaultSubmenuKeyAnsi
									) + ") " + recyclingGuidancesListInner.get(i)
								)
							.collect(Collectors.joining("\n"));
							System.out.printf(
								"Recycling guidance [1, %d]:\n",
								recyclingGuidancesLengthInner
							);
							Util.println(recyclingGuidancesString);
							recyclingGuidanceRaw = Util.getLineAnswer(
								defaultValuePrompt,
								defaultValueAnsi,
								in -> {
									if (!fnIsInt.test(in)) return false;
									int value = Integer.parseInt(in);
									return (value > 0 && recyclingGuidanceIDsListInner.contains(value));
								}
							);
						} else {
							System.out.println("Failed to add recycling guidance: object exists");
							return;
						}
						recyclingGuidanceId = Integer.parseInt(recyclingGuidanceRaw);
					}

					Util.printf(
						"Creating the material with following parameters:\n" +
						"Name: %s\n" +
						"Recycle rate: %f\n" +
						"Emission factor: %f\n" +
						"Recycling category: %d\n" +
						"Recycling guidance ID: %s\n",
						materialName,
						recycleRate,
						emissionFactor,
						recyclingCategoryId,
						recyclingGuidanceId
					);

					int materialId = ApplicationService.addMaterial(
						materialName,
						recycleRate,
						emissionFactor,
						recyclingCategoryId - 1,
						recyclingGuidanceId
					);

					System.out.printf(
						"\nCreated material with ID: %s\n",
						ANSI.formatString(String.valueOf(materialId), ANSI.FG_GREEN)
					);
				}),
				new ConsoleUIEntry("list", "List existing materials", showListOfMaterials),
			};

			createSubmenu(categoryMaterialsEntries, defaultSubmenuUtil, option, "Materials")
				.runMenu();
		}),

		createTopLevelEntry("2", "Products", option -> {

			ConsoleUIEntry[] categoryProductEntries = new ConsoleUIEntry[]{

				new ConsoleUIEntry("create", "Create a new product", () -> {

					if (ApplicationService.getAllMaterialIds().size() == 0) {
						System.out.println("No material are yet added. Add materials first to proceed.");
						return;
					}
					
					// String name
					System.out.println("Product name:");
					String productName = Util.getLineAnswer(defaultValuePrompt, defaultValueAnsi);

					// Map<Integer, Float> materialRatios
					List<String> materialsList = ApplicationService.getAllMaterialDescriptions();
					int materialsLength = materialsList.size();
					String materialsString = IntStream
						.range(0, materialsLength)
						.mapToObj(i -> ANSI.formatString(
							String.valueOf(i + 1), defaultSubmenuKeyAnsi) + ") " + materialsList.get(i)
						)
						.collect(Collectors.joining("\n"));

					System.out.println(
						"Material ratios:\n" +
						"Each entry of format: " +
						ANSI.formatString("<material ID> <ratio>", ANSI.FG_GREEN) + "\n" +
						"Where ratio is either a float ∈ (0, 1], or a percentage (e.g. 69.420%)\n" +
						String.format(
							"When you're done, type %s.",
							"'" + ANSI.formatString("q", ANSI.FG_GREEN) + "'/'" +
							ANSI.formatString("quit", ANSI.FG_GREEN) + "'"
						)
					);

					System.out.println("Material list:");
					Util.println(materialsString);

					Map<Integer, Float> materialRatios = new HashMap<>();
					String productMaterialInputRaw, materialRatioRaw;
					String[] productMaterialInputParts;
					int materialId;
					List<Integer> validMaterialIDs = ApplicationService.getAllMaterialIds();
					float materialRatioInProduct, totalMaterialRatios = 0;

					while (true) {
						productMaterialInputRaw = Util.getLineAnswer(
							defaultValuePrompt,
							defaultValueAnsi,
							in -> {
								return in.matches(regexQuitOpt) ||
									in.matches(
										String.format(
											"^\\d+\\s+(%s%%|%s)$",
											regexDigitsFloat,
											regexDigitsFloat
										)
									);
							}
						);

						if (productMaterialInputRaw.matches(regexQuitOpt)) {
							break;
						}
						
						productMaterialInputParts = productMaterialInputRaw.split("\\s+");

						// first is always a valid integer
						materialId = Integer.parseInt(productMaterialInputParts[0]);

						if (!validMaterialIDs.contains(materialId)) {
							System.out.printf(
								"Material ID '%s' is invalid (doesn't exist)\n",
								ANSI.formatString(String.valueOf(materialId), ANSI.FG_YELLOW)
							);
							continue;
						}

						if (materialRatios.containsKey(materialId)) {
							System.out.printf(
								"Material with id #%s already added. Skipping...\n",
								ANSI.formatString(String.valueOf(materialId), ANSI.FG_YELLOW)
							);
							continue;
						}

						materialRatioRaw = productMaterialInputParts[1];

						// String.matches() checks for the entire
						// string to match the given regex.
						// "69%".matches("%") => false,
						// since string doesn't contain only one
						// string "%" as a whole string
						if (materialRatioRaw.matches(".*%")) {
							// percentage
							materialRatioRaw = materialRatioRaw.substring(0, materialRatioRaw.length() - 1);
							materialRatioInProduct = Float.parseFloat(materialRatioRaw);
							if (materialRatioInProduct > 100) {
								System.out.println("Percentage can't be more than 100%.");
								continue;
							} else if (materialRatioInProduct == 0) {
								System.out.println("Percentage of 0% doesn't make sense.");
								continue;
							}
							materialRatioInProduct /= 100f;
						} else {
							// ratio
							materialRatioInProduct = Float.parseFloat(materialRatioRaw);
							if (materialRatioInProduct > 1 || materialRatioInProduct < 0) {
								System.out.println("Invalid range. Only values ∈ (0, 1]");
							}
						}

						if ((totalMaterialRatios + materialRatioInProduct) > 1) {
							System.out.printf(
								"Sum of all ratios of materials exceeded 100%% (%.2f%%). Material was not added.",
								(totalMaterialRatios + materialRatioInProduct) * 100
							);
							continue;
						}

						// update the value only if everything is ok.
						// Otherwise IEEE-754 32-bit will let us know about
						// it's existance in form of rouding errors when
						// performing calculations on floating point numbers
						totalMaterialRatios += materialRatioInProduct;

						materialRatios.put(materialId, materialRatioInProduct);

						System.out.printf(
							"Added material with ID #%s and ratio %.2f%%.\n",
							ANSI.formatString(String.valueOf(materialId), ANSI.FG_GREEN),
							materialRatioInProduct * 100
						);
					}

					if (materialRatios.size() == 0) {
						System.out.println("No materials were provided. Skipping...");
						return;
					}

					// LocalDateTime lifespan
					System.out.println("End date of the product livespan (YYYY-MM-DD):");
					String productEndDateRaw = Util.getLineAnswer(
							defaultValuePrompt,
							defaultValueAnsi,
							Util::isValidDate
						);

					int
						productEndYear = Integer.parseInt(productEndDateRaw.substring(0, 4)),
						productEndMonth = Integer.parseInt(productEndDateRaw.substring(5, 7)),
						productEndDayOfMonth = Integer.parseInt(productEndDateRaw.substring(8, 10))
					;

					LocalDateTime lifespan = LocalDateTime.of(
						productEndYear,
						productEndMonth,
						productEndDayOfMonth,
						0,
						0
					);

					Util.printf(
						"Creating the product with following parameters:\n" +
						"Name: %s\n" +
						"Ratios: %s\n" +
						"End date: %s\n",
						productName,
						materialRatios,
						String.format(
							"%d-%02d-%02d",
							lifespan.getYear(),
							lifespan.getMonthValue(),
							lifespan.getDayOfMonth()
						)
					);
					
					int productId = ApplicationService.addProduct(productName, materialRatios, lifespan);

					System.out.printf(
						"\nCreated product with ID: %s\n",
						ANSI.formatString(String.valueOf(productId), ANSI.FG_GREEN)
					);
				
				}),

				new ConsoleUIEntry("list", "List existing products", showListOfProducts),

			};

			createSubmenu(categoryProductEntries, defaultSubmenuUtil, option, "Products")
				.runMenu();
		}),

		createTopLevelEntry("3", "Recycling Guidances", option -> {

			ConsoleUIEntry[] categoryRecyclingGuidancesEntries = new ConsoleUIEntry[]{

				new ConsoleUIEntry("create", "Create a recycling guidance", () -> {
					System.out.println("Enter recycling guidance description:");
					String recyclingGuidanceDescription =
						Util.getLineAnswer(
							defaultValuePrompt,
							defaultValueAnsi
						);

					System.out.println(
						ApplicationService.addRecyclingGuidance(recyclingGuidanceDescription)
							? "Successfully added new recycling guidance."
							: "Failed to add new recycling guidance: already exists in the system."
					);
				}),

				new ConsoleUIEntry("list", "List all recycling guidances", () -> {
					List<String> recyclingGuidancesList = ApplicationService.getAllRecyclingGuidanceDescriptions();
					int recyclingGuidancesLength = recyclingGuidancesList.size();
					String recyclingGuidancesString = IntStream
						.range(0, recyclingGuidancesLength)
						.mapToObj(i ->
							ANSI.formatString(
								String.valueOf(i + 1),
								defaultSubmenuKeyAnsi
							) +
							") " + recyclingGuidancesList.get(i)
						)
						.collect(Collectors.joining("\n"));
					Util.println(recyclingGuidancesString);
				})

			};

			createSubmenu(categoryRecyclingGuidancesEntries, defaultSubmenuUtil, option, "Recycling Guidances")
				.runMenu();
		}),

		createTopLevelEntry("4", "Recycling", option -> {
			List<String>
				impactCalculationStrategiesList =
				ApplicationService.getAllImpactCalculationStrategiesDescriptions()
			;

			String impactCalculationStrategiesString = IntStream
				.range(0, impactCalculationStrategiesList.size())
				.mapToObj(i ->
					ANSI.formatString(
						String.valueOf(i + 1),
						defaultSubmenuKeyAnsi
					) +
					") " + impactCalculationStrategiesList.get(i)
				)
				.collect(Collectors.joining("\n"));

			List<Integer>
				validMaterialIDs = ApplicationService.getAllMaterialIds(),
				validProductIDs = ApplicationService.getAllProductIds(),
				validImpactCalculationStrategyIDs =
					ApplicationService.getAllImpactCalculationStrategyIds()
			;

			ConsoleUIEntry[] recyclinSectionEntries = new ConsoleUIEntry[]{

				new ConsoleUIEntry("materials", "Recycle materials", () -> {
					if (validMaterialIDs.size() == 0) {
						System.out.println("No material are yet added. Add materials first to proceed.");
						return;
					}

					System.out.println("Enter a list of material IDs to recycle (separated with spaces):");

					showListOfMaterials.run();

					String materialsListRaw = Util.getLineAnswer(
						defaultValuePrompt,
						defaultValueAnsi,
						in -> in.matches("\\d+(\\s+\\d+)*")
					);
					int[] materialIDs = Arrays
						.asList(materialsListRaw.split("\\s+"))
						.stream()
						.mapToInt(Integer::parseInt)
						.toArray()
					;

					Integer[] invalidMaterialIds = new Integer[materialIDs.length];
					boolean invalidMaterialIdsFound = false;

					int materialId;

					for (
						int i = 0;
						i < materialIDs.length;
						i++
					) {
						materialId = materialIDs[i];
						if (!validMaterialIDs.contains(materialId)) {
							invalidMaterialIds[i] = materialId;
							invalidMaterialIdsFound = true;
						}
					}

					if (invalidMaterialIdsFound) {
						System.out.print("These material IDs are not valid:");
						for (Integer invalidMaterialId : invalidMaterialIds) {
							if (invalidMaterialId != null) {
								System.out.print(
									" " +
									ANSI.formatString(
										String.valueOf(invalidMaterialId),
										ANSI.FG_YELLOW
									)
								);
							}
						}
						System.out.println("\nMake sure to enter valid material IDs.");
						return;
					}

					String
						totalMaterialsAmountDisplay = String.valueOf(materialIDs.length),
						impactCalculationStrategyIdRaw
					;
					List<List<String>> recyclingResultList = new ArrayList<>(materialIDs.length);
					List<String> recyclingResult;
					int impactCalculationStrategyId;

					for (
						int i = 0;
						i < materialIDs.length;
						i++
					) {
						materialId = materialIDs[i];
						System.out.printf(
							"Material(#%s) %s/%s:\n",
							ANSI.formatString(String.valueOf(materialId), ANSI.FG_GREEN),
							ANSI.formatString(String.valueOf(i + 1), ANSI.FG_GREEN),
							totalMaterialsAmountDisplay
						);

						
						System.out.println("Strategy to use:");
						Util.println(impactCalculationStrategiesString);

						impactCalculationStrategyIdRaw = Util.getLineAnswer(
							defaultValuePrompt,
							defaultValueAnsi,
							in -> in.matches(regexDigits) &&
								validImpactCalculationStrategyIDs.contains(Integer.parseInt(in))
							
						);

						impactCalculationStrategyId = Integer.parseInt(impactCalculationStrategyIdRaw);

						recyclingResultList.add(
							ApplicationService.recycleMaterialById(materialId, impactCalculationStrategyId)
						);
					}

					int totalAmountOfRecyclings = recyclingResultList.size();

					for (
						int i = 0;
						i < totalAmountOfRecyclings;
						i++
					) {
						materialId = materialIDs[i];
						recyclingResult = recyclingResultList.get(i);

						System.out.printf(
							"Material(#%s) %s/%s:\n",
							ANSI.formatString(String.valueOf(materialId), ANSI.FG_GREEN),
							ANSI.formatString(String.valueOf(i + 1), ANSI.FG_GREEN),
							totalMaterialsAmountDisplay
						);

						if (recyclingResult == null || recyclingResult.size() == 0)  {
							System.out.println("[Debug] no information provided as a result of recycling");
						}

						for (String line : recyclingResult) Util.print(line);
						System.out.println(
							i != (totalAmountOfRecyclings - 1)
								? "\n"
								: ""
						);
					}

				}),

				new ConsoleUIEntry("products", "Recycle products", () -> {

					if (validProductIDs.size() == 0) {
						System.out.println("No products are yet added. Add materials first to proceed.");
						return;
					}

					System.out.println("Enter a list of product IDs to recycle (separated with spaces):");

					showListOfProducts.run();

					String productsListRaw = Util.getLineAnswer(
						defaultValuePrompt,
						defaultValueAnsi,
						in -> in.matches("\\d+(\\s+\\d+)*")
					);
					int[] productIDs = Arrays
						.asList(productsListRaw.split("\\s+"))
						.stream()
						.mapToInt(Integer::parseInt)
						.toArray()
					;

					Integer[] invalidProducIds = new Integer[productIDs.length];
					boolean invalidProductIdsFound = false;

					int productId;

					for (
						int i = 0;
						i < productIDs.length;
						i++
					) {
						productId = productIDs[i];
						if (!validProductIDs.contains(productId)) {
							invalidProducIds[i] = productId;
							invalidProductIdsFound = true;
						}
					}

					if (invalidProductIdsFound) {
						System.out.print("These product IDs are not valid:");
						for (Integer invalidProductId : invalidProducIds) {
							if (invalidProductId != null) {
								System.out.print(
									" " +
									ANSI.formatString(
										String.valueOf(invalidProductId),
										ANSI.FG_YELLOW
									)
								);
							}
						}
						System.out.println("\nMake sure to enter valid product IDs.");
						return;
					}

					String
						totalProductsAmountDisplay = String.valueOf(productIDs.length),
						impactCalculationStrategyIdRaw
					;
					List<List<String>> recyclingResultList = new ArrayList<>(productIDs.length);
					List<String> recyclingResult;
					int impactCalculationStrategyId;

					for (
						int i = 0;
						i < productIDs.length;
						i++
					) {
						productId = productIDs[i];
						System.out.printf(
							"Product(#%s) %s/%s:\n",
							ANSI.formatString(String.valueOf(productId), ANSI.FG_GREEN),
							ANSI.formatString(String.valueOf(i + 1), ANSI.FG_GREEN),
							totalProductsAmountDisplay
						);
						
						System.out.println("Strategy to use:");
						Util.println(impactCalculationStrategiesString);

						impactCalculationStrategyIdRaw = Util.getLineAnswer(
							defaultValuePrompt,
							defaultValueAnsi,
							in -> in.matches(regexDigits) &&
								validImpactCalculationStrategyIDs.contains(Integer.parseInt(in))
							
						);

						impactCalculationStrategyId = Integer.parseInt(impactCalculationStrategyIdRaw);

						recyclingResultList.add(
							ApplicationService.recycleProductById(productId, impactCalculationStrategyId)
						);
					}

					int totalAmountOfRecyclings = recyclingResultList.size();

					for (
						int i = 0;
						i < totalAmountOfRecyclings;
						i++
					) {
						productId = productIDs[i];
						recyclingResult = recyclingResultList.get(i);

						System.out.printf(
							"Product(#%s) %s/%s:\n",
							ANSI.formatString(String.valueOf(productId), ANSI.FG_GREEN),
							ANSI.formatString(String.valueOf(i + 1), ANSI.FG_GREEN),
							totalProductsAmountDisplay
						);

						if (recyclingResult == null || recyclingResult.size() == 0)  {
							System.out.println("[Debug] no information provided as a result of recycling");
						}

						for (String line : recyclingResult) Util.print(line);
						System.out.println(
							i != (totalAmountOfRecyclings - 1)
								? "\n"
								: ""
						);
					}

				}),
			};

			createSubmenu(recyclinSectionEntries, defaultSubmenuUtil, option, "Recycling")
				.runMenu();
		}),
	};

	/** Create a submenu using full amount of values
	 * - main entries<br>
	 * - util object<br>
	 * - option that was passes into callback (optional)
	 */
	static public ConsoleUI createSubmenu(
		ConsoleUIEntry[] consoleUIEntries,
		Util util,
		String option,
		String category,
		String promptFormat,
		Object ...promptValues
	) {
		ConsoleUIEntry[] allEntries = Util.concatArraysWithCopy(consoleUIEntries, Data.defaultSubmenuEntriesAppends);
		return new ConsoleUI(allEntries, util)
			.setDefaultInputPrompt(
				String.format(promptFormat, promptValues)
			)
			.setInitMessage(
				String.format(
					ANSI.formatString("Category: %s\n\n", ANSI.BOLD),
					ANSI.formatString(category, defaultSubmenuCategoryAnsi)
				)
			);
	}

	/** Create a submenu using trimed amount of values provided:<br>
	 * - main entries<br>
	 * - util object<br>
	 * - option that was passes into callback (optional)
	 */
	static public ConsoleUI createSubmenu(
		ConsoleUIEntry[] consoleUIEntries,
		Util util,
		String option,
		String category
	) {
		return createSubmenu(
			consoleUIEntries,
			util,
			option,
			category,
			Data.defaultSubmenuPromptFormat,
			new Object[]{ANSI.formatString(option, Data.defaultSubmenuParentKeyAnsi)}
		);
	}

	static public ConsoleUIEntry createTopLevelEntry(String option, String category, Lambda<String> callback) {
		return new ConsoleUIEntry(
			option,
			String.format(
				"%s%s",
				defaultTopLevelCategoryPrefix,
				ANSI.formatString(category, defaultSubmenuCategoryAnsi + ANSI.BOLD)
			),
			callback
		);
	}

	static public ConsoleUIEntry createTopLevelEntry(String option, String category, Runnable callback) {
		return createTopLevelEntry(option, category, new Lambda<>(callback));
	}

	static public ConsoleUIEntry createTopLevelEntry(String option, String category, Consumer<String> callback) {
		return createTopLevelEntry(option, category, new Lambda<>(callback));
	}

}
