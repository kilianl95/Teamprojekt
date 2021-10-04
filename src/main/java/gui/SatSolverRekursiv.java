package gui;

import java.util.ArrayList;

public class SatSolverRekursiv {
    /**
     * Zaehlt die Rekursionsschritte.
     */
    public static int rek_counter = 0;
    /**
     * Dient zur Drosselung im Erklaermodus - wird true, falls der
     * RadioButton 'Erklaermodus' aktiviert wird.
     */
    public static boolean delayMode = false;
    /**
     * Verzoegerungszeit fuer den Erklaermodus - laesst sich ueber den
     * Slider festlegen.
     */
    public static long delayTime = 0;
    /**
     * Enthaelt die einzelnen Positionen fuer den Pseudo-Code (1-12).
     */
    public static int position = 0;
    /**
     * Notwendig fuer Position 4 und 7, da es hier zwei unterschiedliche Ausgaenge
     * gibt und Markierung bei der Visualisierung sonst nicht richtig gesetzt wird.
     */
    public static int positionEnd = 0;
    /**
     * Gewaehltes Literal durch den Algorithmus. Hier: Es wird immer der naechstmoegliche
     * Wert aus der ArrayList genommen. Static, weil der Controller fuer die TextArea darauf
     * zugreifen muss.
     */
    public static int literal;

    private ArrayList<Integer> literalList = new ArrayList<>();


    public boolean DPLL(ArrayList<ArrayList<Integer>> F) throws InterruptedException {

        position = 1;

        for (int i = 0; i < F.size(); i++) {

            if (F.get(i).size() == 1) {
                int L = F.get(i).get(0);
                F = simplify(F, L);
                i = -1;
            }
        }

        if (delayMode) {
            Thread.sleep(delayTime);
        }
        position = 2;
        if (delayMode) {
            Thread.sleep(delayTime);
        }

        if (F.isEmpty()) {

            position = 3;
            if (delayMode) {
                Thread.sleep(delayTime);
            }

            return true;
        }

        position = 4;
        if (delayMode) {
            Thread.sleep(delayTime);
        }

        for (ArrayList<Integer> clause : F) {
          if (clause.isEmpty()) {

              position = 5;
              if (delayMode) {
                  Thread.sleep(delayTime);
              }
              positionEnd = 4;

              return false;
          }
        }

        positionEnd = 4;
        position = 6;
        if (delayMode) {
            Thread.sleep(delayTime);
        }

        int L = F.get(0).get(0);
        literal = L;

        literalList.add(L);

        position = 7;
        if (delayMode) {
            Thread.sleep(delayTime);
        }
        positionEnd = 4;

        if (DPLL(simplify(F, L))) {

            rek_counter++;
            positionEnd = 7;
            position = 8;
            if (delayMode) {
                Thread.sleep(delayTime);
            }

            return true;

        } else {

            rek_counter++;
            positionEnd = 7;
            position = 9;
            if (delayMode) {
                Thread.sleep(delayTime);
            }

            L = -L;
            literalList.add(L);
            literal = L;

            return DPLL(simplify(F, L));
        }
    }

    public ArrayList<ArrayList<Integer>> simplify(ArrayList<ArrayList<Integer>> F, int L) throws InterruptedException {

        ArrayList<ArrayList<Integer>> FStrich = new ArrayList<>();

        position = 10;

        for (ArrayList<Integer> clause : F) {
          if (!clause.contains(L)) {
            FStrich.add(clause);
          }
        }

        ArrayList<ArrayList<Integer>> FStrichStrich = new ArrayList<>();

        if (delayMode) {
            Thread.sleep(delayTime);
        }
        position = 11;
        if (delayMode) {
            Thread.sleep(delayTime);
        }

        for (ArrayList<Integer> clause : FStrich) {
            ArrayList<Integer> clauseStrich = new ArrayList<>();
            for (int literal : clause) {
                if (literal != -L) {
                  clauseStrich.add(literal);
                }
            }
            FStrichStrich.add(clauseStrich);
        }

        position = 12;
        if (delayMode) {
            Thread.sleep(delayTime);
        }

        return FStrichStrich;
    }


    public ArrayList<Integer> getLiteralList() {
        return literalList;
    }

}
