# JavaExercise02
Propositional Logic (part 1)

Ressource zu Prioritäten der Operatoren: http://bmanolov.free.fr/javaoperators.php

Aufgaben:
---------
* Wahrheitstabellen für untenstehende Beispiele mit Java erstellen und ausgeben:
* Operatoren: AND (&&), OR(||), XOR(^), NOT(!)

Beispiele:
----------
(A && B) || C

(!(A && B)) || C

!(A \|\| B || C)

!((A && B) || (B && C))

!(A ^ B) 

Denksport: (Extra Aufgabe)
----------
In der Aussagenlogik ist die Implikation wie folgt definiert:
Der Wahrheitswert einer Implikation ist FALSCH wenn und NUR wenn das erste Glied WAHR und seine Konsequenz FALSCH ist;
Also aus WAHR darf nie FALSCH geschlossen werden.
Alle anderen Wahrheitswerte sind WAHR.

Wahrheitstabelle zur Implikation:

|p |	q |	(p ⇒ q)
|--|----|----------
|1 |	1 |  	 1
|1 |	0 |  	 0
|0 |	1 |  	 1
|0 |	0 |    1

Wie erreicht man den Effekt einer Implikation mit den bekannten Elementen?
