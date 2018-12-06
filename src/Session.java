public class Session {
    private boolean isVIP;
    protected CheckoutManager manager;
    boolean hasInformation;
    private ActionType at;
    public Seat seat;

    public Session(CheckoutManager c){
        manager = c;
    }

    public void setIsVIP(boolean VIP){
        isVIP = VIP;
    }

    public void setActionType(ActionType at){
        this.at = at;
    }

    public ActionType getActionType(){
        return at;
    }

    public void setSeat(Seat s){
        seat = s;
    }

    public Seat getSeat(){
        return seat;
    }


    public boolean getIsVIP(){ return isVIP; }

    public boolean startReservation(){
        at = ActionType.RESERVING;
        manager.resolve(this);
        return true; // returns true
    }

    public boolean startCanceling(){
        at = ActionType.CANCELING;
        manager.resolve(this);
        return true; // returns true
    }

    public boolean startPurchasing(){
        at = ActionType.PURCHASING;
        manager.resolve(this);
        return true; // returns true
    }




}
