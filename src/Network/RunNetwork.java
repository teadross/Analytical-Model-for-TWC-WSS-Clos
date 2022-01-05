package Network;

import General.Constant;

public class RunNetwork {
    public void runNetwork(){
        Network Clos = new Network("WSS-Clos-Network", 0);
        Clos.generateTopology();

        double B = Clos.getB();
        double B_old = 1;
        int times = 0;
        System.out.println("Initial Down.");

//        while (Math.abs(B-B_old) > Constant.epison){
        while (times < 100){
            times++;
            System.out.println("Time:"+times);

            B_old = B;

            for (Link link: Clos.getLinkList2()){
                link.calculatingLoad();
                link.calculatingB();
            }

            for (Route route: Clos.getRouteList2()) {
                route.calculatingPb();
            }

            B = Clos.getB();
            System.out.println("B:"+B);
        }
        System.out.println("Final Result of B:"+B);
    }
}
