package data.method.signature;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import data.javaCode.akka.parser.JavaFileParseActor;

/**
 * Merge data generated by akka parser.
 *  
 * Create the search space of signature-similar methods.
 */
public class SignatureSearchSpace {

	/**
	 * Arg1: Path of output data.
	 * Arg2: Number of akka workers.
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		String outputPath = args[0];//"../data/existingMethods/".
		int numberOfWorkers = Integer.parseInt(args[1]);
		
		ActorSystem system = null;
		ActorRef parsingActor = null;
		
		try {
			system = ActorSystem.create("Parsing-Method-System");
			parsingActor = system.actorOf(JavaFileParseActor.props(numberOfWorkers, outputPath), "parse-method-actor");
			parsingActor.tell("MERGE_DATA", ActorRef.noSender());
		} catch (Exception e) {
			system.shutdown();
			e.printStackTrace();
		}
	}

}