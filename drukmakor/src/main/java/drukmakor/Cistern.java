package drukmakor;

/**
 * Ide kell eljuttatni a vizet a szerelőknek. Csövek kapcsolódhatnak hozzá. Időközönként
 * termelődik itt új cső. Tudja mennyi víz jutott el bele. A szerelők itt vehetnek fel új pumpát,
 * bármennyit. Mindig kiszívja a vizet a hozzá kapcsolódó csövekből.
 * Tudja milyen csövek kapcsolódnak bele.
 *
 */
public class Cistern extends ActiveElement {
	
	/**
	 * a ciszternában levő víz szintje
	 * eltárolja, hogy mennyi víz érkezett a cisternába
	 */
	private int waterLevel = 0;
	public int getWaterLevel() { return waterLevel; }
	/**
	 * keletkezik egy új cső, ami lelóg róla
	 */
	@Override public void randomEvent() {
		for (Pipe p : pipes) { // van-e még szabad hely a ciszternán, ha nincs akkor nem hozunk létre új pipeot (különben exceptiont dob)
			if (p == null) {
				Proto.newPipe(this, null);
				return;
			}
		}
	}
	/**
	 * végigmegy az összes rákötött csövön, és kiszívja
	 * belőlük a vizet, növelve ezzel waterLevelt, és pontot szerezve a szerelőknek
	 */
	@Override public void pullWater() {
		for (Pipe p : pipes)
			if (p != null && p.drainWater()) {
				PointCounter.get().addMechanicPoint();
				++waterLevel;
			}
	}
	
	/**
	 *  a dangling pipeokba belétol 1-1 vizet, és
	 *  ezzel analóg módon levon egy-egy pontot a szerelőktől (és a pipe-ok adnak egy-egy
	 *  pontot a szabotőröknek)
	 */
	@Override public void pushWater() {
		for (Pipe p : pipes)
			if (p != null && waterLevel > 0)
				if (p.wasteWater()) {
					PointCounter.get().subtractMechanicPoint();
					--waterLevel;
				}
	}
	/**
	 * egy új pumpát ad vissza mindig: bármikor lehet
	 * új pumpát felvenni a ciszternánál
	 */
	@Override public Pump pickUpPump() {
		return Proto.newPump(new Coords(0,0));
	}
	
	

	/**
	 * A get parancs által kiírandó értékeket adja vissza a megfelelő sorrendben, így megvalósítva az állapotok lekérdezését.
	 */
	@Override
	public Object[] get() {
		int noValidPipes = MAX_CONNECTIONS; // érvényes csövek száma
		while (noValidPipes > 0 && pipes[noValidPipes - 1] == null)
			noValidPipes--;
		Object[] ret = new Object[noValidPipes + 1]; // <csövek> <vízszint (egész szám)>
		for (int i = 0; i < noValidPipes; ++i)
			ret[i] = pipes[i];
		ret[noValidPipes] = waterLevel;
		return ret;
	}
	

	/**
	 * a modell objektumhoz tartozó view
	 */
	private CisternView view;
	/**
	 * visszaadja a modell objektumhoz tartozó viewt
	 */
	@Override public CisternView getView() { return view; }
	/**
	 * setter
	 */
	public void setView(CisternView v) { view = v; }
	

}
