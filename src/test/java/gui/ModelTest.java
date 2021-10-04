package gui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class ModelTest {

    Model model = Mockito.spy(Model.class);

    SatSolverRekursiv solver = Mockito.mock(SatSolverRekursiv.class);
    CnfReader cnf = Mockito.mock(CnfReader.class);

    @Test
    void getAnzLiterals() {
        //GIVEN
        ArrayList<ArrayList<Integer>> knf1 = makeKNF1();
        ArrayList<ArrayList<Integer>> knf2 = makeKNF2();


        //WHEN
        String anz1 = model.getAnzLiterals(knf1);
        String anz2 = model.getAnzLiterals(knf2);

        //THEN
        Assertions.assertEquals("4", anz1);
        Assertions.assertEquals("3", anz2);
    }

    @Test
    void getKNF() throws Exception {
        //GIVEN
        String test = "src/test/resources/TestFiles/Test_CNF.txt";
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        //WHEN
        result = model.getKNF(test);

        //THEN
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(50, result.get(0).get(0));
        Assertions.assertEquals(44, result.get(1).get(2));
    }

    @Test
    void knfSatisfiable() throws InterruptedException {
        model.setSolver(solver);
        //GIVEN
        ArrayList<ArrayList<Integer>> knf1 = makeKNF1();
        ArrayList<ArrayList<Integer>> knf2 = makeKNF2();

        Mockito.when(solver.DPLL(knf1)).thenReturn(true);
        Mockito.when(solver.DPLL(knf2)).thenReturn(false);

        Boolean result1, result2;

        //WHEN
        result1 = model.knfSatisfiable(knf1);
        result2 = model.knfSatisfiable(knf2);

        //THEN
        Assertions.assertTrue(result1);
        Assertions.assertFalse(result2);
    }

    @Test
    public void resetLiteralList() {
        model.setSolver(solver);
        //GIVEN
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        Mockito.when(solver.getLiteralList()).thenReturn(list);

        //WHEN
        model.resetLiteralList();

        //THEN
        Assertions.assertTrue(list.isEmpty());
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
}
