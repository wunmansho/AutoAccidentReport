package com.auto.accident.report.objects;

/**
 * Created by myron on 4/6/2018.
 */

public class VehicleManifest {
    private int VMU_ID;
    private int VMAID_ID;
    private int VMIV_ID;
    private int VMIP_ID;
    private int VM_CUID;
    private String VM_CDATE;
    private int VM_UUID;
    private String VM_UDATE;

    // this must be here to make this Dao Statement work InvolvedParty involvedparty = new InvolvedParty();
    public VehicleManifest() {

    }
    // Result of generate Constructor the InvolvedParty constructor

    public VehicleManifest(int VMU_ID, int VMAID_ID, int VMIV_ID, int VMIP_ID, int VM_CUID, String VM_CDATE, int VM_UUID, String VM_UDATE) {
        this.VMU_ID = VMU_ID;
        this.VMAID_ID = VMAID_ID;
        this.VMIV_ID = VMIV_ID;
        this.VMIP_ID = VMIP_ID;
        this.VM_CUID = VM_CUID;
        this.VM_CDATE = VM_CDATE;
        this.VM_UUID = VM_UUID;
        this.VM_UDATE = VM_UDATE;
    }


// Generate Setters

    public int getVMU_ID() {
        return VMU_ID;
    }

    public void setVMU_ID(int VMU_ID) {
        this.VMU_ID = VMU_ID;
    }

    public int getVMAID_ID() {
        return VMAID_ID;
    }

    public void setVMAID_ID(int VMAID_ID) {
        this.VMAID_ID = VMAID_ID;
    }

    public int getVMIV_ID() {
        return VMIV_ID;
    }

    public void setVMIV_ID(int VMIV_ID) {
        this.VMIV_ID = VMIV_ID;
    }

    public int getVMIP_ID() {
        return VMIP_ID;
    }

    public void setVMIP_ID(int VMIP_ID) {
        this.VMIP_ID = VMIP_ID;
    }


    // Generate Getters

    public int getVM_CUID() {
        return VM_CUID;
    }

    public void setVM_CUID(int VM_CUID) {
        this.VM_CUID = VM_CUID;
    }

    public String getVM_CDATE() {
        return VM_CDATE;
    }

    public void setVM_CDATE(String VM_CDATE) {
        this.VM_CDATE = VM_CDATE;
    }

    public int getVM_UUID() {
        return VM_UUID;
    }

    public void setVM_UUID(int VM_UUID) {
        this.VM_UUID = VM_UUID;
    }

    public String getVM_UDATE() {
        return VM_UDATE;
    }

    public void setVM_UDATE(String VM_UDATE) {
        this.VM_UDATE = VM_UDATE;
    }
}