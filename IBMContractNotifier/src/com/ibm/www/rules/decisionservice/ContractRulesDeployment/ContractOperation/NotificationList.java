/**
 * NotificationList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation;

public class NotificationList  implements java.io.Serializable {
    private com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.Notification[] notifications;

    private java.lang.String senderEmailID;

    private java.lang.String senderEmailPassword;

    public NotificationList() {
    }

    public NotificationList(
           com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.Notification[] notifications,
           java.lang.String senderEmailID,
           java.lang.String senderEmailPassword) {
           this.notifications = notifications;
           this.senderEmailID = senderEmailID;
           this.senderEmailPassword = senderEmailPassword;
    }


    /**
     * Gets the notifications value for this NotificationList.
     * 
     * @return notifications
     */
    public com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.Notification[] getNotifications() {
        return notifications;
    }


    /**
     * Sets the notifications value for this NotificationList.
     * 
     * @param notifications
     */
    public void setNotifications(com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.Notification[] notifications) {
        this.notifications = notifications;
    }

    public com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.Notification getNotifications(int i) {
        return this.notifications[i];
    }

    public void setNotifications(int i, com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.Notification _value) {
        this.notifications[i] = _value;
    }


    /**
     * Gets the senderEmailID value for this NotificationList.
     * 
     * @return senderEmailID
     */
    public java.lang.String getSenderEmailID() {
        return senderEmailID;
    }


    /**
     * Sets the senderEmailID value for this NotificationList.
     * 
     * @param senderEmailID
     */
    public void setSenderEmailID(java.lang.String senderEmailID) {
        this.senderEmailID = senderEmailID;
    }


    /**
     * Gets the senderEmailPassword value for this NotificationList.
     * 
     * @return senderEmailPassword
     */
    public java.lang.String getSenderEmailPassword() {
        return senderEmailPassword;
    }


    /**
     * Sets the senderEmailPassword value for this NotificationList.
     * 
     * @param senderEmailPassword
     */
    public void setSenderEmailPassword(java.lang.String senderEmailPassword) {
        this.senderEmailPassword = senderEmailPassword;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NotificationList)) return false;
        NotificationList other = (NotificationList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.notifications==null && other.getNotifications()==null) || 
             (this.notifications!=null &&
              java.util.Arrays.equals(this.notifications, other.getNotifications()))) &&
            ((this.senderEmailID==null && other.getSenderEmailID()==null) || 
             (this.senderEmailID!=null &&
              this.senderEmailID.equals(other.getSenderEmailID()))) &&
            ((this.senderEmailPassword==null && other.getSenderEmailPassword()==null) || 
             (this.senderEmailPassword!=null &&
              this.senderEmailPassword.equals(other.getSenderEmailPassword())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getNotifications() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNotifications());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNotifications(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSenderEmailID() != null) {
            _hashCode += getSenderEmailID().hashCode();
        }
        if (getSenderEmailPassword() != null) {
            _hashCode += getSenderEmailPassword().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NotificationList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.ibm.com/rules/decisionservice/ContractRulesDeployment/ContractOperation", "notificationList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notifications");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notifications"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.ibm.com/rules/decisionservice/ContractRulesDeployment/ContractOperation", "notification"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderEmailID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "senderEmailID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderEmailPassword");
        elemField.setXmlName(new javax.xml.namespace.QName("", "senderEmailPassword"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
