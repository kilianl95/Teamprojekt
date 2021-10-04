package gui;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CnfReader {
	ArrayList<ArrayList<Integer>> knf;
	
	public CnfReader() {
		knf = new ArrayList<>();
	}

    /**
     * Überprüft, ob der String die korrekte Syntax einer Klausel erfüllt.
     * @param line String, der überprüft werden soll
     * @returns true, falls alle chars des Strings einer Zahl zwischen 0 und 9, einem '-' oder einem
     * Leerzeichen entsprechen.
     *
     */
    public boolean isClause(String line)  {
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (!((c <= 57 && c >= 48) || c == '-' || c == ' ')) {
                return false;
            }

        } return true;
    }

    /**
     * Konvertiert den eingelesenen String in eine Klausel, d.h., die einzelnen Bestandteile des
     * Strings werden überprüft, ob sie in das entsprechende Muster passen und falls ja, in eine
     * ArrayList eingefügt.
     * @param line String, der sich aus durch Leerzeichen gertrennte Zahlen zusammensetzt
     * @return Klausel - Eine ArrayList mit den Zahlen aus dem String
     */
    public ArrayList<Integer> makeLineToClause(String line) {
        ArrayList<Integer> s = new ArrayList<>();

        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(line);
        while (m.find()) {
            int i = Integer.parseInt(m.group());
            if (i != 0) {
                s.add(i);
            }
        }
        return s;
    }

    /**
     * Fügt eine Klausel in die KNF ein.
     * @param s ArrayList mit Integern
     */
    public void addToKNF(ArrayList<Integer> s) {
        if (!s.isEmpty()) {
            knf.add(s);
        }
    }

    public ArrayList<ArrayList<Integer>> getKNF() {
        return knf;
    }

    /**
     * Baut die konjungierte Normalform (KNF) aus dem .cnf-File zusammen.
     * @param reader BufferedReader - liest Zeile für Zeile
     * @return KNF - ArrayList mit Klauseln
     * @throws Exception falls ein Fehler im Ablauf auftritt
     */
    public ArrayList<ArrayList<Integer>> buildKNF(BufferedReader reader)throws Exception{
        String line = reader.readLine();

            while (line != null && !line.isEmpty()) {

                if (isClause(line)) {
                    addToKNF(makeLineToClause(line));
                }
                //read next line
                line = reader.readLine();
            }
            return knf;
    }

    ArrayList<ArrayList<Integer>> getKnf() {
        return knf;
    }
}

