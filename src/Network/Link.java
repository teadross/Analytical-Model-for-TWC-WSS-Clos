package Network;

import General.CommonObject;
import General.Constant;

import java.math.BigDecimal;
import java.util.Random;

public class Link extends CommonObject {
    private Network associatedNetwork = null;					// network that the link belongs to

    private double Load;
    private double B;

    public Link(String name, int index, Node nodeA, Node nodeB, Network associatedNetwork) {
        super(name, index);
        this.associatedNetwork = associatedNetwork;

        this.Load = 0.0;
        this.B = 0.0;
    }

    public void calculatingB() {
        BigDecimal tmp = this.Erlang(this.Load,Constant.W);
        this.B = tmp.doubleValue();
//        System.out.println("(link)"+this.getName()+": B = "+this.B+".");
    }

    public void calculatingLoad() {
        BigDecimal sum = BigDecimal.valueOf(0.0);
        for(Route route: this.associatedNetwork.getRouteList2()){
            if(route.isLinkinRoute(this)){
                BigDecimal coefficient = BigDecimal.valueOf(Constant.Rho);
                BigDecimal numerator = BigDecimal.valueOf(1.0).subtract(BigDecimal.valueOf(route.getPb()));
                BigDecimal denumerator = BigDecimal.valueOf(1.0).subtract(BigDecimal.valueOf(this.B));
                sum = sum.add(coefficient.multiply(numerator.divide(denumerator,15,BigDecimal.ROUND_HALF_DOWN)));
            }
        }
        this.Load = sum.doubleValue();
//        System.out.println("(link)"+this.getName()+": Load = "+this.Load+".");
    }

    public double getLoad() {
        return Load;
    }

    public double getB() {
        return B;
    }

    public BigDecimal Erlang(double rho, int x){
        BigDecimal rhox = BigDecimal.valueOf(Math.pow(rho,x));
        BigDecimal xfac = this.getFactorial(x);
        BigDecimal rhox_xfac = rhox.divide(xfac,15,BigDecimal.ROUND_HALF_DOWN);
        BigDecimal sum = BigDecimal.valueOf(0.0);
        for (int k = 0; k <= x; k++) {
            BigDecimal rhox_tmp = BigDecimal.valueOf(Math.pow(rho,k));
            BigDecimal xfac_tmp = this.getFactorial(k);
            BigDecimal rhox_xfac_tmp = rhox_tmp.divide(xfac_tmp,15,BigDecimal.ROUND_HALF_DOWN);
            sum = sum.add(rhox_xfac_tmp);
        }
        return rhox_xfac.divide(sum,15,BigDecimal.ROUND_HALF_DOWN);
    }

    public BigDecimal getFactorial(double number) {
        if (number <= 1)
            return BigDecimal.valueOf(1);
        else
            return BigDecimal.valueOf(number).multiply(getFactorial(number - 1));
    }

    public BigDecimal getFactorial(double number1, double number2) {
        return (getFactorial(number1)).divide(getFactorial(number2),15,BigDecimal.ROUND_HALF_DOWN);
    }
}
