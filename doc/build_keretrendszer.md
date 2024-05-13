# Maven beüzemelése

Megpróbáltam a gradle-t és a maven-t is, végül a maven mellett döntöttem, azt sikerült beüzemelni. Nem tetszik, hogy nagyon sok mappán belülre kell tenni a forrásfájlokat, az eredeti mappastruktúra sokkal jobb volt szerintem. Az asset-eket illene vagy becsomagolni a jar fájlba (nem tudom, azt mennyire szokták), vagy legalábbis a maven-t megkérni, hogy csomagolja nekünk a jar fájl mellé. Ez a házi feladat keretein kívül esik.

Meg kellett adni a pom fájlban, a jar plugin-nál, hogy mi a main fájl, hogy a manifest-be beleírja, hogy a drukmakor.Grafikus osztály tartalmazza a program belépési pontját.

## Fordítás és futtatás

```sh
cd drukmakor
mvn package
cd ../assets
java -jar ../drukmakor/target/drukmakor-1.0-SNAPSHOT.jar
```

Őszintén, így nem jobb, mint az eredeti (javac + kézzel írt manifest fájl), viszont lehetőséget biztosít a függőségek automatikus feloldására, tehát például a GUI teszthez szükséges könyvtár automatikus letöltésére és felkonfigurálására.
