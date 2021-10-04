package gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Model {
    private CnfReader cnf;
    private SatSolverRekursiv solver;
    private String knfString;
    private ArrayList<ArrayList<Integer>> knf;

    public Model() {
    }

    public Model(SatSolverRekursiv solver) {
        this.solver = solver;
    }

    public void setSolver(SatSolverRekursiv solver) {
        this.solver = solver;
    }

    public void setCnfReader(CnfReader cnf){this.cnf = cnf;}

    public String getKnfString() {
        return knfString;
    }

    public String getAnzClauses() {
        String anzClauses = String.valueOf(knf.size());
        return anzClauses;
    }

    public String getAnzLiterals() {
        ArrayList<Integer> anz = new ArrayList<>();
        for (ArrayList<Integer> clauses : knf) {
            for (int literal : clauses) {
                if (!anz.contains(literal) && !anz.contains(-literal)) {
                    anz.add(literal);
                }
            }
        }
        String anzLiterals = String.valueOf(anz.size());

        return anzLiterals;
    }

    public String getAnzLiterals(ArrayList<ArrayList<Integer>> knf) {
        ArrayList<Integer> anz = new ArrayList<>();
        for (ArrayList<Integer> clauses : knf) {
            for (int literal : clauses) {
                if (!anz.contains(literal) && !anz.contains(-literal)) {
                    anz.add(literal);
                }
            }
        }
        String anzLiterals = String.valueOf(anz.size());

        return anzLiterals;
    }

    /**
     * Baut die KNF durch Zusammenfügen der Methode aus dem CnfReader und einer gültigen .cnf-Datei.
     *
     * @param path Gültiger Pfad-String zu einer .cnf-Datei.
     * @return KNF als ArrayList
     */
    public ArrayList<ArrayList<Integer>> getKNF(String path) throws Exception {

        cnf = new CnfReader();

        BufferedReader reader = new BufferedReader(new FileReader(path));

        knf = cnf.buildKNF(reader);
        knfString = knf.toString();

        reader.close();

        return knf;
    }

    public boolean knfSatisfiable(ArrayList<ArrayList<Integer>> knf) throws InterruptedException {

        if (solver.DPLL(knf)) {
            return true;
        } else {
            return false;
        }
    }

    public String getLiteralList() {
        return solver.getLiteralList().toString();
    }

    public void resetLiteralList() {
        if (solver != null) {
            solver.getLiteralList().clear();
        }
    }

}
