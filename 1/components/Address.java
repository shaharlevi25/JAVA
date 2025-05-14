/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;

public class Address {
    private int zip;
    private int street;

    /**
     * Constructs an Address with a ZIP code and a 6-digit street number.
     * Throws an exception if the street number is not exactly 6 digits.
     */
    public Address(int zip, int street)
    {
        if (street < 100000 || street > 999999) {
            throw new IllegalArgumentException("Street number must be exactly 6 digits");
        }
        this.zip = zip;
        this.street = street;
    }

    public int getStreet() {return street;}

    /**
     * Sets the street number of the address.
     * Throws an exception if the street number is not exactly 6 digits.
     */
    public void setStreet(int street) {
        if (street < 100000 || street > 999999) {
            throw new IllegalArgumentException("Street number must be exactly 6 digits");
        }
        this.street = street;
    }

    public int getZip() {return zip;}
    public void setZip(int zip) {this.zip = zip;}

    /** Returns a string representation of the address in the format "zip-street". */
    @Override
    public String toString() {
        return zip + "-" + street;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false; // שונה מסוג
        Address other = (Address) obj;
        return this.zip == other.zip && this.street == other.street;
    }
}
