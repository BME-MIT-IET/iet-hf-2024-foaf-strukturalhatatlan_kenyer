package drukmakor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;

public class Proto {
	public static void main(String[] args) {
		interpret(System.in);
	}
	
	private static boolean isExiting = false;
	
	// feldolgozza az is-ben jövő parancsokat
	private static void interpret(InputStream is) {
		Scanner sc = new Scanner(is);
		while (!isExiting && sc.hasNextLine()) {
			String[] args = sc.nextLine().split(" ");
			// üres sor esetén újat kérünk, de nem dobunk hibát
			if (args.length == 1 && args[0].equals(""))
				continue;
			// ha érvényes parancs
			if (commands.containsKey(args[0])) {
				try {
					commands.get(args[0]).accept(args);
				} catch (Exception e) {
					System.out.println("Command failed: " + e.getMessage());
				}
			// invalid parancs esetén hibát írunk
			} else {
				System.out.println("Invalid command: \"" + args[0] + "\"!");
			}
		}
		sc.close();
	}

	
	
	// muteolható kiírás
	private static boolean isMuted = false;
	private static void condPr(String s) {
		if (!isMuted)
			System.out.println(s);
	}
	
	
	
	// random
	private static double randVal = -1.0; // <0.0 esetén igazi random értéket adunk
	private static Random random = new Random();
	public static double randomNextDouble() {
		if (randVal < 0.0)
			return random.nextDouble();
		return randVal;
	}
	
	
	// az eltárolt objektumok listái
	// a nevekben az index 1-gyel el van tolva! Ami itt a 0-s index, az a me1 pl
	private static List<Mechanic> meList = new LinkedList<>();
	private static List<Saboteur> saList = new LinkedList<>();
	private static List<Pipe> piList = new LinkedList<>();
	private static List<Pump> puList = new LinkedList<>();
	private static List<Cistern> ciList = new LinkedList<>();
	private static List<Source> soList = new LinkedList<>();
	
	
	
