package Network;

import General.CommonObject;
import General.Constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Route extends CommonObject {
    private ArrayList<Link> linkList = null;

    private double Pb;


    public Route(String name, int index, Node nodeSrc, Node nodeMid, Node nodeDst, Network associatedNetwork) {
        super(name, index);
        this.linkList = new ArrayList<Link>();

        linkList.add((associatedNetwork).findLink(nodeSrc,nodeMid));
        linkList.add((associatedNetwork).findLink(nodeMid,nodeDst));

        Pb = 0.0;
    }

    public void calculatingPb() {
        BigDecimal product = BigDecimal.valueOf(1.0);
        for(Link link: this.getLinkList()){
            BigDecimal tmp = BigDecimal.valueOf(1.0).subtract(BigDecimal.valueOf(link.getB()));
            product = product.multiply(tmp);
        }
        product = BigDecimal.valueOf(1.0).subtract(product);
        this.Pb = product.doubleValue();
//        System.out.println("(route)"+this.getName()+": Pb = "+Pb+".");
    }

    public double getPb() {
        return this.Pb;
    }

    public ArrayList<Link> getLinkList() {
        return this.linkList;
    }

    public boolean isLinkinRoute (Link link) {
        return this.linkList.contains(link);
    }
}
