package com.sb3.sbsj.api.spot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SpotApiResponse {
    @JsonProperty("response")
    private Response response;

    @Data
    public static class Response {
        @JsonProperty("header")
        private Header header;

        @JsonProperty("body")
        private Body body;
    }

    @Data
    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;

        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    @Data
    public static class Body {
        @JsonProperty("items")
        private Items items;
    }

    @Data
    public static class Items {
        @JsonProperty("item")
        private List<Item> item; // List<Item>으로 변경
    }

    @Data
    public static class Item {
        @JsonProperty("title")
        private String title;

        @JsonProperty("tel")
        private String tel;

        @JsonProperty("addr1")
        private String addr1;

        @JsonProperty("zipcode")
        private String zipcode;

        @JsonProperty("firstimage")
        private String firstimage;

        @JsonProperty("mapx")
        private String mapx;

        @JsonProperty("mapy")
        private String mapy;

        @JsonProperty("areacode")
        private String areacode;

        @JsonProperty("contenttypeid")
        private String contenttypeid;
    }

}



