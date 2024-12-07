package forwardchain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ForwardChain {

    public static Rule chooseRule(ArrayList<Rule> rules) {
        // For simplicity, we choose the first rule in the list
        return rules.get(0);
    }

    public static boolean forwardChain(Rule[] ruleBase, ArrayList<String> facts, ArrayList<String> goals) {
        boolean res = true;
        List<String> proofSteps = new ArrayList<>(); // Track proof steps

        // Loop until all goals are proven or one fails
        for (String goal : goals) {
            // If the goal is already in the facts, skip it
            if (facts.contains(goal)) {
                continue;
            }

            // Initialize untriggered rules and rules to consider
            ArrayList<Rule> untriggeredRules = new ArrayList<>(Arrays.asList(ruleBase));
            ArrayList<Rule> rulesToConsider = new ArrayList<>(Arrays.asList(ruleBase));

            // Loop until no rules left to consider or goal is proven
            while (!rulesToConsider.isEmpty() && !facts.contains(goal)) {
                Rule rule = chooseRule(rulesToConsider);
                rulesToConsider.remove(rule);

                // If all premises of the rule are in the facts
                if (facts.containsAll(rule.P)) {
                    // Add conclusions of the rule to the facts
                    facts.addAll(rule.C);

                    // Record the rule applied for the proof step
                    proofSteps.add(rule.P + " => " + rule.C);

                    // Remove the rule from untriggered rules
                    untriggeredRules.remove(rule);

                    // Update rules to consider
                    rulesToConsider = new ArrayList<>(untriggeredRules);
                }
            }
        }

        // Check if all goals are in the facts
        for (String goal : goals) {
            if (!facts.contains(goal)) {
                res = false;
                break;
            }
        }

        // Print proof steps
        System.out.println("Proof steps:");
        for (String step : proofSteps) {
            System.out.println("- " + step);
        }

        return res;
    }



    public static void main(String[] args) {
        // Define the rule base
        Rule[] ruleBase = new Rule[9];
        ArrayList<String> P, C;

        // Define rules and add them to the rule base
        P = new ArrayList<>(Arrays.asList("A", "B"));
        C = new ArrayList<>(Arrays.asList("F"));
        ruleBase[0] = new Rule(0, P, C);

        P = new ArrayList<>(Arrays.asList("F", "H"));
        C = new ArrayList<>(Arrays.asList("I"));
        ruleBase[1] = new Rule(1, P, C);

        P = new ArrayList<>(Arrays.asList("D", "H", "G"));
        C = new ArrayList<>(Arrays.asList("A"));
        ruleBase[2] = new Rule(2, P, C);

        P = new ArrayList<>(Arrays.asList("O", "G"));
        C = new ArrayList<>(Arrays.asList("H"));
        ruleBase[3] = new Rule(3, P, C);

        P = new ArrayList<>(Arrays.asList("E", "H"));
        C = new ArrayList<>(Arrays.asList("B"));
        ruleBase[4] = new Rule(4, P, C);

        P = new ArrayList<>(Arrays.asList("G", "A"));
        C = new ArrayList<>(Arrays.asList("B"));
        ruleBase[5] = new Rule(5, P, C);

        P = new ArrayList<>(Arrays.asList("G", "H"));
        C = new ArrayList<>(Arrays.asList("P"));
        ruleBase[6] = new Rule(6, P, C);

        P = new ArrayList<>(Arrays.asList("G", "H"));
        C = new ArrayList<>(Arrays.asList("O"));
        ruleBase[7] = new Rule(7, P, C);

        P = new ArrayList<>(Arrays.asList("D", "O", "G"));
        C = new ArrayList<>(Arrays.asList("J"));
        ruleBase[8] = new Rule(8, P, C);

        // Create a scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Display a welcome message
        System.out.println("Welcome to the Expert System Interface");

        // Loop to allow users to input queries
        while (true) {
            // Prompt the user to input a goal
            System.out.print("Enter a fact to prove (type 'exit' to quit): ");
            String input = scanner.nextLine().trim();

            // Check if the user wants to exit
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting Expert System Interface. Goodbye!");
                break;
            }

            // Define the initial facts base (assuming some initial facts)
            ArrayList<String> facts = new ArrayList<>(Arrays.asList("D", "O", "G"));

            // Call the forward chaining method to prove the goal
            boolean result = forwardChain(ruleBase, facts, new ArrayList<>(Arrays.asList(input)));

            // Display the result
            if (result) {
                System.out.println("The fact \"" + input + "\" can be proven.");
            } else {
                System.out.println("The fact \"" + input + "\" cannot be proven.");
            }
        }

        // Close the scanner
        scanner.close();
    }
}
