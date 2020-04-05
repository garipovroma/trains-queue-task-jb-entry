package base;

public class Train implements Comparable {
    private int id;
    private int arrivingTime;
    private int unloadingTime;
    private int payment;
    public Train() {}
    public Train(int id, int arrivingTime, int unloadingTime, int payment) {
        this.id = id;
        this.arrivingTime = arrivingTime;
        this.unloadingTime = unloadingTime;
        this.payment = payment;
    }
    public int getId() {
        return id;
    }
    public int getArrivingTime() {
        return arrivingTime;
    }
    public int getUnloadingTime() {
        return unloadingTime;
    }
    public int getPayment() {
        return payment;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Train) {
            Train that = (Train) o;
            int x1 = this.arrivingTime;
            int x2 = that.getArrivingTime();
            int y1 = this.unloadingTime;
            int y2 = that.getUnloadingTime();
            return (x1 < x2 || x1 == x2 && y1 < y2) ? -1 : 1;
        }
        return 0;
    }
}
