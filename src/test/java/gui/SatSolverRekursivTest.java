package gui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class SatSolverRekursivTest {

    SatSolverRekursiv solver;

    @BeforeEach
    void setUp(){solver = new SatSolverRekursiv();}

    @Test
    void DPLL() throws InterruptedException {
        //GIVEN
        ArrayList<ArrayList<Integer>> knf1 = makeKNF1();
        ArrayList<ArrayList<Integer>> knf2 = makeKNF2();

        Boolean result1, result2;

        //WHEN
        result1  = solver.DPLL(knf1);
        result2 = solver.DPLL(knf2);

        //THEN
        Assertions.assertTrue(result1);
        Assertions.assertFalse(result2);
    }

    //KNF ist erfüllbar
    private ArrayList<ArrayList<Integer>> makeKNF1() {
        ArrayList<Integer> clause1 = new ArrayList<>();
        clause1.add(5);
        clause1.add(-7);

        ArrayList<Integer> clause2 = new ArrayList<>();
        clause2.add(7);
        clause2.add(-10);
        clause2.add(1);

        ArrayList<ArrayList<Integer>> knf1 = new ArrayList<>();
        knf1.add(clause1);
        knf1.add(clause2);
        return knf1;
    }

    //KNF ist nicht erfüllbar
    private ArrayList<ArrayList<Integer>> makeKNF2() {
        ArrayList<Integer> clause3 = new ArrayList<>();
        clause3.add(1);
        clause3.add(2);

        ArrayList<Integer> clause4 = new ArrayList<>();
        clause4.add(1);
        clause4.add(3);

        ArrayList<Integer> clause5 = new ArrayList<>();
        clause5.add(-3);
        clause5.add(2);

        ArrayList<Integer> clause6 = new ArrayList<>();
        clause6.add(-2);
        clause6.add(-3);

        ArrayList<Integer> clause7 = new ArrayList<>();
        clause7.add(-1);
        clause7.add(3);

        ArrayList<ArrayList<Integer>> knf2 = new ArrayList<>();
        knf2.add(clause3);
        knf2.add(clause4);
        knf2.add(clause5);
        knf2.add(clause6);
        knf2.add(clause7);
        return knf2;
    }

    @Test
    void simplify() throws InterruptedException {
        //GIVEN
        ArrayList<ArrayList<Integer>> knf = makeKNF1();

        int literal1 = 5;
        int literal2 = 10;

        ArrayList<ArrayList<Integer>> result1;
        ArrayList<ArrayList<Integer>> result2;

        //WHEN
        result1 = solver.simplify(knf,literal1);
        result2 = solver.simplify(knf,literal2);

        //THEN
        Assertions.assertEquals(1, result1.size());
        Assertions.assertEquals(-10, result1.get(0).get(1));
        Assertions.assertEquals(2, result2.size());
        Assertions.assertEquals(1, result2.get(1).get(1));
    }
}
