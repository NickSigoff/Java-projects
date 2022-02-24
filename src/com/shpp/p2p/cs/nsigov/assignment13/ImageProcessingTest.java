package com.shpp.p2p.cs.nsigov.assignment13;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageProcessingTest {

    @Test
    void test1() {
        String[] args = {"1.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(5, test1.findSilhouettes());
    }

    @Test
    void test2() {
        String[] args = {"2.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(36, test1.findSilhouettes());
    }

    @Test
    void test3() {
        String[] args = {"3.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(5, test1.findSilhouettes());
    }

    @Test
    void test4() {
        String[] args = {"4.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(9, test1.findSilhouettes());
    }

    @Test
    void test5() {
        String[] args = {"5.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(5, test1.findSilhouettes());
    }

    @Test
    void test6() {
        String[] args = {"6.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(8, test1.findSilhouettes());
    }

    @Test
    void test7() {
        String[] args = {"6.jpg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(6, test1.findSilhouettes());
    }

    @Test
    void test8() {
        String[] args = {"7.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(17, test1.findSilhouettes());
    }

    @Test
    void test9() {
        String[] args = {"8.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(10, test1.findSilhouettes());
    }

    @Test
    void test10() {
        String[] args = {"9.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(9, test1.findSilhouettes());
    }

    @Test
    void test11() {
        String[] args = {"10.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(10, test1.findSilhouettes());
    }

    @Test
    void test12() {
        String[] args = {"11.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(29, test1.findSilhouettes());
    }

    @Test
    void test13() {
        String[] args = {"12.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(36, test1.findSilhouettes());
    }

    @Test
    void test14() {
        String[] args = {"13.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(5, test1.findSilhouettes());
    }

    @Test
    void test15() {
        String[] args = {"14.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(77, test1.findSilhouettes());
    }

    @Test
    void test16() {
        String[] args = {"15.jpg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(20, test1.findSilhouettes());
    }

    @Test
    void test17() {
        String[] args = {"17.bmp"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(3, test1.findSilhouettes());
    }

    @Test
    void test18() {
        String[] args = {"18.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(3, test1.findSilhouettes());
    }

    @Test
    void test19() {
        String[] args = {"21.jpg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(1, test1.findSilhouettes());
    }

    @Test
    void test20() {
        String[] args = {"22.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(6, test1.findSilhouettes());
    }

    @Test
    void test21() {
        String[] args = {"233.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(4, test1.findSilhouettes());
    }

    @Test
    void test23() {
        String[] args = {"children.jpg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(9, test1.findSilhouettes());
    }
    @Test
    void test24() {
        String[] args = {"tst_65.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(6, test1.findSilhouettes());
    }
    @Test
    void test26() {
        String[] args = {"tst_47.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(1, test1.findSilhouettes());
    }
    @Test
    void test27() {
        String[] args = {"tst_02.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(2, test1.findSilhouettes());
    }
    @Test
    void test28() {
        String[] args = {"tst_40.jpg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(6, test1.findSilhouettes());
    }
    @Test
    void test29() {
        String[] args = {"tst_04.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(4, test1.findSilhouettes());
    }
    @Test
    void test30() {
        String[] args = {"tst_49.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(4, test1.findSilhouettes());
    }
    @Test
    void test31() {
        String[] args = {"tst_25.jpg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(1, test1.findSilhouettes());
    }
    @Test
    void test32() {
        String[] args = {"tst_07.jpg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(13, test1.findSilhouettes());
    }
    @Test
    void test33() {
        String[] args = {"tst_38.jpg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(1, test1.findSilhouettes());
    }
    @Test
    void test34() {
        String[] args = {"tst_09.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(6, test1.findSilhouettes());
    }
    @Test
    void test35() {
        String[] args = {"tst_10.jpg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(36, test1.findSilhouettes());
    }
    @Test
    void test36() {
        String[] args = {"tst_46.jpeg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(3, test1.findSilhouettes());
    }
    @Test
    void test37() {
        String[] args = {"tst_12.jpg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(7, test1.findSilhouettes());
    }

    @Test
    void test39() {
        String[] args = {"tst_15.jpg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(8, test1.findSilhouettes());
    }
    @Test
    void test40() {
        String[] args = {"tst_16.bmp"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(13, test1.findSilhouettes());
    }
    @Test
    void test41() {
        String[] args = {"tst_57.jpg"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(10, test1.findSilhouettes());
    }
    @Test
    void test42() {
        String[] args = {"tst_68.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(2, test1.findSilhouettes());
    }
    @Test
    void test38() {
        String[] args = {"tst_69.png"};
        ImageProcessing test1 = new ImageProcessing(args);
        assertEquals(3, test1.findSilhouettes());
    }
}