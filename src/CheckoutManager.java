public class CheckoutManager {
    private BuyerQueue<Session> normalQ;
    private BuyerQueue<Session> VIPQ;
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
        if (sessionId.getStatus().equals("VIP")){
            VIPQ.add(sessionId);
        }
        normalQ.add(sessionId);
        return true;
    }

    public boolean completePurchase(Session sessionId, String seat){

    }

    public Seat[][] getSeats(){
        return seats;
        //TODO: graphics that shows table with availability - grey/white/red/green
    }

}
