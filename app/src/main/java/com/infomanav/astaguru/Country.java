package com.infomanav.astaguru;

public class Country {
  
 String code = null;
 String name = null;
 String artistid = null;
 boolean selected = false;

 public String getArtistid() {
  return artistid;
 }

 public void setArtistid(String artistid) {
  this.artistid = artistid;
 }

 public Country(String artistid, String code, String name, boolean selected) {
  super();
  this.artistid = artistid;
  this.code = code;
  this.name = name;
  this.selected = selected;
 }
  
 public String getCode() {
  return code;
 }
 public void setCode(String code) {
  this.code = code;
 }
 public String getName() {
  return name;
 }
 public void setName(String name) {
  this.name = name;
 }
 
 public boolean isSelected() {
  return selected;
 }
 public void setSelected(boolean selected) {
  this.selected = selected;
 }
  
}
