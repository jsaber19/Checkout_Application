import java.util.ArrayList;

public class CheckoutManager {
    public BuyerQueue<Session> normalQ;
    public BuyerQueue<Session> VIPQ;
    private ArrayList<Session> allSessions = new ArrayList<>();
    private Seat[][] seats;


    // constructors
    public CheckoutManager(){
        seats = new Seat[25][25];
        for (int i = 0; i < seats.length; i++){
            for (int j = 0; j<seats[i].length; j++){
                String row = "";
                for(int k = 0; k <= j/25; k++){
                    row += (char)(j%25+65);
                }
                seats[i][j] = new Seat(row, i);
                seats[i][j].setRowNumericalRepresentation(j);
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

    // updates the session depending on its queued up action
    public void update() throws Exception{
        // checks whether session is VIP or not
        // TODO: put this in some while loop somewhere? this is the bulk of the logic for the actual queue use
        Session curr = null;
        if(!VIPQ.isEmpty()) {
             curr = VIPQ.poll();
        }else if(!normalQ.isEmpty()){
            curr = normalQ.poll();
        }

        // completes the queued up action
        switch (curr.getActionType()){
            case RESERVING:
                reserveSeat(curr);
                break;
            case PURCHASING:
                completePurchase(curr);
                break;
            case CANCELING:
                cancelReservation(curr);
                break;
            default:
                throw new Exception("No action set in current session");
        }
    }

    // tracks session so checkoutmanager object can communicate with windows
    public int add(Session session){
        allSessions.add(session);
        return allSessions.size() - 1;
    }
    public Session getSession(int id){
        return allSessions.get(id);
    }

    // puts session in either VIP or normal Q
    public boolean resolve(Session sessionId){
        if (sessionId.getIsVIP()){
            VIPQ.add(sessionId);
        }
        normalQ.add(sessionId);
        return true;
    }


    public Seat[][] getSeats(){
        return seats;
    }

    public boolean isFull(){
        for (Seat[] arr : seats){
            for (Seat s : arr){
                if (s.getAvailable()) return false;
            }
        }
        return true;
    }

    public boolean reserveSeat(Session sessionId){
        sessionId.seat.setAvailable(false);
        return true;
    }
    public boolean completePurchase(Session sessionId){
        //TODO nothing because this is complete
        return true;
    }
    public boolean cancelReservation(Session sessionId){
        sessionId.seat.setAvailable(true);
        return true;
    }

    // TODO: main class with main app window that will open session windows through some button – will also take care of reserving seats
    // TODO: while loop for session making button in main class whose condition for running is that there are still available seats

}
