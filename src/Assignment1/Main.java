package Assignment1;

public class Main {

    public static void main(String[] args) {

        Price p1, p2, p3, p4, p5;

        try {

            System.out.println("\nTest - Assignment1.PriceFactory.makePrice(String):");
            System.out.println("\t1) Should say $98,765.00: " + PriceFactory.makePrice("98765"));
            System.out.println("\t2) Should say $0.00: " + PriceFactory.makePrice("000"));
            System.out.println("\t3) Should say $0.22: " + PriceFactory.makePrice(".22"));
            System.out.println("\t4) Should say $14.75: " + PriceFactory.makePrice("14.75"));
            System.out.println("\t5) Should say $25.79: " + PriceFactory.makePrice("25.79"));
            System.out.println("\t6) Should say $1.76: " + PriceFactory.makePrice("001.76"));
            System.out.println("\t7) Should say $4,567.89: " + PriceFactory.makePrice("4,567.89"));
            System.out.println("\t8) Should say $-12.85: " + PriceFactory.makePrice("$-12.85"));
            System.out.println("\t9) Should say $-12.00: " + PriceFactory.makePrice("$-12"));
            System.out.println("\t10) Should say $-0.89: " + PriceFactory.makePrice("$-.89"));
            System.out.println("\t11) Should say $1.20: " + PriceFactory.makePrice("1.20"));
            System.out.println("\t12) Should say $12.34 " + PriceFactory.makePrice("$12.34"));
            System.out.println("\t13) Should say $1.00: " + PriceFactory.makePrice("1"));
            System.out.println("\t14) Should say $1.00: " + PriceFactory.makePrice("$1."));

            try {
                System.out.println("\t15) Should throw an Assignment1.InvalidPriceException: ");
                PriceFactory.makePrice("12.3456");
            } catch (Exception ipo) {
                System.out.println("\t\t" + ipo.getClass().getSimpleName() + ": " + ipo.getMessage());
            }
            try {
                System.out.println("\t16) Should throw an Assignment1.InvalidPriceException: ");
                PriceFactory.makePrice("12.3");
            } catch (Exception ipo) {
                System.out.println("\t\t" + ipo.getClass().getSimpleName() + ": " + ipo.getMessage());
            }


        } catch (Exception ipo) {
            System.out.println(ipo.getMessage());
            return;
        }

        // Prices for testing
        p1 = PriceFactory.makePrice(100000);
        p2 = PriceFactory.makePrice(1299);
        p3 = PriceFactory.makePrice(85);
        p4 = PriceFactory.makePrice(-7550);
        p5 = PriceFactory.makePrice(100000);


        System.out.println("\nTest - isNegative:");
        System.out.println("\t1) Should say false: " + p1.isNegative());
        System.out.println("\t2) Should say false: " + p2.isNegative());
        System.out.println("\t3) Should say false: " + p3.isNegative());
        System.out.println("\t4) Should say true: " + p4.isNegative());

        System.out.println("\nTest - add");
        try {
            System.out.println("\t1) Result: Should say $1,012.99: " + p1.add(p2));
            System.out.println("\t2) Result: Should say $13.84: " + p2.add(p3));
            System.out.println("\t3) Result: Should say $-74.65: " + p3.add(p4));
            System.out.println("\t4) Should generate an exception with a message: ");
            p4.add(null);
        } catch (Exception ipo) {
            System.out.println(ipo.getMessage());
        }

        System.out.println("\nTest - subtract");
        try {
            System.out.println("\t1) Result: Should say $987.01: " + p1.subtract(p2));
            System.out.println("\t2) Result: Should say $12.14: " + p2.subtract(p3));
            System.out.println("\t3) Result: Should say $76.35: " + p3.subtract(p4));
            System.out.println("\t4) Should generate an exception with a message:");
            p4.subtract(null);
        } catch (Exception ipo) {
            System.out.println(ipo.getMessage());
        }

        System.out.println("\nTest - multiply");
        System.out.println("\t1) Should say $8,000.00: " + p1.multiply(8));
        System.out.println("\t2) Should say $38.97: " + p2.multiply(3));
        System.out.println("\t3) Should say $18.70: " + p3.multiply(22));
        System.out.println("\t4) Should say $-755.00: " + p4.multiply(10));


        System.out.println("\nTest - greaterOrEqual");
        try {
            System.out.println("\t1) Should say true: " + p1.greaterOrEqual(p1));
            System.out.println("\t2) Should say true: " + p1.greaterOrEqual(p2));
            System.out.println("\t3) Should say false: " + p4.greaterOrEqual(p2));
            System.out.println("\t4) Should say true: " + p3.greaterOrEqual(p4));
            System.out.println("\t5) Should generate an exception with a message: ");
            p4.greaterOrEqual(null);
        } catch (Exception ipo) {
            System.out.println(ipo.getMessage());
        }

        System.out.println("\nTest - lessOrEqual");
        try {
            System.out.println("\t1) Should say true: " + p1.lessOrEqual(p1));
            System.out.println("\t2) Should say false: " + p1.lessOrEqual(p2));
            System.out.println("\t3) Should say true: " + p2.lessOrEqual(p3));
            System.out.println("\t4) Should say false: " + p3.lessOrEqual(p4));
            System.out.println("\t5) Should generate an exception with a message: ");
            p4.lessOrEqual(null);
        } catch (Exception ipo) {
            System.out.println(ipo.getMessage());
        }

        System.out.println("\nTest - greaterThan");
        try {
            System.out.println("\t1) Should say false: " + p1.greaterThan(p1));
            System.out.println("\t2) Should say true: " + p1.greaterThan(p2));
            System.out.println("\t3) Should say false: " + p3.greaterThan(p2));
            System.out.println("\t4) Should say true: " + p3.greaterThan(p4));
            System.out.println("\t5) Should generate an exception with a message: ");
            p4.greaterThan(null);
        } catch (Exception ipo) {
            System.out.println(ipo.getMessage());
        }

        System.out.println("\nTest - lessThan");
        try {
            System.out.println("\t1) Should say false: " + p1.lessThan(p1));
            System.out.println("\t2) Should say false: " + p1.lessThan(p2));
            System.out.println("\t3) Should say true: " + p3.lessThan(p2));
            System.out.println("\t4) Should say false: " + p3.lessThan(p4));
            System.out.println("\t5) Should generate an exception with a message: ");
            p4.lessThan(null);
        } catch (Exception ipo) {
            System.out.println(ipo.getMessage());
        }

        System.out.println("\nTest - equals");
        System.out.println("\t1) Should say true: " + p1.equals(p1));
        System.out.println("\t2) Should say true: " + p1.equals(p5));
        System.out.println("\t3) Should say true: " + p5.equals(p1));
        System.out.println("\t4) Should say false: " + p1.equals(p2));
        System.out.println("\t5) Should say false: " + p2.equals(p3));
        System.out.println("\t6) Should say false: " + p3.equals(p4));
        System.out.println("\t7) Should say false: " + p4.equals(null));

        System.out.println("\nTest - compareTo");
        System.out.println("\t1) Should generate 0: " + p1.compareTo(p5));
        System.out.println("\t2) Should generate any positive number: " + p1.compareTo(p2));
        System.out.println("\t3) Should generate any negative number: " + p3.compareTo(p2));
        System.out.println("\t4) Should generate any positive number: " + p3.compareTo(p4));
        System.out.println("\t5) Should generate any negative number: " + p4.compareTo(null));

        System.out.println("\nTest - toString");
        System.out.println("\t1) Should generate $1,000.00: " + p1);
        System.out.println("\t2) Should generate $12.99: " + p2);
        System.out.println("\t3) Should generate $0.85: " + p3);
        System.out.println("\t4) Should generate $-75.50: " + p4);

        System.out.println("\nTest - hashCode");
        System.out.println("\t1) Should generate true: " + (p1.hashCode() == p1.hashCode()));
        System.out.println("\t2) Should generate true: " + (p1.hashCode() == p5.hashCode()));
        System.out.println("\t3) Should generate false: " + (p1.hashCode() == p2.hashCode()));
        System.out.println("\t4) Should generate false: " + (p1.hashCode() == p3.hashCode()));
        System.out.println("\t5) Should generate false: " + (p1.hashCode() == p4.hashCode()));
        System.out.println("\t6) Should generate false: " + (p2.hashCode() == p3.hashCode()));
        System.out.println("\t7) Should generate false: " + (p2.hashCode() == p4.hashCode()));
        System.out.println("\t8) Should generate false: " + (p3.hashCode() == p4.hashCode()));

    }
}
