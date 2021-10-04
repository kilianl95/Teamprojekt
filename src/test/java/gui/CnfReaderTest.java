package gui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

public class CnfReaderTest {

    CnfReader cnfReader;


    @BeforeEach
    void setUp(){
        cnfReader = new CnfReader();
    }

    @Test
    void isClause(){
        String line1 = "2 -30 99 0";
        String line2 = "-11 66 203 0";
        String line3 = "-b 22 0";
        String line4 = "22 // cb 0";

        Assertions.assertTrue(cnfReader.isClause(line1));
        Assertions.assertTrue(cnfReader.isClause(line2));
        Assertions.assertFalse(cnfReader.isClause(line3));
        Assertions.assertFalse(cnfReader.isClause(line4));
    }

    @Test
    void makeLineToClause(){
        String lineValid = "-67 8 99 102 0";

        ArrayList<Integer> result = cnfReader.makeLineToClause(lineValid);

        Assertions.assertEquals(4, result.size());
        Assertions.assertEquals(-67, result.get(0));
        Assertions.assertEquals(8, result.get(1));
        Assertions.assertEquals(99, result.get(2));
        Assertions.assertEquals(102, result.get(3));
    }

    @Test
    void addToKNF_empty(){
        //GIVEN
        ArrayList<Integer> list = new ArrayList<>();

        //WHEN
        cnfReader.addToKNF(list);

        //THEN
        Assertions.assertTrue(cnfReader.getKNF().isEmpty());
    }

    @Test
    void addToKNF_notEmpty(){
        //GIVEN
        ArrayList<Integer> list = new ArrayList<>();
        list.add(5);

        //WHEN
        cnfReader.addToKNF(list);

        //THEN
        Assertions.assertFalse(cnfReader.getKNF().isEmpty());
        Assertions.assertEquals(list, cnfReader.getKNF().get(0));
    }


    @Test
    void buildKNF_emptyReader() throws Exception {
        //GIVEN
        String line = "";
        BufferedReader reader = new BufferedReader(
                new StringReader(line)
        );
        //WHEN
        ArrayList<ArrayList<Integer>> list = cnfReader.buildKNF(reader);

        //THEN
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    void buildKNF_filledReader() throws Exception {
        //GIVEN
        String line = "-67 8 99 102 0";
        BufferedReader reader = new BufferedReader(
                new StringReader(line)
        );
        //WHEN
        ArrayList<ArrayList<Integer>> list = cnfReader.buildKNF(reader);

        //THEN
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(-67, list.get(0).get(0));
        Assertions.assertEquals(102, list.get(0).get(3));
    }


}
