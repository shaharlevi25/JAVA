/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;

public class NonStandartPackage extends Package {

        private int width;
        private int length;
        private int height;

        public NonStandartPackage(Priority priority, Address senderAddress, Address destinationAddress, int width, int length, int height){
            super(priority, senderAddress, destinationAddress);
            this.width = width;
            this.length = length;
            this.height = height;
        }

    public int getwidth() {return width;}
    public void setWeight(int width) {this.width = width;}
    public int getLength() {return length;}
    public void setLength(int length) {this.length = length;}
    public int getHeight() {return height;}
    public void setHeight(int height) {this.height = height;}

    @Override
        public String toString() {
            return "NonStandardPackage [" + super.toString() + ", length=" + length + ", width=" + width + ", height="+ height + "]";
        }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NonStandartPackage other = (NonStandartPackage) obj;
        return this.length == other.length && this.height == other.height && this.width == other.width;
    }

}
