# Tesztesetek létrehozása

A tesztek mappában található "tesztek_tervei.pdf" fájlban található először a tesztek terve egy átfogó táblázatban, majd a tesztek részletes leírása, a pontos kivitelezendő utasításokkal és a modell alapján várt kimenetekkel.

A tesztek tervezésénél igyekeztünk az alkalmazás funkcióit az összes eshetőségre kipróbálni például a szerelő a törött és nem törött és nem törött pumpát is megpróbálja megjavítani. Ezzel igyekeztünk elkerülni a pontatlanságból észre nem vett hibákat, melyek talán kikerülnék a tesztelés során a figyelmünket, hiszen triviálisan elronthatatlan eseteknek tűnnek első ránézésre.

Nem egyszerű feladat egy alkalmazás átfogó, minden esetre kitérő tesztelése.

# Manuális tesztelés elvégzése

A tesztelésről szóló pdf alapján szúró-próba szerűen elvégeztem a teszteseteket a fútó alkalmazásban. A tesztelés command line-on keresztül történt, ahova egyesével beírtam a parancsokat, majd ellenőriztem a kimeneteket az elvárt kimenetekkel. Minden ilyen elvégzett tesztet lefotóztam, mely képeket egy erre külön létrehozott mappában helyeztem el.

Az általam elvégzett tesztek nagy része ugyanazt a kimenetet adta, mint ami az elvárt volt. A pdf-ben 21-es sorszámmal jelölt teszteset viszont végeredményül a legutolsó parancs után rossz eredményt mutatott.

A tanulság amit levontam a manuális tesztelésből az a fontossága, ugyanis a jelenlegi példámból is látszik, hogy ugyan nem ellenőriztem mindet és a választásaim teljesen véletlenszerűek voltak, mégis sikerült találnom olyat ami nem helyesen működött. A rendszer éles működése közben is belefutna ugyanekkora valószínűséggel egy ilyen hibába a felhasználó, ami nagyon kellemetlen lenne. A manuális tesztelés által viszont a felhasználó bőrébe bújva tudjuk átvizsgálni a rendszert, hogy a lehető legtöbb hibát elimináljuk.