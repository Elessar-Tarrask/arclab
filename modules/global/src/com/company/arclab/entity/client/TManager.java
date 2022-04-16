package com.company.arclab.entity.client;

import com.haulmont.cuba.core.entity.annotation.Extends;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;

@javax.persistence.DiscriminatorValue("TMANAGER")
@Entity(name = "arclab_TManager")
@Extends(User.class)
public class TManager extends User {
    private static final long serialVersionUID = 5812899565957949150L;

    @Column(name = "MANAGER_NETWORK_NAME")
    private String managerNetworkName;

    @Column(name = "EMPLOYEE_ID")
    private String employeeId;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "DIGITAL_SIGN")
    private String digitalSign;

    public String getDigitalSign() {
        return digitalSign;
    }

    public void setDigitalSign(String digitalSign) {
        this.digitalSign = digitalSign;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getManagerNetworkName() {
        return managerNetworkName;
    }

    public void setManagerNetworkName(String managerNetworkName) {
        this.managerNetworkName = managerNetworkName;
    }
}