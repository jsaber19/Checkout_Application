public class CheckoutManager {
    private BuyerQueue<Session> normalQ;
    private BuyerQueue<Session> VIPQ;
    private BuyerQueue<Session> transactionQ;
    private Seat[][] seats;

    public CheckoutManager(){
        seats = new Seat[25][25];
        for (int i = 0; i < seats.length; i++){
            for (int j = 0; j<seats[i].length; j++){
                String row = "";
                for(int k = 0; k <= j/25; k++){
                    row += (char)(j%25+41);
                }
                seats[i][j] = new Seat(row, i);
            }
        }

        normalQ = new BuyerQueue<Session>();
        VIPQ = new BuyerQueue<Session>();
    }

    public CheckoutManager(Seat[][] seats){
        this.seats = seats;

        normalQ = new BuyerQueue<Session>();
        VIPQ = new BuyerQueue<Session>();
    }

    public boolean add(Session sessionId){
        if (sessionId.getIsVIP()){
            VIPQ.add(sessionId);
        }
        normalQ.add(sessionId);
        return true;
    }

    public boolean completePurchase(Session sessionId, String seat){
        return true;
    }

    public Seat[][] getSeats(){
        return seats;
        //TODO: graphics that shows table with availability - grey/white/red/green
    }

    public boolean isFull(){
        for (Seat[] arr : seats){
            for (Seat s : arr){
                if (s.getAvailable()) return false;
            }
        }
        return true;
    }

    public boolean reserveSeat(){
        return true;
    }

    // TODO: main class with main app window that will open session windows through some button – will also take care of reserving seats
    // TODO: while loop for session making button in main class whose condition for running is that there are still available seats

}
