# AssertJ Swing

Utánanézve Swing GUI teszteléshez ez a keretrendszer tűnt a legkiforrottabbnak, így ezt választottuk.

JUnit 5 környezetben lehet vele swing alkalmazásokat tesztelni, viszonylag egyszerűen.

Egy komolyabb nehézséget jelentett számunkra, hogy nem nyomta meg a gombot. Az lett a megoldás, hogy az ablakot át kell méretezni. Előtte azt hittük, hogy nem baj, hogy 0 méretű az ablak (mert azt maga a tesztelő keretrendszer méretezte át akkorára), mert a gui bemenetet szimulálja akkor is, de ez nem így történik. Muszáj, hogy az ablak kellően nagy legyen, és akkor tudja a keretrendszer csak a gombot megnyomni.

A tesztek főleg a főmenüt tesztelik, mert abban vannak gombok, és label-ök, amiket könnyű az Assertj-vel tesztelni.

Az `mvn test` parancssal lefutnak a gui tesztek is így.