	// új objektumot regisztrálva létrehozó függvények
	// ezeket a függvényeket a modell is használhatja, sőt, ezeket kell használnia,
	// hogy rendesen nevet is kapjanak a futás közben létrehozott objektumok
	public static Mechanic newMechanic(Element cp) {
		Mechanic me = new Mechanic(cp);
		meList.add(me);
		return me;
	}
	public static Saboteur newSaboteur(Element cp) {
		Saboteur sa = new Saboteur(cp);
		saList.add(sa);
		return sa;
	}
	public static Pipe newPipe(ActiveElement ae1, ActiveElement ae2) {
		Pipe pi = new Pipe(ae1, ae2);
		piList.add(pi);
		return pi;
	}
	public static Pump newPump() {
		Pump pu = new Pump();
		puList.add(pu);
		return pu;
	}
	public static Cistern newCistern() {
		Cistern ci = new Cistern();
		ciList.add(ci);
		return ci;
	}
	public static Source newSource() {
		Source so = new Source();
		soList.add(so);
		return so;
	}
	
	
	// objektumból csinálunk nevet
	private static String nameOf(Object obj) {
		if (meList.contains(obj))
			return "me" + (meList.indexOf(obj) + 1);
		if (saList.contains(obj))
			return "sa" + (saList.indexOf(obj) + 1);
		if (piList.contains(obj))
			return "pi" + (piList.indexOf(obj) + 1);
		if (puList.contains(obj))
			return "pu" + (puList.indexOf(obj) + 1);
		if (ciList.contains(obj))
			return "ci" + (ciList.indexOf(obj) + 1);
		if (soList.contains(obj))
			return "so" + (soList.indexOf(obj) + 1);
		throw new IllegalArgumentException("Object \"" + obj + "\" is not in the database!");
	}
	
	
	// parszolunk sima típusokat
	private static boolean parseBool(String s) {
		s = s.toLowerCase();
		if (s.equals("y") || s.equals("t") || s.equals("yes") || s.equals("true"))
			return true;
		if (s.equals("n") || s.equals("f") || s.equals("no") || s.equals("false"))
			return false;
		throw new IllegalArgumentException("Could not parse \"" + s + "\" as a boolean value!");
	}
	private static int parseInt(String s) {
		try {
			return Integer.parseInt(s);//TODO ez milyen hibaüzenetet ír ki??? kell ez egyáltalán
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Could not parse \"" + s + "\" as an integer value!");
		}
	}
	
	
	
	// névből csinálunk objektumot
	// tryParse null-t ad vissza, ha sikertelen a parszolás, de más típussá még lehet esetleg parszolni
	private static <T> T tryParse(String s, String twoLetterTypeName, List<T> listOfObjects) {
		if (s.length() < 3)
			throw new IllegalArgumentException("\"" + s + "\" is too short to be a name of an object!");
		if (!s.substring(0, 2).equals(twoLetterTypeName))
			return null;
		// innentől biztos, hogy a típus jó, szóval innentől exceptiont dobunk, mivel
		// hiba esetén biztosan rossz a formátum
		int idx = parseInt(s.substring(2)) - 1;
		if (idx < 0 || idx >= listOfObjects.size())
			throw new IllegalArgumentException("Number \"" + s.substring(2) + "\" out of range! Valid range is from 1 to " + listOfObjects.size() + " inclusive.");
		return listOfObjects.get(idx);
	}
	// ezeket is lehetne generikusan, de valószínűleg classcastexceptionöket kéne elkapni, ami nem olyan szép
	private static Tickable parseTickable(String s) {
		Tickable t = tryParse(s, "me", meList);
		if (t == null) t = tryParse(s, "sa", saList);
		if (t == null) t = tryParse(s, "pi", piList);
		if (t == null) t = tryParse(s, "pu", puList);
		if (t == null) t = tryParse(s, "ci", ciList);
		if (t == null) t = tryParse(s, "so", soList);
		if (t == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of something that can tick!");
		return t;
	}
	private static Element parseElement(String s) {
		Element e = tryParse(s, "pi", piList);
		if (e == null) e = tryParse(s, "pu", puList);
		if (e == null) e = tryParse(s, "ci", ciList);
		if (e == null) e = tryParse(s, "so", soList);
		if (e == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of an Element!");
		return e;
	}
	private static ActiveElement parseActiveElement(String s) {
		ActiveElement ae = tryParse(s, "pu", puList);
		if (ae == null) ae = tryParse(s, "ci", ciList);
		if (ae == null) ae = tryParse(s, "so", soList);
		if (ae == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of an ActiveElement!");
		return ae;
	}
	private static Character parseCharacter(String s) {
		Character c = tryParse(s, "me", meList);
		if (c == null) c = tryParse(s, "sa", saList);
		if (c == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Character!");
		return c;
	}
	private static Mechanic parseMechanic(String s) {
		Mechanic me = tryParse(s, "me", meList);
		if (me == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Mechanic!");
		return me;
	}
	private static Saboteur parseSaboteur(String s) {
		Saboteur sa = tryParse(s, "sa", saList);
		if (sa == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Saboteur!");
		return sa;
	}
	// ezekre perpillanat nincs szükség, unused kód volna, ha nem lennének kikommentezve
	/*
	private static Pipe parsePipe(String s) {
		Pipe pi = tryParse(s, "pi", piList);
		if (pi == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Pipe!");
		return pi;
	}
	private static Pump parsePump(String s) {
		Pump pu = tryParse(s, "pu", puList);
		if (pu == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Pump!");
		return pu;
	}
	private static Cistern parseCistern(String s) {
		Cistern ci = tryParse(s, "ci", ciList);
		if (ci == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Cistern!");
		return ci;
	}
	private static Source parseSource(String s) {
		Source so = tryParse(s, "so", soList);
		if (so == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Source!");
		return so;
	}
	*/
	
	
	
	
	// parancsok
	// Egy parancs egy String[]-et kapó függvény, paraméterként kapja az argumentumokat,
	// úgy, hogy a parancsot magát is megkapja, a 0. indexben.
	// kiírni a condPr függvénnyel ír, hogyha silent load történik, akkor el lehessen némítani
	// a hibákat kivétel dobásával jelzi, melyet a hívó elkap, és kiírja a felhasználónak a hibát.
	private static Map<String, Consumer<String[]>> commands;
	static {
		commands = new HashMap<>();
		commands.put("#", Proto::hashtag);
		commands.put("exit", Proto::exit);
		commands.put("add", Proto::add);
		commands.put("clear", Proto::clear);
		commands.put("load", Proto::load);
		commands.put("get", Proto::get);
		commands.put("random", Proto::random);
		commands.put("moveto", Proto::moveto);
		commands.put("alterpump", Proto::alterpump);
		commands.put("piercepipe", Proto::piercepipe);
		commands.put("fix", Proto::fix);
		commands.put("disconnectpipe", Proto::disconnectpipe);
		commands.put("connectpipe", Proto::connectpipe);
		commands.put("pickupdanglingpipe", Proto::pickupdanglingpipe);
		commands.put("pickuppump", Proto::pickuppump);
		commands.put("placepump", Proto::placepump);
		commands.put("stickypipe", Proto::stickypipe);
		commands.put("slipperypipe", Proto::slipperypipe);
		commands.put("tick", Proto::tick);
	}


	private static void testArgsLength(String[] args, int length) {
		if (args.length != length)
			throw new IllegalArgumentException(args.length > length ? "Too many arguments!" : "Too few arguments!");
	}
	
	private static void hashtag(String[] args) {
	}
	
	private static void exit(String[] args) {
		testArgsLength(args, 1);
		isExiting = true;
	}
	
	private static void add(String[] args) {
		if (args.length < 2)
			throw new IllegalArgumentException("Too few arguments!");
		String type = args[1];
		if (type.equals("me")) {
			testArgsLength(args, 3);
			condPr(nameOf(newMechanic(parseElement(args[2]))));
		} else if (type.equals("sa")) {
			testArgsLength(args, 3);
			condPr(nameOf(newSaboteur(parseElement(args[2]))));
		} else if (type.equals("pi")) {
			if (args.length != 3 && args.length != 4)
				throw new IllegalArgumentException(args.length > 3 ? "Too many arguments!" : "Too few arguments!");
			ActiveElement end2 = null;
			if (args.length == 4)
				end2 = parseActiveElement(args[3]);
			condPr(nameOf(newPipe(parseActiveElement(args[2]), end2)));
		} else if (type.equals("pu")) {
			testArgsLength(args, 2);
			condPr(nameOf(newPump()));
		} else if (type.equals("ci")) {
			testArgsLength(args, 2);
			condPr(nameOf(newCistern()));
		} else if (type.equals("so")) {
			testArgsLength(args, 2);
			condPr(nameOf(newSource()));
		} else {
			throw new IllegalArgumentException("Non-existent type \"" + type + "\"! Available ones are me, sa, pi, pu, ci and so.");
		}
	}
	
	private static void clear(String[] args) {
		testArgsLength(args, 1);
		meList.clear();
		saList.clear();
		piList.clear();
		puList.clear();
		ciList.clear();
		soList.clear();
		randVal = -1.0;
	}
	
	private static void load(String[] args) {
		if (args.length != 2 && args.length != 3)
			throw new IllegalArgumentException(args.length > 2 ? "Too many arguments!" : "Too few arguments!");
		boolean silent = false;
		if (args.length == 3)
			silent = parseBool(args[2]);
		// körbekerülni, hogyha silenten töltünk be egy olyan fájlt, amiben van egy nem silent betöltés
		boolean prevMuted = isMuted;
		if (silent)
			isMuted = true;
		try {
			interpret(new FileInputStream(args[1]));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File could not be read, " + e.getMessage() + ".");
		}
		isMuted = prevMuted;
	}
	
	private static void get(String[] args) {
		testArgsLength(args, 2);
		Object[] states = parseTickable(args[1]).get();
		String fullStr = ""; // nem túl költséghatékony, de nekünk tökéletes lesz
		boolean first = true;
		for (Object state : states) {
			String s = state.toString();
			try {
				s = nameOf(state);
			// ez az exception akkor dobódik, ha az objektum nincs az adatbázisban; erre van a fentebbi toString
			} catch (IllegalArgumentException e) {}
			fullStr += (first ? "" : " ") + s;
			first = false;
		}
		condPr(fullStr);
	}
	
	private static void random(String[] args) {
		if (args.length > 2)
			throw new IllegalArgumentException("Too many arguments!");
		if (args.length == 1) {
			randVal = -1.0;
			return;
		}	
		double val = -1.0;
		try {
			val = Double.parseDouble(args[1]);
			if (val > 1.0 || val < 0.0)
				throw new IllegalArgumentException("\"" + args[1] + "\" is out of range! Valid range is between 0.0 and 1.0.");
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Could not parse \"" + args[1] + "\" as a double value!");
		}
		randVal = val;
	}
	
	private static void moveto(String[] args) {
		testArgsLength(args, 3);
		Boolean result = parseCharacter(args[1]).moveTo(parseElement(args[2]));
		condPr(result.toString());
	}
	
	private static void alterpump(String[] args) {
		testArgsLength(args, 4);
		Boolean result = parseCharacter(args[1]).alterPump(parseInt(args[2]), parseInt(args[3]));
		condPr(result.toString());
	}
	
	private static void piercepipe(String[] args) {
		testArgsLength(args, 2);
		Boolean result = parseCharacter(args[1]).piercePipe();
		condPr(result.toString());
	}
	
	private static void fix(String[] args) {
		testArgsLength(args, 2);
		Boolean result = parseMechanic(args[1]).fix();
		condPr(result.toString());
	}
	
	private static void disconnectpipe(String[] args) {
		testArgsLength(args, 3);
		Boolean result = parseMechanic(args[1]).disconnectPipe(parseInt(args[2]));
		condPr(result.toString());
	}
	
	private static void connectpipe(String[] args) {
		testArgsLength(args, 3);
		Boolean result = parseMechanic(args[1]).connectPipe(parseInt(args[2]));
		condPr(result.toString());
	}
	
	private static void pickupdanglingpipe(String[] args) {
		testArgsLength(args, 3);
		Boolean result = parseMechanic(args[1]).pickUpDanglingPipe(parseInt(args[2]));
		condPr(result.toString());
	}
	
	private static void pickuppump(String[] args) {
		testArgsLength(args, 2);
		Boolean result = parseMechanic(args[1]).pickUpPump();
		condPr(result.toString());
	}
	
	private static void placepump(String[] args) {
		testArgsLength(args, 2);
		Boolean result = parseMechanic(args[1]).placePump();
		condPr(result.toString());
	}
	
	private static void stickypipe(String[] args) {
		testArgsLength(args, 2);
		Boolean result = parseCharacter(args[1]).stickyPipe();
		condPr(result.toString());
	}
	
	private static void slipperypipe(String[] args) {
		testArgsLength(args, 2);
		Boolean result = parseSaboteur(args[1]).slipperyPipe();
		condPr(result.toString());
	}
	
	private static void tick(String[] args) {
		testArgsLength(args, 1);
		parseTickable(args[1]).tick();
	}
}