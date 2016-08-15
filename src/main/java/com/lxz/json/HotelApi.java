package com.lxz.json;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.lxz.util.JsonUtil;
import jodd.io.NetUtil;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;

/**
 * @author: xiaole Date: 14-6-24 Time: 下午8:29
 * @version: \$Id$
 */
public class HotelApi implements Serializable {
    private String vendor;
    private int price;
    private int oprice;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getOprice() {
        return oprice;
    }

    public void setOprice(int oprice) {
        this.oprice = oprice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static void main(String[] args) throws Exception {
        String url = "xxx";
        String content = NetUtil.downloadString(url);

        JsonNode jsonNode = JsonUtil.toJsonNode(content);

        List<JsonNode> prices = jsonNode.findValues("prices");

        JsonNode pricesJsonNode = prices.get(0);

        System.out.println(JsonUtil.encode(pricesJsonNode));


        List<HotelApi> hotelList = Lists.newArrayList();

        Iterator<JsonNode> priceIt = pricesJsonNode.iterator();

        while (priceIt.hasNext()){
            hotelList.add(JsonUtil.jsonNodeToT(priceIt.next(),HotelApi.class));
        }

        for(HotelApi hotelApi: hotelList){
            System.out.println(hotelApi.getVendor());
        }

        System.out.println(JsonUtil.encode(hotelList));


        JsonNode vendors = jsonNode.findValue("vendors");
        System.out.println(vendors);

        Iterator<String> vendorIds = vendors.getFieldNames();
        while (vendorIds.hasNext()){
            String vendorId = vendorIds.next();
            System.out.println(vendorId);

            JsonNode vendorNode = vendors.findValue(vendorId);
            System.out.println(vendorNode);
        }

        System.out.println("=========================================================================================");

        Iterator<Map.Entry<String, JsonNode>> vendorss = vendors.getFields();

        while (vendorss.hasNext()){
            Map.Entry<String, JsonNode> node = vendorss.next();
            System.out.println(node.getKey()+"->"+node.getValue());
        }
    }
}
