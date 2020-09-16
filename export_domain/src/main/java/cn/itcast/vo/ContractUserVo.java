package cn.itcast.vo;

import java.io.Serializable;

public class ContractUserVo implements Serializable {

    private Integer state;
    private String email;

    public ContractUserVo() {
    }

    public ContractUserVo(Integer state, String email) {
        this.state = state;
        this.email = email;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ContractUserVo{" +
                "state=" + state +
                ", email='" + email + '\'' +
                '}';
    }
}
