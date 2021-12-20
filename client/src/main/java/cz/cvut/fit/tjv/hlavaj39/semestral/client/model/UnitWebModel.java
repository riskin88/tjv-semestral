package cz.cvut.fit.tjv.hlavaj39.semestral.client.model;

public class UnitWebModel extends  UnitDto{
//    private boolean numberCollision;
//    private boolean successfulCreation = true;
    public enum STATE {
        undefined,
        success,
        numberCollision,
        noNumber
    }

    private STATE errorState = STATE.success;

    public UnitWebModel() {
    }

    public UnitWebModel(STATE errorState, UnitDto unitDto) {
        super(unitDto.getNumber(), unitDto.getName(), unitDto.getLocation());
        this.errorState = errorState;
    }

    public STATE getErrorState() {
        return errorState;
    }

    public void setErrorState(STATE errorState) {
        this.errorState = errorState;
    }

//    public UnitWebModel(boolean numberCollision, boolean successfulCreation, UnitDto unitDto) {
//        super(unitDto.getNumber(), unitDto.getName(), unitDto.getLocation());
//        this.numberCollision = numberCollision;
//        this.successfulCreation = successfulCreation;
//    }


//
//    public boolean isNumberCollision() {
//        return numberCollision;
//    }
//
//    public void setNumberCollision(boolean numberCollision) {
//        this.numberCollision = numberCollision;
//    }
//
//    public boolean isSuccessfulCreation() {
//        return successfulCreation;
//    }
//
//    public void setSuccessfulCreation(boolean successfulCreation) {
//        this.successfulCreation = successfulCreation;
//    }
}
