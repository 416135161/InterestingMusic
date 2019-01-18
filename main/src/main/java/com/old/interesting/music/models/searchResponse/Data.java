/**
  * Copyright 2019 bejson.com 
  */
package com.old.interesting.music.models.searchResponse;
import java.util.List;

/**
 * Auto-generated: 2019-01-18 9:46:58
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private List<Aggregation> aggregation;
    private String tab;
    private List<Info> info;
    private int correctiontype;
    private long timestamp;
    private int allowerr;
    private int total;
    private int istag;
    private int istagresult;
    private int forcecorrection;
    private String correctiontip;
    public void setAggregation(List<Aggregation> aggregation) {
         this.aggregation = aggregation;
     }
     public List<Aggregation> getAggregation() {
         return aggregation;
     }

    public void setTab(String tab) {
         this.tab = tab;
     }
     public String getTab() {
         return tab;
     }

    public void setInfo(List<Info> info) {
         this.info = info;
     }
     public List<Info> getInfo() {
         return info;
     }

    public void setCorrectiontype(int correctiontype) {
         this.correctiontype = correctiontype;
     }
     public int getCorrectiontype() {
         return correctiontype;
     }

    public void setTimestamp(long timestamp) {
         this.timestamp = timestamp;
     }
     public long getTimestamp() {
         return timestamp;
     }

    public void setAllowerr(int allowerr) {
         this.allowerr = allowerr;
     }
     public int getAllowerr() {
         return allowerr;
     }

    public void setTotal(int total) {
         this.total = total;
     }
     public int getTotal() {
         return total;
     }

    public void setIstag(int istag) {
         this.istag = istag;
     }
     public int getIstag() {
         return istag;
     }

    public void setIstagresult(int istagresult) {
         this.istagresult = istagresult;
     }
     public int getIstagresult() {
         return istagresult;
     }

    public void setForcecorrection(int forcecorrection) {
         this.forcecorrection = forcecorrection;
     }
     public int getForcecorrection() {
         return forcecorrection;
     }

    public void setCorrectiontip(String correctiontip) {
         this.correctiontip = correctiontip;
     }
     public String getCorrectiontip() {
         return correctiontip;
     }

}