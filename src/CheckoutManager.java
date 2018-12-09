import java.util.Hashtable;

public class CheckoutManager {
    // fields

    // 8 queues for tracking hashcodes and seats
    public BuyerQueue<Integer> reserve;
    public BuyerQueue<Integer> reserveVIP;
    public BuyerQueue<Integer> checkout;
    public BuyerQueue<Integer> checkoutVIP;
    public BuyerQueue<Integer> cancel;
    public BuyerQueue<Integer> cancelVIP;
    public BuyerQueue<Seat> seatQueue;
    public BuyerQueue<Seat> seatQueueVIP;

    // a hashtable for linking seats and their corresponding checkout windows' hashcodes
    public Hashtable<Integer, Seat> seatHashTable;

    // 2D array for all the seats
    private Seat[][] seatArray;


    // constructors
    public CheckoutManager(int size, int rowsVIP){
        seatArray = new Seat[size][size];
        for (int i = 0; i < seatArray.length; i++){
            for (int j = 0; j< seatArray[i].length; j++){
                String row = "";
                for(int k = 0; k <= j/25; k++){
                    row += (char)(j%25+65);
                }
                // if the seat is in the first rowsVIP rows, then make that seat VIP
                Seat temp = new Seat(row, i, (j < rowsVIP) ? true : false);
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

        seatHashTable = new Hashtable<>();

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

        seatHashTable = new Hashtable<>();
    }
    public CheckoutManager(){
        this(25, 0);
    }
    public CheckoutManager(int size) { this(size, 0); }

    // getter for seatArray
    public Seat[][] getSeatArray(){
        return seatArray;
    }

    // sends request for a seat to be reserved; stores the seat and its checkout window in correct queues
    public void startReservation(Integer newWindowHashcode, Seat selectedSeat){
        // seats' requests sent to different queues depending on whether VIP or not
        if(selectedSeat.isVIP()){
            reserveVIP.add(newWindowHashcode);
            seatQueueVIP.add(selectedSeat);
        }else{
            reserve.add(newWindowHashcode);
            seatQueue.add(selectedSeat);
        }
    }

    // processes reservation request; sets seat to unavailable, links the seat and its checkout window in hashtable, removes it from reserve queue
    private void reserve(){
        // differentiate between VIP or not
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

    // sends request for seats to be purchased after having been reserved, stores seat's window's hashcode in correct checkout queue
    public void startCheckout(Integer windowHashCode){
        // VIP or not
        if(seatHashTable.get(windowHashCode).isVIP()){
            checkoutVIP.add(windowHashCode);
        }else{
            checkout.add(windowHashCode);
        }
    }

    // completes purchase request by removing the seat's window's hashcode from checkout queue startCheckout() added it to
    // does not change the seats availability status so the seat stays permanently unavailable
    private void checkout(){
        // vip or not
        if(!checkoutVIP.isEmpty()){
            checkoutVIP.poll();
        }else if (!checkout.isEmpty()){
            checkout.poll();
        }
    }

    // sends a request to cancel a reservation; adds seat's window's hashcode to cancel queue
    public void startCancel(Integer windowHashCode){
        // vip or not
        if(seatHashTable.get(windowHashCode).isVIP()){
            cancelVIP.add(windowHashCode);
        }else{
            cancel.add(windowHashCode);
        }
    }

    // cancels a reservation for a seat by setting its availability to true again and removes it from the queue for being canceled
    private void cancel(){
        if(!cancelVIP.isEmpty()){
            seatHashTable.get(cancelVIP.poll()).setAvailable(true);
        }else if (!cancel.isEmpty()){
            seatHashTable.get(cancel.poll()).setAvailable(true);
        }
    }

    // method that will be run concurrently to other processes to cycle through queues and process requests for reserving, purchasing or canceling
    public void update(){
        reserve();
        checkout();
        cancel();
    }


}
