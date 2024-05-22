# Manuális kódátvizsgálás

A manuális kódátvizsgálás során átnéztük a project különböző .java fájljait. Szabad szemmel főként a kommentezésben találtunk hibákat, melyek nem súlyos hibák, de a kód átláthatósága szemponjából fontosnak tartottuk javítani. 

Egyik ilyen hiba volt az inkonzisztens kommentezés. A project alapvetően egy konvencióknak megfelelő, nagyon átlátható kommentezést tartalmazott. Minden osztályhoz, illetve függvényhez társultak ilyen kommentek, amelyek leírták a főbb funkcióit, illetve, hogy mire lehet használni. A függvényeknél részletesen elemezésre is került a paraméterek szerepe, illetve a viszatérési értékek típusa is. Minden ilyen komment magyar nyelven íródott, így az alkalmazás egészében ezt a nyelvet helyezzük előtérbe. Inkonzisztenciát okozott, hogy néhol a kódba, a jobb megérthetőség szempontjából beleírt kommentek angolul voltak, ezeket igyekeztük átírni magyar nyelvre. Szerintünk ez egy fontos szempont, ugyanis mivel a fő nyelv magyar, ezért az az alap feltételezés, hogy aki a kódot olvassa tud magyar nyelven, az angol viszont nem elvárás. Ezért ilyen szempontból is fontos, hogy ne hagyjunk benne más nyelvű kommenteket.

A másik hiba, pedig a nem oda illő, helyenként "vicces" kommentek. Programozás közben saját magunk szórakoztatására, illetve a kód megérthetősége szempontjából sokan szoktunk ilyen kommenteket írni, viszont amikor már nem ebben a fázisban vagyunk és akár más fejlesztők is hozzá kell férjenek a kódhoz, célszerű ezeket kitörölni. Egyrészt ők nem feltétlen értik már a kontextust ami miatt az odakerült, másrészt komolytalannak is tűnhet. 

# Statikus kódanalízis

ide kell majd írni még