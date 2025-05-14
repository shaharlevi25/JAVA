/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;

public class SmallPackage extends Package{

    private boolean acknowledge;

    public SmallPackage(Priority priority, Address senderAddress, Address destinationAddress, boolean acknowledge){
        super(priority, senderAddress, destinationAddress);
        this.acknowledge = acknowledge;
    }
    public boolean isAcknowledge() {return acknowledge;}
    public void setAcknowledge(boolean acknowledge) {this.acknowledge = acknowledge;}

    @Override
    public String toString() {
        return "SmallPackage [" + super.toString() + ", acknowledge = " + acknowledge + "]";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false; // שונה מסוג
        SmallPackage other = (SmallPackage) obj;
        return this.acknowledge == other.acknowledge;
    }
}
