Jeu SAE IHM

Nom du jeu : Derniers espoirs
Scenario : En 2025, une epidemie fait rage sur la Terre et zombifie ses victimes. Vous incarnez Johnny, ancien militaire, qui a pris la route pour rejoindre sa famille. Cependant, votre voiture tombe en panne a la suite d'un accident. Vous devrez alors trouver de nouvelles pieces pour pouvoir faire fonctionner votre vehicule et ainsi reprendre votre route. Pour cela, vous allez devoir fouiller les environs, aider d'autres survivants et faire du troc mais egalement affronter de terribles ennemis.

## Prerequis

- **Java 18** (ou superieur) installe sur la machine
  - Verifier avec : `java -version`
  - Telecharger : https://adoptium.net/

Aucune autre installation n'est necessaire (Maven est inclus via le wrapper).

## Lancer le jeu

### Windows
Double-cliquer sur `launcher/launch.cmd` ou executer depuis un terminal :
```
mvnw.cmd javafx:run
```

### Linux / macOS
```
chmod +x mvnw launcher/launch.sh
./launcher/launch.sh
```
Ou directement :
```
./mvnw javafx:run
```

## Compiler le projet
```
mvnw.cmd package
```
(ou `./mvnw package` sur Linux/macOS)

## Commandes du jeu

- **ZQSD** : se deplacer
- **Clic gauche** : attaquer (vise l'ennemi le plus proche)
- **E** : ouvrir l'inventaire secondaire
- **R** : interagir (parler, ramasser, pousser, tirer, creuser, lire la carte)
- Glisser-deposer dans l'inventaire pour changer d'arme/objet

## Armes et objets

- Machette : arme de base, corps-a-corps
- Pistolet : attaque a distance
- Cocktail Molotov : attaque a distance, brule arbres/buissons
- Pied de biche : ouvre les portes verrouillees
- Pelle : decouvre des objets sous la terre
- Tenue anti-radiation : explorer les zones radioactives
- Carte : trouver des tresors

## Ennemis

- **MasticatorZ** : attaque au corps-a-corps
- **LeZamikaze** : s'approche et explose
- **Bave-Zmort** : crache des projectiles
- **ZomBzilla** (boss de fin) : projectiles + invocation de zombies
