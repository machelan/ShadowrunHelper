package tech.sqlabs.shadowrunhelper.character;

public class character {
    private int fID;
    private int fAGE;
    private String fNAME;
    private String fNATIONALITY;
    private String fGENDER;
    private String fMETATYPE;

    public character() {}
    public character(int AGE, String NAME, String NATIONALITY, String GENDER, String METATYPE) {
        super();
        this.fAGE = AGE;
        this.fNAME = NAME;
        this.fNATIONALITY = NATIONALITY;
        this.fGENDER = GENDER;
        this.fMETATYPE = METATYPE;
    }

    public int getID(){
        return fID;
    }
    public void setID(int d){
        fID=d;
    }

    public int getAGE(){
        return fAGE;
    }
    public void setAGE(int d){
        fAGE=d;
    }

    public String getNAME(){
        return fNAME;
    }
    public void setNAME(String d){
        fNAME=d;
    }

    public String getGENDER(){
        return fGENDER;
    }
    public void setGENDER(String d){
        fGENDER=d;
    }

    public String getNATIONALITY(){
        return fNATIONALITY;
    }
    public void setNATIONALITY(String d){
        fNATIONALITY=d;
    }

    public String getMETATYPE(){
        return fMETATYPE;
    }
    public void setMETATYPE(String d){
        fMETATYPE=d;
    }

    @Override
    public String toString() {
        return "character <id:" + fID + ", name:" + fNAME + ", metatype:" + fMETATYPE +">";
    }
}
