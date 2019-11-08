package com.danbro.gmall.api.po;

import lombok.Data;

import java.io.Serializable;
@Data
public class OmsCompanyAddressPo implements Serializable {

    private Long id;
    private String addressName;
    private boolean sendStatus;
    private boolean receiveStatus;
    private String name;
    private String phone;
    private String province;
    private String city;
    private String region;
    private String detailAddress;


}
