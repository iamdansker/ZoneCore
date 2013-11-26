/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.jeppes.ZoneCore.Exceptions;

/**
 *
 * @author jeppe
 */
public class NotBooleanException extends Exception{
    private String booleanString;
    public NotBooleanException(String booleanString){
        this.booleanString = booleanString;
    }
    public String getBooleanString() {
        return booleanString;
    }
}
