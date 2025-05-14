/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;

import java.util.Objects;

public class Tracking {
    private int time;
    private Node node;
    private Status status;

    public Tracking(int Time, Node node, Status status)
    {

        this.time = Time;
        this.node = node;
        this.status = status;

    }
    public int getTime() {return time;}
    public void setTime(int time) {this.time = time;}
    public Node getNode() {return node;}
    public void setNode(Node node) {this.node = node;}
    public Status getStatus() {return status;}
    public void setStatus(Status status) {this.status = status;}


    /**
     * Returns a formatted string representing the tracking event based on the type of node involved.
     * Formats include:
     * - Customer: if the node is null.
     * - Van: includes van ID and status.
     * - StandardTruck: includes truck ID and status.
     * - NonStandardTruck: includes truck ID and status.
     * - Hub: includes branch name and status.
     * - Branch: includes branch ID and status.
     */
    @Override
    public String toString() {
        if (node == null) {
            return "  "+time + ": Customer , status=" + status;
        }
        else if (node instanceof Van) {
            Van van = (Van) node;
            return "  "+time + ": Van " + van.getTruckID() + ", status=" + status;
        }
        else if (node instanceof StandardTruck) {
            StandardTruck standardTruck = (StandardTruck) node;
            return "  "+ time + ": StandardTruck " + standardTruck.getTruckID() + ", status=" + status;
        }
        else if( node instanceof  NonStandardTruck) {
            NonStandardTruck nonStandardTruck=(NonStandardTruck)node;
            return "  "+time + ": NonStandardTruck " + nonStandardTruck.getTruckID() + ", status=" + status;

        }
        else if (node instanceof Hub) {
            Hub hub =(Hub)node;
            return "  "+time + ": " + hub.getBranchName() + ", status=" + status;

        }
        else if (node instanceof  Branch) {
            Branch branch =(Branch)node;
            return "  "+ time + ": Branch " + branch.getBranchId() + ", status=" + status;
        }

        return "";

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tracking other = (Tracking) obj;
        return time == other.time && Objects.equals(node, other.node) && status == other.status;
    }

}
