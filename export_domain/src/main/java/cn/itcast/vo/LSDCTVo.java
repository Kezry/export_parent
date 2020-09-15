package cn.itcast.vo;

/**
 * 封装三个字段
 * 信用证号(lcno)、装运港(shipmentPort)、目的港(destinationPort)、收货人(consignee)、运输方式(transportMode)
 * @author lwj
 */
public class LSDCTVo {

    private String lcno;
    private String shipmentPort;
    private String destinationPort;
    private String consignee;
    private String transportMode;


    public LSDCTVo() {
    }

    public LSDCTVo(String lcno, String shipmentPort, String destinationPort, String consignee, String transportMode) {
        this.lcno = lcno;
        this.shipmentPort = shipmentPort;
        this.destinationPort = destinationPort;
        this.consignee = consignee;
        this.transportMode = transportMode;
    }

    /**
     * 获取
     * @return lcno
     */
    public String getLcno() {
        return lcno;
    }

    /**
     * 设置
     * @param lcno
     */
    public void setLcno(String lcno) {
        this.lcno = lcno;
    }

    /**
     * 获取
     * @return shipmentPort
     */
    public String getShipmentPort() {
        return shipmentPort;
    }

    /**
     * 设置
     * @param shipmentPort
     */
    public void setShipmentPort(String shipmentPort) {
        this.shipmentPort = shipmentPort;
    }

    /**
     * 获取
     * @return destinationPort
     */
    public String getDestinationPort() {
        return destinationPort;
    }

    /**
     * 设置
     * @param destinationPort
     */
    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    /**
     * 获取
     * @return consignee
     */
    public String getConsignee() {
        return consignee;
    }

    /**
     * 设置
     * @param consignee
     */
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    /**
     * 获取
     * @return transportMode
     */
    public String getTransportMode() {
        return transportMode;
    }

    /**
     * 设置
     * @param transportMode
     */
    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    @Override
    public String toString() {
        return "LSDCTVo{lcno = " + lcno + ", shipmentPort = " + shipmentPort + ", destinationPort = " + destinationPort + ", consignee = " + consignee + ", transportMode = " + transportMode + "}";
    }
}
