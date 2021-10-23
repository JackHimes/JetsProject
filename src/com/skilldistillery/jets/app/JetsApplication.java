package com.skilldistillery.jets.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.jets.entities.AirField;
import com.skilldistillery.jets.entities.CargoJet;
import com.skilldistillery.jets.entities.FighterJet;
import com.skilldistillery.jets.entities.Jet;
import com.skilldistillery.jets.entities.PassengerJet;

public class JetsApplication {
	private AirField airField = new AirField();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<Jet> listOfJets = new ArrayList<Jet>();
		JetsApplication ja = new JetsApplication();
		boolean keepGoing = true;

		ja.jetListCreation(listOfJets);

		while (keepGoing) {
			ja.displayMenu();
			int userInput = scanner.nextInt();
			switch (userInput) {
			case 1:
				ja.displayFleet(listOfJets);
				break;
			case 2:
				ja.flyTheFleet(listOfJets);
				break;
			case 3:
				ja.findFastestJet(listOfJets);
				break;
			case 4:
				ja.findLongestRangeJet(listOfJets);
				break;
			case 5:
				ja.loadFleetsCargo(listOfJets);
				break;
			case 6:
				ja.combatReadyFleets(listOfJets);
				break;
			case 7:
				ja.addJetToFleet(listOfJets, scanner);
				break;
			case 8:
				ja.removeJetFromFleet(listOfJets, scanner);
				break;
			case 9:
				keepGoing = false;
				System.out.println("You have left the air field");
				break;
			default:
				System.out.println("Please enter a valid option");
				break;
			}

		}

	}

	public void jetListCreation(List<Jet> listOfJets) {

		try (BufferedReader bufIn = new BufferedReader(new FileReader("jets.txt"))) {
			String line;
			while ((line = bufIn.readLine()) != null) {
				String[] seperatedJets = line.split(",");
				String type = seperatedJets[0];
				String model = seperatedJets[1];
				double speed = Double.parseDouble(seperatedJets[2]);
				int range = Integer.parseInt(seperatedJets[3]);
				long price = Long.parseLong(seperatedJets[4]);

				if (type.equals("FighterJet")) {
					Jet j = new FighterJet(model, speed, range, price);
					listOfJets.add(j);
				}
				if (type.equals("CargoJet")) {
					Jet j = new CargoJet(model, speed, range, price);
					listOfJets.add(j);
				}
				if (type.equals("PassengerJet")) {
					Jet j = new PassengerJet(model, speed, range, price);
					listOfJets.add(j);
				}

			}
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	public void displayMenu() {
		System.out.println("");
		System.out.println("=====================================");
		System.out.println("|                                   |");
		System.out.println("|  1. Fleet list                    |");
		System.out.println("|  2. Fly all jets                  |");
		System.out.println("|  3. View fastest jet              |");
		System.out.println("|  4. View longest range jet        |");
		System.out.println("|  5. Load all cargo jets           |");
		System.out.println("|  6. Deploy fighter jets           |");
		System.out.println("|  7. Add jet to the fleet          |");
		System.out.println("|  8. Remove jet from fleet         |");
		System.out.println("|  9. Quit program                  |");
		System.out.println("|                                   |");
		System.out.println("=====================================");
		System.out.println("");

	}

	public void displayFleet(List<Jet> listOfJets) {
		int counter = 0;
		for (Jet jet : listOfJets) {
			System.out.println(jet);
			System.out.println("----------------------------------------------------------------------------------");
			counter++;
		}
		System.out.println("There are " + counter + " jets in the fleet currently.");
	}

	public void flyTheFleet(List<Jet> listOfJets) {
		System.out.println("The fleet of Jets are in the sky! Active jets:\n");

		for (Jet jet : listOfJets) {
			jet.fly();
		}

	}

	public void findFastestJet(List<Jet> listOfJets) {
		Jet fasterJet = null;
		double lastJetsSpeed = 0;
		for (Jet jet : listOfJets) {
			if (jet.getSpeed() > lastJetsSpeed) {
				fasterJet = jet;
				lastJetsSpeed = jet.getSpeed();

			}
		}
		System.out.println("The fastest jet: ");
		System.out.println(fasterJet);
	}

	public void findLongestRangeJet(List<Jet> listOfJets) {
		Jet LongerRangeJet = null;
		int lastJetsRange = 0;
		for (Jet jet : listOfJets) {
			if (jet.getRange() > lastJetsRange) {
				LongerRangeJet = jet;
				lastJetsRange = jet.getRange();

			}
		}
		System.out.println("The longest range jet: ");
		System.out.println(LongerRangeJet);
	}

	public void loadFleetsCargo(List<Jet> listOfJets) {
		System.out.println("Initiating cargo loading:");
		for (Jet jet : listOfJets) {
			if (jet instanceof CargoJet) {
				((CargoJet) jet).loadCargo();
			}
		}
		System.out.println("All cargo jets are loaded and ready to go!");
	}

	public void combatReadyFleets(List<Jet> listOfJets) {
		System.out.println("pilots reporting to their stations!");
		for (Jet jet : listOfJets) {
			if (jet instanceof FighterJet) {
				((FighterJet) jet).fight();
			}
		}
		System.out.println("All fighter jets are ready to engage!");
	}

	public void addJetToFleet(List<Jet> listOfJets, Scanner scanner) {
		System.out.print("Please enter the model type of this passenger jet: ");
		String modelOfNewJet;
		modelOfNewJet = scanner.next();

		System.out.print("Please enter the max speed of this jet(in MPH): ");
		double speedOfNewJet = scanner.nextDouble();
		scanner.nextLine();

		System.out.print("Please enter the max range of this jet(in miles): ");
		int rangeOfNewJet = scanner.nextInt();
		scanner.nextLine();

		System.out.print("Please enter the price of this jet(in $): ");
		long priceOfNewJet = scanner.nextLong();
		scanner.nextLine();

		Jet customJet = new PassengerJet(modelOfNewJet, speedOfNewJet, rangeOfNewJet, priceOfNewJet);
		listOfJets.add(customJet);
	}
	
	public void removeJetFromFleet(List<Jet> listOfJets, Scanner scanner) {
		int i = 0;
		for (Jet jet : listOfJets) {
			System.out.println((i+1) + ". " + jet.toString());
			i++;
		}
		System.out.print("Please select the jet you would like to remove: ");
		int removeInput = (scanner.nextInt() -1);
		listOfJets.remove(removeInput);
	}

}
