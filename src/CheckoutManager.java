import java.util.Hashtable;

public class CheckoutManager {
    public BuyerQueue<Integer> reserve;
    public BuyerQueue<Integer> reserveVIP;
    public BuyerQueue<Integer> checkout;
    public BuyerQueue<Integer> checkoutVIP;
    public BuyerQueue<Integer> cancel;
    public BuyerQueue<Integer> cancelVIP;
    public BuyerQueue<Seat> seatQueue;
    public BuyerQueue<Seat> seatQueueVIP;

    public Hashtable<Integer, Seat> seatHashTable;

    private Seat[][] seatArray;


    // constructors
    public CheckoutManager(int size){
        seatArray = new Seat[size][size];
        for (int i = 0; i < seatArray.length; i++){
            for (int j = 0; j< seatArray[i].length; j++){
                String row = "";
                for(int k = 0; k <= j/25; k++){
                    row += (char)(j%25+65);
                }
                Seat temp = new Seat(row, i);
                seatArray[i][j] = temp;
                temp.setRowNumericalRepresentation(j);
            }
        }
        reserve = new BuyerQueue<>();
        reserveVIP = new BuyerQueue<>();
        checkout = new BuyerQueue<>();
        checkoutVIP = new BuyerQueue<>();
        cancel = new BuyerQueue<>();
        cancelVIP = new BuyerQueue<>();
        seatQueue = new BuyerQueue<>();
        seatQueueVIP = new BuyerQueue<>();

    }
    public CheckoutManager(Seat[][] seatArray){
        this.seatArray = seatArray;
        reserve = new BuyerQueue<>();
        reserveVIP = new BuyerQueue<>();
        checkout = new BuyerQueue<>();
        checkoutVIP = new BuyerQueue<>();
        cancel = new BuyerQueue<>();
        cancelVIP = new BuyerQueue<>();
        seatQueue = new BuyerQueue<>();
        seatQueueVIP = new BuyerQueue<>();
    }

    public CheckoutManager(){
        this(25);
    }


    public Seat[][] getSeatArray(){
        return seatArray;
    }

    public void startReservation(Integer newWindowHashcode, Seat selectedSeat){
        if(selectedSeat.isVIP()){
            reserveVIP.add(newWindowHashcode);
            seatQueueVIP.add(selectedSeat);
        }else{
            reserve.add(newWindowHashcode);
            seatQueue.add(selectedSeat);
        }
    }

    private void reserve(){
        if(!reserveVIP.isEmpty()){
            Seat temp = seatQueueVIP.poll();
            seatHashTable.put(reserveVIP.poll(), temp);
            temp.setAvailable(false);
        }else if (!reserve.isEmpty()){
            Seat temp = seatQueue.poll();
            seatHashTable.put(reserve.poll(), temp);
            temp.setAvailable(false);
        }
    }

    public void startCheckout(Integer windowHashCode){
        if(seatHashTable.get(windowHashCode).isVIP()){
            checkoutVIP.add(windowHashCode);
        }else{
            checkout.add(windowHashCode);
        }
    }

    private void checkout(){
        if(!checkoutVIP.isEmpty()){
            checkoutVIP.poll();
        }else if (!checkout.isEmpty()){
            checkout.poll();
        }
    }

    public void startCancel(Integer windowHashCode){
        if(seatHashTable.get(windowHashCode).isVIP()){
            cancelVIP.add(windowHashCode);
        }else{
            cancel.add(windowHashCode);
        }
    }

    private void cancel(){
        if(!cancelVIP.isEmpty()){
            seatHashTable.get(cancelVIP.poll()).setAvailable(false);
        }else if (!cancel.isEmpty()){
            seatHashTable.get(cancel.poll()).setAvailable(false);
        }
    }


}
