/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;

public class StandartPackage extends Package{

    private double weight;

    public StandartPackage(Priority priority, Address senderAddress, Address destinationAddress, double weight){
        super(priority, senderAddress, destinationAddress);
        this.weight = weight;
    }

    public double getWeight() {return weight;}
    public void setWeight(double weight) {this.weight = weight;}

    @Override
    public String toString() {
        return "StandardPackage [" + super.toString() + ", weight = " + weight+ "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false; // שונה מסוג
        StandartPackage other = (StandartPackage) obj;
        return this.weight == other.weight;
    }
}
