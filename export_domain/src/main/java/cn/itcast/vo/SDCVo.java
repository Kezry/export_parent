package cn.itcast.vo;

/**
 * 封装三个字段
 * 装运港(shipmentPort)、目的港(destinationPort)、收货人(consignee)
 * @author lwj
 */
public class SDCVo {

    private String shipmentPort;
    private String destinationPort;
    private String consignee;


    public SDCVo() {
    }

    public SDCVo(String shipmentPort, String destinationPort, String consignee) {
        this.shipmentPort = shipmentPort;
        this.destinationPort = destinationPort;
        this.consignee = consignee;
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

    @Override
    public String toString() {
        return "SDCVo{shipmentPort = " + shipmentPort + ", destinationPort = " + destinationPort + ", consignee = " + consignee + "}";
    }
}
