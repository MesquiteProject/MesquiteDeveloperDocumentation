package mesquite.examples.ExNumberForTreeUI;import mesquite.lib.*;import mesquite.lib.duties.*;/* This module calculates a statistic of probably little use, what it calls the Excess Polytomies Index. A polytomy with 5 descendants costs 3 (an excess of 3 over the dichotomous case); the cost of all polytomies is added and returned. (1.) Calculates the simple Excess Polytomies index. (2.) Added is a silly menu item and a handler for it.  (3.) Added is a check menu item controlling whether the statistic is calculated as the square root of excess at each node. */public class ExNumberForTreeUI extends NumberForTree {	//(3.) The boolean wrapper for the flag as to whether the calculation uses square root	MesquiteBoolean useSquareRoot;	/*.................................................................................................................*/	/** (1.) Required for all modules.  Called when module started.  Returns true if started successfully.*/	public boolean startJob(String arguments, Object condition, boolean hiredByName) {		//(3.) the boolean wrapper to store whether to use square roots		useSquareRoot = new MesquiteBoolean(false);		//(2.) a silly menu item		addMenuItem("Hello", makeCommand("sayHello", this)); //(2.) requesting a menu item when the module is running, with command to be given when menu item chosen		//(3.) the menu item to toggle whether to use square roots		addCheckMenuItem(null, "Use Square Roots For Excess", makeCommand("toggleSquareRoot", this), useSquareRoot);		return true;	}	/*.................................................................................................................*/	/** (2.) Handling commands */	public Object doCommand(String commandName, String arguments, CommandChecker checker){		//(2.) handling the command sayHello		if (checker.compare(this.getClass(), "Greets the user", null, commandName, "sayHello")) { 			alert("Hello!  The menu item belongs to " + getName() + ", and example module to show how to program Mesquite.");			return null;		}		//(3.) handling the command toggleSquareRoot		else if (checker.compare(this.getClass(), "Sets whether or not square roots are used in Excess Polytomies calculations", "[on = use square roots; off]", commandName, "toggleSquareRoot")) {			useSquareRoot.toggleValue(parser.getFirstToken(arguments)); //toggles yes/know whether 			parametersChanged(); //notifies employers that parameters have been changed			return null;		}		else			return  super.doCommand(commandName, arguments, checker);	}	/*.................................................................................................................*/	/** (1.) Calculates Excess Polytomies Index*/	private double excessPolytomies(Tree tree, int node, CategoricalDistribution character) {		double excess = 0;		if (tree.nodeIsInternal(node)) {  //recurse up the tree			for (int daughter = tree.firstDaughterOfNode(node); tree.nodeExists(daughter); daughter = tree.nextSisterOfNode(daughter))				excess += excessPolytomies(tree, daughter, character);			if (tree.nodeIsPolytomous(node)) { 				int numExcess = tree.numberOfDaughtersOfNode(node) -2;				//(3.) Calculation varies depending on the flag useSquareRoot				if (useSquareRoot.getValue()){ //yes, use square roots					if (numExcess >=0) //just in case!						excess += Math.sqrt(numExcess); //how many beyond 2?				}				else					excess += numExcess; //how many beyond 2?			}		}		return excess;	}	/*.................................................................................................................*/	/** (1.) Required by NumberForTree.  Called in case this module needs to initialize anything; this module doesn't */	public void initialize(Tree tree){	}	/*.................................................................................................................*/	/** (1.) This is the focal method of a NumberForTree.  A tree is passed; the tree should not be modified!!!!	The number is returned in result; a description of the result can be returned in resultString.	This particular module does a simple quantification of how resolved is the tree.*/	public void calculateNumber(Tree tree, MesquiteNumber result, MesquiteString resultString) {		if (result==null || tree == null)			return;		clearResultAndLastResult(result);		double excess = excessPolytomies(tree, tree.getRoot(), character);		result.setValue(excess);		if (resultString!=null) {			if (useSquareRoot.getValue()) //(3.)  indicate in string that square roots are used				resultString.setValue("Excess Polytomies Index (sqrt): "+ result.toString());			else				resultString.setValue("Excess Polytomies Index: "+ result.toString());		}		saveLastResult(result);		saveLastResultString(resultString);	}	/*.................................................................................................................*/	/** (1.) Explains what the module does.*/	public String getExplanation() {		return "Calculates a resolution index for a tree.  This is an example module to show how to program for Mesquite.";	}	/*.................................................................................................................*/	/** (1.) Name of module*/	public String getName() {		return "Excess Polytomies Index (example module)";	}}